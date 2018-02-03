/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser.token;

import com.dotdashline.tools.cliparser.meta.CommandMeta;

/**
 * This class associates the metadata with the token.
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
public class CommandToken {

    // associates with the command metadata
    private CommandMeta meta;

    // associates with the user input commonad
    private String value;

    /**
     * Default constructor.
     *
     * @param meta
     * @param token
     */
    public CommandToken(CommandMeta meta, String token) {
        this.meta = meta;
        this.value = token;
    }

    /**
     * Returns the command metadata.
     * @return the associated command metatdata
     */
    public CommandMeta getMeta() {
        return meta;
    }
    
    /**
     * Returns that command form the user input. 
     * @return the user input command
     */
    public String getValue() {
        return value;
    }
}
