package com.ivoronline.springboot_db_multitenant_dingleentity_shcemaparamtersfromtable.schema.config;

public class SchemaContext {

    private static final ThreadLocal<Integer> schema = new ThreadLocal<>();
    
    public static void setSchema(Integer newSchema) {
        schema.set(newSchema);
    }
    
    public static Integer getSchema() {
        return schema.get();
    }
    
    public static void clear() {
        schema.remove();
    }
    
}