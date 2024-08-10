package com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.schema.config;

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
