package com.ivoronline.controller;

import com.ivoronline.master.entity.Tenant;
import com.ivoronline.master.repository.TenantRepository;
import com.ivoronline.schema.config.SchemaConfig;
import com.ivoronline.schema.config.SchemaContext;
import com.ivoronline.schema.entity.Person;
import com.ivoronline.schema.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.DataSource;

@RestController
public class MyController {

  //PROPERTIES
  @Autowired
  PersonRepository personRepository;
  @Autowired
  TenantRepository tenantRepository;
  //@Autowired @Qualifier("multiRoutingDataSource") MultiRoutingDataSource multiRoutingDataSource;
  
  //=========================================================================================================
  // SET
  //=========================================================================================================
  @ResponseBody
  @GetMapping("/Set")
  public String set() {
    
    SchemaConfig.targetDataSources.put(3, dataSource3());
    SchemaConfig.multiRoutingDataSource.afterPropertiesSet();
  
  /*
    Map<Object, DataSource> bla = multiRoutingDataSource.getResolvedDataSources();
    DataSource dataSource = dataSource3();
    bla.put(3, dataSource);
    multiRoutingDataSource.afterPropertiesSet();
   */
  
    /*
    Map<Object, Object> targetDataSources = new HashMap<>();
                        targetDataSources.put(1, dataSource1());
                        targetDataSources.put(2, dataSource2());
                        targetDataSources.put(3, dataSource3());
                        multiRoutingDataSource.setDefaultTargetDataSource(dataSource1());
    multiRoutingDataSource.setTargetDataSources(targetDataSources);
    multiRoutingDataSource.afterPropertiesSet();
    
     */
    
    return "OK";
    
  }
  
  //=========================================================================================================
  // DATA SOURCE
  //=========================================================================================================
  public DataSource dataSource1() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
                            dataSource.setUrl            ("jdbc:oracle:thin:@localhost:1522/orcl");
                            dataSource.setUsername       ("SCHEMA1");
                            dataSource.setPassword       ("LETMEIN");
                          //dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
    return dataSource;
  }
  
  //=========================================================================================================
  // DATA SOURCE
  //=========================================================================================================
  public DataSource dataSource2() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
                            dataSource.setUrl            ("jdbc:oracle:thin:@localhost:1522/orcl");
                            dataSource.setUsername       ("SCHEMA2");
                            dataSource.setPassword       ("LETMEIN");
                          //dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
    return dataSource;
  }
  
  //=========================================================================================================
  // DATA SOURCE
  //=========================================================================================================
  public DataSource dataSource3() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
                            dataSource.setUrl            ("jdbc:oracle:thin:@localhost:1522/orcl");
                            dataSource.setUsername       ("SCHEMA3");
                            dataSource.setPassword       ("LETMEIN");
                          //dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
    return dataSource;
  }
  
  //=========================================================================================================
  // HELLO
  //=========================================================================================================
  @ResponseBody
  @GetMapping("/Hello")
  public Tenant hello() {
    SchemaContext.setSchema(3);
    Person person = personRepository.save(new Person(0, "John", 33));
    Tenant tenant = tenantRepository.findById(1).get();
    return tenant;
  }
  
}
