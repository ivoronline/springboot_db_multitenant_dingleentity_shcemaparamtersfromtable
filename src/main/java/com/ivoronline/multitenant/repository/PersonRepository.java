package com.ivoronline.multitenant.repository;

import com.ivoronline.multitenant.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> { }
