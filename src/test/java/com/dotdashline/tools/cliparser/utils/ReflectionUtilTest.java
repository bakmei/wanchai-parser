package com.dotdashline.tools.cliparser.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIParamTag;

public class ReflectionUtilTest {

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
      MyLittleTest1 obj = new MyLittleTest1();
      Field field = ReflectionUtil.getAnnotatedFields(obj.getClass(), CLIParamTag.class).get(0);
      ReflectionUtil.setValueToField(obj, field, sample);
      Assert.assertEquals(sample, obj.getTestField());
   }

   class MyLittleTest1 {
      @CLIParamTag
      private String testField;

      public String getTestField() {
         return testField;
      }
   }

   @Test
   public void test_set_value_using_constructor_2() throws IllegalAccessException, CLIParserException {
      // setting value to the annotated class
      String sample = Sampling.sampleString();
      MyLittleTest2 obj = new MyLittleTest2();

      // get the Host2 field
      Field field = ReflectionUtil.getAnnotatedFields(obj.getClass(), CLIParamTag.class).get(0);
      ReflectionUtil.setValueToField(obj, field, sample);
      Assert.assertEquals(sample, obj.getTestField().getValue());
   }

   class MyLittleTest2 {
      @CLIParamTag
      private Host2 testField;

      public Host2 getTestField() {
         return testField;
      }
   }

   static class Host2 {
      private String v;

      public Host2(String s) {
         this.v = s;
      }

      public String getValue() {
         return this.v;
      }
   }

   @Test
   public void test_find_method() {
      Method m = ReflectionUtil.findMethod(Host3.class, true, Host3.class, String.class);
      Assert.assertNotNull(m);
      Assert.assertEquals("valueOf", m.getName());
   }

   @Test
   public void test_set_value_using_static_method_3() throws IllegalAccessException, CLIParserException {
      // setting value to the annotated class
      String sample = Sampling.sampleString();
      MyLittleTest3 obj = new MyLittleTest3();

      // get the Host3 field
      Field field = ReflectionUtil.getAnnotatedFields(obj.getClass(), CLIParamTag.class).get(0);
      ReflectionUtil.setValueToField(obj, field, sample);
      Assert.assertEquals(sample, obj.getTestField().getValue());
   }

   static class MyLittleTest3 {
      @CLIParamTag
      private Host3 testField;

      public Host3 getTestField() {
         return testField;
      }
      
      // this method helps on mapping String to Host3 
      public static Host3 map(String s) {
         return Host3.valueOf(s);
      }
   }

   static class Host3 {
      private String v;

      private Host3(String s) {
         this.v = s;
      }

      public static Host3 valueOf(String s) {
         return new Host3(s);
      }

      public String getValue() {
         return this.v;
      }
   }
}
