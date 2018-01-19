package com.dotdashline.tools.cliparser;

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
 * @since 1.0
 *
 */
public class DefaultCLIParser extends AbstractCLIParser implements CLIParser {

    /**
     * This overridden constructor includes the default implementation for the HelpCommand.
     * 
     * @param taggedClasses
     */
    public DefaultCLIParser(Class<?>... taggedClasses) {
        // The DefaultCommand will be used when the first token doesn't matched with any of the commands.
        super(CollectionUtil.concat(taggedClasses, new Class<?>[] { DefaultCommand.class}));
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
        this(findCommandTaggedClasses(Arrays.asList(scanPackageNames)));
    }



}
