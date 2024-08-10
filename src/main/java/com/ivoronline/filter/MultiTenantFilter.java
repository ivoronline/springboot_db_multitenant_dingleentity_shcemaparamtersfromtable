package com.ivoronline.filter;

import com.ivoronline.master.repository.TenantRepository;
import com.ivoronline.schema.config.SchemaConfig;
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

@Component
public class MultiTenantFilter extends OncePerRequestFilter {

  //PROPERTIES
  @Autowired TenantRepository tenantRepository;
  
  //========================================================================================================
  // DO FILTER INTERNAL
  //========================================================================================================
  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws IOException, ServletException {

    //SET TENANT
    SchemaConfig.targetDataSources.put(3, dataSource3());
    SchemaConfig.multiRoutingDataSource.afterPropertiesSet();
    chain.doFilter(request, response);
  
  }
  
  //=========================================================================================================
  // DATA SOURCE
  //=========================================================================================================
  public DataSource dataSource3() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
                            dataSource.setUrl            ("jdbc:oracle:thin:@localhost:1522/orcl");
                            dataSource.setUsername       ("SCHEMA3");
                            dataSource.setPassword       ("LETMEIN");
                          //dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
    return dataSource;
  }
  
}

