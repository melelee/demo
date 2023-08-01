package com.melelee.spring.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Arrays;

/**
 * @author menglili
 */
public class Demo {

    public static void main(String[] args) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);

        Arrays.stream(beanInfo.getPropertyDescriptors())
                .forEach(System.out::println);
    }
}
