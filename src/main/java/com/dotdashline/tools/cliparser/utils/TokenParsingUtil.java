/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.dotdashline.tools.cliparser.meta.CommandMeta;
import com.dotdashline.tools.cliparser.meta.OptionMeta;

/**
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
public class TokenParsingUtil {

    /**
     * Consume the next from the queue if it matches with any of the defined
     * options. This method calls recursively until the queue
     *
     * @param tokens
     * @param optionMeta
     * @return
     */
    public static void parseOptionTokens(Queue<String> tokens, CommandMeta meta, Map<OptionMeta, List<String>> output) {

        // find the corresponding option metadata.
        OptionMeta optionMeta = null;

        while ((optionMeta = meta.findOption(tokens.peek())) != null) {

            List<String> optionTokens = new ArrayList<>();

            // consume the token that matched
            optionTokens.add(tokens.poll());

            // consume one more tokens if
            // 1. the option is exclusive
            // 2. the queue has something
            // 3. the next element does not match with other any other options
            if (optionMeta.isExclusive() && !tokens.isEmpty() && meta.findOption(tokens.peek()) == null) {
                optionTokens.add(tokens.poll());
            }

            // add to output
            output.put(optionMeta, optionTokens);
        }
    }

    public static Object parseInclusiveOptionValue(OptionMeta meta, String token) {
        if (token == null) {
            return null;
        }

        String[] keyValuePair = token.split(String.valueOf(meta.getSeparator()));
        return (keyValuePair != null && keyValuePair.length == 2) ? keyValuePair[1] : null;
    }
}
