package demo.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import demo.model.Account;
import demo.model.Client;
import demo.model.Login;
import demo.model.Story;

//@Transactional
@Repository
public class DaoImp<T> extends BaseDao<Object> implements Dao<Object> {
//	private static Logger log =LoggerFactory.getLogger("DaoImp");
	@PersistenceContext
	private EntityManager em;

	// @Autowired
	private Story storyInput;
	// @Autowired
	private Story storyOutput;

	public Story getStory() {
		return new Story();
	}

	public DaoImp() {
		super();

	}

	public DaoImp(SessionFactory sessionFactory) {
		super();

	}

	@Transactional(rollbackFor = Exception.class)
	public void newAccount(Long id, Class<?> T) {
		String nameMethod = "newAccount";
		Client client = (Client) getById(id, T);
		client.setAccounts(new Account());

	}

	// MANDATORY: Transaction must be created before.
	@Transactional(propagation = Propagation.MANDATORY)
	public void addAmount(Long id, Long amount, Long idPartner) throws BankTransactionException {
		String nameMethod = "addAmount";
		Account account = (Account) getById(id, Account.class);
		if (account == null) {
			throw new BankTransactionException("Account not found " + id);
		}
		
		if (account.getSum() + amount < 0) {
			throw new BankTransactionException(
					"The money in the account '" + id + "' is not enough (" + account.getSum() + ")");
		}

		if (amount >= 0) {
			storyInput = getStory();
			storyInput.input("transfer from" + idPartner, amount);
			account.setHistories(storyInput);
		} else {
			storyOutput = getStory();
			storyOutput.output("transfer to " + idPartner, amount);
			account.setHistories(storyOutput);
		}

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public void sendMoney(Long fromAccountId, Long toAccountId, Long amount) throws BankTransactionException {
		String nameMethod = "sendMoney";
		addAmount(toAccountId, amount, fromAccountId);
		addAmount(fromAccountId, -amount, toAccountId);
	}

	@Transactional(readOnly = true, rollbackFor = javax.persistence.NoResultException.class)
	@Override
	public Login findLoginByname(String username) {
		String nameMethod = "findLoginByname";
		TypedQuery<Login> list = null;
		list = em.createQuery("SELECT u from Login u WHERE u.login = :username", Login.class).setParameter("username",
				username);

		Login user = list.getSingleResult();

		return user;
	}

	@Transactional(rollbackFor = Exception.class)
	public void addSumAccount(Long number, Long sum, String source) {
		String nameMethod = "addSumAccount";
		Account account = (Account) getById(number, Account.class);
		storyInput = getStory();
		storyInput.input(source, sum);
		account.setHistories(storyInput);

	}

	@Transactional
	public Boolean deleteAccount(Long id, Long number) {
		Client client = (Client) getById(id, Client.class);
		String nameMethod = "deleteAccount";
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
		String nameMethod = "findLoginInBd";
		if (findLoginByname(login) != null)
			return true;
		else
			return false;
	}

	public Boolean ClientHaveAccount(Client client, Long numberAccount) {
		String nameMethod = "ClientHaveAccount";
		return client.getAccounts().stream().map(e -> e.getNumber()).anyMatch(e -> e.equals(numberAccount));

	}
	
	@Transactional(readOnly = true)
	public Object nameLoginClientOwner(Long idClientOwner) {
		String nameMethod = "nameLoginClientOwner";
		String name = null;
		Client client = (Client) getById(idClientOwner, Client.class);
		if(client !=null)
			name= client.getLogin().getLogin();
		
		return name;
	}
}
