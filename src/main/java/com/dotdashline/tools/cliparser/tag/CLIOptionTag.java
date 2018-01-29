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
public @interface CLIOptionTag {

    /**
     * This is the prefix to be matched for this option.
     * 
     * e.g. "--count"
     * e.g. "-count"
     * 
     * @return
     */
	String value();

    String description() default "Please fill in the description.";

    /**
     * Default will be set to 'false', non-exclusive (inclusive).
     *
     * The default separator for inclusive option is '=', this can be set by the separater character.
     *
     * i.e options are passed in the following format:
     * <key>=<value>
     *   e.g. --sort=3 or -sort=3
     *   e.g. --reverse=true or --reverse or -reverse
     * 
     * Rather, if the flag is set to true, then the option should present in the following format:
     *
     * <key><space><value>
     *   e.g. "--sort 3" or -sort 3
     *   e.g. "--reverse true" or "--reverse" or "-reverse true" or "-reverse"
     * 
     * @return
     */
    boolean exclusive() default false;
    
    /**
     * The symbol for separating the inclusive option key/value.
     *
     * @return a separator, to be used when parsing the inclusive option.  e.g. --sort=3
     */
    char separator() default '=';
}
