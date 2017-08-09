package cn.sxf.xls.entity;

import java.io.Serializable;

/**
 * Created by fenglonghui on 2017/6/14.
 */

public class Student implements Serializable {

    private String name;
    private String id;
    private int age;
    // 男：true，女： false
    private boolean sex;
    private long weight;

    public Student() {
        super();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", weight=" + weight +
                '}';
    }
}
