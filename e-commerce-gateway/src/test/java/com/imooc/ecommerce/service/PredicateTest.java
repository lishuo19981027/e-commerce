package com.imooc.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;


/*
Java8 Predicate使用方法与思想
* */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PredicateTest {

    public static List<String> MICRO_SERVICE = Arrays.asList(
            "nacos","authority","gateway","ribbon","feign","hystrix","e-commerce"
    );

    /*text 方法主要用于参数符不符合规则，返回值是boolean*/
    @Test
    public void testPredicateTest(){

        Predicate<String> letterLengthLimit = s->s.length()>=5;
        MICRO_SERVICE.stream().filter(letterLengthLimit).forEach(System.out::println);
    }

    /*and方法等同于我们的逻辑与&&,存在短路特性，需要所有的条件都满足*/
    @Test
    public void testPredicateAnd(){

        Predicate<String> letterLengthLimit = s->s.length()>5;
        Predicate<String> letterStartLimit = s->s.startsWith("gate");
        MICRO_SERVICE.stream().filter(
                letterLengthLimit.and(letterStartLimit)).forEach(System.out::println);
    }

    /*or方法等同于我们的逻辑或||*/
    @Test
    public void testPredicateOr(){

        Predicate<String> letterLengthLimit = s->s.length()>5;
        Predicate<String> letterStartLimit = s->s.startsWith("gate");
        MICRO_SERVICE.stream().filter(
                letterLengthLimit.or(letterStartLimit)).forEach(System.out::println);
    }

    /*negate方法等同于我们的逻辑非！*/
    @Test
    public void testPredicateNegate(){
        Predicate<String> letterStartLimit = s->s.startsWith("gate");
        MICRO_SERVICE.stream().filter(
                letterStartLimit.negate()).forEach(System.out::println);
    }


    /*
    * isEqual类似于equals（）
    * */
    @Test
    public void testPredicateIsEqual(){

        Predicate<String> equalGateway = s -> Predicate.isEqual("gateway").test(s);
        MICRO_SERVICE.stream().filter(equalGateway).forEach(System.out::println);
    }
}
