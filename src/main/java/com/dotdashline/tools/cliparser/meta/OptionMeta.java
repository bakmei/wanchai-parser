/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser.meta;

import java.lang.reflect.Field;

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

    public String getName() {
        return annotation.value();
    }

    public String getDescription() {
        return annotation.description();
    }

    public int getAddtionalTokenCount() {
        return annotation.addtionalTokenCount();
    }
    
    public boolean isArray() {
        return field == null ? false :field.getType().isArray();
    }

    public boolean isMatch(String token) {
        return getName().equals(token);
    }

}
