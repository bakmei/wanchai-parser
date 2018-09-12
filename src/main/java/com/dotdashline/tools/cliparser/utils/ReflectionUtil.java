/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
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

   public static final String newLine = System.getProperty("line.separator");

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
   public static void setValueToField(Object obj, Field field, Object value) throws CLIParserException {
      boolean isAccessible = field.isAccessible();

      try {
         if (!isAccessible) {
            field.setAccessible(true);
         }
         try {
            field.set(obj, value);
         } catch (IllegalAccessException | IllegalArgumentException e) {
            // try to call the constructor with java.langString
            Constructor c;
            try {
               if (field.getType().isPrimitive()) {
                  setPrimitiveValueToField(obj, field, value);
               } else {
                  c = field.getType().getConstructor(String.class);
                  field.set(obj, c.newInstance(value));
               }
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException
                  | InvocationTargetException | NoSuchMethodException | SecurityException e1) {
               // try last time, see if there is a static method for
               // instantiating
               // the object
               Method m = ReflectionUtil.findMethod(obj.getClass(), true, field.getType(), value.getClass());
               if (m != null) {
                  try {
                     field.set(obj, m.invoke(null, value));
                  } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
                     throw new CLIParserException(String.format("Failed to invoke static method [%s] to instantiate the field [%s] ", m.getName(), field));
                  }
               } else {
                  throw new CLIParserException(String.format(
                        "The token doesn't match the type of the option/param field, or the custom type doesn't provide a java.lang.String constructor: %s %s",
                        field.getName(), value), e1);
               }
            }
         }
      } catch (Exception e) {
         // this try/catch is for the need of finally block
         throw e;
      } finally {
         // reset the field to private if it was private
         if (!isAccessible) {
            field.setAccessible(false);
         }
      }
   }

   private static void setPrimitiveValueToField(Object obj, Field field, Object value)
         throws IllegalArgumentException, IllegalAccessException {

      if (field.getType().equals(boolean.class)) {
         field.setBoolean(obj, Boolean.parseBoolean(value.toString()));

      } else if (field.getType().equals(int.class)) {
         field.setInt(obj, Integer.parseInt(value.toString()));

      } else if (field.getType().equals(long.class)) {
         field.setLong(obj, Long.parseLong(value.toString()));

      } else if (field.getType().equals(float.class)) {
         field.setFloat(obj, Float.parseFloat(value.toString()));

      } else if (field.getType().equals(double.class)) {
         field.setDouble(obj, Double.parseDouble(value.toString()));

      } else if (field.getType().equals(byte.class)) {
         field.setByte(obj, Byte.parseByte(value.toString()));

      } else if (field.getType().equals(short.class)) {
         field.setShort(obj, Short.parseShort(value.toString()));

      } else if (field.getType().equals(char.class)) {
         field.setChar(obj, value.toString().charAt(0));
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
    *           throws CLIParserException
    */
   public static void setValuesToArrayField(Object cmdObj, Field field, List<String> tokens) throws CLIParserException {
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
      ReflectionUtil.setValueToField(cmdObj, field, paramArray);
   }

   public static Method findMethod(Class<?> clazz, boolean isStatic, Class<?> returnType, Class<?>... paramTypes) {
      Method[] methods = clazz.getDeclaredMethods();
      for (Method m : methods) {
         if (m.getReturnType().equals(returnType) && Modifier.isStatic(m.getModifiers())
               && Arrays.asList(paramTypes).equals(Arrays.asList(m.getParameterTypes()))) {
            return m;
         }
      }
      return null;
   }

   public static String toString(Object obj) {
      return toString(obj, 0);
   }

   private static String toString(Object obj, int indent) {

      if (obj == null) {
         return "<NULL>";
      }

      StringBuilder ret = new StringBuilder();

      ret.append(getIndentString(indent)).append("[ ").append(obj.getClass().getSimpleName()).append(" ]")
            .append(newLine);

      final int childIndent = indent + 1;
      Arrays.asList(obj.getClass().getDeclaredFields()).stream().forEach(x -> {
         ret.append(getIndentString(childIndent)).append(x.getName()).append(" (").append(x.getType().getSimpleName())
               .append(") -> ");
         Object childObj = null;
         boolean isAccessible = x.isAccessible();
         if (!isAccessible) {
            x.setAccessible(true);
         }
         try {
            childObj = x.get(obj);
         } catch (IllegalArgumentException | IllegalAccessException e) {
            ret.append(e.getMessage());
         }

         // restore the value
         if (!isAccessible) {
            x.setAccessible(false);
         }

         if (childObj == null || childObj.getClass() == null || childObj.getClass().getPackage() == null) {
            ret.append("<NULL>");
         } else if (childObj.getClass().getPackage().getName().startsWith("com.dotdashline")) {
            ret.append(toString(childObj, childIndent + 1));
         } else {
            ret.append(childObj.toString());
         }
         ret.append(newLine);
      });

      return ret.toString();
   }

   private static Object getIndentString(int indent) {
      return String.join("", Collections.nCopies(indent * 3, " "));
   }
}
