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
public @interface CLICommandTag {

   // this value will be used during the first round matching (exact).
   String value();

   String desc() default "<name of this description>";

   // this value will be used during the second round matching (regex).
   String regex() default "";
}
