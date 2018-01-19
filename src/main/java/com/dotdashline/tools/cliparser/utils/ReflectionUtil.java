package com.dotdashline.tools.cliparser.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.dotdashline.tools.cliparser.CLIParserException;

/**
*
* @author Raymond Tsang
* @author Steven Liang
*
* @since 0.1
*/

public class ReflectionUtil {

    public static Object createObject ( Class < ? > aClass , Object obj )
            throws NoSuchMethodException , SecurityException , InstantiationException , IllegalAccessException ,
            InvocationTargetException {
        @SuppressWarnings("rawtypes")
        Constructor c = aClass.getConstructor ( obj.getClass () );
        return c.newInstance ( obj );
    }

    /**
     * Returns a collection of fields which are annotated by the given annotation.
     * 
     * @param objClass
     * @param annoClass
     * @return
     */
    public static List < Field > getAnnotatedFields ( Class < ? > objClass , Class<? extends Annotation> annotation ) {
        return Arrays.asList ( objClass.getDeclaredFields () ).stream ()
                .filter ( x -> x.isAnnotationPresent ( annotation ) ).collect ( Collectors.toList () );
    }

    /**
     * Set the given value to a private field.
     *
     * @param obj
     * @param field
     * @param value
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setValueToField ( Object obj , Field field , Object value )
            throws IllegalAccessException {
        if ( field.isAccessible () ) {
            field.set ( obj , value );
        } else {
            field.setAccessible ( true );
            field.set ( obj , value );
            field.setAccessible ( false );
        }
    }

    public static Object createCommandObject(Class<?> clazz) throws InstantiationException, IllegalAccessException {
        if (clazz == null) {
            throw new IllegalArgumentException("The input class is null.");
        }
        return clazz.newInstance();
    }
    

    /**
     * Update the rest of the tokens in the queue to the array type field.
     * 
     * @param cmdObj
     * @param field
     * @param tokenQueue
     *            throws CLIParserException
     */
    public static void setValuesToArrayField(Object cmdObj, Field field, List<String> tokens)
            throws CLIParserException {
        // get the type of the array
        Class<?> arrayType = field.getType().getComponentType();

        // create an instance of the array
        Object[] paramArray = (Object[]) Array.newInstance(arrayType, tokens.size());

        // update the array with he token in the queue.
        for (int i = 0; i < paramArray.length; i++) {
            try {
                paramArray[i] = ReflectionUtil.createObject(arrayType, tokens.get(i));
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                    | InvocationTargetException e) {
                throw new CLIParserException("Failed to set array type values into parameter field.", e);
            }
        }

        // finally set the array to the field
        try {
            ReflectionUtil.setValueToField(cmdObj, field, paramArray);
        } catch (IllegalAccessException e) {
            throw new CLIParserException("Failed to set array type values into parameter field.", e);
        }
    }
}
