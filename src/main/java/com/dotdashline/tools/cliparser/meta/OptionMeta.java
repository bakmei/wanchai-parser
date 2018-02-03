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
 * This class models the metadata for {@link CLIOptionTag} annotation class.
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
public class OptionMeta {

    // a field which is annotated by {@link CLIOptionTag}
    private Field field;

    // the details of the option
    private CLIOptionTag annotation;

    // a list of option prefixes
    private List<String> prefixes;

    /**
     * Default constructor.
     *
     * @param field a field
     */
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

    /**
     * Returns the {@link CLIOptionTag} field.
     *
     * @return the annotated field
     */
    public Field getField() {
        return field;
    }

    /**
     * Returns the description of this option.
     *
     * @return a description
     */
    public String getDescription() {
        return annotation.desc();
    }

    /**
     * Returns the option separator (Inclusive mode only).
     *
     * @return a separator character
     */
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

    /**
     * Returns true if the field is an array.
     *
     * @return a flag indicates that the field is an array or not
     */
    public boolean isArray() {
        return field == null ? false : field.getType().isArray();
    }

    /**
     * Returns true if the given token is match with this option.
     *
     * @param token
     *            a token from user input
     * @return a flag indicates that the token is matched or not
     */
    public boolean isMatch(String token) {
        if (token == null) {
            return false;
        }

        // exclusive, then the token has to match with the option name. e.g.
        // --<option name>
        if (isExclusive()) {
            return containsPrefix(token);
        }

        // inclusive, the token has to contain two values separated by the
        // separator
        String[] keyValuePair = token.split(String.valueOf(getSeparator()));
        return keyValuePair != null && keyValuePair.length > 0 && containsPrefix(keyValuePair[0]);
    }

    /**
     * Returns a list of option prefixes.
     *
     * @return a list of prefixes
     */
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
