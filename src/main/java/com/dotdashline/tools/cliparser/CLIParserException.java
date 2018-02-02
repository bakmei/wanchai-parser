/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser;

import com.dotdashline.tools.cliparser.token.TokenModel;

/**
 * This exception represents any errors that occurred within the CLI parser
 * implementation.
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 *
 */
@SuppressWarnings("serial")
public class CLIParserException extends Exception {

    private ErrorCode errorCode;
    private String[] userInput;
    private TokenModel tokenModel;

    public TokenModel getTokenModel() {
        return tokenModel;
    }

    public CLIParserException() {
        super();
    }

    public CLIParserException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public CLIParserException(String msg) {
        super(msg);
    }

    public CLIParserException(String msg, ErrorCode errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }

    public CLIParserException(String msg, Throwable arg1) {
        super(msg, arg1);
    }

    public CLIParserException(ErrorCode errorCode, String[] userInput) {
        this.errorCode = errorCode;
        this.userInput = userInput;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String[] getUserInput() {
        return userInput;
    }
}
