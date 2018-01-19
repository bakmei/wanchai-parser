package com.dotdashline.tools.cliparser.tag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
*
* @author Raymond Tsang
* @author Steven Liang
*
* @since 1.0 
*/
@Retention(RetentionPolicy.RUNTIME)
public @interface CLICommandTag {

    /**
     * Stores the command token for the annotated class for matching with the user
     * input.
     * @return
     */
	String value();

	boolean isDefault() default false;
	
}
