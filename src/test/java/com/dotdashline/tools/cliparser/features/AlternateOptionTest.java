package com.dotdashline.tools.cliparser.features;

import org.junit.Assert;
import org.junit.Test;

import com.dotdashline.tools.cliparser.CLIParser;
import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.DefaultCLIParser;
import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIOptionTag;

public class AlternateOptionTest {

    @Test
    public void test() throws NoSuchFieldException, SecurityException, CLIParserException {
        CLIParser parser = new DefaultCLIParser(MyClass.class);
        Object obj = parser.parse("connect --host=localhost".split(" "));
        ((MyClass) obj).verify();
    }
    
    /**
     * Supports format: "connect --host=localhost" or "connect -H=localhost"
     */
    @CLICommandTag("connect")
    public static class MyClass {
        @CLIOptionTag(value={"--host", "-H"}, regex="[--host|-H]")
        private String hostName;

        public void verify() {
            Assert.assertNotNull(hostName);
            Assert.assertEquals("localhost", hostName);
        }
    }
}
