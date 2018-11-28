package main;

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

//	you can use for fast setting your database (for properly  structure tables)
public class Main {

	public static void main(String[] args) {

		DriverAdapterCPDS cpds = new DriverAdapterCPDS();
		try {
			cpds.setDriver("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException e) {
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

		em.persist(client);
		em.getTransaction().commit();

		System.out.println("End of main");
	}

}
