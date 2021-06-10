/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft;

import javax.naming.NamingException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 *
 * @author faust
 */
@Configuration
@EnableJpaRepositories(
    basePackages = "com.funesoft.repository", 
    entityManagerFactoryRef = "dbEntityManager", 
    transactionManagerRef = "dbTransactionManager"
)
@Profile("test")
public class TestConfigurationMySQL {
    
    @Autowired
    private Environment environment;
    
    public static final String PERSISTENCE_UNIT_NAME = "MySQL";
    
    @Bean
    @Profile("test")
    public LocalContainerEntityManagerFactoryBean dbEntityManager() throws NamingException {
        final LocalContainerEntityManagerFactoryBean em  = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(sqlDataSource());
        em.setPackagesToScan(new String[] {"com.funesoft.model"});
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform(environment.getProperty("spring.datasource.dialect"));
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        return em;
    }
    
    @Bean
    @ConfigurationProperties(prefix="spring.sql-datasource")
    @Profile("test")
    public DataSource sqlDataSource() throws NamingException {
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driverClassName"));
        dataSource.setUrl(environment.getProperty("spring.datasource.jdbcUrl"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return dataSource;
    }
    
    @Bean
    @Profile("test")
    public PlatformTransactionManager dbTransactionManager() throws NamingException {
        final JpaTransactionManager transactionManager  = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(dbEntityManager().getObject());
        return transactionManager;
    }
    
}
