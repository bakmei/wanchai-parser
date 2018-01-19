package com.dotdashline.tools.cliparser.token;

import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.meta.MetaModel;

public final class TokenModelFactory {

    private TokenModelFactory() {
    }

    public static TokenModel createTokenModel(MetaModel meta, String...tokens) throws CLIParserException {
        return new TokenModel(meta, tokens);
    }
}
