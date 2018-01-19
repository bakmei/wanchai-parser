package com.dotdashline.tools.cliparser;

import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIOptionTag;

/**
 * 
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 1.0
 */
@CLICommandTag(value = "help", isDefault = true)
public class DefaultCommand {

    @CLIOptionTag("--help")
    private boolean isHelp;

}
