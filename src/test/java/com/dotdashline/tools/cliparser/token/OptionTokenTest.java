package com.dotdashline.tools.cliparser.token;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.DefaultCLIParser;
import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIOptionTag;

public class OptionTokenTest {

    public void test_no_input_options() throws CLIParserException {
        DefaultCLIParser parser = new DefaultCLIParser(TestCommand.class);
        Assert.assertEquals(true,
                ((TestCommand) parser.parse(new String[] { "test", "--boolean", "true" })).pbooleanOption);
        Assert.assertEquals(1111, ((TestCommand) parser.parse(new String[] { "--int", "1111" })).pintOption);
        Assert.assertEquals(2222, ((TestCommand) parser.parse(new String[] { "--long", "2222" })).plongOption);
        Assert.assertEquals(3333.3f, ((TestCommand) parser.parse(new String[] { "--float", "3333.3" })).pfloatOption,
                0.00001);
        Assert.assertEquals(4444.4d, ((TestCommand) parser.parse(new String[] { "--double", "4444.4" })).pdoubleOption,
                0.00001);
        Assert.assertEquals("This is a sentance.",
                ((TestCommand) parser.parse(new String[] { "--String", "This is a sentence." })).stringOption);
        Assert.assertEquals(Boolean.TRUE,
                ((TestCommand) parser.parse(new String[] { "--Boolean", "True" })).booleanOption);
        Assert.assertEquals(new Integer(7777),
                ((TestCommand) parser.parse(new String[] { "--Integer", "7777" })).integerOption);
        Assert.assertEquals(new Long(8888), ((TestCommand) parser.parse(new String[] { "--Long", "8888" })).longOption);
        Assert.assertEquals(new Float(9999.9f),
                ((TestCommand) parser.parse(new String[] { "--Float", "9999.9" })).floatOption);
        Assert.assertEquals(new Double(1234.5d),
                ((TestCommand) parser.parse(new String[] { "--Double", "1234.5" })).doubleOption);
        Assert.assertEquals(new BigInteger("12345678901234567890"),
                ((TestCommand) parser.parse(new String[] { "--BigInteger", "12345678901234567890" })).bigIntegerOption);
    }

    @CLICommandTag("test")
    public static class TestCommand {

        @CLIOptionTag("--boolean")
        public boolean pbooleanOption = false;

        @CLIOptionTag("--int")
        public int pintOption = -1;

        @CLIOptionTag("--long")
        public long plongOption = -1L;

        @CLIOptionTag("--float")
        public float pfloatOption = -1.0f;

        @CLIOptionTag("--double")
        public double pdoubleOption = -1.0d;

        @CLIOptionTag("--String")
        public String stringOption;

        @CLIOptionTag("--Boolean")
        public Boolean booleanOption;

        @CLIOptionTag("--Integer")
        public Integer integerOption;

        @CLIOptionTag("--Long")
        public Long longOption;

        @CLIOptionTag("--Float")
        public Float floatOption;

        @CLIOptionTag("--Double")
        public Double doubleOption;

        @CLIOptionTag("--BigInteger")
        public BigInteger bigIntegerOption;
    }
}
