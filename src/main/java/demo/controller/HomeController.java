package demo.controller;

import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static Logger log =LoggerFactory.getLogger("HomeController");

	@Autowired
	private ModelAndView model;

	@Autowired
	private SendMoneyForm sendMoneyForm;

	@Autowired(required = true)
	private Login loginEntity;

	@Autowired
	private Data data;

	@Autowired(required = true)
	private Client client;

	@Autowired
	private InfoProblem infoProblem;

	private String rolePrincipal;

	private Long idPrincipal;

	@Autowired
	private AccountCheckAddSum accountCheckAddSum;

	@Autowired
	private Dao<?> dao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView start() {
		String nameMethod = "start";
		log.info(nameMethod,"------------------              --------------------------------------------------------------------------------------------------------------------------------------------------------");
		log.trace("Hello World!");
		log.debug("How are you today?");
		log.info("I am fine.");
		log.warn("I love programming.");
		log.error("I am programming.");
		model.setViewName("redirect:" + "home");
		return model;
	}

	@RequestMapping("/admin/listAll")
	public ModelAndView handleRequest() throws Exception {
		String nameMethod = "handleRequest";
		clearSettingOfOldModel();
		loadAllClients();
		return model;

	}

	@RequestMapping(value = "/admin/showHistories", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showClientHistoryForAdmin(@RequestParam(value = "idClient") Long id,
			@RequestParam(value = "idAccount", required = false) Long idAccount) {
		String nameMethod = "showClientHistoryForAdmin";
		clearSettingOfOldModel();
		loadOneClient(id);

		if (loadCurrentAccount(idAccount) == null) {
			handlerEvents("not found card: " + idAccount);
		}

		model.setViewName("showClientAdmin");

		return model;

	}

	@RequestMapping(value = "/client/showHistories", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showClientHistory(@RequestParam(value = "idClient") Long id,
			@RequestParam(value = "idAccount", required = false) Long idAccount) {
		String nameMethod = "showClientHistory";
		model.setViewName("showClient");
		clearSettingOfOldModel();
		loadOneClient(id);
		if (loadCurrentAccount(idAccount) == null) {
			handlerEvents("not found card: " + idAccount);
		}

		return model;
	}

	// Spring Security see this :
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView login(@RequestParam(value = "error", required = false) String error) {
		String nameMethod = "login";
		model.setViewName("login");
		return model;

	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home() {
		String nameMethod = "home";
		updateDatePrincipal();
		clearSettingOfOldModel();
		if ("ROLE_CLIENT".equals(rolePrincipal)) {
			loadOneClient(idPrincipal);
			model.setViewName("showClient");
			return model;
		}

		if ("ROLE_ADMIN".equals(rolePrincipal)) {
			model.setViewName("allClients");
			loadAllClients();
			return model;
		}
		if ("ROLE_CASHIER".equals(rolePrincipal)) {
			model.setViewName("cashier");
			return model;
		}

		return model;
	}

	@RequestMapping(value = "/admin/addAccount", method = { RequestMethod.POST })
	public ModelAndView addAccountForAdmin(@RequestParam(value = "idClient") Long id) {
		String nameMethod = "addAccountForAdmin";
		clearSettingOfOldModel();
		try {
			dao.newAccount(id, Client.class);
			loadOneClient(id);
			model.setViewName("showClientAdmin");
		} catch (NullPointerException e) {
			handlerEvents("not found id: " + id);
			loadAllClients();

		}

		return model;

	}

	@RequestMapping(value = "/admin/deleteClient", method = { RequestMethod.POST })
	public ModelAndView deleteClientForAdmin(@RequestParam(value = "id") Long id) {
		String nameMethod = "deleteClientForAdmin";
		try {
			dao.remove(id, Client.class);
		} catch (IllegalArgumentException e) {
			handlerEvents("not found id: " + id);
		}

		loadAllClients();
		model.setViewName("allClients");

		return model;
	}

	@RequestMapping(value = "/admin/showClient", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showClientForAdmin(@RequestParam(value = "id") Long id,
			@RequestParam(value = "idAccount", required = false) Long idAccount) {
		String nameMethod = "showClientForAdmin";
		clearSettingOfOldModel();
		model.setViewName("showClientAdmin");
		if (loadOneClient(id) == null) {
			handlerEvents("not found id: " + id);
			loadAllClients();
			return model;
		}

		if (idAccount != null) {
			loadCurrentAccount(idAccount);
		}

		return model;
	}

	@RequestMapping(value = "/client/showClient", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showClientForClient(@RequestParam(value = "idAccount", required = false) Long idAccount) {
		String nameMethod = "showClientForClient";
		clearSettingOfOldModel();
		model.setViewName("showClient");
		loadCurrentAccount(idAccount);
		if (idAccount != null) {
			loadCurrentAccount(idAccount);
		}

		return model;
	}

	@RequestMapping(value = "/admin/populateEdit", method = { RequestMethod.POST })
	public ModelAndView addEditClien(@RequestParam(value = "id") Long id) {
		String nameMethod = "addEditClien";
		clearSettingOfOldModel();
		model.setViewName("clientForm");

		if ((client = loadOneClient(id)) == null) {
			handlerEvents("not found id: " + id);
			loadAllClients();
			return model;
		}

		client.getLogin().setPasswordEmpty();
		return model;

	}

	@RequestMapping(value = "/admin/edit", method = { RequestMethod.POST })
	public ModelAndView editClientForAdmin(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "login.id", required = false) Long idLogin,
			@RequestParam(value = "data.id", required = false) Long idData,
			@RequestParam(value = "login.login") String login, @RequestParam(value = "login.password") String password,
			@RequestParam(value = "login.role") String role, @RequestParam(value = "data.firstName") String firsName,
			@RequestParam(value = "data.secondName") String secondName,
			@RequestParam(value = "data.lastName") String lastName) throws Exception {
		String nameMethod = "editClientForAdmin";
		clearSettingOfOldModel();
		client.setId(id);
		data.setAllData(idData, firsName, secondName, lastName);
		loginEntity.setAllData(idLogin, login, password, role);
		client.setData(data);
		client.setLogin(loginEntity);

		try {
			if (dao.findLoginInBd(login)) {
				if (id != null) {
					if (!login.equals(dao.nameLoginClientOwner(id))) {
						handlerEvents("login isn't unique");
						loginEntity.setPasswordEmpty();
						model.setViewName("clientForm");
						model.addObject("client", client);
						return model;
					}

				} else {
					handlerEvents("login isn't unique");
					loginEntity.setPasswordEmpty();
					model.setViewName("clientForm");
					model.addObject("client", client);
					return model;
				}

			}
		} catch (NoResultException e) {
			;// this means that not found this login, it's ok
		}

		if (client.getId() == null) {
			dao.add(client);
		} else {
			dao.merge(client.getData());
			dao.merge(client.getLogin());
		}
		loadAllClients();
		model.setViewName("allClients");

		return model;
	}

	@RequestMapping(value = "/admin/newClient", method = RequestMethod.GET)
	public ModelAndView newClient(HttpServletRequest request) {
		String nameMethod = "newClient";
		clearSettingOfOldModel();
		model.setViewName("clientForm");
		model.addObject("client", new Client());

		return model;
	}

	@RequestMapping(value = "/admin/deleteAccount", method = RequestMethod.POST)
	public ModelAndView deleteAccount(@RequestParam(value = "idClient") Long id,
			@RequestParam(value = "idAccount") Long idAccount) {
		String nameMethod = "deleteAccount";
		clearSettingOfOldModel();
		if (!dao.deleteAccount(id, idAccount)) {
			handlerEvents("not found card: " + idAccount);
		}
		model.setViewName("showClientAdmin");
		loadOneClient(id);

		return model;
	}

	@RequestMapping(value = "/cashier/AccountCheckAddSum", method = RequestMethod.POST)
	public ModelAndView accountCheckAddSum(@RequestParam(value = "idAccountTo") Long idAccountTo,
			@RequestParam(value = "amount") Long sum, @RequestParam(value = "denied") Boolean denied) {
		String nameMethod = "accountCheckAddSum";
		clearSettingOfOldModel();
		model.setViewName("cashier");
		accountCheckAddSum.setDenied(denied);
		if (denied) {
			infoProblem.setCause("denied");
			model.setViewName("redirect:" + "home");

		} else {
			accountCheckAddSum.setAmountClientAccountTo(sum);
			accountCheckAddSum.setIdAccountTo(idAccountTo);
			model.addObject("dataTransfer", accountCheckAddSum);

		}

		return model;
	}

	@RequestMapping(value = "/cashier/AccountAddSum", method = RequestMethod.POST)
	public ModelAndView addSumAccount(@RequestParam(value = "idAccountTo") Long idAccountTo,
			@RequestParam(value = "denied") Boolean denied, @RequestParam(value = "amount") Long sum,
			@RequestParam(value = "idAccountToCheck") Long idAccountToCheck,
			@RequestParam(value = "amountCheck") Long sumCheck) {
		String nameMethod = "addSumAccount";
		clearSettingOfOldModel();
		String infoCashier = "cashier id: " + idPrincipal + " N Novgorod";

		if (denied && !(sum.equals(sumCheck) && idAccountToCheck.equals(idAccountTo))) {
			infoProblem.setCause("denied");
			model.setViewName("redirect:" + "home");

		} else {
			try {
				dao.addSumAccount(idAccountTo, sum, infoCashier);
				handlerEvents("ok");
			} catch (Exception e) {
				handlerEvents("Error of transaction");

			}
			model.setViewName("cashier");
		}

		return model;
	}

	@RequestMapping(value = "/client/transfer", method = RequestMethod.POST)
	public ModelAndView viewSendMoneyPage(@RequestParam(value = "idClient") Long id,
			@RequestParam(value = "idAccount", required = false) Long idAccount) {
		String nameMethod = "viewSendMoneyPage";
		clearSettingOfOldModel();
		loadOneClient(id);
		model.setViewName("showClient");
		model.addObject("sendMoneyForm", sendMoneyForm);

		return model;
	}

	@RequestMapping(value = "/client/sendMoney", method = RequestMethod.POST)
	public ModelAndView processSendMoney(@RequestParam(value = "fromAccountId") Long fromAccountId,
			@RequestParam(value = "toAccountId") Long toAccountId, @RequestParam(value = "id") Long id,
			@RequestParam(value = "amount") Long amount) {
		String nameMethod = "processSendMoney";
		clearSettingOfOldModel();
		Client client = loadOneClient(id);

		if (!dao.ClientHaveAccount(client, fromAccountId)) {
			handlerEvents("it's not your account");
			model.setViewName("showClient");
			model.addObject("sendMoneyForm", sendMoneyForm);

			return model;
		}

		try {

			dao.sendMoney(fromAccountId, toAccountId, amount);
		} catch (BankTransactionException e) {
			handlerEvents("Error: " + e.getMessage());
		}

		model.setViewName("showClient");
		loadCurrentAccount(fromAccountId);
		loadOneClient(id);

		return model;
	}

	private void updateDatePrincipal() {
		String nameMethod = "updateDatePrincipal";
		clearSettingOfOldModel();
		String userName = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			Authentication principal = SecurityContextHolder.getContext().getAuthentication();
			userName = principal.getName();
			this.idPrincipal = dao.findLoginByname(userName).getIdClient();

			this.rolePrincipal = ((principal.getAuthorities().toArray())[0]).toString();
		}
	}

	// all setting of model, which need clean before starting new
	private void clearSettingOfOldModel() {
		String nameMethod = "clearSettingOfOldModel";
		model.addObject("currentAccount", null);
		model.addObject("client", null);
		model.addObject("error", null);
		model.addObject("sendMoneyForm", null);
		model.addObject("dataTransfer", null);
		model.addObject("clients", null);
	}

	private void handlerEvents(String dataForPass) {
		String nameMethod = "handlerEvents";
		infoProblem.setCause(dataForPass);
		model.addObject("error", infoProblem);
	}

	@SuppressWarnings("unchecked")
	private void loadAllClients() {
		String nameMethod = "loadAllClients";
		List<Client> listClients = (List<Client>) dao.getAll(Client.class);
		model.setViewName("allClients");
		model.addObject("clients", listClients);
	}

	private Client loadOneClient(Long id) {
		String nameMethod = "loadOneClient";
		Client client = (Client) dao.getById(id, Client.class);
		model.addObject("client", client);
		return client;
	}

	private Account loadCurrentAccount(Long idAccount) {
		String nameMethod = "loadCurrentAccount";
		Account account = (Account) dao.getById(idAccount, Account.class);
		model.addObject("currentAccount", account);
		return account;
	}

}