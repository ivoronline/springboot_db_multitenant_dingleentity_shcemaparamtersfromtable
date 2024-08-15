package com.ivoronline.multitenant.mastertenant.repository;

import com.ivoronline.multitenant.mastertenant.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Integer> { }
