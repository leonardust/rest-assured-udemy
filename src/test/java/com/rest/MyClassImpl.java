package com.rest;

public class MyClassImpl implements MyInterface{

    private MyInterface myInterface;

    public MyInterface printMe() {
        System.out.println("I'm me");
        return myInterface;
    }
}
