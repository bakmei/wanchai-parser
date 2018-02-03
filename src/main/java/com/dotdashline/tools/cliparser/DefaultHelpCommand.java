/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser;

import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIParamTag;

/**
 * Implementation for providing help messages to user {@see DefaultCLIParser}.
 * 
 * Prints help messages when user input is "help"
 * or "help <command>"
 * 
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
@CLICommandTag(value = DefaultCLIParser.CMD_HELP_TOKEN, desc = "Display this help message.")
public class DefaultHelpCommand {

    @CLIParamTag(desc = "")
    private String command;
}
