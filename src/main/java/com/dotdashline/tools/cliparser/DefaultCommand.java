/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser;

import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIOptionTag;

/**
 * 
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
@CLICommandTag(value = "help", isDefault = true)
public class DefaultCommand {

    @CLIOptionTag("--help")
    private boolean isHelp;

}
