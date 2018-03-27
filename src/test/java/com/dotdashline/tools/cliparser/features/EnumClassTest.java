package com.dotdashline.tools.cliparser.features;

import org.junit.Assert;
import org.junit.Test;

import com.dotdashline.tools.cliparser.CLIParser;
import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.DefaultCLIParser;
import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIOptionTag;

public class EnumClassTest {

   @Test
   public void test() throws CLIParserException {
      CLIParser parser = new DefaultCLIParser(MyCMDClass.class);
      MyCMDClass obj = (MyCMDClass) parser.parse("CMD1 --dataType=OPEN".split(" "));

      Assert.assertNotNull(obj);
      Assert.assertEquals("OPEN", obj.getDataType().toString());
   }

   @CLICommandTag("CMD1")
   public static class MyCMDClass {

      @CLIOptionTag(value = { "--dataType" })
      private DataType dataType;

      public DataType getDataType() {
         return dataType;
      }

      public static DataType valueOf(String s) {
         return DataType.valueOf(s);
      }
   }

   /**
    * Can only be instantiated by static method.
    */
   public static enum DataType {
      OPEN("OPEN"), HIGH("HIGH"), LOW("LOW"), CLOSE("CLOSE");

      private String name;

      DataType(String name) {
         this.name = name;
      }

      @Override
      public String toString() {
         return name;
      }

   }
}
