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

    public static Object createObject(Class<?> aClass, Object obj) throws NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        @SuppressWarnings("rawtypes")
        Constructor c = aClass.getConstructor(obj.getClass());
        return c.newInstance(obj);
    }

    /**
     * Returns a collection of fields which are annotated by the given
     * annotation.
     * 
     * @param objClass
     * @param annoClass
     * @return
     */
    public static List<Field> getAnnotatedFields(Class<?> objClass, Class<? extends Annotation> annotation) {
        return Arrays.asList(objClass.getDeclaredFields()).stream().filter(x -> x.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    /**
     * Set the given value to a private field.
     *
     * @param obj
     * @param field
     * @param value
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws CLIParserException
     */
    @SuppressWarnings("rawtypes")
    public static void setValueToField(Object obj, Field field, Object value)
            throws IllegalAccessException, CLIParserException {
        boolean isAccessible = field.isAccessible();
        if (!isAccessible) {
            field.setAccessible(true);
        }
        try {
            field.set(obj, value);
        } catch (IllegalArgumentException e) {
            // try to call the constructor with java.langString
            Constructor c;
            try {
                c = field.getType().getConstructor(String.class);
                field.set(obj, c.newInstance(value));
            } catch (IllegalArgumentException | InstantiationException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e1) {
                throw new CLIParserException(String.format(
                        "The token doesn't match the type of the option/param field, or the custom type doesn't provide a java.lang.String constructor: %s %s",
                        field.getName(), value), e1);
            }
        }
        // reset the field to private if it was private
        if (!isAccessible) {
            field.setAccessible(false);
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
