package com.dotdashline.tools.cliparser.utils;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIParamTag;
import com.dotdashline.tools.cliparser.utils.ReflectionUtil;

public class ReflectionUtilTest {

    /**
     * Test if the private field is set.
     * 
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws CLIParserException 
     */
    @Test
    public void test_reflection_set_private() throws IllegalArgumentException, IllegalAccessException, CLIParserException {
        String sample = Sampling.sampleString();
        MyLittleTest obj = new MyLittleTest();
        Field field = ReflectionUtil.getAnnotatedFields(obj.getClass(), CLIParamTag.class).get(0);
        ReflectionUtil.setValueToField(obj, field, sample);
        Assert.assertEquals(sample, obj.getTestField());
    }

    @CLICommandTag("something")
    class MyLittleTest {
        @CLIParamTag
        private String testField;

        public String getTestField() {
            return testField;
        }
    }
}
