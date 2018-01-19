package com.dotdashline.tools.cliparser.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dotdashline.tools.cliparser.utils.CollectionUtil;

public class CollectionUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test_concat_arrays_base() {
        Assert.assertNull(CollectionUtil.concat(null));
        Assert.assertArrayEquals(new Integer[] {}, CollectionUtil.concat(new Integer[] {}));
        Assert.assertArrayEquals(new Integer[] { 1 }, CollectionUtil.concat(new Integer[] { 1 }));
    }

    @Test
    public void test_concat_arrays_two() {
        Assert.assertArrayEquals(new Integer[] { 1, 2, 3 },
                CollectionUtil.concat(new Integer[] { 1 }, new Integer[] { 2, 3 }));
    }

}
