package com.darren.java8.optional;

/**
 * @author Darren
 * @date 2018/4/16
 */
public class Godness {

    private String name;

    public Godness() {
    }

    public Godness(String name) {

        this.name = name;
    }

    @Override
    public String toString() {
        return "Godness{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
