package com.dotdashline.tools.cliparser.token;

import com.dotdashline.tools.cliparser.meta.CommandMeta;

/**
 * This class associates the metadata with the token.
 *
 * @author Raymond Tsang 
 * @author Steven Liang
 *
 * @since 1.0
 *
 */
public class CommandToken {

    private CommandMeta meta;
    
    private String value;

    public CommandToken(CommandMeta meta, String token) {
        this.meta = meta;
        this.value = token;
    }

    public CommandMeta getMeta() {
        return meta;
    }

}
