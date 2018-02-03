package com.dotdashline.tools.cliparser;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AbstractCLIParserTest {

    private TestParser parser;

    @Before
    public void setUp() throws Exception {
        // any class is fine
        parser = new TestParser(AbstractCLIParserTest.class);
    }

    @Test
    public void test_null_cmd() throws CLIParserException {
        try {
            parser.parse(null);
            fail("Should have thrown CLIParserException.");
        } catch (CLIParserException e) {
            Assert.assertEquals(ErrorCode.NO_INPUT, e.getErrorCode());
        }
    }

    @Test
    public void test_empty_cmd() throws CLIParserException {
        try {
            parser.parse(new String[] {});
            fail("Should have thrown CLIParserException.");
        } catch (CLIParserException e) {
            Assert.assertEquals(ErrorCode.NO_INPUT, e.getErrorCode());
        }
    }

    @Test
    public void test_missing_cmd() throws CLIParserException {
        try {
            parser.parse(new String[] { "undefined" });
            fail("Should have thrown CLIParserException.");
        } catch (CLIParserException e) {
            Assert.assertEquals(ErrorCode.INVALID_COMMAND, e.getErrorCode());
        }
    }

    class TestParser extends AbstractCLIParser implements CLIParser {
        public TestParser(Class<?>... classes) throws CLIParserException {
            super(classes);
        }

    }
}
