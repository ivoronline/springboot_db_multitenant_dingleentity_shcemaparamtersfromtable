package com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.controller;

import com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.master.repository.TenantRepository;
import com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.schema.config.SchemaContext;
import com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.schema.entity.Person;
import com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.schema.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

  //PROPERTIES
  @Autowired PersonRepository personRepository;
  @Autowired TenantRepository tenantRepository;

  //=========================================================================================================
  // MIX STATEMENTS
  //=========================================================================================================
  @ResponseBody
  @GetMapping("/Hello")
  public Person hello() {
    SchemaContext.setSchema(2);
    Person person = personRepository.save(new Person(0, "John", 22));
    return person;
  }

}
