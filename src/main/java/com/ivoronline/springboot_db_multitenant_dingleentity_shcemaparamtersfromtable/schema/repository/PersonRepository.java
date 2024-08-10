package com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.schema.repository;

import com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.schema.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> { }
