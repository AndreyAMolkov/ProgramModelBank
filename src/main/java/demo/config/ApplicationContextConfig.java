package demo.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import demo.dao.Dao;
import demo.dao.DaoImp;
import demo.model.Account;
import demo.model.Client;
import demo.model.Data;
import demo.model.Login;
import demo.model.Story;

@Configuration
@EnableWebMvc
@ComponentScan("demo.*") 
@EnableTransactionManagement
@Import({ FormLoginSecurityConfig.class })
public class ApplicationContextConfig implements TransactionManagementConfigurer  { 
  
//	 @Bean
//	 public Logger logger() {
//		 return LoggerFactory.getLogger("STDOUT");
//	 }
//	
	@Scope("prototype")
	@Bean(name = "story" )
	public Story getStory() {
		return new Story();
	}
	
	@Bean(name = "model")
	public ModelAndView getModel() {
		return new ModelAndView();
	}
	
	
    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
  
	@Bean(name = "dataSourse")
	@Primary
	public DataSource getDataSource() {	

    
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
  

    return tds;

}
	 
	@Autowired
	@Bean(name="sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
		
		LocalSessionFactoryBuilder sessionBuilder = 
				new LocalSessionFactoryBuilder(dataSource);
		sessionBuilder.addAnnotatedClass(Account.class);
		sessionBuilder.addAnnotatedClass(Client.class);
		sessionBuilder.addAnnotatedClass(Data.class);
		sessionBuilder.addAnnotatedClass(Login.class);
		sessionBuilder.addAnnotatedClass(Story.class);
		sessionBuilder.setProperty("hibernate.dialect", "MySQL57InnoDB");
		sessionBuilder.setProperty("hibernate.show_sql", "true");
		sessionBuilder.setProperty("hbm2ddl.auto", "update");
		sessionBuilder.setProperty("use_sql_comments", "true");
		sessionBuilder.setProperty("hibernate.enable_lazy_load_no_trans", "true");	
		return sessionBuilder.buildSessionFactory();
	}


	

	@Autowired
	@Bean(name = "dao")
	public Dao<?> getDao (SessionFactory sessionFactory) {
		return new DaoImp<Object>(sessionFactory);
	}

	@Override
	@Bean(name = "transactionManager")
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		HibernateTransactionManager transactionManager= new HibernateTransactionManager();
		transactionManager.setSessionFactory( getSessionFactory(getDataSource()));
	return transactionManager;
	}
}