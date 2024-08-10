package com.ivoronline.springboot_db_multitenant_singleentity_fromtable.schema.repository;

import com.ivoronline.springboot_db_multitenant_singleentity_fromtable.schema.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> { }
