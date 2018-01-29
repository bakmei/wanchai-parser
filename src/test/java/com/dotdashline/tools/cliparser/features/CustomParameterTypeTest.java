package com.dotdashline.tools.cliparser.features;

import org.junit.Assert;
import org.junit.Test;

import com.dotdashline.tools.cliparser.CLIParser;
import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.DefaultCLIParser;
import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIParamTag;

public class CustomParameterTypeTest {
    @Test
    public void test() throws NoSuchFieldException, SecurityException, CLIParserException {
        CLIParser parser = new DefaultCLIParser(MyClass.class);
        Object obj = parser.parse("connect localhost:1234".split(" "));
        ((MyClass) obj).verify();
    }

    /**
     * Handles format: "connect localhost:1234"
     */
    @CLICommandTag("connect")
    public static class MyClass {
        @CLIParamTag(value = "tcpConfig", description = "Specify <host>:<port> for the connection.")
        private HostPortPair hostPortPair;

        public void verify() {
            Assert.assertNotNull(hostPortPair);
            Assert.assertEquals("localhost", hostPortPair.getHost());
            Assert.assertEquals(1234, hostPortPair.getPort());
        }
    }

    /**
     * Customized defined class for storing the values.
     */
    public static class HostPortPair {
        private String host;
        private int port;

        /**
         * Constructor to create POJO for storing host and port.
         * Parse the input internally. 
         *
         * @param hostAndPort
         */
        public HostPortPair(String hostAndPort) {
            String[] inputs = hostAndPort.split(":");
            this.host = inputs[0];
            this.port = Integer.parseInt(inputs[1]);
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }
    }
}
