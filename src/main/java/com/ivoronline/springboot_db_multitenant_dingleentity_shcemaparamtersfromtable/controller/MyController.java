package com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.controller;

import com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.master.entity.Tenant;
import com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.master.repository.TenantRepository;
import com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.schema.config.MultiRoutingDataSource;
import com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.schema.config.SchemaContext;
import com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.schema.entity.Person;
import com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.schema.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MyController {

  //PROPERTIES
  @Autowired PersonRepository personRepository;
  @Autowired TenantRepository tenantRepository;
  @Autowired @Qualifier("multiRoutingDataSource") MultiRoutingDataSource multiRoutingDataSource;
  
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
  
  //=========================================================================================================
  // SET
  //=========================================================================================================
  @ResponseBody
  @GetMapping("/Set")
  public String set() {
    Map<Object, Object> targetDataSources = new HashMap<>();
                        targetDataSources.put(3, dataSource3());
    multiRoutingDataSource.setTargetDataSources(targetDataSources);
    return "OK";
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

}
