/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser.meta;

import java.lang.reflect.Field;

import com.dotdashline.tools.cliparser.tag.CLIParamTag;

/**
 * This class models the metadata for {@link CLIParamTag} annotation class.
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
public class ParamMeta implements Comparable<ParamMeta> {

    // the field which is annotated by {@link CLIParamTag}
    private Field field;

    // the details of the annotation
    private CLIParamTag annotation;

    /**
     * Default constructor.
     *
     * @param field a class field annotated by {@link CLIParamTag}
     */
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

    /**
     * Returns the annotated field.
     *
     * @return a class field
     */
    public Field getField() {
        return field;
    }

    /**
     * Returns the description of the parameter.
     *
     * @return a description
     */
    public String getDescription() {
        return annotation == null ? "" : annotation.desc();
    }

    /**
     * Returns the weight of the parameter.
     *
     * @return a weight
     */
    public double getWeight() {
        return annotation == null ? 1.0d : annotation.weight();
    }

    /**
     * Returns the name of the parameter.
     *
     * @return a name
     */
    public String getName() {
        return annotation == null ? "" : annotation.value();
    }

    public boolean isArray() {
        return field == null ? false : field.getType().isArray();
    }

    /**
     * For sorting purpose.
     */
    @Override
    public int compareTo(ParamMeta o) {
        return o == null ? -1 : (this.getWeight() > o.getWeight() ? 1 : -1);
    }
}
