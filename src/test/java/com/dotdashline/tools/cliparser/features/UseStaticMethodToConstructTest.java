package com.dotdashline.tools.cliparser.features;

import org.junit.Assert;
import org.junit.Test;

import com.dotdashline.tools.cliparser.CLIParser;
import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.DefaultCLIParser;
import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIOptionTag;

public class UseStaticMethodToConstructTest {

   @Test
   public void test() throws CLIParserException {
      CLIParser parser = new DefaultCLIParser(MyCMDClass.class);
      MyCMDClass obj = (MyCMDClass) parser.parse("CMD1 --symbol=AAPL".split(" "));

      Assert.assertNotNull(obj);
      Assert.assertEquals("AAPL", obj.getSymbol().value());
   }

   @CLICommandTag("CMD1")
   public static class MyCMDClass {

      @CLIOptionTag(value = { "--symbol" })
      private Symbol symbol;

      public Symbol getSymbol() {
         return symbol;
      }

      public static Symbol mapToSymbol(String s) {
         return Symbol.valueOf(s);
      }
   }

   /**
    * Can only be instantiated by static method.
    */
   public static class Symbol {
      private String value;

      private Symbol(String v) {
         this.value = v;
      }

      public static Symbol valueOf(String v) {
         return new Symbol(v);
      }

      public String value() {
         return value;
      }
   }
}
