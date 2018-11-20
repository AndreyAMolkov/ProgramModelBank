import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Iterator;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.mockito.Mock;

public class DaoImplTest {
	
	 @Mock
	  private DataSource mockDataSource;
	 
	 @Mock
	  private SessionFactory mockSessionFactory;
	 
	 
	 
	 
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	
//	@Test
//	public void testNewAccount() {
//	
//		Dao<?> instance = (Dao<?>) new DaoImplTest();
//		
//		Long id=1L;
//		Class<?>T = Client.class;
//		Client testClient = new Client();
//		when(instance.getById(id,T)).thenReturn(testClient);
//		
//		Client client=null;
//		    
//         client=(Client) instance.getById(id,T);
//    	client.setAccounts(new Account());
//	}

//	@Test
//	public void testAddAmount() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSendMoney() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindLoginByname() {
//		fail("Not yet implemented");
//	}
//
	@Test
	public void addSumAccount_Add100_AddNewStoryAndAddSumOnAccount() {
		//подготавливаем
				Iterator i = mock(Iterator.class);
				when(i.next()).thenReturn("Hello").thenReturn("World");
				//выполняем
				String result = i.next()+" "+i.next();
				//сравниваем
				assertEquals("Hello World", result);
	}
//
//	@Test
//	public void testDeleteAccount() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindLoginInBd() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testClientHaveAccount() {
//		fail("Not yet implemented");
//	}

}
