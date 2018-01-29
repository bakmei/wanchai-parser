package com.dotdashline.tools.cliparser.meta;

import org.junit.Assert;
import org.junit.Test;

import com.dotdashline.tools.cliparser.tag.CLIOptionTag;

public class OptionMetaTest {

    @Test
    public void test_regular() throws NoSuchFieldException, SecurityException {
        OptionMeta m = new OptionMeta(MyClass.class.getDeclaredField("optionField"));

        Assert.assertTrue(m.getPrefixes().contains("a_name"));
        Assert.assertEquals(MyClass.class.getDeclaredField("optionField"), m.getField());
        Assert.assertEquals(true, m.isExclusive());
        Assert.assertEquals("a sample option.", m.getDescription());
    }

    @Test
    public void test_alternative() throws NoSuchFieldException, SecurityException {
        OptionMeta m = new OptionMeta(MyClass.class.getDeclaredField("alternativeOptionField"));

        Assert.assertEquals(2, m.getPrefixes().size());
        Assert.assertTrue(m.getPrefixes().contains("--first_prefix"));
        Assert.assertTrue(m.getPrefixes().contains("-second_prefix"));

        Assert.assertEquals(MyClass.class.getDeclaredField("alternativeOptionField"), m.getField());
    }
    
    

    static class MyClass {
        @CLIOptionTag(value = "a_name", description = "a sample option.", exclusive = true)
        private boolean optionField;

        @CLIOptionTag(value = {"--first_prefix", "-second_prefix"}, description = "support alternative option prefix..")
        private boolean alternativeOptionField;
    }
}
