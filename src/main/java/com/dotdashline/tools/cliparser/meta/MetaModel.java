/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.tag.CLICommandTag;

/**
 * This class defines the root of the model.
 * 
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
public class MetaModel {
    private Map<String, CommandMeta> commands = new HashMap<>();

    /**
     * Creating a metadata to associate with the command tagged class. if the
     * given class is annotated as default., then memorize it.
     *
     * @param c
     */
    public void addCommand(Class<?> c) {
        Optional.ofNullable(c.getAnnotation(CLICommandTag.class))
                .ifPresent(x -> commands.put(x.value(), new CommandMeta(c)));
    }

    /**
     * Returns the metadata for the given command.
     *
     * @param cmd
     *            a command token
     * @return an object which contains the metadata
     * @throws CLIParserException
     */
    public CommandMeta getCommand(String cmd) throws CLIParserException {
        return commands.get(cmd);
    }

    public CommandMeta getCommandByRegex(String token) {
        for (Entry<String, CommandMeta> e : commands.entrySet()) {
            CommandMeta meta = e.getValue();
            if (meta.getRegex() != null && meta.getRegex().equals("")
                    && Pattern.matches(e.getValue().getRegex(), token)) {
                return meta;
            }
        }
        return null;
    }

    /**
     * Returns all the classes which are {@link CLICommandTag} annotated.
     *
     * @return a set of class object
     */
    public Set<Class<?>> getAllCommandClasses() {
        return commands.values().stream().map(x -> x.getCommandClass()).collect(Collectors.toSet());
    }

    /**
     * Returns the metadata of the all the classes which are
     * {@link CLICommandTag} annotated.
     *
     * @return
     */
    public List<CommandMeta> getAllCommandMetas() {
        return new ArrayList<>(commands.values());
    }

}
