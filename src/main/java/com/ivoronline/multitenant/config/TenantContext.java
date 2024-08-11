package com.ivoronline.multitenant.config;

public class TenantContext {

  private static final ThreadLocal<Integer> tenant = new ThreadLocal<>();
  
  public static void setTenant(Integer newSchema) {
    tenant.set(newSchema);
  }
  
  public static Integer getTenant() {
    return tenant.get();
  }
  
  public static void clear() {
    tenant.remove();
  }
    
}