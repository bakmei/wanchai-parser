package com.dotdashline.tools.cliparser;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.DefaultCLIParser;
import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIOptionTag;

public class OptionParsingTest {

    // command for testing the inclusive option mode.
    final String CMD_TEST_IN = "test-inclusive";

    // command for testing the exclusive option mode.
    final String CMD_TEST_EX = "test-exclusive";

    // command for testing the mix option modes.
    final String CMD_TEST_MIX = "test-mix";

    @Test
    public void test_single_exclusive_option() throws CLIParserException {
        DefaultCLIParser parser = new DefaultCLIParser(TestExclusiveOptionsCommand.class);

        Assert.assertEquals(true, ((TestExclusiveOptionsCommand) parser
                .parse(new String[] { CMD_TEST_EX, "--boolean", "true" })).pbooleanOption);
        Assert.assertEquals(111,
                ((TestExclusiveOptionsCommand) parser.parse(new String[] { CMD_TEST_EX, "--short", "111" })).pshortOption);
        Assert.assertEquals(1111,
                ((TestExclusiveOptionsCommand) parser.parse(new String[] { CMD_TEST_EX, "--int", "1111" })).pintOption);
        Assert.assertEquals(2222,
                ((TestExclusiveOptionsCommand) parser.parse(new String[] { CMD_TEST_EX, "--long", "2222" })).plongOption);
        Assert.assertEquals(3333.3f, ((TestExclusiveOptionsCommand) parser
                .parse(new String[] { CMD_TEST_EX, "--float", "3333.3" })).pfloatOption, 0.00001);
        Assert.assertEquals(4444.4d, ((TestExclusiveOptionsCommand) parser
                .parse(new String[] { CMD_TEST_EX, "--double", "4444.4" })).pdoubleOption, 0.00001);
        Assert.assertEquals("This is a sentence.", ((TestExclusiveOptionsCommand) parser
                .parse(new String[] { CMD_TEST_EX, "--String", "This is a sentence." })).stringOption);
        Assert.assertEquals(Boolean.TRUE, ((TestExclusiveOptionsCommand) parser
                .parse(new String[] { CMD_TEST_EX, "--Boolean", "True" })).booleanOption);
        Assert.assertEquals(new Integer(7777), ((TestExclusiveOptionsCommand) parser
                .parse(new String[] { CMD_TEST_EX, "--Integer", "7777" })).integerOption);
        Assert.assertEquals(new Long(8888),
                ((TestExclusiveOptionsCommand) parser.parse(new String[] { CMD_TEST_EX, "--Long", "8888" })).longOption);
        Assert.assertEquals(new Float(9999.9f), ((TestExclusiveOptionsCommand) parser
                .parse(new String[] { CMD_TEST_EX, "--Float", "9999.9" })).floatOption);
        Assert.assertEquals(new Double(1234.5d), ((TestExclusiveOptionsCommand) parser
                .parse(new String[] { CMD_TEST_EX, "--Double", "1234.5" })).doubleOption);
        Assert.assertEquals(new BigInteger("12345678901234567890"), ((TestExclusiveOptionsCommand) parser
                .parse(new String[] { CMD_TEST_EX, "--BigInteger", "12345678901234567890" })).bigIntegerOption);
    }

    @Test
    public void test_single_inclusive_option() throws CLIParserException {
        DefaultCLIParser parser = new DefaultCLIParser(TestInclusiveOptionsCommand.class);

        Assert.assertEquals(true, ((TestInclusiveOptionsCommand) parser
                .parse(new String[] { CMD_TEST_IN, "--boolean=true" })).pbooleanOption);

        Assert.assertEquals(111,
                ((TestInclusiveOptionsCommand) parser.parse(new String[] { CMD_TEST_IN, "--short=111" })).pshortOption);

        Assert.assertEquals(1111,
                ((TestInclusiveOptionsCommand) parser.parse(new String[] { CMD_TEST_IN, "--int=1111" })).pintOption);

        Assert.assertEquals(2222,
                ((TestInclusiveOptionsCommand) parser.parse(new String[] { CMD_TEST_IN, "--long=2222" })).plongOption);

        Assert.assertEquals(3333.3f,
                ((TestInclusiveOptionsCommand) parser.parse(new String[] { CMD_TEST_IN, "--float=3333.3" })).pfloatOption,
                0.00001);

        Assert.assertEquals(4444.4d,
                ((TestInclusiveOptionsCommand) parser.parse(new String[] { CMD_TEST_IN, "--double=4444.4" })).pdoubleOption,
                0.00001);

        Assert.assertEquals("This is a sentence.", ((TestInclusiveOptionsCommand) parser
                .parse(new String[] { CMD_TEST_IN, "--String=This is a sentence." })).stringOption);

        Assert.assertEquals(Boolean.TRUE,
                ((TestInclusiveOptionsCommand) parser.parse(new String[] { CMD_TEST_IN, "--Boolean=true" })).booleanOption);

        Assert.assertEquals(new Integer(7777),
                ((TestInclusiveOptionsCommand) parser.parse(new String[] { CMD_TEST_IN, "--Integer=7777" })).integerOption);

        Assert.assertEquals(new Long(8888),
                ((TestInclusiveOptionsCommand) parser.parse(new String[] { CMD_TEST_IN, "--Long=8888" })).longOption);

        Assert.assertEquals(new Float(9999.9f),
                ((TestInclusiveOptionsCommand) parser.parse(new String[] { CMD_TEST_IN, "--Float=9999.9" })).floatOption);

        Assert.assertEquals(new Double(1234.5d),
                ((TestInclusiveOptionsCommand) parser.parse(new String[] { CMD_TEST_IN, "--Double=1234.5" })).doubleOption);

        Assert.assertEquals(new BigInteger("12345678901234567890"), ((TestInclusiveOptionsCommand) parser
                .parse(new String[] { CMD_TEST_IN, "--BigInteger=12345678901234567890" })).bigIntegerOption);
    }

    @Test
    public void test_all_inclusive_options() throws CLIParserException {
        DefaultCLIParser parser = new DefaultCLIParser(TestInclusiveOptionsCommand.class);

        TestInclusiveOptionsCommand cmd = ((TestInclusiveOptionsCommand) parser.parse(
                new String[] { CMD_TEST_IN, "--boolean=true", "--short=111", "--int=1111", "--long=2222", "--float=3333.3",
                        "--double=4444.4", "--String=This is a sentence.", "--Boolean=true", "--Integer=7777",
                        "--Long=8888", "--Float=9999.9", "--Double=1234.5", "--BigInteger=12345678901234567890" }));

        Assert.assertEquals(true, cmd.pbooleanOption);

        Assert.assertEquals(111, cmd.pshortOption);

        Assert.assertEquals(1111, cmd.pintOption);

        Assert.assertEquals(2222, cmd.plongOption);

        Assert.assertEquals(3333.3f, cmd.pfloatOption, 0.00001);

        Assert.assertEquals(4444.4d, cmd.pdoubleOption, 0.00001);

        Assert.assertEquals("This is a sentence.", cmd.stringOption);

        Assert.assertEquals(Boolean.TRUE, cmd.booleanOption);

        Assert.assertEquals(new Integer(7777), cmd.integerOption);

        Assert.assertEquals(new Long(8888), cmd.longOption);

        Assert.assertEquals(new Float(9999.9f), cmd.floatOption);

        Assert.assertEquals(new Double(1234.5d), cmd.doubleOption);

        Assert.assertEquals(new BigInteger("12345678901234567890"), cmd.bigIntegerOption);

    }

    @Test
    public void test_all_exclusive_options() throws CLIParserException {
        DefaultCLIParser parser = new DefaultCLIParser(TestExclusiveOptionsCommand.class);

        TestExclusiveOptionsCommand cmd = ((TestExclusiveOptionsCommand) parser.parse(new String[] { CMD_TEST_EX,
                "--boolean", "true", "--short", "111", "--int", "1111", "--long", "2222", "--float", "3333.3",
                "--double", "4444.4", "--String", "This is a sentence.", "--Boolean", "true", "--Integer", "7777",
                "--Long", "8888", "--Float", "9999.9", "--Double", "1234.5", "--BigInteger", "12345678901234567890" }));

        Assert.assertEquals(true, cmd.pbooleanOption);

        Assert.assertEquals(111, cmd.pshortOption);

        Assert.assertEquals(1111, cmd.pintOption);

        Assert.assertEquals(2222, cmd.plongOption);

        Assert.assertEquals(3333.3f, cmd.pfloatOption, 0.00001);

        Assert.assertEquals(4444.4d, cmd.pdoubleOption, 0.00001);

        Assert.assertEquals("This is a sentence.", cmd.stringOption);

        Assert.assertEquals(Boolean.TRUE, cmd.booleanOption);

        Assert.assertEquals(new Integer(7777), cmd.integerOption);

        Assert.assertEquals(new Long(8888), cmd.longOption);

        Assert.assertEquals(new Float(9999.9f), cmd.floatOption);

        Assert.assertEquals(new Double(1234.5d), cmd.doubleOption);

        Assert.assertEquals(new BigInteger("12345678901234567890"), cmd.bigIntegerOption);

    }

    /**
     * Test the mix use of inclusive and exclusive mode.
     *
     * @throws CLIParserException
     */
    @Test
    public void test_mix_option_formats() throws CLIParserException {
        DefaultCLIParser parser = new DefaultCLIParser(TestMixOptionsCommand.class);
        TestMixOptionsCommand cmd = ((TestMixOptionsCommand) parser
                .parse(new String[] { CMD_TEST_MIX, "--format1=1111", "--format2", "2222" }));

        Assert.assertEquals(1111, cmd.formatValue1);
        Assert.assertEquals(2222, cmd.formatValue2);
    }

    @CLICommandTag("test-exclusive")
    public static class TestExclusiveOptionsCommand {

        @CLIOptionTag(value = "--boolean", exclusive = true)
        public boolean pbooleanOption = false;

        @CLIOptionTag(value = "--short", exclusive = true)
        public int pshortOption = -1;

        @CLIOptionTag(value = "--int", exclusive = true)
        public int pintOption = -1;

        @CLIOptionTag(value = "--long", exclusive = true)
        public long plongOption = -1L;

        @CLIOptionTag(value = "--float", exclusive = true)
        public float pfloatOption = -1.0f;

        @CLIOptionTag(value = "--double", exclusive = true)
        public double pdoubleOption = -1.0d;

        @CLIOptionTag(value = "--String", exclusive = true)
        public String stringOption;

        @CLIOptionTag(value = "--Boolean", exclusive = true)
        public Boolean booleanOption;

        @CLIOptionTag(value = "--Integer", exclusive = true)
        public Integer integerOption;

        @CLIOptionTag(value = "--Long", exclusive = true)
        public Long longOption;

        @CLIOptionTag(value = "--Float", exclusive = true)
        public Float floatOption;

        @CLIOptionTag(value = "--Double", exclusive = true)
        public Double doubleOption;

        @CLIOptionTag(value = "--BigInteger", exclusive = true)
        public BigInteger bigIntegerOption;
    }

    @CLICommandTag("test-inclusive")
    public static class TestInclusiveOptionsCommand {

        @CLIOptionTag("--boolean")
        public boolean pbooleanOption = false;

        @CLIOptionTag("--short")
        public int pshortOption = -1;

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

    @CLICommandTag("test-mix")
    public static class TestMixOptionsCommand {

        @CLIOptionTag("--format1")
        public int formatValue1 = -1;

        @CLIOptionTag(value = "--format2", exclusive = true)
        public int formatValue2 = -1;
    }
}
