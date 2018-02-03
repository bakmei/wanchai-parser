/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser;

import java.util.Optional;

import com.dotdashline.tools.cliparser.token.TokenModel;

/**
 * This exception class represents any errors that occurred during the parsing
 * process.
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 *
 */
@SuppressWarnings("serial")
public class CLIParserException extends Exception {

    private String[] userInput;
    private Optional<ErrorCode> errorCode = Optional.empty();
    private Optional<TokenModel> tokenModel = Optional.empty();

    public CLIParserException() {
        super();
    }

    public CLIParserException(ErrorCode errorCode) {
        super();
        this.errorCode = Optional.of(errorCode);
    }

    public CLIParserException(String msg) {
        super(msg);
    }

    public CLIParserException(String msg, ErrorCode errorCode) {
        super(msg);
        this.errorCode = Optional.of(errorCode);
    }

    public CLIParserException(String msg, Throwable arg1) {
        super(msg, arg1);
    }

    public CLIParserException(ErrorCode errorCode, String[] userInput) {
        this.errorCode = Optional.of(errorCode);
        this.userInput = userInput;
    }

    public ErrorCode getErrorCode() {
        return errorCode.orElse(ErrorCode.NOT_AVAILABLE);

    }

    public TokenModel getTokenModel() {
        return tokenModel.orElse(null);
    }

    public String[] getUserInput() {
        return userInput;
    }
}
