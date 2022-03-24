package com.cms.configuration;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class DBConfig {
	@Bean
    public ComboPooledDataSource dataSource() {
    	ComboPooledDataSource dataSource = new ComboPooledDataSource();
    	try {
			dataSource.setDriverClass(env.getRequiredProperty("db.driver"));
			String url = env.getRequiredProperty("db.url");
			dataSource.setJdbcUrl(url);
	    	dataSource.setUser(env.getRequiredProperty("db.username"));
	    	dataSource.setPassword(env.getRequiredProperty("db.password"));
	    	
	    	dataSource.setInitialPoolSize(Integer.parseInt(env.getRequiredProperty("ds.c3po.init_pool_size")));
	    	dataSource.setMinPoolSize(Integer.parseInt(env.getRequiredProperty("ds.c3po.min_size")));
	    	dataSource.setMaxPoolSize(Integer.parseInt(env.getRequiredProperty("ds.c3po.max_size")));
	    	dataSource.setMaxIdleTime(Integer.parseInt(env.getRequiredProperty("ds.c3po.timeout")));
	    	dataSource.setMaxConnectionAge(Integer.parseInt(env.getRequiredProperty("ds.c3po.maxConnectionAge")));
	    	dataSource.setTestConnectionOnCheckin(true);
	    	dataSource.setIdleConnectionTestPeriod(Integer.parseInt(env.getRequiredProperty("ds.c3po.idleConnectionTestPeriod")));
	    	dataSource.setAutomaticTestTable(env.getRequiredProperty("ds.c3po.automaticTestTable"));
	    	dataSource.setMaxIdleTimeExcessConnections(Integer.parseInt(env.getRequiredProperty("ds.c3po.maxIdleTimeExcessConnections")));
    	} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
    	
        return dataSource;
    }
	
//	@Bean
//	public DataSource dataSource() {
//	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
//	    dataSource.setDriverClassName(env.getProperty("db.driver"));
//	    dataSource.setUrl(env.getProperty("db.url"));
//	    dataSource.setUsername(env.getProperty("db.username"));
//	    dataSource.setPassword(env.getProperty("db.password"));
//	    return dataSource;
//	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	    LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
	    entityManagerFactory.setDataSource(dataSource);
	    
	    entityManagerFactory.setPackagesToScan(env.getProperty("entitymanager.packagesToScan"));
	    
	    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
	    
	    Properties additionalProperties = new Properties();
	    additionalProperties.put(
	        "hibernate.dialect", 
	        env.getProperty("hibernate.dialect"));
	    additionalProperties.put(
	        "hibernate.show_sql", 
	        env.getProperty("hibernate.show_sql"));
	    entityManagerFactory.setJpaProperties(additionalProperties);
	    
	    return entityManagerFactory;
	}
	
	@Bean
	public JpaTransactionManager transactionManager() {
	    JpaTransactionManager transactionManager = new JpaTransactionManager(); 
	    transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
	    return transactionManager;
	}
	  
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
	    return new PersistenceExceptionTranslationPostProcessor();
	}
	
	@Autowired
	private Environment env;

	@Autowired
	private DataSource dataSource;


//	@Autowired
//	private SessionFactory sessionFactory;
	@Autowired
	private LocalContainerEntityManagerFactoryBean entityManagerFactory;
}
