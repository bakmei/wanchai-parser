package com.dotdashline.tools.cliparser.meta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * @since 1.0 
 */
public class CommandMeta {
    private Class<?> commandClass;
    private Map<String, OptionMeta> options = new HashMap<>();
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

    public List<ParamMeta> getParamMeta() {
        return parameters;
    }

    public OptionMeta findOption(String token) {
        for (OptionMeta meta : options.values()) {
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
                .forEach(x -> options.put(x.getAnnotation(CLIOptionTag.class).value(), new OptionMeta(x)));
    }
}
