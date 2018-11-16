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
import demo.model.Login;
import demo.model.SendMoneyForm;

@Controller
public class HomeController {
	@Autowired
	private ModelAndView model;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private Dao<?> dao;
	
	@RequestMapping("/client/listAll")
	public ModelAndView handleRequest() throws Exception{
		List<Client> listClients =  (List<Client>) dao.getAll(Client.class);
		listClients.stream().forEach(c->c.getAccounts());//for update history	
		ModelAndView model = new ModelAndView("allClients");
		model.addObject("clients", listClients);
		//model.addObject("listAccount", listAccount);
		
		return model;
		
	}
	@RequestMapping(value = "/admin/showHistories", method=RequestMethod.GET)
	public ModelAndView showClientHistoryForAdmin(HttpServletRequest request) {
		Integer id = Integer.parseInt(request.getParameter("id"));
		Integer idAccount = Integer.parseInt(request.getParameter("idAccount"));
		Account account = (Account) dao.getById(idAccount.longValue(),Account.class);
		Client client = (Client) dao.getById(id.longValue(),Client.class);	

		ModelAndView model = new ModelAndView("showClient");
		model.addObject("client", client);
		
		return model.addObject("currentAccount", account);
		
	}

	
	//Spring Security see this :
	@RequestMapping(value = "/login", method= {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error) {

		ModelAndView model = new ModelAndView();

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
			@RequestParam(value = "id", required = false) Long id) {

		 dao.newAccount(id,Client.class);

		
		Client client2 = (Client) dao.getById(id,Client.class);
		client2.getAccounts();
		ModelAndView model = new ModelAndView("showClient");		
		
		return model.addObject("client", client2);

	}
	

	@RequestMapping(value = "/admin/deleteClient", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView deleteClientForAdmin(
			@RequestParam(value = "id") Long id) {
		
	//	Client client = (Client) dao.getById(id,Client.class);
		try {
			dao.remove(id,Client.class);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		List<Client> listClients =  (List<Client>) dao.getAll(Client.class);
		listClients.stream().forEach(c->c.getAccounts());//for update history	
		ModelAndView model = new ModelAndView("allClients");
		model.addObject("clients", listClients);
		
		return model;
	}
	@RequestMapping(value = "/admin/showClient", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView showClientForAdmin(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "idAccount", required = false) Long idAccount) {
		Client client = (Client) dao.getById(id,Client.class);
		ModelAndView model = new ModelAndView("showClient");
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
			@RequestParam(value = "login.login", required = false) String login,
			@RequestParam(value = "login.password", required = false) String password,
			@RequestParam(value = "login.role", required = false) String role,
			@RequestParam(value = "data.firstName", required = false) String firsName,
			@RequestParam(value = "data.secondName", required = false) String secondName,
			@RequestParam(value = "data.lastName", required = false) String lastName
			
			)
	 throws Exception{
			
		Client client = new Client();
		client.setData(new Data(firsName,secondName, lastName));
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
			
			ModelAndView model = new ModelAndView("allClients");
			model.addObject("clients", listClients);
		
		return model;
	}
	
	@RequestMapping(value = "/admin/newClient", method=RequestMethod.GET)
	public ModelAndView editUser(HttpServletRequest request) {

		ModelAndView model = new ModelAndView("ClientForm");
		model.addObject("client", new Client());
		
		return model;
	}
	 @RequestMapping("/failureUrl")
	    public String fail(Model model) {
	        
	        model.addAttribute("message", "Incorrect password or name for  - " + getPrincipal());
	        
	        return "failureUrl";
	        
	    }
	@RequestMapping(value = "/admin/delete", method=RequestMethod.POST)
	public ModelAndView deleteUser(@RequestParam(value = "id") Long id,
			@RequestParam(value = "object", required = false) String nameObject) {
		Class<?> obj = dao.nameToObject(nameObject);
		
		dao.remove(id,obj);
		
		List<Client> listClients =  (List<Client>) dao.getAll(Client.class);

		listClients.stream().forEach(c->c.getAccounts());//for update history
		
		ModelAndView model = new ModelAndView("allClients");
		model.addObject("clients", listClients);
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
	    		@RequestParam(value = "idClient", required = false)Long id,
	    		@RequestParam(value = "idAccount", required = false)Long idAccount) {
	 
	      
		 
			Client client = (Client) dao.getById(id,Client.class);	

			//ModelAndView model = new ModelAndView("showClient");
			model.setViewName("showClient");
			model.addObject("client", client);
		  // SendMoneyForm form = new SendMoneyForm(1L, 2L, 700L);

	        model.addObject("sendMoneyForm", new SendMoneyForm());
	 
	        return model;
	    }
	 
	    @RequestMapping(value = "/client/sendMoney", method = RequestMethod.POST)
	    public ModelAndView processSendMoney(
	    		@RequestParam(value = "fromAccountId")Long fromAccountId,
	    		@RequestParam(value = "toAccountId")Long toAccountId,
	    		@RequestParam(value = "id")Long id,
	    		@RequestParam(value = "amount")Long amount) {
	 //   	SendMoneyForm sendMoneyForm;

	        try {
//	            dao.sendMoney(sendMoneyForm.getFromAccountId(), //
//	                    sendMoneyForm.getToAccountId(), //
//	                    sendMoneyForm.getAmount());
	        	dao.sendMoney(fromAccountId,toAccountId, amount);
	        } catch (BankTransactionException e) {
	            
	        	//model.add("errorMessage", "Error: " + e.getMessage());
	            
	        }
	    	Client client = (Client) dao.getById(id,Client.class);
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