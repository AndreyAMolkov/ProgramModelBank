package demo.dao;

import java.util.List;

import demo.model.Account;
import demo.model.Client;
import demo.model.Credential;
import demo.model.Data;

public interface Dao {

	public void merge(Client client);

	public void remove(Long idClient);

	public Client getClientById(Long idClient);
	
	public Account getAccountById(Long idAccount);
	
	public Data getDataById(Long idData);

	public Credential findCredentialByname(String username);

	public void add(Object obj);

	public void newAccount(Long idClient);

	public void sendMoney(Long fromAccountId, Long toAccountId, Long amount) throws BankTransactionException;

	public void addAmount(Long id, Long amount, Long idPartner) throws BankTransactionException;

	public List<Client> getAll();

	public void addSumAccount(Long number, Long sum, String source);

	public Boolean deleteAccount(Long id, Long number);

	public Boolean findLoginInBd(String login);

	public Boolean clientHaveAccount(Client client, Long numberAccount);

	public Object nameLoginClientOwner(Long idClientOwner);
}
