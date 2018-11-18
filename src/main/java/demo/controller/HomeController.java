package demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import demo.dao.BankTransactionException;
import demo.dao.Dao;
import demo.model.Account;
import demo.model.Client;
import demo.model.Data;
import demo.model.InfoProblem;
import demo.model.Login;
import demo.model.SendMoneyForm;

@Controller
public class HomeController {
	@Autowired
	private ModelAndView model;
	
	@Autowired
	private InfoProblem infoProblem;
	
//	@Autowired
//	private SessionFactory sessionFactory;
	
	@Autowired
	private Dao<?> dao;
	
	@RequestMapping("/admin/listAll")
	public ModelAndView handleRequest() throws Exception{
		List<Client> listClients =  (List<Client>) dao.getAll(Client.class);
		listClients.stream().forEach(c->c.getAccounts());//for update history	
		model.setViewName("allClients");
		model.addObject("clients", listClients);
		
		return model;
		
	}
	@RequestMapping(value = "/admin/showHistories", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView showClientHistoryForAdmin(
			@RequestParam(value = "idClient") Long id,
			@RequestParam(value = "idAccount", required = false) Long idAccount) {

		Account account = (Account) dao.getById(idAccount,Account.class);
		Client client = (Client) dao.getById(id,Client.class);	

		model.setViewName("showClient");
		model.addObject("client", client);
		
		return model.addObject("currentAccount", account);
		
	}

	
	//Spring Security see this :
	@RequestMapping(value = "/login", method= {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error) {

		model.setViewName("login");
		return model;

	}
		
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public String index() {
		//empty
		
		return "index";
	}
	

	@RequestMapping(value = "/admin/addAccount", method={RequestMethod.POST})
	public ModelAndView addAccountForAdmin(
			@RequestParam(value = "idClient") Long id) {

		 dao.newAccount(id,Client.class);
		Client client = (Client) dao.getById(id,Client.class);
		client.getAccounts();
		model.setViewName("showClient");			
		return model.addObject("client", client);

	}
	

	@RequestMapping(value = "/admin/deleteClient", method={RequestMethod.POST})
	public ModelAndView deleteClientForAdmin(
			@RequestParam(value = "id") Long id) {
		
		try {
			dao.remove(id,Client.class);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		List<Client> listClients =  (List<Client>) dao.getAll(Client.class);
		listClients.stream().forEach(c->c.getAccounts());//for update history	
		model.setViewName("allClients");
		model.addObject("clients", listClients);
		
		return model;
	}
	@RequestMapping(value = "/admin/showClient", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView showClientForAdmin(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "idAccount", required = false) Long idAccount) {
		Client client = (Client) dao.getById(id,Client.class);
		model.setViewName("showClient");
		if(idAccount != null) {
			Account account = (Account) dao.getById(idAccount,Account.class);
			model.addObject("currentAccount", account);
		}
		model.addObject("client", client);
		
		return model;
	}
	@RequestMapping(value = "/client/showClient", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView showClientForClient(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "idAccount", required = false) Long idAccount) {
		Client client = (Client) dao.getById(id,Client.class);
		model.setViewName("showClient");
		if(idAccount != null) {
			Account account = (Account) dao.getById(idAccount,Account.class);
			model.addObject("currentAccount", account);
		}
		model.addObject("client", client);
		
		return model;
	}
	@RequestMapping(value = "/admin/edit", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView addNewClientForAdmin(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "login.login") String login,
			@RequestParam(value = "login.password") String password,
			@RequestParam(value = "login.role") String role,
			@RequestParam(value = "data.firstName") String firsName,
			@RequestParam(value = "data.secondName") String secondName,
			@RequestParam(value = "data.lastName") String lastName
			
			)
	 throws Exception{
		Client client = new Client();
		client.setData(new Data(firsName,secondName, lastName));
		
		if(id == null && dao.findLoginInBd(login)) {
			model.setViewName("ClientForm");
			infoProblem.setCause("login isn't unique");
			model.addObject("error", infoProblem);
			client.getLogin().setRole(role);
			model.addObject("client", client);
			return model;
		}
		
		client.setLogin(new Login(login, password, role));	
		if(id == null) {	
			dao.add(client);
		}
		else {
			
			client.setId(id);
			dao.merge(client);
		}
			List<Client> listClients =  (List<Client>) dao.getAll(Client.class);

			listClients.stream().forEach(c->c.getAccounts());//for update history
			
			model.setViewName("allClients");
			model.addObject("clients", listClients);
		
		return model;
	}
	
	@RequestMapping(value = "/admin/newClient", method=RequestMethod.GET)
	public ModelAndView editUser(HttpServletRequest request) {

		model.setViewName("ClientForm");
		model.addObject("client", new Client());
		
		return model;
	}
	 @RequestMapping("/failureUrl")
	    public String fail(Model model) {
	        
	        model.addAttribute("message", "Incorrect password or name for  - " + getPrincipal());
	        
	        return "failureUrl";
	        
	    }
	@RequestMapping(value = "/admin/delete", method=RequestMethod.POST)
	public ModelAndView deleteUser(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "object", required = false) String nameObject) {
		Class<?> obj = dao.nameToObject(nameObject);
		
		dao.remove(id,obj);
		List<Client> listClients =  (List<Client>) dao.getAll(Client.class);
		listClients.stream().forEach(c->c.getAccounts());//for update history
		model.setViewName("allClients");
		model.addObject("clients", listClients);
		return model;
	}
	@RequestMapping(value = "/admin/deleteAccount", method=RequestMethod.POST)
	public ModelAndView deleteAccount(
			@RequestParam(value = "idClient") Long id,
			@RequestParam(value = "idAccount") Long idAccount) {

		Boolean result =dao.deleteAccount(id,idAccount);

		Client client =  (Client) dao.getById(id,Client.class);
		client.getAccounts().forEach(c->c.getHistories());//for update history
		
		model.setViewName("showClient");
		model.addObject("client", client);
		return model;
	}
	@RequestMapping(value = "/admin/AccountAddSum", method=RequestMethod.POST)
	public ModelAndView addSumAccount(
			@RequestParam(value = "idClient") Long id,
			@RequestParam(value = "idAccount") Long idAccount,
			@RequestParam(value = "amount") Long sum) {

		
		Boolean result =dao.addSumAccount(id,idAccount,sum,"present from admin");

		Client client =  (Client) dao.getById(id,Client.class);
		client.getAccounts().forEach(c->c.getHistories());//for update history
		
		model.setViewName("showClient");
		model.addObject("client", client);
		return model;
	}
	
//
//	@RequestMapping(value = "/save", method=RequestMethod.POST)
//	public ModelAndView saveUser(@ModelAttribute User user) {
//	  public ModelAndView processSendMoney(SendMoneyForm sendMoneyForm,
//	
//		userDao.saveOrUpdate(user);
//		ModelAndView model = new ModelAndView("redirect:/");
//		
//		return model;
//	}
	
	
	@RequestMapping(value = "/client/transfer", method = RequestMethod.POST)
	    public ModelAndView viewSendMoneyPage(
	    		@RequestParam(value = "idClient")Long id,
	    		@RequestParam(value = "idAccount", required = false)Long idAccount) {
	 
			Client client = (Client) dao.getById(id,Client.class);	
						
			model.setViewName("showClient");
			model.addObject("client", client);
	        model.addObject("sendMoneyForm", new SendMoneyForm());
	 
	        return model;
	    }
	 
	    @RequestMapping(value = "/client/sendMoney", method = RequestMethod.POST)
	    public ModelAndView processSendMoney(
	    		@RequestParam(value = "fromAccountId")Long fromAccountId,
	    		@RequestParam(value = "toAccountId")Long toAccountId,
	    		@RequestParam(value = "id")Long id,
	    		@RequestParam(value = "amount")Long amount) {
	    	Client client = (Client) dao.getById(id,Client.class);	
	    	
	    	if(!dao.ClientHaveAccount(client, fromAccountId)) {
				
	    		infoProblem.setCause("it's not your account");
	    		model.addObject("error",infoProblem);
	    		model.setViewName("showClient");
				model.addObject("client", client);
		        model.addObject("sendMoneyForm", new SendMoneyForm());
		 
	    		return model;
			}	
	    	
	    	
	    	try {

	        	dao.sendMoney(fromAccountId,toAccountId, amount);
	        } catch (BankTransactionException e) {
	            infoProblem.setCause("Error: " + e.getMessage());
	        	model.addObject("error", infoProblem);
	            
	        }
	    	client = (Client) dao.getById(id,Client.class);
	    	model.setViewName("showClient");
	        model.addObject("client", client);
	        return model;
	    }
	private String getPrincipal(){
        String userName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     
	        if (auth != null) {
	    	  Authentication principal 
	    	  			= SecurityContextHolder.getContext().getAuthentication();
	    	   if (principal != null) {
	            userName = principal.getName();
	        } else {
	            userName = "empty";
	        }
     
        }
   
        return userName;
       
    
    }

}