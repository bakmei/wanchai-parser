/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser;

import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIOptionTag;
import com.dotdashline.tools.cliparser.tag.CLIParamTag;

/**
 * Implementation for providing help messages to user. The message will be
 * printed to the standard output.
 *
 * This implementation serves two purposes:
 * 
 * 1. Print help messages when user input is "help" or "-help" or "--help"
 * 2. Captures any  
 * 
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
@CLICommandTag(value = DefaultCLIParser.CMD_HELP_TOKEN, desc = "Display this help message.")
public class DefaultHelpCommand {

    @CLIOptionTag(value = { "--help", "-help", "-H" }, desc = "Display this help message.")
    private boolean isHelp;
    
    @CLIParamTag(desc="")
    private String command;

}
