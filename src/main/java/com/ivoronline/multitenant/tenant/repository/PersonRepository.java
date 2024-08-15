package com.ivoronline.multitenant.tenant.repository;

import com.ivoronline.multitenant.tenant.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> { }
