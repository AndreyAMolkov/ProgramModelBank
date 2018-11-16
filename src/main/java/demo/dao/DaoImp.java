package demo.dao;
	

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import demo.model.Account;
import demo.model.Client;
import demo.model.Login;
import demo.model.Login_;
import demo.model.Story;



//@Transactional
@Repository
public class DaoImp<T> extends BaseDao<Object> implements Dao<Object> {
	
	 @PersistenceContext
	 private EntityManager em;
	 
	 @Autowired
	 private Story story;
	
	 @Autowired
	 private PlatformTransactionManager transactionManager;
	 

	@Autowired
	private SessionFactory sessionFactory;

	
	public DaoImp() {
		super();

	}

	public DaoImp(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
		
	}

	
	 @Transactional(rollbackFor=DataAccessException.class)
	    public Boolean newAccount( Long id,Class T)   {
	      Client client=null;
	      try {
	         client=(Client) getById(id,T);
	         client.getAccounts();
	    	client.setAccounts(new Account());


	    	em.merge(client);
	    	
	      } 
	      catch (DataAccessException e) {
	         System.out.println("Error in creating record, rolling back");
	         throw e;
	      }
	    	
	    	return true;
	      }
	 public Class<?> nameToObject(String nameObject){
		 Object object = null;
		 if(nameObject.equals((Client.class).getSimpleName()))
				 object = Client.class;
		 
		 return (Class<?>) object;
	 }


	  // MANDATORY: Transaction must be created before.
	    @Transactional(propagation = Propagation.MANDATORY)
	    public void addAmount(Long id, Long amount, Long idPartner) throws BankTransactionException {
	        Account account = (Account) getById(id,Account.class);
	        if (account == null) {
	            throw new BankTransactionException("Account not found " + id);
	        }
	        Long newBalance = account.getSum() + amount;//-------------------------------------------------------------------
	        if (account.getSum() + amount < 0) {
	            throw new BankTransactionException(
	                    "The money in the account '" + id + "' is not enough (" + account.getSum() + ")");
	        }
	//        Story story = account.getNewStory();
	        if(amount >= 0)
	        	story.input("transfer from"  + idPartner,amount);
	        else
	        	story.output("transfer to "  + idPartner,amount);
	        
	        account.setHistories(story);
	    }
	 
	    // Do not catch BankTransactionException in this method.
	    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	    public void sendMoney(Long fromAccountId, Long toAccountId, Long amount) throws BankTransactionException {
	 
	        addAmount(toAccountId, amount, fromAccountId);
	        addAmount(fromAccountId, -amount, toAccountId);
	    }

		@Override
		public Login findLoginByname(String username) {
			
			Login user =null;
			List<Login> listUser = null;

			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Login> criteria = builder.createQuery( Login.class );
			Root<Login> root = criteria.from( Login.class );
			criteria.select( root );
			criteria.where( builder.equal( root.get( Login_.name ), username ) );

			listUser = em.createQuery( criteria ).getResultList();

			em.close();
					  try {
						user = listUser.get(0);
						
					} catch (java.lang.IndexOutOfBoundsException e) {
						
						return null;
					}
							

			return user;
		}



	


}


