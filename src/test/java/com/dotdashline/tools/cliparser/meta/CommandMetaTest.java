package com.dotdashline.tools.cliparser.meta;

import org.junit.Assert;
import org.junit.Test;

import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIOptionTag;
import com.dotdashline.tools.cliparser.tag.CLIParamTag;

public class CommandMetaTest {

    /**
     * Make sure it returns the same class.
     */
    @Test
    public void test() {
        CommandMeta m = new CommandMeta(MyCommand.class);
        Assert.assertEquals(MyCommand.class, m.getCommandClass());
    }

    /**
     * Make sure null won't be accepted by the constructor.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_null() {
        new CommandMeta(null);
    }  

    /**
     * Make sure the non-annotated class won't be accepted.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_not_annotated_class() {
        new CommandMeta(ClassWithoutAnnotation.class);
    }

    @Test
    public void test_parameters() {
        
    }

    @CLICommandTag("mycommand")
    static class MyCommand{
        @CLIOptionTag(value="a_name", desc="a sample option.") 
        private boolean optionField;

        @CLIParamTag(value="a_name", desc="a sample param.", weight=0.99d) 
        private String[] paramField;
    }

    static class ClassWithoutAnnotation {
        @CLIOptionTag(value="a_name", desc="a sample option.") 
        private boolean optionField;

        @CLIParamTag(value="a_name", desc="a sample param.", weight=0.99d) 
        private String[] paramField;
    } 

    @CLICommandTag("mycommand")
    static class My{
        @CLIOptionTag(value="a_name", desc="a sample option.") 
        private boolean optionField;

        @CLIParamTag(value="a_name", desc="a sample param.", weight=0.99d) 
        private String[] paramField;
    }  
    
}
