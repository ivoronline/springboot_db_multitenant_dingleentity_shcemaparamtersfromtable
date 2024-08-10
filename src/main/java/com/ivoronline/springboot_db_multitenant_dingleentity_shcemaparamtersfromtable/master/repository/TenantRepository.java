package com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.master.repository;

import com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.master.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Integer> { }
