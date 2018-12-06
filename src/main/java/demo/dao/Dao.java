package demo.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.transaction.CannotCreateTransactionException;

import demo.model.Account;
import demo.model.Client;
import demo.model.Credential;
import demo.model.Data;

public interface Dao {

	public void merge(Client client)throws CannotCreateTransactionException;

	public void remove(Long idClient)throws CannotCreateTransactionException;

	public Client getClientById(Long idClient)throws PersistenceException;
	
	public Account getAccountById(Long idAccount)throws PersistenceException;
	
	public Data getDataById(Long idData)throws PersistenceException;

	public Credential findCredentialByname (String username)throws CannotCreateTransactionException;

	public void add(Object obj)throws CannotCreateTransactionException;

	public void newAccount(Long idClient)throws CannotCreateTransactionException;

	public void sendMoney(Long fromAccountId, Long toAccountId, Long amount) throws BankTransactionException;

	public void addAmount(Long id, Long amount, Long idPartner) throws BankTransactionException;

	public List<Client> getAll();

	public void addSumAccount(Long number, Long sum, String source)throws CannotCreateTransactionException;

	public Boolean deleteAccount(Long id, Long number)throws CannotCreateTransactionException;

	public Boolean findLoginInBd(String login)throws CannotCreateTransactionException;

	public Boolean clientHaveAccount(Client client, Long numberAccount)throws CannotCreateTransactionException;

	public Object nameLoginClientOwner(Long idClientOwner)throws CannotCreateTransactionException;
}
