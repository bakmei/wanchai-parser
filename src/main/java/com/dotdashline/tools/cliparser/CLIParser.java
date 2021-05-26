/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser;

/**
 * This interface defines the entry point for parsing the user input.
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
public interface CLIParser {

    /**
     * This is the main entry point for parsing the user input.
     *
     * @param tokens,
     *            a series of tokens inputed by user
     * @return an instant of one of the {@link CLICommandTag} annotated classes
     *         that matches the first token of the user input.
     * @throws CLIParserException
     *             if there were any errors happened during paring the input
     *             tokens
     */
    Object parse(String... tokens) throws CLIParserException;
}
