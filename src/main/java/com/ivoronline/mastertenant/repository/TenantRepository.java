package com.ivoronline.mastertenant.repository;

import com.ivoronline.mastertenant.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Integer> { }
