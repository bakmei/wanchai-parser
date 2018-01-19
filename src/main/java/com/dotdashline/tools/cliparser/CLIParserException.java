package com.dotdashline.tools.cliparser;

/**
 * This exception represents any errors that occurred within the CLI parser implementation.
 *
 * @author Raymond Tsang 
 * @author Steven Liang
 *
 * @since 1.0
 *
 */
@SuppressWarnings("serial")
public class CLIParserException extends Exception {

	public CLIParserException() {
	    super();
	}

	public CLIParserException(String arg0) {
		super(arg0);
	}

	public CLIParserException(Throwable arg0) {
		super(arg0);
	}

	public CLIParserException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CLIParserException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
}
