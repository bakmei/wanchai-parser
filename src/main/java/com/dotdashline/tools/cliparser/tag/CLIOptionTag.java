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
    * e.g. "--count" e.g. "-count"
    * 
    * @return
    */
   String[] value();

   String desc() default "<name of this description>";

   /**
    * Default is inclusive. i.e. false.
    * 
    * Definition of Inclusive/Exclusive:
    * 
    * Inclusive option format: <key>=<value>
    * 
    * Inclusive option refers to both option header and the option value are
    * expressed in one token.
    * 
    * e.g. --host=localhost where the "--host" is the option header and the
    * "localhost" part is the option value.
    * 
    * e.g. --reverse=true or --reverse are special boolean field which the value
    * can be neglected.
    * 
    * Exclusive option format: <key> <value> pairs
    * 
    * Exclusive option refers to the option header and the option value are
    * expressed separately.
    * 
    * e.g. --host localhost.
    * 
    * The option header and the option value are separated by a space.
    *
    * @return true if the exclusive format is preferred, otherwise, false for inclusive. 
    */
   boolean exclusive() default false;

   /**
    * The symbol for separating the inclusive option key/value.
    *
    * @return a separator, to be used when parsing the inclusive option. e.g.
    *         --sort=3
    */
   char separator() default '=';

}
