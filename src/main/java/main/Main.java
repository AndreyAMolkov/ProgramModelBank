package main;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.dbcp2.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import demo.model.Account;
import demo.model.Client;
import demo.model.Data;
import demo.model.Login;
import demo.model.Story;

public class Main {

	public static void main(String[] args) {
//		  SessionFactory sessionFactory =
//				       new Configuration()
//			            .configure()
//			            .buildSessionFactory();

		DriverAdapterCPDS cpds = new DriverAdapterCPDS();
		try {
			cpds.setDriver("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cpds.setUrl("jdbc:mysql://localhost:3306/bank?useSSL=false");
		cpds.setUser("root");
		cpds.setPassword("1234");
		SharedPoolDataSource tds = new SharedPoolDataSource();
		tds.setConnectionPoolDataSource(cpds);
		tds.setMaxTotal(10);
		tds.setMaxConnLifetimeMillis(50);
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(tds);
		sessionBuilder.addAnnotatedClass(Account.class);
		sessionBuilder.addAnnotatedClass(Client.class);
		sessionBuilder.addAnnotatedClass(Data.class);
		sessionBuilder.addAnnotatedClass(Login.class);
		sessionBuilder.addAnnotatedClass(Story.class);
		sessionBuilder.setProperty("hibernate.dialect", "MySQL57InnoDB");
		sessionBuilder.setProperty("hibernate.show_sql", "true");
		sessionBuilder.setProperty("hibernate.hbm2ddl.auto", "update");
		sessionBuilder.setProperty("hibernate.use_sql_comments", "true");
		SessionFactory sessionFactory = sessionBuilder.buildSessionFactory();

		Session session = sessionFactory.openSession();

		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();

		Data data = new Data("Ivan", "Ivanovich", "Vasin");

		Login loginC = new Login("root", "1234", "ADMIN");

		Client client = new Client(loginC, data);

		// Account account = new Account(5000L);
		// client.setAccounts(account);
		List<Story> history = new ArrayList<Story>(5);

//		Story story;
//		Story story2;
//		for (Long i = 1L; i < 3; i++) {
//			story = new Story();
//			story.input("from air", i * 344);
//			history.add(story);
//			story2 = new Story();
//			story2.output("AUSHAN", i * 34);
//			history.add(story2);
//		}
//
//		client.getAccounts().get(0).setHistories(history);
//    	  Client client =new Client();
//    	  client =em.find(Client.class, 1L);
//    	  System.out.println(client);
//    	  client.getDataOfClient().setAllData("sadfdsfdsf","Rdsfds", "dsfgnbkm");
//    	  client.getLoginOfClient().setAllData("try", "123fdg4", "USEfgR");

//    	  client.setDataOfClient(data);

		em.persist(client);
		em.getTransaction().commit();

//		Client client1 = em.find(Client.class, 3L);

//		System.out.println(client1);
//		  em.flush();
//		   em.getTransaction().commit();
//		   em.close();

		session.close();
		sessionFactory.close();
		System.out.println("End of main");
	}

}
