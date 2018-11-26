package demo.dao;

import java.util.List;

import demo.model.Client;
import demo.model.Login;

public interface Dao<T> {

	@SuppressWarnings("hiding")
	public <T> T merge(final T object);

	public void remove(Long id, Class<?> T);

	public T getById(Long id, Class<?> T);

	public Login findLoginByname(String username);

	public void add(Object obj);

	public void newAccount(Long id, Class<?> T);

	public void sendMoney(Long fromAccountId, Long toAccountId, Long amount) throws BankTransactionException;

	public void addAmount(Long id, Long amount, Long idPartner) throws BankTransactionException;

	public List<T> getAll(Class<?> T);

	public void addSumAccount(Long number, Long sum, String source);

	public Boolean deleteAccount(Long id, Long number);

	public Boolean findLoginInBd(String login);

	public Boolean ClientHaveAccount(Client client, Long numberAccount);

	public Object nameLoginClientOwner(Long idClientOwner);
}
