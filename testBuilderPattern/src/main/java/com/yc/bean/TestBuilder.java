package com.yc.bean;

/**
 * @Author zp
 * @Date 2023/8/27 16:26
 * @PackageName:com.yc
 * @ClassName: Test
 * @Description:
 * @Version 1.0
 */
public class TestBuilder {
    public static void main(String[] args) {
        Person p=Person.builder()
                .name("John")
                .age(16)
                .gender("male")
                .build();
        System.out.println(p);
    }
}