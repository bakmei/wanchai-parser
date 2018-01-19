/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser.token;

import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.meta.MetaModel;

/**
*
* @author Raymond Tsang
* @author Steven Liang
*
* @since 0.1
*/
public final class TokenModelFactory {

    private TokenModelFactory() {
    }

    public static TokenModel createTokenModel(MetaModel meta, String...tokens) throws CLIParserException {
        return new TokenModel(meta, tokens);
    }
}
