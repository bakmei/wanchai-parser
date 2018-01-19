package com.dotdashline.tools.cliparser.samples.wording;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dotdashline.tools.cliparser.CLIParser;
import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.DefaultCLIParser;

/**
 * This class demonstrates how to use option parameters.
 *
 */
public class SampleTest {

    private CLIParser parser;

    @Before
    public void setup() throws CLIParserException {
        parser = new DefaultCLIParser(new String[] { this.getClass().getPackage().getName() });
    }

    @Test
    public void test_word_fun() throws CLIParserException {
        WordCommand obj = (WordCommand) parser.parse("SHOWME How are you".split(" "));
        Assert.assertEquals("How are you", obj.execute());
    }

    @Test
    public void test_word_fun_reverse() throws CLIParserException {
        WordCommand obj = (WordCommand) parser.parse("SHOWME --reverse you are How".split(" "));
        Assert.assertEquals("How are you", obj.execute());
    }
}
