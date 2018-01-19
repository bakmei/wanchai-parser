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

	String value();

    String description() default "Please fill in the description.";

    int addtionalTokenCount() default 0;
}
