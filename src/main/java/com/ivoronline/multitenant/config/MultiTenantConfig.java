package com.ivoronline.multitenant.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackages            = "com.ivoronline.multitenant.repository",
  entityManagerFactoryRef = "multiEntityManager",
  transactionManagerRef   = "multiTransactionManager"
)
public class MultiTenantConfig {

  //PROPERTIES
  public static Map<Object, Object>   targetDataSources     = new HashMap<>();
  public static MultiTenantDataSource multitenantDataSource = new MultiTenantDataSource();
  private final String ENTITY_PACKAGE = "com.ivoronline.multitenant.entity";
  
  //=========================================================================================================
  // DEFAULT DATA SOURCE
  //=========================================================================================================
  @Primary
  @Lazy
  @Bean
  @ConfigurationProperties("default.spring.datasource")
  public DataSource defaultDataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }
  
  //=========================================================================================================
  // MULTI ROUTING DATA SOURCE
  //=========================================================================================================
  @Bean
  public MultiTenantDataSource multiRoutingDataSource() {
    
    multitenantDataSource.setDefaultTargetDataSource(defaultDataSource());
    multitenantDataSource.setTargetDataSources(targetDataSources);

    return multitenantDataSource;
    
  }
  
  //=========================================================================================================
  // ENTITY MANAGER FACTORY BEAN
  //=========================================================================================================
  @Bean
  public LocalContainerEntityManagerFactoryBean multiEntityManager() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
                                           em.setDataSource(multiRoutingDataSource());
                                           em.setPackagesToScan(ENTITY_PACKAGE);
                                           em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
                                           em.setJpaProperties(hibernateProperties());
    return em;
  }
  
  //=========================================================================================================
  // TRANSACTION MANAGER
  //=========================================================================================================
  @Bean
  public PlatformTransactionManager multiTransactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
                          transactionManager.setEntityManagerFactory(multiEntityManager().getObject());
    return transactionManager;
  }
  
  //=========================================================================================================
  // SESSION FACTORY
  //=========================================================================================================
  @Primary
  @Bean
  public LocalSessionFactoryBean dbSessionFactory() {
    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
                            sessionFactoryBean.setDataSource(multiRoutingDataSource());
                            sessionFactoryBean.setPackagesToScan(ENTITY_PACKAGE);
                            sessionFactoryBean.setHibernateProperties(hibernateProperties());
    return sessionFactoryBean;
  }
  
  //=========================================================================================================
  // HIBERNATE PROPERTIES
  //=========================================================================================================
  private Properties hibernateProperties() {
    Properties properties = new Properties();
               properties.put("hibernate.show_sql"  , true);
               properties.put("hibernate.format_sql", true);
    return properties;
  }
  
}