package com.ivoronline.master.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
  basePackages            = "com.ivoronline.master.repository",
  entityManagerFactoryRef = "masterEntityManager",
  transactionManagerRef   = "masterTransactionManager"
)
public class MasterConfig {

  //PROPERTIES
  private final String ENTITY_PACKAGE = "com.ivoronline.master.entity";

  //=========================================================================================================
  // DATA SOURCE
  //=========================================================================================================
  @Bean
  @ConfigurationProperties("master.spring.datasource")
  public DataSource masterDataSource() {
    return DataSourceBuilder.create().build();
  }
  
  //=========================================================================================================
  // ENTITY MANAGER FACTORY BEAN
  //=========================================================================================================
  @Bean
  public LocalContainerEntityManagerFactoryBean masterEntityManager() {
      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
                                             em.setDataSource(masterDataSource());
                                             em.setPackagesToScan(ENTITY_PACKAGE);
                                             em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      return em;
  }

  //=========================================================================================================
  // TRANSACTION MANAGER
  //=========================================================================================================
  @Bean
  public PlatformTransactionManager masterTransactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
                            transactionManager.setEntityManagerFactory(masterEntityManager().getObject());
      return transactionManager;
  }
  
  //=========================================================================================================
  // SESSION FACTORY
  //=========================================================================================================
  @Bean
  public LocalSessionFactoryBean masterSessionFactory() {
      LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
                              sessionFactoryBean.setDataSource(masterDataSource());
                              sessionFactoryBean.setPackagesToScan(ENTITY_PACKAGE);
      return sessionFactoryBean;
  }
  
}
