package com.dotdashline.tools.cliparser.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.meta.CommandMeta;
import com.dotdashline.tools.cliparser.meta.OptionMeta;

public class TokenParsingUtil {


    /**
     * If the next token matches one of the annotated options, then create a map
     * entry for that token and potentials the additional tokens .
     *
     * @param tokens
     * @param optionMeta
     * @return
     */
    public static void parseOptionTokens(Queue<String> tokens, CommandMeta meta,
            Map<OptionMeta, List<String>> output) {

        // find the corresponding option metadata.
        OptionMeta optionMeta = meta.findOption(tokens.peek());
        if (optionMeta == null) {
            return;
        }

        // consume the token that matched
        String optionToken = tokens.poll();

        // attempt to consume the additional tokens for this option
        List<String> additionalTokens = parseOptionParams(meta, optionMeta.getAddtionalTokenCount(), tokens);

        output.put(optionMeta, Arrays.asList(
                CollectionUtil.concat(new String[] { optionToken }, additionalTokens.toArray(new String[] {}))));

        // if the meta says no additional tokens, then parse the next token
        // (recursively)
        parseOptionTokens(tokens, meta, output);
    }

    /**
     * 
     * @param optionToken
     * @param commandMeta
     * @param optionMeta
     * @param tokenQueue
     * @return
     * @throws CLIParserException
     */
    public static List<String> parseOptionParams(CommandMeta commandMeta, int optionParamCount,
            Queue<String> tokenQueue) {

        // pop the additional tokens into a list
        List<String> ret = new ArrayList<>();

        if (optionParamCount > 0) {
            // loop and keep consuming additional tokens until count reaches
            for (int i = 0; i < optionParamCount; i++) {
                if (tokenQueue.isEmpty()) {
                    break;
                }

                // check if the additional token matches any option
                if (commandMeta.findOption(tokenQueue.peek()) != null) {
                    // oops! The additional token is actually another option, we
                    // are done.
                    break;
                }

                // consume the token and include in the result
                ret.add(tokenQueue.poll());
            }
        }
        return ret;
    }
}
