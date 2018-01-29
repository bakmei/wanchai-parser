/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
public class CollectionUtil {

    /**
     * Transfer the elements from array to queue.
     * 
     * @param tokens
     * @return
     */
    public static <T> Queue<T> arrayToQueue(T[] tokens) {
        Queue<T> ret = new ArrayBlockingQueue<>(tokens.length);
        Arrays.asList(tokens).stream().forEach(x -> ret.add(x));
        return ret;
    }

    /**
     * Concatenates two or more arrays.
     *
     * @param arrays
     * @return
     */
    @SafeVarargs
    public static <T> T[] concat(T[]... arrays) {
        if (arrays == null) {
            return null;
        }
        if (arrays.length == 0) {
            return null;
        }
        if (arrays.length == 1) {
            return arrays[0];
        }

        int count = Stream.of(arrays).mapToInt(x -> x.length) .sum();
        @SuppressWarnings("unchecked")
        T[] ret = (T[]) Array.newInstance(arrays.getClass().getComponentType().getComponentType(), count);
        return Stream.of(arrays).flatMap(Stream::of).collect(Collectors.toList()).toArray(ret);
    }

    public static <T> List<String> queueToList(Queue<String> tokens) {
        return tokens.stream().collect(Collectors.toList());
    }
}
