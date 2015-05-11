package com.demo.SpringBootTest1.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;
 
@Configuration
@EnableTransactionManagement
public class DataSourceConfig{
	
	@Value("${hibernate.dialect}")
	private  String HIBERNATE_DIALECT;
	
	@Value("${hibernate.show_sql}")
	private   String SHOW_SQL;
	
	@Value("${hibernate.format_sql}")
	private   String FORMAT_SQL;
	
	@Value("${hibernate.hbm2ddl.auto}")
	private   String HBM2DDL_AUTO;
	
	@Value("${entity.package.scan}")
	private  String ENTITY_PACKAGE;
	
	
	@Value("${db.driver}")
	private String DB_DRIVER;
	
	@Value("${db.url}")
	private String DB_URL;
	
	@Value("${db.username}")
	private String DB_USERNAME;
	
	@Value("${db.password}")
	private String DB_PASSWORD;
	
	
	
	@Bean
	public DataSource  dataSource1() throws PropertyVetoException{
		ComboPooledDataSource pool=new ComboPooledDataSource();
		pool.setDriverClass(DB_DRIVER);
		pool.setDataSourceName("dataSource1");
		pool.setJdbcUrl(DB_URL);
		pool.setInitialPoolSize(3);
		pool.setMaxIdleTime(2000);
		pool.setMaxPoolSize(6);
		pool.setUser(DB_USERNAME);
		pool.setPassword(DB_PASSWORD);
		return pool;
	}
	
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() throws PropertyVetoException{
		LocalSessionFactoryBean sessionFactory=new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource1());
		sessionFactory.setPackagesToScan(ENTITY_PACKAGE);
		Properties pro=new Properties();
		pro.put("hibernate.dialect",HIBERNATE_DIALECT);
		pro.put("hibernate.show_sql", SHOW_SQL);
		pro.put("hibernate.format_sql", FORMAT_SQL);
		pro.put("hibernate.hbm2ddl.auto", HBM2DDL_AUTO);
		
		sessionFactory.setHibernateProperties(pro);
		return sessionFactory;
	}
	
	@Bean
	public HibernateTransactionManager transactionManager() throws PropertyVetoException{
		HibernateTransactionManager transactionFactory=new HibernateTransactionManager();
		transactionFactory.setSessionFactory(sessionFactory().getObject());
		return transactionFactory;
	}
	
	
	
	
	
	
	 
}
