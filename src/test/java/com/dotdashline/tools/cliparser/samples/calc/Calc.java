package com.dotdashline.tools.cliparser.samples.calc;

/**
 * An arbitrary interface for generalizing a collection of commands.
 *
 * It is because the parser will return in Object type, it is easy for the command
 * class to implement an interface such that all the commands can be treated the same way.
 * 
 */
public interface Calc {

    /**
     * Returns the result after computation.
     *
     * @return a result in String type.
     */
    public String calc();
}
