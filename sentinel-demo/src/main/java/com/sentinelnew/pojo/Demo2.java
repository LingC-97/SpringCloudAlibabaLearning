package com.sentinelnew.pojo;

public class Demo2 {
    public static void main(String[] args) {
        Object[] arr = new Object[4];

        arr[0] = new Object();
        arr[1] = new String("xxx");
        arr[2] = new Integer(10);
        arr[3] = new Demo2();

        for (Object obj1:arr) {
            System.out.println(obj1.toString());
        }
    }

    @Override
    public String toString() {
//        return super.toString();
        return "我是Demo2类";
    }
}
