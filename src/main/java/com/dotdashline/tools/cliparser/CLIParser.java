/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser;

import java.util.Set;

/**
 * This interface defines the entry point for parsing the user input.
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 1.0
 */
public interface CLIParser {

    /**
     * This is the main entry point for parser the user input.
     * 
     * @param tokens user input in String array
     * @return an instant of the matched annotated class which matches the input command 
     * @throws CLIParserException if there are any obstacles
     */
    Object parse(String... tokens) throws CLIParserException;
    

    Set<Class<?>> getAllCommandClasses();
}
