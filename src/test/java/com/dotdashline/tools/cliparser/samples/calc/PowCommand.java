package com.dotdashline.tools.cliparser.samples.calc;

import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIParamTag;

/**
 * An implementation class for representing the annotated command.
 *
 * This class will calculate the power of the given base and power numbers.
 * The first parameter is the base and the second parameter is the power.  The rest of the parameters
 * will be ignored.
 *
 */
@CLICommandTag(value="POW", desc="Calculate the power to the first value.")
public class PowCommand implements Calc {

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
        return String.valueOf(Math.pow(values[0], values[1]));
    }
}
