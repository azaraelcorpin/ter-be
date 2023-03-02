package com.k12.ter.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableAutoConfiguration
@EnableJpaRepositories(
    basePackages = "com.k12.ter.DataSource_TER", 
    entityManagerFactoryRef = "TerEntityManager", 
    transactionManagerRef = "TerTransactionManager"
)
public class DataSource_TerConfig {
    @Autowired
    private Environment env;

    public DataSource_TerConfig() {
        super();
    }

    @Bean(name = "TerEntityManager")
    public LocalContainerEntityManagerFactoryBean TerEntityManager() {
        final LocalContainerEntityManagerFactoryBean EntityManager = new LocalContainerEntityManagerFactoryBean();
        EntityManager.setDataSource(TerDataSource());
        EntityManager.setPackagesToScan("com.k12.ter.DataSource_TER.model");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        EntityManager.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("ter.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
        properties.put("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
        EntityManager.setJpaPropertyMap(properties);

        return EntityManager;
    }

    @Bean
    public DataSource TerDataSource() {
 
        DriverManagerDataSource dataSource
          = new DriverManagerDataSource();
        dataSource.setDriverClassName(
          env.getProperty("ter.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("ter.datasource.url"));
        dataSource.setUsername(env.getProperty("ter.datasource.username"));
        dataSource.setPassword(env.getProperty("ter.datasource.password"));
 
        return dataSource;
    }

    @Bean(name = "TerTransactionManager")
    public PlatformTransactionManager TerTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(TerEntityManager().getObject());
        return transactionManager;
    }
}
