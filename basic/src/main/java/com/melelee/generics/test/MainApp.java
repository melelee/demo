package com.melelee.generics.test;

public class MainApp
{
    public static void main(String[] args)
    {
        Child child = new Child();
        Parent<String> parent = child;

        parent.sayHello("This is a string");
    }
}
