package com.ivoronline.springboot_db_multitenant_singleentity_fromtable.schema.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiRoutingDataSource extends AbstractRoutingDataSource {

  //=========================================================================================================
  // DETERMINE CURRENT LOOKUP KEY
  //=========================================================================================================
  @Override
  protected Object determineCurrentLookupKey() {
    return SchemaContext.getSchema();
  }
    
}
