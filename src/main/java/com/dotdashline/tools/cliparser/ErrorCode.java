/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser;

/**
 * A collection of parser error codes.
 * 
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
public enum ErrorCode {
   INVALID_COMMAND(1, "Invalid command."), // The input command was invalid,
                                           // probably undefined.
   INVALID_OPTION(2, "Invalid option."), // The input option was invalid,
                                         // probably undefined.
   INVALID_PARAM(3, "Invalid parameter"), // The input parameter was invalid.
   NO_INPUT(4, "No user input."),
   INTERNAL_ERROR(500, "Internal error."); // No user input.

   private final int levelCode;
   private final String message;

   private ErrorCode(int levelCode, String msg) {
      this.levelCode = levelCode;
      this.message = msg;
   }

   public String getMessage() {
      return message;
   }
}
