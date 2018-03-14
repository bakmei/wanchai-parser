/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser;

import java.util.Set;

import com.dotdashline.tools.cliparser.meta.MetaModel;
import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIParamTag;
import com.dotdashline.tools.cliparser.token.CommandToken;

/**
 * Implementation for providing help messages. {@see DefaultCLIParser}
 * 
 * Prints help messages when user input is "help" * or "help <command>"
 * 
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
@CLICommandTag(value = DefaultCLIParser.CMD_HELP_TOKEN, regex=".*", desc = "Display this help message.")
public class DefaultHelpCommand implements Runnable {

    @CLIParamTag()
    private MetaModel metaModel;

    private CommandToken commandToken;
    
    /**
     * Am I abusing threading model?
     */
    @Override
    public void run() {
        if (commandToken != null) {
            if (commandToken.getValue().equals(DefaultCLIParser.CMD_HELP_TOKEN)) {
                printHelp(metaModel.getAllCommandClasses());
            } else { 
                printHelp(commandToken);
            }
        }
        printBasicHelp();
    }

    private void printBasicHelp() {
        // TODO Auto-generated method stub
        
    }

    private void printHelp(CommandToken commandToken2) {
        // TODO Auto-generated method stub
        
    }

    private void printHelp(Set<Class<?>> allCommandClasses) {
        // TODO Auto-generated method stub
        
    }

}
