package com.ivoronline.tenant.repository;

import com.ivoronline.tenant.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> { }
