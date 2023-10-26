package com.melelee.generics.test;

// 定义一个泛型类
public class GenericClass<T> {
    private T value;

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public static void main(String[] args) {

        // 使用泛型类
//        GenericClass<String> stringGeneric = new GenericClass<>();
//        stringGeneric.setValue("Hello");
//        String value = stringGeneric.getValue();

//// 编译后的泛型类型擦除
//        GenericClass stringGeneric = new GenericClass();
//        stringGeneric.setValue("Hello");
//        String value = (String) stringGeneric.getValue();  // 需要进行类型转换

// 运行时类型异常示例
        GenericClass<String> stringGeneric = new GenericClass<>();
        GenericClass<Integer> integerGeneric = new GenericClass<>();

        System.out.println(stringGeneric.getClass() == integerGeneric.getClass());  // 输出: true

        stringGeneric.setValue("Hello");

        try {
            Integer value = integerGeneric.getValue();  // 运行时抛出 ClassCastException 异常
            System.out.println(value);
        } catch (ClassCastException e) {
            System.out.println("ClassCastException: " + e.getMessage());
        }
    }
}

