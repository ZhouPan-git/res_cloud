package com.yc.bean2;

/**
 * @Author zp
 * @Date 2023/8/27 16:56
 * @PackageName:com.yc.bean2
 * @ClassName: Test2
 * @Description:
 * @Version 1.0
 */
public class Test2 {
    public static void main(String[] args) {
        Apple a= Apple.builder()
                .id(1)
                .name("pg")
                .build();
        System.out.println(a);
    }
}