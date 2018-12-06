package demo.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
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

import demo.constant.Constants;
import demo.dao.Dao;
import demo.dao.DaoImp;
import demo.model.Account;
import demo.model.AccountCheckAddSum;
import demo.model.Client;
import demo.model.Credential;
import demo.model.Data;
import demo.model.InfoProblem;
import demo.model.SendMoneyForm;
import demo.model.Story;

@Configuration
@EnableWebMvc
@ComponentScan("demo.*")
@EnableTransactionManagement
@Import({ FormLoginSecurityConfig.class })
public class ApplicationContextConfig implements TransactionManagementConfigurer {
	private static Logger log = LoggerFactory.getLogger("demo.controller.ApplicationContextConfig");

	@Bean(name = "loginEntity")
	public Credential getCredential() {
		return new Credential();
	}

	@Bean(name = "sendMoneyForm")
	public SendMoneyForm getSendMoneyForm() {
		return new SendMoneyForm();
	}

	@Bean(name = "data")
	public Data getData() {
		return new Data();
	}

	@Bean(name = "client")
	public Client getClient() {
		return new Client();
	}

	@Bean(name = "accountCheckAddSum")
	public AccountCheckAddSum getAccountCheckAddSum() {
		return new AccountCheckAddSum();
	}

	@Bean
	public InfoProblem infoProblem() {
		return new InfoProblem();
	}

	@Bean(name = "story")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
		String nameMethod = "DataSource";
		DriverAdapterCPDS cpds = new DriverAdapterCPDS();
		try {
			cpds.setDriver("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException e) {
			log.error(nameMethod + Constants.ONE_PARAMETERS, "Error", e);
		}
		cpds.setUrl("jdbc:mysql://localhost:3306/bank?useSSL=false&allowPublicKeyRetrieval=true");
		cpds.setUser("root");
		cpds.setPassword("1234");
		SharedPoolDataSource tds = new SharedPoolDataSource();
		tds.setConnectionPoolDataSource(cpds);
		tds.setMaxTotal(10);
		tds.setMaxConnLifetimeMillis(50);

		return tds;

	}

	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {

		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
		sessionBuilder.addAnnotatedClass(Account.class);
		sessionBuilder.addAnnotatedClass(Client.class);
		sessionBuilder.addAnnotatedClass(Data.class);
		sessionBuilder.addAnnotatedClass(Credential.class);
		sessionBuilder.addAnnotatedClass(Story.class);
		sessionBuilder.setProperty("hibernate.dialect", "MySQL57InnoDB");
		// sessionBuilder.setProperty("hibernate.show_sql", "false");
		sessionBuilder.setProperty("hbm2ddl.auto", "update");
		// sessionBuilder.setProperty("hibernate.use_sql_comments", "false");
		sessionBuilder.setProperty("hibernate.enable_lazy_load_no_trans", "true");
		return sessionBuilder.buildSessionFactory();
	}

	@Autowired
	@Bean(name = "dao")
	public Dao getDao() {
		return new DaoImp();
	}

	@Override
	@Bean(name = "transactionManager")
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory(getDataSource()));
		return transactionManager;
	}
}