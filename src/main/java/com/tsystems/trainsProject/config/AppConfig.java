package com.tsystems.trainsProject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScans(value = { @ComponentScan("com.tsystems.trainsProject")})
public class AppConfig {

        @Value("${spring.datasource.driver-class-name}")
        private String dbDriver;

        @Value("${spring.datasource.url}")
        private String dbUrl;

        @Value("${spring.datasource.data-username}")
        private String dbUserName;

        @Value("${spring.datasource.data-password}")
        private String dbPassword;

        @Value("${spring.jpa.properties.dialect}")
        private String hibernateDialect;

        @Value("${spring.jpa.show-sql}")
        private String hibernateShowDdl;

        @Value("${spring.jpa.hibernate.ddl-auto}")
        private String hibernateDdlAuto;
        @Value("${spring.jpa.hibernate.use-new-id-generator-mappings}")
        private String hibernateNewUserIdGenertorMapping;

        @Value("${entitymanager.packagesToScan}")
        private String entityPackegesToScan;

        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(dbDriver);
            dataSource.setUrl(dbUrl);
            dataSource.setUsername(dbUserName);
            dataSource.setPassword(dbPassword);
            try {
                dataSource.getConnection().getCatalog();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return dataSource;
        }

        @Bean
        public LocalSessionFactoryBean localSessionFactory() {
            LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
            sessionFactoryBean.setDataSource(dataSource());
            sessionFactoryBean.setPackagesToScan(entityPackegesToScan);
            Properties hibernateProperties = new Properties();
            hibernateProperties.put("spring.jpa.properties.dialect", hibernateDialect);
            hibernateProperties.put("spring.jpa.show-sql", hibernateShowDdl);
            hibernateProperties.put("spring.jpa.hibernate.ddl-auto", hibernateDdlAuto);
            hibernateProperties.put("spring.jpa.hibernate.use-new-id-generator-mappings", hibernateNewUserIdGenertorMapping);
            sessionFactoryBean.setHibernateProperties(hibernateProperties);
            return sessionFactoryBean;
        }

        @Bean
        public HibernateTransactionManager transactionManager() {
            HibernateTransactionManager transactionManager = new HibernateTransactionManager();
            transactionManager.setSessionFactory(localSessionFactory().getObject());
            return transactionManager;
        }
    }
