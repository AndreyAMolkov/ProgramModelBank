package demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import demo.model.Account;
import demo.model.Client;
import demo.model.Data;

public abstract class BaseDao implements Dao {
	@PersistenceContext
	private EntityManager em;

	public EntityManager getEntityManager() {
		return em;
	}

	public BaseDao() {

	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Client getClientById(Long idClient) {
		return em.find(Client.class, idClient);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Account getAccountById(Long idAccount) {
		return em.find(Account.class, idAccount);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Data getDataById(Long idData) {
		return em.find(Data.class, idData);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> getAll() {
		return em.createQuery("SELECT c FROM Client c").getResultList();

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void merge(Client client) {
		em.merge(client);
	}

	@Transactional
	public void add(Object obj) {
		em.persist(obj);
	}

	@Transactional
	public void remove (Long idClient) {
		Client c = getClientById(idClient);
		em.remove(c);
	}

}
