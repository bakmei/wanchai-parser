package com.dotdashline.tools.cliparser.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.meta.CommandMeta;
import com.dotdashline.tools.cliparser.meta.MetaModelFactory;
import com.dotdashline.tools.cliparser.meta.OptionMeta;
import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIOptionTag;
import com.dotdashline.tools.cliparser.utils.CollectionUtil;
import com.dotdashline.tools.cliparser.utils.TokenParsingUtil;

public class ParserUtilTest {

    private final static String TestCommand = "test";

    private Map<OptionMeta, List<String>> actual;
    private CommandMeta meta;

    @Before
    public void setup() throws CLIParserException {
        actual = new HashMap<>();
        meta = MetaModelFactory.createModel(TestCommand.class).getCommand(TestCommand);
    }

    /**
     * Test parsing a list of option tokens. Input: "-option1 -option2 -option3"
     * Output: Map should contains: "optionMeta("-option1"): {"-option1"},
     * "optionMeta("-option2"): {"-option2"}, "optionMeta("-option3"):
     * {"-option3"}
     * 
     * @throws CLIParserException
     */
    @Test
    public void test_parsing_option_tokens() {
        String[] input = new String[] { "-option1", "-option2", "-option3" };

        Queue<String> tokens = CollectionUtil.arrayToQueue(input);

        TokenParsingUtil.parseOptionTokens(tokens, meta, actual);

        Assert.assertEquals(input.length, actual.size());

        // validate if the output is associated with the corresponding
        // OptionMeta
        Arrays.asList(input).stream()
                .forEach(token -> Assert.assertEquals(Arrays.asList(token), actual.get(meta.findOption(token))));
    }

    @CLICommandTag(TestCommand)
    class TestCommand {
        @CLIOptionTag("-option1")
        private boolean option1;

        @CLIOptionTag("-option2")
        private boolean option2;

        @CLIOptionTag("-option3")
        private boolean option3;
    }
}
