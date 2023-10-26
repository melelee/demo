package com.melelee.generics.test;

public class Parent<T>
{
    public void sayHello(T value)
    {
        System.out.println("This is Parent Class, value is " + value);
    }
}