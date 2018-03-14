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
        Assert.assertEquals(null, parser.parse(null));
    }

    @Test
    public void test_empty_cmd() throws CLIParserException {
        Assert.assertEquals(null, parser.parse(new String[] {}));
    }

    @Test
    public void test_missing_cmd() throws CLIParserException {
        Assert.assertNull(parser.parse(new String[] { "undefined" }));
    }
}
