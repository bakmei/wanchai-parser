package com.dotdashline.tools.cliparser.token;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.DefaultCLIParser;
import com.dotdashline.tools.cliparser.DefaultHelpCommand;
import com.dotdashline.tools.cliparser.tag.CLICommandTag;

public class CommandTokenTest {

    /**
     * The default implementation of the parser would include the DefaultCommand
     * for handling the case where no command is matched.
     *
     * @throws CLIParserException
     */
    @Test
    @Deprecated
    public void test_default_cases() throws CLIParserException {
        DefaultCLIParser parser = new DefaultCLIParser("xyz");
        Set<Class<?>> actual = parser.getAllCommandClasses();
        
        // token model
        Assert.assertNotNull(actual);
        Assert.assertEquals(1, actual.size());
        Assert.assertEquals(DefaultHelpCommand.class, actual.iterator().next());
    }

    /**
     * Verify if the input command maps to the correct command object instance.
     *
     * @throws CLIParserException
     */
    @Test
    public void test_command_object_map() throws CLIParserException {
        DefaultCLIParser parser = new DefaultCLIParser(CommandClass1.class, CommandClass2.class, CommandClass3.class);
        Assert.assertEquals(CommandClass1.class, parser.parse("Command1").getClass());
        Assert.assertEquals(CommandClass2.class, parser.parse("Command2").getClass());
        Assert.assertEquals(CommandClass3.class, parser.parse("Command3").getClass());
    }

    /**
     * For inner class, define the class as static for the reflection to work.
     * Non-static inner class is not support at this moment.
     * 
     */
    @CLICommandTag("Command1")
    public static class CommandClass1 {
    }

    @CLICommandTag("Command2")
    public static class CommandClass2 {
    }

    @CLICommandTag("Command3")
    public static class CommandClass3 {
    }
}
