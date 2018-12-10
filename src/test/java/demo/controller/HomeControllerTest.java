package demo.controller;

import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
//import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import demo.constant.Constants;
import demo.dao.DaoImp;
import demo.model.Account;
import demo.model.Client;
import demo.model.InfoProblem;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {
	@InjectMocks
	HomeController homeController;
	@Mock
	@PersistenceContext
	private EntityManager em;
	@Mock
	private ModelAndView model;
	@Mock
	private DaoImp dao;
	@Mock
	private Client client;
	@Mock
	private InfoProblem infoProblem;

	@Test public void showClientHistoryForAdmin_PersistenceException_AddHandlerEvent() {
		Account account = new Account();
		InfoProblem mockProblem = new InfoProblem();
		mockProblem.setCause(Constants.CONNECTION_REFUSED);
		Long id=1L;
		when(dao.getClientById(id)).thenThrow(PersistenceException.class);
		when(em.find(Account.class, id)).thenThrow(PersistenceException.class);

		homeController.showClientHistoryForAdmin(1L,1L);
		
		
	}

}
