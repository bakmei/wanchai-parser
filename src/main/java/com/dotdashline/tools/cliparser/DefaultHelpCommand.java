/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser;

import java.io.PrintStream;
import java.util.List;

import com.dotdashline.tools.cliparser.meta.CommandMeta;
import com.dotdashline.tools.cliparser.meta.MetaModel;
import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIOptionTag;
import com.dotdashline.tools.cliparser.tag.CLIParamTag;
import com.dotdashline.tools.cliparser.token.TokenModel;

/**
 * Leverage the token mechanism to print help messages.
 * 
 * Prints help messages when the command is in the format of "help [command]"
 * 
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
@CLICommandTag(value = DefaultCLIParser.INTERNAL_CMD_HELP, desc = "Display this help message.")
public class DefaultHelpCommand implements Runnable {

	@CLIOptionTag(DefaultCLIParser.INTERNAL_OUTPUT_STREAM)
	private PrintStream printStream;

	@CLIOptionTag(DefaultCLIParser.INTERNAL_TOKEN_MODEL)
	private MetaModel metaModel;

	@CLIOptionTag(DefaultCLIParser.INTERNAL_TOKEN_MODEL)
	private TokenModel tokenModel;

	@CLIParamTag()
	private String commandNeedHelp;

	@Override
	public void run() {
		// For the command of just "help"
		if (commandNeedHelp == null || commandNeedHelp.equals("")) {
			printMetaModel();
		} else {
			printTokenModel();
		}
	}

	private void printMetaModel() {
		List<CommandMeta> metas = metaModel.getAllCommandMetas();
		if (metas != null) {
			metas.stream().forEach(x -> printCommandMeta(x));
		}
	}

	private void printCommandMeta(CommandMeta x) {
		printString(x.getName() + " --> " + x.getDescription());
	}

	private void printString(String string) {
		getPrintStream().print(string);
	}

	private PrintStream getPrintStream() {
		if (printStream == null) {
			printStream = System.out;
		}
		return printStream;
	}

	private void printTokenModel() {
		if (tokenModel != null && tokenModel.getCommandToken() != null
				&& tokenModel.getCommandToken().getMeta() != null) {
			printCommandMeta(tokenModel.getCommandToken().getMeta());
		} else {
			printString("Unknown command.");
		}
	}
}