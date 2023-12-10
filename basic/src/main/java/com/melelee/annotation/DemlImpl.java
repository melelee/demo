package com.melelee.annotation;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class DemlImpl extends Demo {
    @Override
    public void getInfo() {
        System.out.println(1);
    }

    @Override
    public void setInfo() {

    }

    public static void main(String[] args) throws NoSuchMethodException {
        Class<DemlImpl> aClass = DemlImpl.class;

        System.out.println(aClass.getAnnotation(Transactional.class));

        Annotation[] annotations = aClass.getAnnotations();
        extracted(annotations);

        System.out.println("---------------------------");

        Method method = aClass.getMethod("getInfo", null);
        Annotation[] annotations1 = method.getDeclaredAnnotations();
        extracted(annotations1);


    }

    private static void extracted(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            System.out.println(annotation.annotationType().getName());
        }
    }
}
