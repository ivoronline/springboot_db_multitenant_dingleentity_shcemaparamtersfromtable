package com.ivoronline.master.repository;

import com.ivoronline.master.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Integer> { }
