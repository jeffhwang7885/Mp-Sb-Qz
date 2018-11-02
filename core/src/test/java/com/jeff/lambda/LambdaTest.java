package com.jeff.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LambdaTest {
//    =====Java 8 Lambda actions=====
//    Interface Consumer/Function/Supplier/Predicate
//
//    Consumer: 只有input
//    Function: 有input、output, e.g. 物件轉換
//    Supplier: 只有output, e.g. 回傳隨機亂數資料
//    Predicate:有input，回傳true/false, e.g. filter
//
//* 如何處理lambda中的exception,讓程式可讀性更高?
//    hint: 使用functional interface

//    Example:
//            -------------code example----------------
    // example of usage
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        strings.add("1");
        List<Integer> intList = strings.stream().map(rethrowFunction(t -> transformToInteger(t))).collect(Collectors.toList());
        System.out.println(intList.get(0));
    }

    public static Integer transformToInteger(String s) {
        return Integer.valueOf(s);
    }

// example of refactor
    @FunctionalInterface
    public interface Function_WithExceptions<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    public static <T, R, E extends Exception> Function<T, R> rethrowFunction(Function_WithExceptions<T, R, E> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch(Exception e) {
//                throwsAsUnchecked(e);
                return null;
            }
        };
    }

    private static <E extends Throwable> void throwsAsUnchecked(Exception e) throws Exception {
        throw e;
    }
}
