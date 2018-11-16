package demo.dao;

import java.util.List;

import demo.model.Login;

//@Transactional
public interface Dao<T> {
	

	public <T> T merge(final T object) ;
	public void remove(Long id,Class<?> T) ;
	public T getById( Long id, Class<?> T);
	public Login findLoginByname(String username);
	public void add(Object obj);
	public Boolean newAccount( Long id,Class T);

	public Class<?> nameToObject(String nameObject);
	public void sendMoney(Long fromAccountId, Long toAccountId, Long amount) throws BankTransactionException;
	public void addAmount(Long id, Long amount, Long idPartner) throws BankTransactionException;

	public List<T> getAll(Class<?> T);


	}

