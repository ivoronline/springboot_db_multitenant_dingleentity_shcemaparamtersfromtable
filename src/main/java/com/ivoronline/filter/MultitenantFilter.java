package com.ivoronline.filter;

import com.ivoronline.multitenant.config.TenantContext;
import com.ivoronline.tenant.entity.Tenant;
import com.ivoronline.tenant.repository.TenantRepository;
import com.ivoronline.multitenant.config.MultiTenantConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@Component
public class MultitenantFilter extends OncePerRequestFilter {

  //PROPERTIES
  @Autowired TenantRepository tenantRepository;
  
  //========================================================================================================
  // DO FILTER INTERNAL
  //========================================================================================================
  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    
    //LOAD TENANTS
    loadTenants();

    //SET TENANT
    String  tenantString = request.getParameter("tenant");
    if (tenantString != null) {
      Integer tenant = Integer.valueOf(tenantString);
      TenantContext.setTenant(tenant);
    }
    
    //CALL NEXT FILTER
    chain.doFilter(request, response);
  
  }
  
  //=========================================================================================================
  // LOAD TENANTS
  //=========================================================================================================
  public void loadTenants() {
  
    //GUARD AGAINST: ALREADY LOADED
    if(MultiTenantConfig.targetDataSources.size() != 0) { return; }
    
    //LOAD TENANTS
    List<Tenant> tenants = tenantRepository.findAll();
    int index = 1;
    for(Tenant tenant : tenants) {
      String     name       = tenant.getName();
      String     password   = tenant.getPassword();
      DataSource datasource = createDataSource(name, password);
      MultiTenantConfig.targetDataSources.put(index++, datasource);
    }
    
    //SET DEFAULT DATA SOURCE
    DataSource defaultDataSource = (DataSource) MultiTenantConfig.targetDataSources.get(0);
    MultiTenantConfig.multitenantDataSource.setDefaultTargetDataSource(defaultDataSource);
    
    //REFRESH DATA SOURCES
    MultiTenantConfig.multitenantDataSource.afterPropertiesSet();
    
  }
  
  //=========================================================================================================
  // CREATE DATA SOURCE
  //=========================================================================================================
  public DataSource createDataSource(String name, String password) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
                            dataSource.setUrl     ("jdbc:oracle:thin:@localhost:1522/orcl");
                            dataSource.setUsername(name);
                            dataSource.setPassword(password);
    return dataSource;
  }
  
}

