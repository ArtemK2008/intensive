package com.kalachev.intensive.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.kalachev.intensive.ComponentScanInterface;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:DbProperties")
@ComponentScan(basePackageClasses = {
    ComponentScanInterface.class }, excludeFilters = {
        @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
public class AppConfig {

  @Autowired
  Environment env;

  @Bean
  public BasicDataSource dataSource() {
    BasicDataSource ds = new BasicDataSource();
    ds.setDriverClassName(env.getProperty("JDBC_DRIVER"));
    ds.setUrl(env.getProperty("URL"));
    ds.setUsername(env.getProperty("NAME"));
    ds.setPassword(env.getProperty("PASSWORD"));
    ds.setInitialSize(5);
    ds.setMaxTotal(10);
    return ds;
  }

  @Bean
  public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    sessionFactory.setPackagesToScan("com.kalachev.intensive");
    sessionFactory.setHibernateProperties(hibernateProperties());
    return sessionFactory;
  }

  private final Properties hibernateProperties() {
    Properties hibernateProperties = new Properties();
    hibernateProperties.setProperty("hibernate.hbm2ddl.auto",
        env.getProperty("DLL-MODE"));
    hibernateProperties.put("hibernate.jdbc.batch_size", "5");
    hibernateProperties.put("hibernate.show_sql", true);
    hibernateProperties.setProperty("hibernate.dialect",
        env.getProperty("DIALECT"));
    return hibernateProperties;
  }

  @Bean
  public PlatformTransactionManager hibernateTransactionManager() {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager
        .setSessionFactory(sessionFactory(dataSource()).getObject());
    return transactionManager;
  }

}
