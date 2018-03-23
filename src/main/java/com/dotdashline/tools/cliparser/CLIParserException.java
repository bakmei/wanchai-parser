/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser;

import java.util.List;
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
   private ErrorCode errorCode;
   private TokenModel tokenModel;
   private List<Exception> subLevelErrors;

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

   public CLIParserException(ErrorCode errorCode, List<Exception> subLevelErrors) {
      this.errorCode = errorCode;
      this.subLevelErrors = subLevelErrors;
   }

   public CLIParserException(ErrorCode errorCode, Exception e) {
      super(e);
      this.errorCode = errorCode;
   }

   public CLIParserException(ErrorCode errorCode, Throwable t) {
      super(t);
      this.errorCode = errorCode;
   }

   public ErrorCode getErrorCode() {
      return errorCode;
   }

   public TokenModel getTokenModel() {
      return tokenModel;
   }

   public String[] getUserInput() {
      return userInput;
   }

   @Override
   public String toString() {
      StringBuilder ret = new StringBuilder();
      ret.append(String.format("Error code: %s%n", errorCode));
      if (userInput != null) {
         ret.append(String.format("User Input: %s%n", userInput));
      }
      if (subLevelErrors != null) {
         ret.append(String.format("Sub-level errors: %s%n", subLevelErrors));
      }
      if (tokenModel != null) {
         ret.append(String.format("Token model: %s%n", tokenModel));
      }
      return ret.toString();
   }

}
