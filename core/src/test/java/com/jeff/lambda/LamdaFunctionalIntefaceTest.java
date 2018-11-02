package com.jeff.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LamdaFunctionalIntefaceTest {

    @Test
    public void testBiFunction() {
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);

        map.replaceAll((str, inte) -> str.equals("1")? inte : inte + 1000);
        map.forEach((s, i)-> System.out.println(s + " : "+ i));

        Map<String, Integer> smap = new HashMap<>();
        smap.computeIfAbsent("John", s->s.length());
        smap.forEach((s, i) -> System.out.println(s + " : "+ i));
    }

    @Test
    public void testFunction() {
        Function<Integer, String> intToString = s -> s.toString();
        Function<String, String> quote = s -> "'" + s + "'";
        Function<Integer, String> quoteIntToString = quote.compose(intToString);
        assertEquals("'5'", quoteIntToString.apply(5));
    }

    @Test
    public void testSupplier() {
        Supplier<Double> lazyValue = giveSupplier();

        Double d = lazyValue.get();
        System.out.println(d);
        assertTrue(d.equals(9d));
    }

    private Supplier<Double> giveSupplier() {
        System.out.println("give");
        return () -> {
            return 9d;
        };
    }

    @Test
    public void testStream() {
        int[] fibs = {0, 1};
        Stream<Integer> fibonacci = Stream.generate(() -> {
            int result = fibs[1];
            int fib3 = fibs[0] + fibs[1];
            fibs[0] = fibs[1];
            fibs[1] = fib3;
            return result;
        }).limit(10);

        fibonacci.forEach(p-> System.out.println(p));
    }

    @Test
    public void testSum() {
        List<Integer> values = Arrays.asList(3, 5, 8, 9, 12);

        int sum = values.stream()
                .reduce(0, (i1, i2) -> i1 + i2);

        System.out.println(sum);
    }
}
