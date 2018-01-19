package com.dotdashline.tools.cliparser.meta;

import java.lang.reflect.Field;

import com.dotdashline.tools.cliparser.tag.CLIOptionTag;
import com.dotdashline.tools.cliparser.tag.CLIParamTag;

/**
*
* @author Raymond Tsang
* @author Steven Liang
*
* @since 1.0 
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
