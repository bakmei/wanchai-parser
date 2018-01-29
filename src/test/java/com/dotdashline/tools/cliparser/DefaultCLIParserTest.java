package com.dotdashline.tools.cliparser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultCLIParserTest {

    private CLIParser parser;

    @Before
    public void setUp() throws Exception {
        // any class is fine
        parser = new DefaultCLIParser(DefaultCLIParserTest.class);
    }

    @Test
    public void test_null_cmd() throws CLIParserException {
        Assert.assertEquals(DefaultCommand.class, parser.parse(null).getClass());
    }

    @Test
    public void test_empty_cmd() throws CLIParserException {
        Assert.assertEquals(DefaultCommand.class, parser.parse(new String[] {}).getClass());
    }

    @Test
    public void test_missing_cmd() throws CLIParserException {
        Assert.assertEquals(DefaultCommand.class, parser.parse(new String[] { "undefined" }).getClass());
    }
}
