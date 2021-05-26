package com.dotdashline.tools.cliparser.features;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Test;

import com.dotdashline.tools.cliparser.DefaultCLIParser;
import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIOptionTag;
import com.dotdashline.tools.cliparser.tag.CLIParamTag;

public class HelpMessageTest {

    final String expected = "";

    @Test
    public void testHelpMessages() throws Exception {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(out);) {
            DefaultCLIParser parser = new DefaultCLIParser(SampleCmd.class);
            parser.setPrintStream(ps);
            parser.parse(new String[] { "helpdemo", "--help" });
            Assert.assertEquals(expected, out.toString("UTF-8"));
        } catch (Exception e) {
            throw e;
        }
    }

    @CLICommandTag(desc = "A sample class to demostrate how help messasge works.", value = "helpdemo")
    public static class SampleCmd {

        public SampleCmd() {
        }

        @CLIParamTag(desc = "A user name.")
        private String name;

        @CLIOptionTag(desc = "An example for demostrating the heplp message.", value = "--desc")
        private String description;
    }
}