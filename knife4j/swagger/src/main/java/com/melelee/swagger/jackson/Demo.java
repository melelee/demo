package com.melelee.swagger.jackson;

import cn.hutool.db.PageResult;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        TypeResolverBuilder<?> typeResolver = new CustomTypeResolverBuilder();
        typeResolver.init(JsonTypeInfo.Id.NAME, null);
        typeResolver.inclusion(JsonTypeInfo.As.PROPERTY);
        objectMapper.setDefaultTyping(typeResolver);


//        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);




        Tiger tiger = new Tiger();
        tiger.setName("tiger");
        tiger.setWeight(100L);
        Result<Tiger> success = new Result<>(false, "success", 0, tiger);

        objectMapper.writeValue(System.out, success);

    }

    private static void extracted(ObjectMapper objectMapper, String tigerJson) throws JsonProcessingException {
        try {
            Animal animal = objectMapper.readValue(tigerJson, Animal.class);
            System.out.println(animal);

            Animal animal1 = objectMapper.readValue(tigerJson, Tiger.class);
            System.out.println(animal1);

            Animal animal2 = objectMapper.readValue(tigerJson, Lion.class);
            System.out.println(animal2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
