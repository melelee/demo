package com.melelee.threadlocal;

public class Demo implements Runnable{

    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("123");
        String string = threadLocal.get();
        System.out.println(string);
        new Thread(new Demo()).start();
        new Thread(new Demo()).start();
        new Thread(new Demo()).start();

    }

    @Override
    public void run() {
        String string = threadLocal.get();
        System.out.println(string);
    }
}
