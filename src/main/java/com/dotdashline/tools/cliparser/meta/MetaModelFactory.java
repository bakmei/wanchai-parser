package com.dotdashline.tools.cliparser.meta;

import java.util.Arrays;
import java.util.List;

import com.dotdashline.tools.cliparser.tag.CLICommandTag;

/**
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 1.0
 */
public class MetaModelFactory {

    private MetaModelFactory() {
    }

    /**
     * Create the Command model with a collection of annotated classes.
     *
     * @param classes
     * @return
     */
    public static MetaModel createModel(Class<?>... classes) {
        MetaModel model = new MetaModel();
        Arrays.asList(classes).stream().filter(x -> x.isAnnotationPresent(CLICommandTag.class))
                .forEach(c -> model.addCommand(c));
        return model;
    }
}
