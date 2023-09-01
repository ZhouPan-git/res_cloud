package com.yc;

import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @Author zp
 * @Date 2023/8/27 20:28
 * @PackageName:com.yc
 * @ClassName: Test3_reactor
 * @Description:
 * @Version 1.0
 */
public class Test3_reactor {
    public static void main(String[] args) {
        Test3_reactor t = new Test3_reactor();

        // 1. 使用普通 for 循环
        List<Integer> list = t.all();
        for (Integer i : list) {
            System.out.print(i + "\t");
        }
        System.out.println();
        for (int i=0; i<list.size(); i++) {
            System.out.print(list.get(i)+"\t");
        }
        System.out.println();

        // 2. Stream 方法
        Stream<Integer> stream = t.all2(); // 获取 Stream
        // 传统写法使用 map() 进行映射，并打印结果
        stream.map(new Function<Integer, String>() {
            @SneakyThrows
            @Override
            public String apply(Integer o) {
                Thread.sleep(500);
                return o + "\t";
            }
        }).map(o->o+";")
                .map(o->o+"="+o.length())
                .map(o->o+"\n")
                .filter(o->o.startsWith("1"))
                .sorted()
                .forEach(System.out::print);

        // 2.1 升级 Lambda
        //stream.map(v -> v + "\t").forEach(System.out::print);
        System.out.println();

        // 3. Flux
        Flux<Integer> flux = t.all3();
        flux.map(o -> o + "\t").subscribe(System.out::print);
        System.out.println();

        // 4. Mono
        Mono<String> mono = t.all4();
        mono.as(v -> v.map(o ->o+"\tmap").subscribe(System.out::println));
    }
    // 1. 传统写法
    public List<Integer> all() {
        return Arrays.asList(1, 2, 3, 4, 5, 6);
    }

    // 2. Stream写法
    public Stream<Integer> all2() {
        return Stream.of(1, 2, 3, 4, 5, 6);
    }

    // 3. 反应式Flux
    public Flux<Integer> all3() {
        // return Flux.fromArray(new Integer[]{1, 2, 3, 4, 5, 6});
        // 还有 fromIterable, fromStream
        return Flux.just(1, 2, 3, 4, 5, 6);
    }

    // 4. 反应式Mono
    public Mono<String> all4() {
        return Mono.just("hello world");
    }
}