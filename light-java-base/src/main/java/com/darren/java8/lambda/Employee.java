package com.darren.java8.lambda;

import lombok.Data;

@Data
public class Employee {
    private Integer id = 0;
    private String name;
    private Integer age;
    private Long saleary;
    public Status status;

    public Employee(Integer id, String name, Integer age, Long saleary, Status status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.saleary = saleary;
        this.status = status;
    }

    public Employee(String name, Integer age, Long saleary, Status status) {
        this.name = name;
        this.age = age;
        this.saleary = saleary;
        this.status = status;
    }

    public Employee(String name, Integer age, Long saleary) {
        this.id =id ++;
        this.name = name;
        this.age = age;
        this.saleary = saleary;
    }

    public Employee() {
    }

    public Employee(Integer id, Integer age) {
        this.id = id;
        this.age = age;
    }

    public Employee(Integer id) {
        this.id = id;
    }

    public enum Status {
        FREE,
        BUSY,
        VOCATION;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getSaleary() {
        return saleary;
    }

    public void setSaleary(Long saleary) {
        this.saleary = saleary;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
