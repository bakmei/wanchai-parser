/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser.meta;

import java.lang.reflect.Field;

import com.dotdashline.tools.cliparser.tag.CLIParamTag;

/**
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
*/
public class ParamMeta implements Comparable<ParamMeta> {
    private Field field;
    private CLIParamTag annotation;

    public ParamMeta(Field field) {
        if (field == null) {
            throw new IllegalArgumentException("The input field is missing.");
        }
        annotation = field.getAnnotation(CLIParamTag.class);
        if (annotation == null) {
            throw new IllegalArgumentException("The input is not tagged with CLIParamTag");
        }
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public String getDescription() {
        return annotation == null ? "" : annotation.description();
    }
    
    public double getWeight() {
        return annotation == null ? 1.0d : annotation.weight();
    }
    
    public String getName() {
        return annotation == null ? "" : annotation.value();
    }

    public boolean isArray() {
        return field == null ? false :field.getType().isArray();
    }

    @Override
    public int compareTo(ParamMeta o) {
        return o == null ? -1 : (this.getWeight() > o.getWeight() ? 1 : -1);
    }
}
