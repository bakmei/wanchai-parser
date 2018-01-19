package com.dotdashline.tools.cliparser.meta;

import org.junit.Assert;
import org.junit.Test;

import com.dotdashline.tools.cliparser.tag.CLIOptionTag;


public class OptionMetaTest {

    @Test
    public void test() throws NoSuchFieldException, SecurityException {
        OptionMeta m = new OptionMeta(MyClass.class.getDeclaredField("optionField"));
        Assert.assertEquals("a_name", m.getName());
        Assert.assertEquals(MyClass.class.getDeclaredField("optionField"), m.getField());
        Assert.assertEquals(99, m.getAddtionalTokenCount());
        Assert.assertEquals("a sample option.", m.getDescription());
    }

    static class MyClass {
        @CLIOptionTag(value="a_name", description="a sample option.", addtionalTokenCount = 99) 
        private boolean optionField;
    }
}
