package com.ivoronline.springboot_db_multitenant_singleentity_fromtable.master.repository;

import com.ivoronline.springboot_db_multitenant_singleentity_fromtable.master.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Integer> { }
