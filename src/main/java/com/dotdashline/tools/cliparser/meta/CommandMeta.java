/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser.meta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIOptionTag;
import com.dotdashline.tools.cliparser.tag.CLIParamTag;
import com.dotdashline.tools.cliparser.utils.ReflectionUtil;

/**
 * This class models the meta data for the command class.
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
public class CommandMeta {
    private Class<?> commandClass;
    private List<OptionMeta> options = new ArrayList<>();
    private List<ParamMeta> parameters = new ArrayList<>();

    public CommandMeta(Class<?> c) {
        if (c == null) {
            throw new IllegalArgumentException("The input class is null, cannot construct command metadata.");
        }
        if (c.getAnnotation(CLICommandTag.class) == null) {
            throw new IllegalArgumentException("The input class is not CLICommandTag annotated.");
        }
        this.commandClass = c;
        addOptions(c);
        addParameters(c);
    }

    public Class<?> getCommandClass() {
        return commandClass;
    }

    public List<OptionMeta> getOptionMeta() {
        return options;
    }

    public List<ParamMeta> getParamMeta() {
        return parameters;
    }

    public OptionMeta findOption(String token) {
        for (OptionMeta meta : options) {
            if (meta.isMatch(token)) {
               return meta; 
            }
        }
        return null;
    }

    /**
     * Add the {@link ParameterTag} annotated fields.
     */
    private void addParameters(Class<?> c) {
        // for each annotated fields, create the ParameterElement object.
        ReflectionUtil.getAnnotatedFields(c, CLIParamTag.class).stream()
                .forEach(x -> parameters.add(new ParamMeta(x)));
    }

    /**
     * Add the {@link OptionTag} annotated fields. 
     */
    private void addOptions(Class<?> c) {
        // for each annotated fields, create the OptionElement object.
        Arrays.asList ( c.getDeclaredFields () ).stream ()
        .filter ( x1 -> x1.isAnnotationPresent ( CLIOptionTag.class ) ).collect ( Collectors.toList () ).stream()
                .forEach(x -> options.add(new OptionMeta(x)));
    }

    public String getName() {
        return getCommandTag().value();
    }

    private CLICommandTag getCommandTag() {
        return getCommandClass().getAnnotation(CLICommandTag.class);
    }

    public Object getDescription() {
        return getCommandTag().desc();
    }
}
