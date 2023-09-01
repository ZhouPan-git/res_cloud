package com.yc.bean;

import java.io.Serializable;

/**
 * @Author zp
 * @Date 2023/8/27 16:15
 * @PackageName:com.yc.bean
 * @ClassName: Person
 * @Description:
 * @Version 1.0
 */
public class Person implements Serializable {
    private String name;
    private Integer age;
    private String gender;

    public Person() {
    }

    public Person(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.gender = builder.gender;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder{
        private String name;
        private Integer age;
        private String gender;

        public Builder name(String name){
            this.name=name;
            return this;
        }
        public Builder age(Integer age){
            this.age=age;
            return this;
        }
        public Builder gender(String gender){
            this.gender=gender;
            return this;
        }
        public Person build(){
            return new Person(this);
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}