/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser.meta;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Annotation anno = c.getAnnotation(CLICommandTag.class);
        if (anno != null) {
            commands.put(c.getAnnotation(CLICommandTag.class).value(), new CommandMeta(c));
        }
    }

    public CommandMeta getCommand(String cmd) throws CLIParserException {
        return commands.get(cmd);
    }

    public Set<Class<?>> getAllCommandClasses() {
        return commands.values().stream().map(x -> x.getCommandClass()).collect(Collectors.toSet());
    }

    public List<CommandMeta> getAllCommandMetas() {
        return new ArrayList<>(commands.values());
    }
}
