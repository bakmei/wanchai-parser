package com.dotdashline.tools.cliparser.samples.calc;

import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIParamTag;

/**
 * An implementation class for representing the annotated command.
 *
 * This class will subtract the former parameters from the first parameters.
 *
 */
@CLICommandTag("SUBTRACT")
public class SubtractCommand implements Calc {

    /**
     * The parser will automatically detect the type and fill up the values.
     */
    @CLIParamTag
    private Double[] values;

    /**
     * Implement the interface for returning a result to the caller.
     * The interface is optional, just to provide a generic way for the parser to execute
     * the command.
     */
    @Override
    public String calc() {
        double ret = values[0];
        for (int i = 1; i < values.length; i++) {
            ret -= values[i];
        }
        return String.valueOf(ret);
    }
}
