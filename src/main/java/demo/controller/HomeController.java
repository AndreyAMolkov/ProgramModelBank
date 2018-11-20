package demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import demo.dao.BankTransactionException;
import demo.dao.Dao;
import demo.model.Account;
import demo.model.AccountCheckAddSum;
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
	
	private String rolePrincipal;
	
	private Long idPrincipal;
	
	@Autowired
	private AccountCheckAddSum accountCheckAddSum;
	
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

		model.setViewName("showClientAdmin");
		model.addObject("client", client);
		
		return model.addObject("currentAccount", account);
		
	}
	@RequestMapping(value = "/client/showHistories", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView showClientHistory(
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
		
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public ModelAndView index() {
		updateDatePrincipal();
		if("ROLE_CLIENT".equals(rolePrincipal)) {
			Client client = (Client) dao.getById(idPrincipal, Client.class);
			model.setViewName("showClient");
			model.addObject("client", client);
			return model;
		}
		
		if("ROLE_ADMIN".equals(rolePrincipal)) {
			List<Client> listClients =  (List<Client>) dao.getAll(Client.class);
			
			model.setViewName("allClients");
			model.addObject("clients", listClients);
			return model;
		}
		if("ROLE_CASHIER".equals(rolePrincipal)) {
			model.setViewName("cashier");
			return model;
		}
		
		return model;
	}
	

	@RequestMapping(value = "/admin/addAccount", method={RequestMethod.POST})
	public ModelAndView addAccountForAdmin(
			@RequestParam(value = "idClient") Long id) {

		dao.newAccount(id,Client.class);
		Client client = (Client) dao.getById(id,Client.class);
		client.getAccounts();
		model.setViewName("showClientAdmin");			
		return model.addObject("client", client);

	}
	

	@SuppressWarnings("unchecked")
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
		model.setViewName("showClientAdmin");
		if(idAccount != null) {
			Account account = (Account) dao.getById(idAccount,Account.class);
			model.addObject("currentAccount", account);
		}
		model.addObject("client", client);
		
		return model;
	}
	@RequestMapping(value = "/client/showClient", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView showClientForClient(
			@RequestParam(value = "idAccount", required = false) Long idAccount) {
		Client client = (Client) dao.getById(idPrincipal,Client.class);
		
		model.setViewName("showClient");
		if(idAccount != null) {
			Account account = (Account) dao.getById(idAccount,Account.class);
			model.addObject("currentAccount", account);
		}
		model.addObject("client", client);
		
		return model;
	}
	
	@RequestMapping(value = "/admin/populateEdit", method={RequestMethod.POST})
	public ModelAndView addEditClien(@RequestParam(value = "id") Long id) {
			Client client = (Client)dao.getById(id,Client.class);
		client.getLogin().setPasswordEmpty();
		model.addObject("client",client);
		model.setViewName("ClientForm");
		return model;
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/admin/edit", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView addNewClientForAdmin(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "login.id", required = false) Long idLogin,
			@RequestParam(value = "data.id", required = false) Long idData,
			@RequestParam(value = "login.login") String login,
			@RequestParam(value = "login.password") String password,
			@RequestParam(value = "login.role") String role,
			@RequestParam(value = "data.firstName") String firsName,
			@RequestParam(value = "data.secondName") String secondName,
			@RequestParam(value = "data.lastName") String lastName
			
			)
	 throws Exception{
		Client client = new Client();
		client.setId(id);
		client.setData(new Data(firsName,secondName, lastName));
		client.getData().setId(idData);
		if(dao.findLoginInBd(client.getLogin().getLogin())) {
			model.setViewName("ClientForm");
			infoProblem.setCause("login isn't unique");
			model.addObject("error", infoProblem);
			client.getLogin().setPasswordEmpty();
			model.addObject("client", client);
			return model;
		}
		
		client.setLogin(new Login(login, password, role));	
		client.getLogin().setId(idLogin);
		if(client.getId() == null) {	
			dao.add(client);
		}else {		
			dao.merge(client.getData());
			dao.merge(client.getLogin());
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
	
	@RequestMapping(value = "/admin/delete", method=RequestMethod.POST)
	public ModelAndView deleteUser(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "object", required = false) String nameObject) {
		
		dao.remove(id,Client.class);
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
		
		model.setViewName("showClientAdmin");
		model.addObject("client", client);
		return model;
	}
	@RequestMapping(value = "/cashier/AccountCheckAddSum", method=RequestMethod.POST)
	public ModelAndView addSumAccount(
			@RequestParam(value = "idAccountTo", required = false) Long idAccountTo,
			@RequestParam(value = "amount", required = false) Long sum,
			@RequestParam(value = "denied") Boolean denied
			) {
		
		model.setViewName("cashier");
		accountCheckAddSum.setDenied(denied);
		model.addObject("error", null);
		if(denied) {
			model.addObject("dataTransfer", null);
			
		}
		else{
			
			accountCheckAddSum.setAmountClientAccountTo(sum);
			accountCheckAddSum.setIdAccountTo(idAccountTo);
			model.addObject("dataTransfer", accountCheckAddSum);
		}
		
		return model;
	}
	@RequestMapping(value = "/cashier/AccountAddSum", method=RequestMethod.POST)
	public ModelAndView addSumAccount(
			@RequestParam(value = "idAccountTo") Long idAccountTo,
			@RequestParam(value = "denied") Boolean denied,
			@RequestParam(value = "amount") Long sum) {

		String infoCashier = "cashier id: " + idPrincipal + " N Novgorod";
		if(denied) {
			infoProblem.setCause("denied");
		}else {
			try {
			dao.addSumAccount(idAccountTo,sum,infoCashier);
			infoProblem.setCause("ok");
			}catch (Exception e) {
				infoProblem.setCause("Error of transaction");
				
			}
			
		}
		model.addObject("error", infoProblem);
		model.setViewName("cashier");
		model.addObject("dataTransfer", null);
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
	    	model.addObject("error", null);
	    	
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

			Account account = (Account) dao.getById(fromAccountId,Account.class);
			client = (Client) dao.getById(id,Client.class);	

			model.setViewName("showClient");
			model.addObject("client", client);
			model.addObject("currentAccount", account);
	    	
	    	
	    	model.setViewName("showClient");
	        model.addObject("client", client);
	        return model;
	    }
	private void updateDatePrincipal(){
		infoProblem.setCause("");
		model.addObject("error",null);
		model.addObject("currentAccount",null);
		
		
        String userName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     
	        if (auth != null) {
	    	  Authentication principal 
	    	  			= SecurityContextHolder.getContext().getAuthentication();
	    	  userName = principal.getName();
	    	  this.idPrincipal = dao.findLoginByname(userName).getIdClient();

	    	  
	    	  this.rolePrincipal = ((principal.getAuthorities().toArray())[0]).toString();
	        }
	}

}