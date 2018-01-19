package com.dotdashline.tools.cliparser.samples.calc;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dotdashline.tools.cliparser.CLIParser;
import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.DefaultCLIParser;
import com.dotdashline.tools.cliparser.tests.utils.Sampling;

/**
 * This class demonstrates how to parse parameters in different ways.
 *
 */
public class SampleTest {

    private CLIParser parser;

    @Before
    public void setup() throws CLIParserException {
        parser = new DefaultCLIParser(new String[] { SampleTest.class.getPackage().getName() });
    }

    @Test
    public void test_add() throws CLIParserException {
        Calc obj = (Calc) parser.parse("ADD 1.1 10".split(" "));
        Assert.assertEquals(new Double(11.1d), new Double(obj.calc()));
    }

    @Test
    public void test_subtract() throws CLIParserException {
        Calc obj = (Calc) parser.parse("SUBTRACT 11.1 10".split(" "));
        Assert.assertEquals(new Double(1.1d), new Double(obj.calc()), 0.00001d);
    }

    @Test
    public void test_add_multiple_params() throws CLIParserException {
        final int numOfParams = 100;

        Double[] numbers = Sampling.sampeDoubles(numOfParams);
        Double ans = Arrays.asList(numbers).stream().mapToDouble(i -> i.doubleValue()).sum();
        Calc obj = (Calc) parser.parse(("ADD" + Sampling.concat(numbers, ' ')).split(" "));
        Assert.assertEquals(ans, new Double(obj.calc()));
    }

    @Test
    public void test_pow() throws CLIParserException {
        Calc obj = (Calc) parser.parse("POW 1.0 1.33".split(" "));
        Assert.assertEquals(1.0d, new Double(obj.calc()), 0.00001d);
    }

}
