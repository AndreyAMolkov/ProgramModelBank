package demo.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import demo.model.Account;
import demo.model.Client;
import demo.model.Credential;
import demo.model.Story;

//@Transactional
@Repository
public class DaoImp extends BaseDao implements Dao {
	@PersistenceContext
	private EntityManager em;

	public Story getStory() {
		return new Story();
	}


	@Transactional(rollbackFor = Exception.class)
	public void newAccount(Long id) {
		Client client = getClientById(id);
		client.setAccounts(new Account());
	}

	// MANDATORY: Transaction must be created before.
	@Transactional(propagation = Propagation.MANDATORY)
	public void addAmount(Long id, Long amount, Long idPartner) throws BankTransactionException {

		Account account = getAccountById(id);
		if (account == null) {
			throw new BankTransactionException("Account not found " + id);
		}

		if (account.getSum() + amount < 0) {
			throw new BankTransactionException(
					"The money in the account '" + id + "' is not enough (" + account.getSum() + ")");
		}

		if (amount >= 0) {
			Story storyInput = getStory();
			storyInput.input("transfer from" + idPartner, amount);
			account.setHistories(storyInput);
		} else {
			Story storyOutput = getStory();
			storyOutput.output("transfer to " + idPartner, amount);
			account.setHistories(storyOutput);
		}

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public void sendMoney(Long fromAccountId, Long toAccountId, Long amount) throws BankTransactionException {
		addAmount(toAccountId, amount, fromAccountId);
		addAmount(fromAccountId, -amount, toAccountId);
	}

	@Transactional(readOnly = true, rollbackFor = javax.persistence.NoResultException.class)
	@Override
	public Credential findCredentialByname(String username) {
		TypedQuery<Credential> list = null;
		list = em.createQuery("SELECT c from Credential c WHERE c.login = :username", Credential.class).setParameter("username",
				username);

		return list.getSingleResult();
	}

	@Transactional(rollbackFor = Exception.class)
	public void addSumAccount(Long number, Long sum, String source) {
		Account account = getAccountById(number);
		Story storyInput = getStory();
		storyInput.input(source, sum);
		account.setHistories(storyInput);

	}

	@Transactional
	public Boolean deleteAccount(Long id, Long number) {
		Client client = getClientById(id);
		for (Account account : client.getAccounts()) {
			if (account.getNumber().equals(number)) {
				client.getAccounts().remove(account);
				return true;
			}

		}

		return false;
	}

	@Transactional(readOnly = true)
	public Boolean findLoginInBd(String login) {
		Boolean result = false;
		if (findCredentialByname(login) != null) {
			result = true;
		}
		return result;
	}

	public Boolean clientHaveAccount(Client client, Long numberAccount) {
		return client.getAccounts().stream().map(Account::getNumber).anyMatch(e -> e.equals(numberAccount));

	}

	@Transactional(readOnly = true)
	public Object nameLoginClientOwner(Long idClientOwner) {
		String name = null;
		Client client = getClientById(idClientOwner);
		if (client != null)
			name = client.getCredential().getLogin();

		return name;
	}
}
