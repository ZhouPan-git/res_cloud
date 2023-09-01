package com.yc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Author zp
 * @Date 2023/8/27 18:37
 * @PackageName:com.yc
 * @ClassName: Test1
 * @Description:
 * @Version 1.0
 */
public class TestPredicate {
    class A implements Predicate<String> {
        @Override
        public boolean test(String s) {
            if (s.length() > 3) {
                return true;
            }
            return false;
        }
    }
    public static void main(String[] args) {
        Predicate p;
        BiPredicate pp;
        //使用Predicate过滤集合
        List<String> ns = Arrays.asList("Alice","Bob","Charlie","David");//创建不可变集合
        List<String> names=new ArrayList<>( ns );//转为可变集合﹐以便后面使用removeIf删除元索

        //定义一个Predicate·判断字符串长度是否大开3
        Predicate<String> lengthPredicate = s -> s.length() > 3;//函数

        // 1.使用匿名内部类创建 Predicate，过滤长度大于 3 的字符串
        names.removeIf(new Predicate<String>() {
            @Override
            public boolean test(String o) {
                if (o.length() > 3) {
                    return true;
                }
                return false;
            }
        });

        //2.使用removeIf方法移除不满足Predicate的元索(即元索长度大于3的字符串都删除)
        names.removeIf(lengthPredicate) ;

        //3. lambda写法
        names. removeIf(o->o.length()>3 ) ;

        System. out.println(names); // [Bob]

        //TODO: Predicate<T> and方法使用
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Predicate<Integer>predicate1 =x->x >3;
        Predicate<Integer> predicate2 = x ->x < 9;

        // 过滤大于3且小于9的元素
        List<Integer> collect = list.stream()
                                    .filter(predicate1.and(predicate2))
                                    .collect(Collectors.toList());
        System.out.println(collect); // [4, 5, 6, 7, 8]

        // or 方法使用
        collect = list.stream()
                      .filter(predicate1.or(predicate2))
                      .collect(Collectors.toList());
        System.out.println(collect); // [1, 2, 3, 4, 5, 6, 7, 8, 9]

        // negate 使用否定
        List<String> list3 = Arrays.asList("java", "c++", "c", "c#", "php", "kotlin", "javascript");
        Predicate<String> predicate3 = x -> x.endsWith("+");
        // 不以 + 号结尾
        List<String> collect3 = list3.stream()
                                     .filter(predicate3.negate())
                                     .collect(Collectors.toList());
        System.out.println(collect3); // [java, c++, c, php, kotlin, javascript]


        // TODO: test 使用
        List<String> list4 = Arrays.asList("java", "c++", "c", "c#", "php", "kotlin", "javascript");
        Predicate<String> predicate4 = x -> x.endsWith("+");

        // 以 + 号结尾
        List<String> collect4 = list4.stream().filter(predicate4::test).collect(Collectors.toList());
        System.out.println(collect4);

        // TODO: predicate 的链式调用
        List<String> list5 = Arrays.asList("java", "c++", "c", "c#", "php", "kotlin", "javascript");
        Predicate<String> predicate5 = x -> x.startsWith("c");
        // 以 C 开头或者以 t 结尾
        boolean ret = predicate5.or(x -> x.endsWith("t")).test("javascript");
        System.out.println(ret); // true

        // 以 C 开头且长度等于 4
        boolean ret2 = predicate5.and(x -> x.length() == 4).negate().test("java");
        System.out.println(ret2); // false

        // 使用 BiPredicate 判断两个字符串是否相等
        BiPredicate<String, String> equalPredicate = (s1, s2) -> s1.equals(s2);
        System.out.println(equalPredicate.test("Hello", "Hello")); // true
        System.out.println(equalPredicate.test("Hello", "World")); // false
    }
}