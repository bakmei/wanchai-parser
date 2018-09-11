package com.dotdashline.tools.cliparser.samples.misc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dotdashline.tools.cliparser.CLIParser;
import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.DefaultCLIParser;
import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIOptionTag;

public class GetDataCmdTest {
   private CLIParser parser;

   @Before
   public void setup() throws CLIParserException {
      parser = new DefaultCLIParser(GetDataCmd.class);
   }

   @Test
   public void test() throws Exception {
      GetDataCmd cmd = (GetDataCmd) parser.parse("GET", "--symbol=AAPL", "--datatype=OPEN");
      Assert.assertNotNull(cmd);
      Assert.assertEquals("AAPL", cmd.getSymbol().getValue());
      Assert.assertEquals(DataType.valueOf("OPEN"), cmd.getDataType());
      Assert.assertNull(null, cmd.getResult());
      
      // invoke the command
      Thread t = new Thread(cmd);
      t.start();
      t.join(); // wait for the thread to finish
      Assert.assertEquals(10, cmd.getResult().values.size());
   }

   @CLICommandTag(value = "GET")
   public static class GetDataCmd implements Runnable, CmdResult {

      @CLIOptionTag("--symbol")
      private Symbol symbol;

      @CLIOptionTag("--datatype")
      private DataType dataType;

      @CLIOptionTag(value = "--fromDate")
      private DataDate fromDate;

      private DataTuple result;

      private Exception lastException;

      @Override
      public void run() {
         try {
            // return an empty list, in real world, we can return a list of
            // values.
            result = getData(symbol, dataType, fromDate);
         } catch (Exception e) {
            lastException = e;
         }
      }

      private DataTuple getData(Symbol symbol2, DataType dataType2, DataDate fromDate2) {
         // return some values;
         List<Double> values = new ArrayList<>();
         for (int i = 0; i < 10; i++) {
            values.add(i * 1000.0);
         }
         return new DataTuple(values);
      }

      public DataType getDataType() {
         return dataType;
      }

      public Symbol getSymbol() {
         return symbol;
      }

      public DataTuple getResult() throws Exception {
         if (lastException != null) {
            throw lastException;
         }
         return result;
      }

      public static Symbol toSymbol(String s) {
         return Symbol.valueOf(s);
      }

      public static DataType toDataType(String s) {
         return DataType.valueOf(s);
      }

      public static DataDate toDataDate(String s) throws ParseException {
         return DataDate.valueOf(s);
      }

   }

   static class DataTuple {

      private List<Double> values;

      public DataTuple(List<Double> values) {
         this.values = values;
      }

      public List<Double> getValues() {
         return values;
      }
   }

   static enum DataType {

      OPEN("OPEN"), HIGH("HIGH"), LOW("LOW"), CLOSE("CLOSE"), ADJ_CLOSE("ADJ_CLOSE"), VOLUME("VOLUME"), MA_SMA(
            "MA_SMA");

      private String name;

      DataType(String name) {
         this.name = name;
      }

      @Override
      public String toString() {
         return name;
      }

      public static Set<DataType> select(DataType... dataTypes) {
         return new HashSet<>(Arrays.asList(dataTypes));
      }

   }

   static class Symbol {

      private String value;

      private Symbol(String value) {
         this.value = value;
      }

      public String getValue() {
         return value;
      }

      public static Symbol valueOf(String value) {
         return new Symbol(value.toUpperCase());
      }
   }

   public static class DataDate {

      private LocalDate date;

      private DataDate() {
      }

      private DataDate(LocalDate d) {
         this(d.getYear(), d.getMonthValue(), d.getDayOfMonth());
      }

      private DataDate(int year, int month, int dayOfMonth) {
         date = LocalDate.of(year, month, dayOfMonth);
      }

      public static DataDate valueOf(Calendar d) {
         return new DataDate(d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      }

      public static DataDate valueOf(String dateString) throws ParseException {
         SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getInstance();
         Calendar c = Calendar.getInstance();
         c.setTime(format.parse(dateString));
         return DataDate.valueOf(c);
      }
   }

   interface CmdResult {
      Object getResult() throws Exception;
   }
}
