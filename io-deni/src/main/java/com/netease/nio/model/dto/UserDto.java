package com.netease.nio.model.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2015/7/4
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public class UserDto {

    private String name;
    private Integer age;
    private Double money;
    private String desc;

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getDesc() {
        return desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", money=" + money +
                ", desc='" + desc + '\'' +
                '}';
    }
}
