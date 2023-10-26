package com.melelee.generics.test;

public class Child extends Parent<String> {
    public void sayHello(String value) {
        System.out.println("This is Child class, value is " + value);
    }

    public static void main(String[] args) {
        System.out.println("sdf");
    }
}