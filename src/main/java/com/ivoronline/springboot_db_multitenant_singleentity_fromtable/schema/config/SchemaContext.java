package com.ivoronline.springboot_db_multitenant_singleentity_fromtable.schema.config;

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