package com.sentinelnew.controller;

public class Cat {
    int age ;
    public Cat(){
//        this(1);
        this.age = 1;

    }
    public Cat(int age){
        this.age = age ;
        System.out.println(age);

    }

    public static void main(String[] args) {
        Cat c = new Cat(1121);
        Cat cat= new Cat();
    }

}

