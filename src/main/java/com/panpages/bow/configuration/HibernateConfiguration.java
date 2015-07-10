package com.panpages.bow.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.panpages.bow.configuration" })
@PropertySource(value = { "classpath:application.properties" })
public class HibernateConfiguration {

    @Autowired
    private Environment environment;
    
    private static LocalSessionFactoryBean sessionFactory = null;
    
    public static LocalSessionFactoryBean getSessionFactory() {
    	return sessionFactory;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.panpages.bow.model" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        
        if (HibernateConfiguration.sessionFactory == null) {
        	HibernateConfiguration.sessionFactory = sessionFactory;
        }
        
        return sessionFactory;
     }
	
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(getEnvironment().getRequiredProperty(ConfigConstant.JDBC_DRIVER_CLASS_NAME.getName()));
        dataSource.setUrl(getEnvironment().getRequiredProperty(ConfigConstant.JDBC_URL.getName()));
        dataSource.setUsername(getEnvironment().getRequiredProperty(ConfigConstant.JDBC_USERNAME.getName()));
        dataSource.setPassword(getEnvironment().getRequiredProperty(ConfigConstant.JDBC_PASSWORD.getName()));
        return dataSource;
    }
    
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", getEnvironment().getRequiredProperty(ConfigConstant.HIBERNATE_DIALECT.getName()));
        properties.put("hibernate.show_sql", getEnvironment().getRequiredProperty(ConfigConstant.HIBERNATE_SHOW_SQL.getName()));
        properties.put("hibernate.format_sql", getEnvironment().getRequiredProperty(ConfigConstant.HIBERNATE_FORMAT_SQL.getName()));
        return properties;        
    }
    
	@Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(s);
       return txManager;
    }

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
}

