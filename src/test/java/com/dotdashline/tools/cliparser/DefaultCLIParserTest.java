package com.dotdashline.tools.cliparser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.dotdashline.tools.cliparser.CLIParser;
import com.dotdashline.tools.cliparser.CLIParserException;

public class DefaultCLIParserTest {

    @Mock
    private CLIParser parser;

    @Before
    public void setUp() throws Exception {
    }

    @Test(expected = NullPointerException.class)
    public void test_null_token() throws CLIParserException {
        parser.parse(null);
    }

    @Test(expected = NullPointerException.class)
    public void test_empty_token() throws CLIParserException {
        parser.parse(new String[] {});
    }
}
