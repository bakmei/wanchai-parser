package com.dotdashline.tools.cliparser.meta;

import org.junit.Assert;
import org.junit.Test;

import com.dotdashline.tools.cliparser.tag.CLIParamTag;

public class ParamMetaTest {

    @Test
    public void test() throws NoSuchFieldException, SecurityException {
        ParamMeta m = new ParamMeta(MyClass.class.getDeclaredField("paramField"));
        Assert.assertEquals(MyClass.class.getDeclaredField("paramField"), m.getField());
        Assert.assertEquals(0.99d, m.getWeight(), 0.00001);
        Assert.assertEquals("a sample param.", m.getDescription());
        Assert.assertFalse(m.isArray());
    }
    
    @Test
    public void test_array() throws NoSuchFieldException, SecurityException {
        ParamMeta m = new ParamMeta(MyClass.class.getDeclaredField("arrayField"));
        Assert.assertEquals(MyClass.class.getDeclaredField("arrayField"), m.getField());
        Assert.assertEquals(0.99d, m.getWeight(), 0.00001);
        Assert.assertEquals("a sample array field.", m.getDescription());
        Assert.assertTrue(m.isArray());
    }

    static class MyClass {
        @CLIParamTag(value="a_name", description="a sample param.", weight=0.99d) 
        private boolean paramField;

        @CLIParamTag(value="array", description="a sample array field.", weight=0.99d) 
        private String[] arrayField;
    }
}
