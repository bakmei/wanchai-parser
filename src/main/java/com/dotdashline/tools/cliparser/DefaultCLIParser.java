/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Set;

import com.dotdashline.tools.cliparser.utils.CollectionUtil;

/**
 * This is the default implementation of the {@link CLIParser} interface.
 *
 * This class sub-class from the AbstractCLIParser class to provide the default
 * behavior of the parser.
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 *
 */
public final class DefaultCLIParser extends AbstractCLIParser implements CLIParser {

	public static final String INTERNAL_CMD_HELP = "__CMD_HELP__";
	public static final String INTERNAL_TOKEN_MODEL = "__COMMAND_TOKEN_MODEL__";
	public static final String INTERNAL_OUTPUT_STREAM = "__COMMAND_OUTPUT_STREAM__";

	private PrintStream outputPrintStream;

	/**
	 * This overridden constructor in addition to include the
	 * {@link DefaultHelpCommand}.
	 * 
	 * @param annotatedClasses
	 * @throws CLIParserException
	 */
	public DefaultCLIParser(Class<?>... annotatedClasses) throws CLIParserException {
		super(CollectionUtil.concat(annotatedClasses, new Class<?>[] { DefaultHelpCommand.class }));
	}

	/**
	 * Construct the parser with a collection package names which holds the
	 * annotated Command classes. The classes which are not annotated using
	 * {@see CLICommandTag} will be ignored.
	 * 
	 * @param scanPackageNames
	 *            packages that contains the @see CLICommandTag class.
	 * @throws CLIParserException
	 */
	public DefaultCLIParser(String... scanPackageNames) throws CLIParserException {
		this(gatherCommandClasses(Arrays.asList(scanPackageNames)));
	}

	@Override
	public Object parse(String... tokens) throws CLIParserException {
		try {
			Object cmdObj = super.parse(tokens);
			// if the returned command is the {@link DefaultHelpCommand} (owned
			// by this class),
			// execute it.
			if (cmdObj.getClass().isAssignableFrom(DefaultHelpCommand.class)) {
				try {
					Thread exec = new Thread((DefaultHelpCommand) cmdObj);
					exec.start();
					exec.join();
				} catch (Throwable t) {
					throw new CLIParserException(ErrorCode.INTERNAL_ERROR, t);
				}
			}
			return cmdObj;
		} catch (CLIParserException e) {
			throw e;
		} catch (Throwable t) {
			throw new CLIParserException(ErrorCode.INTERNAL_ERROR, t);
		}
	}

	/**
	 * Should be removed, only used in unit test.
	 *
	 * @return
	 */
	@Deprecated
	public Set<Class<?>> getAllCommandClasses() {
		return metaModel.getAllCommandClasses();
	}

	public void setPrintStream(PrintStream ps) {
		this.outputPrintStream = ps;
	}
}
