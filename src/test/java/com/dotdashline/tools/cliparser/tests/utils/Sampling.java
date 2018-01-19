package com.dotdashline.tools.cliparser.tests.utils;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

/**
 * This class provides different sampling values for testing.
 *
 */
public class Sampling {

    public static <T> String concat(T[] elements, Character separator) {
        StringBuilder sb = new StringBuilder();
        Arrays.asList(elements).stream().forEach(x -> sb.append(separator).append(x.toString()));
        return sb.toString();
    }

    public static Integer[] sampeInts(int num) {
        Integer[] ret = new Integer[num];
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < ret.length; i++) {
            ret[i] = r.nextInt();
        }
        return ret;
    }

    public static Double[] sampeDoubles(int num) {
        Double[] ret = new Double[num];
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < ret.length; i++) {
            ret[i] = r.nextDouble();
        }
        return ret;
    }

    public static String sampleString() {
        return UUID.randomUUID().toString();
    }
}
