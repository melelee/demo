package com.melelee.swagger.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Demo {
    public static void main(String[] args) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        Tiger tiger = new Tiger();
        tiger.setName("tiger");
        tiger.setWeight(100L);
        String tigerJson = objectMapper.writeValueAsString(tiger);
        System.out.println(tigerJson);

        extracted(objectMapper, tigerJson);

        Animal animal = new Animal();
        animal.setName("animal");
        String animalJson = objectMapper.writeValueAsString(animal);
        System.out.println(animalJson);

        extracted(objectMapper, animalJson);

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
