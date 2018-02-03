/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser;

public enum ErrorCode {
    INVALID_COMMAND(1), // The input command was invalid, probably undefined.
    INVALID_OPTION(2),  // The input option was invalid, probably undefined.
    INVALID_PARAM(3),   // The input parameter was invalid.
    NO_INPUT(4),        // No user input.
    NOT_AVAILABLE(999);

    private final int levelCode;

    private ErrorCode(int levelCode) {
        this.levelCode = levelCode;
    }
}
