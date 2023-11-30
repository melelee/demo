package com.melelee.swagger.jackson;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

public class CustomTypeResolverBuilder extends DefaultTypeResolverBuilder {
    public CustomTypeResolverBuilder() {
        super(DefaultTyping.NON_FINAL);
    }

    @Override
    public boolean useForType(JavaType t) {
        System.out.println(t.getRawClass().getName());
        if (t.getRawClass().getName().startsWith("com.melelee")) {
            return true;
        }
        return false;
    }
}
