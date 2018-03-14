/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser;

import java.util.Arrays;
import java.util.Set;

import com.dotdashline.tools.cliparser.utils.CollectionUtil;
import com.dotdashline.tools.cliparser.utils.HelpUtil;

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
public class DefaultCLIParser extends AbstractCLIParser implements CLIParser {

    public static final String CMD_HELP_TOKEN = "help";

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

                }
            }
            return cmdObj;
        } catch (CLIParserException e) {
            try {
                HelpUtil.showToConsole(e.getErrorCode().getMessage());
                HelpUtil.showAllCommands(this.metaModel);
            } catch (Exception e2) {
                throw new CLIParserException("Internal Error.", e2);
            }
        } catch (Throwable t) {
            throw new CLIParserException("Internal Error.", t);
        }
        return null;
    }
    //
    // private void handleException(CLIParserException e) throws
    // CLIParserException {
    // if (ErrorCode.INVALID_COMMAND.equals(e.getErrorCode()) ||
    // ErrorCode.NO_INPUT.equals(e.getErrorCode())) {
    // handleInvalidCommand(e);
    // } else if (ErrorCode.INVALID_OPTION.equals(e.getErrorCode())) {
    // handleInvalieOption(e);
    // } else if (ErrorCode.INVALID_PARAM.equals(e.getErrorCode())) {
    // handleInvalieParam(e);
    // } else {
    // // add more handlers here
    // }
    //
    // }
    //
    // private void handleInvalieParam(CLIParserException e) {
    // }
    //
    // private void handleInvalieOption(CLIParserException e) throws
    // CLIParserException {
    // String userCommand = e.getUserInput()[0];
    // HelpUtil.showToConsole("'%s' is not a valid option for command '%s'",
    // "<option>", userCommand);
    // HelpUtil.showSingleCommand(metaModel.getCommand(userCommand));
    // }
    //
    // /**
    // * Handles the invalid command exception. The default behavior should show
    // * the user about all the commands and usage.
    // *
    // * @param e
    // */
    // private void handleInvalidCommand(CLIParserException e) {
    // String userCmd = e.getUserInput() == null ? "" : (e.getUserInput().length
    // == 0 ? "" : e.getUserInput()[0]);
    // HelpUtil.showToConsole("'%s' is not a valid command.", userCmd);
    // HelpUtil.showAllCommands(this.metaModel);
    // }

    /**
     * Should be removed, only used in unit test.
     *
     * @return
     */
    @Deprecated
    public Set<Class<?>> getAllCommandClasses() {
        return metaModel.getAllCommandClasses();
    }
}
