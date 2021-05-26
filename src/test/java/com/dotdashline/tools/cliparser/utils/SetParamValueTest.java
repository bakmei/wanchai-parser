package com.dotdashline.tools.cliparser.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.tag.CLIParamTag;

public class SetParamValueTest {

   //////////////////////////////////////////////////////////////////////////////////
   // Test setting value to private field.
   //////////////////////////////////////////////////////////////////////////////////

   /**
    * Test if the private field is set.
    * 
    * @throws IllegalAccessException
    * @throws IllegalArgumentException
    * @throws CLIParserException
    */
   @Test
   public void test_reflection_set_private_1()
         throws IllegalArgumentException, IllegalAccessException, CLIParserException {
      String sample = Sampling.sampleString();
      ClassHasStringParamField obj = new ClassHasStringParamField();
      Field field = ReflectionUtil.getAnnotatedFields(obj.getClass(), CLIParamTag.class).get(0);
      ReflectionUtil.setValueToField(obj, field, sample);
      Assert.assertEquals(sample, obj.getTestField());
   }

   class ClassHasStringParamField {
      @CLIParamTag
      private String testField;

      public String getTestField() {
         return testField;
      }
   }

   //////////////////////////////////////////////////////////////////////////////////
   // Test creating value using defined constructor.
   //////////////////////////////////////////////////////////////////////////////////

   @Test
   public void test_set_value_using_constructor_2() throws IllegalAccessException, CLIParserException {
      // setting value to the annotated class
      String sample = Sampling.sampleString();
      ClassHasClassField obj = new ClassHasClassField();

      // get the Host2 field
      Field field = ReflectionUtil.getAnnotatedFields(obj.getClass(), CLIParamTag.class).get(0);
      ReflectionUtil.setValueToField(obj, field, sample);
      Assert.assertEquals(sample, obj.getTestField().getValue());
   }

   class ClassHasClassField {
      @CLIParamTag
      private HostWithConstructor testField;

      public HostWithConstructor getTestField() {
         return testField;
      }
   }

   static class HostWithConstructor {
      private String v;

      public HostWithConstructor(String s) {
         this.v = s;
      }

      public String getValue() {
         return this.v;
      }
   }

   //////////////////////////////////////////////////////////////////////////////////
   // Test creating value using defined method if constructor is missing.
   //////////////////////////////////////////////////////////////////////////////////

   @Test
   public void test_find_method() {
      Method m = ReflectionUtil.findMethod(UseStaticMethodToConstruct.class, true, UseStaticMethodToConstruct.class,
            String.class);
      Assert.assertNotNull(m);
      Assert.assertEquals("valueOf", m.getName());
   }

   @Test
   public void test_set_value_using_static_method() throws IllegalAccessException, CLIParserException {
      // setting value to the annotated class
      String sample = Sampling.sampleString();
      ClassConstainsClassField obj = new ClassConstainsClassField();

      // get the Host3 field
      Field field = ReflectionUtil.getAnnotatedFields(obj.getClass(), CLIParamTag.class).get(0);
      ReflectionUtil.setValueToField(obj, field, sample);
      Assert.assertEquals(sample, obj.getTestField().getValue());
   }

   static class ClassConstainsClassField {
      @CLIParamTag
      private UseStaticMethodToConstruct testField;

      public UseStaticMethodToConstruct getTestField() {
         return testField;
      }

      // this method helps on mapping String to
      // ClassUsingStaticMethodConstructor,
      // the parser will look for static method that matches the parameter and
      // return types.
      public static UseStaticMethodToConstruct map(String s) {
         return UseStaticMethodToConstruct.valueOf(s);
      }
   }

   static class UseStaticMethodToConstruct {
      private String v;

      private UseStaticMethodToConstruct(String s) {
         this.v = s;
      }

      public static UseStaticMethodToConstruct valueOf(String s) {
         return new UseStaticMethodToConstruct(s);
      }

      public String getValue() {
         return this.v;
      }
   }

   //////////////////////////////////////////////////////////////////////////////////
   // Test setting array value.
   //////////////////////////////////////////////////////////////////////////////////
/*   @Test
   public void test_set_arrayvalue_using_static_method() throws IllegalAccessException, CLIParserException {
      // setting value to the annotated class
      String[] samples = Sampling.sampleStrings(10);
      ClassContainsArrayField obj = new ClassContainsArrayField();

      // get the annotated field
      Field field = ReflectionUtil.getAnnotatedFields(obj.getClass(), CLIParamTag.class).get(0);
      
      // set the array value
      ReflectionUtil.setValueToField(obj, field, sample);
      Assert.assertEquals(sample, obj.getTestField().getValue());
   }

   static class ClassContainsArrayField {
      @CLIParamTag
      private UseStaticMethodToConstruct[] testField;

      public UseStaticMethodToConstruct[] getTestField() {
         return testField;
      }

      // this method helps on mapping String to
      // ClassUsingStaticMethodConstructor, will be called when constructing the
      // elemments in the array.
      public static UseStaticMethodToConstruct map(String s) {
         return UseStaticMethodToConstruct.valueOf(s);
      }
   }
*/
}
