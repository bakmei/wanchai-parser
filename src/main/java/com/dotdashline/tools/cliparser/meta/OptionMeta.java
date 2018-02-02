/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser.meta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.dotdashline.tools.cliparser.tag.CLIOptionTag;

/**
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
public class OptionMeta {

    private Field field;
    private CLIOptionTag annotation;
    private List<String> prefixes;

    public OptionMeta(Field field) {
        if (field == null) {
            throw new IllegalArgumentException("The input field is missing.");
        }
        annotation = field.getAnnotation(CLIOptionTag.class);
        if (annotation == null) {
            throw new IllegalArgumentException("The input is not tagged with CLIOPtionTag");
        }
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public String getDescription() {
        return annotation.desc();
    }

    public char getSeparator() {
        return annotation.separator();
    }

    /**
     * Return true if the option expect an additional parameter. The default
     * value is false defined in {@link CLIOptionTag}
     * 
     * i.e "--option1", "option1Param" instead of "--option1=option1Param"
     *
     * @return whether the option format is exclusive or not.
     */
    public boolean isExclusive() {
        return annotation.exclusive();
    }

    public boolean isArray() {
        return field == null ? false : field.getType().isArray();
    }

    public boolean isMatch(String token) {
        if (token == null) {
            return false;
        }
        
        // if it is exclusive, then the token has to match with the option name.  e.g. --<option name>
        if (isExclusive()) {
            return containsPrefix(token);
        }

        // if it is inclusive, the token has to contain two values separated by the separator
        String[] keyValuePair = token.split(String.valueOf(getSeparator()));
        return keyValuePair != null && keyValuePair.length > 0 && containsPrefix(keyValuePair[0]);
    }

    public List<String> getPrefixes() {
        if (prefixes == null) {
            prefixes = Arrays.asList(annotation.value());
        }
        return prefixes;
    }

    private boolean containsPrefix(String token) {
        return getPrefixes().contains(token);
    }
}
