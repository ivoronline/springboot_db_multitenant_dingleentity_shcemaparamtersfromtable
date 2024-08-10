package com.ivoronline.schema.repository;

import com.ivoronline.schema.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> { }
