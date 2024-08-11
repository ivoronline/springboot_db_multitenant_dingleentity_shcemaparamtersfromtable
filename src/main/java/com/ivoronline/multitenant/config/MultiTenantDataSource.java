package com.ivoronline.multitenant.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiTenantDataSource extends AbstractRoutingDataSource {

  //=========================================================================================================
  // DETERMINE CURRENT LOOKUP KEY
  //=========================================================================================================
  @Override
  protected Object determineCurrentLookupKey() {
    return TenantContext.getTenant();
  }
    
}
