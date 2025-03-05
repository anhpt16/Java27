package com.example.day_04;

public class Dog implements Action, Power{
    @Override
    public void eat() {
        System.out.println("Dog eat Cat");
    }

    @Override
    public void power() {
        System.out.println("Dog powered");
    }
}
