/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser.tag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
*/
@Retention(RetentionPolicy.RUNTIME)
public @interface CLIParamTag {

    String value() default "";
    
    /**
     * Description of the parameter.  This field will be used when generating the help message.
     *
     * @return
     */
    String description() default "Please fill in the description.";

    /**
     * This field affects the order of parsing.
     * @return
     */
    double weight() default 0.5d;
}
