package com.darren.java8.optional;

import java.util.Optional;

/**
 * @author Darren
 * @date 2018/4/16
 */
public class Man {

    private Optional<Godness> godness = Optional.empty();

    public Man(Optional<Godness> godness) {
        this.godness = godness;
    }

    public Man() {
    }

    @Override
    public String toString() {
        return "Man{" +
                "godness=" + godness +
                '}';
    }

    public Optional<Godness> getGodness() {
        return godness;
    }

    public void setGodness(Optional<Godness> godness) {
        this.godness = godness;
    }
}
