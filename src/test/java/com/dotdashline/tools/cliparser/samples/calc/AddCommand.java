package com.dotdashline.tools.cliparser.samples.calc;

import java.util.Arrays;

import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIParamTag;

/**
 * An implementation class for representing the annotated command.
 *
 * This class will add up a list of integers.
 *
 */
@CLICommandTag(value = "ADD", desc = "Sum up the given numbers.")
public class AddCommand implements Calc {

    /**
     * The parser will automatically detect the type and fill up the values.
     */
    @CLIParamTag()
    private Double[] values;

    /**
     * Implement the interface for returning a result to the caller. The
     * interface is optional, just to provide a generic way for the parser to
     * execute the command.
     */
    @Override
    public String calc() {
        return String.valueOf(Arrays.asList(values).stream().mapToDouble(i -> i.doubleValue()).sum());
    }
}
