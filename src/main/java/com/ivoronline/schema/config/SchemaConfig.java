package com.ivoronline.schema.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
  basePackages            = "com.ivoronline.schema.repository",
  entityManagerFactoryRef = "multiEntityManager",
  transactionManagerRef   = "multiTransactionManager"
)
public class SchemaConfig {

  //PROPERTIES
  public static Map<Object, Object> targetDataSources = new HashMap<>();
  public static MultiRoutingDataSource multiRoutingDataSource = new MultiRoutingDataSource();
  private final String ENTITY_PACKAGE = "com.ivoronline.schema.entity";
  
  //=========================================================================================================
  // SCHEMA 1 DATA SOURCE
  //=========================================================================================================
  @Primary
  @Bean
  @ConfigurationProperties("schema1.spring.datasource")
  public DataSource schema1DataSource() {
      return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }
  
  //=========================================================================================================
  // SCHEMA 2 DATA SOURCE
  //=========================================================================================================
  @Bean
  @ConfigurationProperties("schema2.spring.datasource")
  public DataSource schema2DataSource() {
      return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }
  
  //=========================================================================================================
  // SCHEMA 3 DATA SOURCE
  //=========================================================================================================
  @Bean
  @ConfigurationProperties("schema3.spring.datasource")
  public DataSource schema3DataSource() {
      return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }
  
  //=========================================================================================================
  // MULTI ROUTING DATA SOURCE
  //=========================================================================================================
  @Bean
  public MultiRoutingDataSource multiRoutingDataSource() {
      
      targetDataSources.put(1, schema1DataSource());
      targetDataSources.put(2, schema2DataSource());
      //targetDataSources.put(3, schema3DataSource());
      
      
                             multiRoutingDataSource.setDefaultTargetDataSource(schema1DataSource());
                             multiRoutingDataSource.setTargetDataSources(targetDataSources);

      return multiRoutingDataSource;
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