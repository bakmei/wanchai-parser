/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser.token;

import java.util.List;

import com.dotdashline.tools.cliparser.meta.OptionMeta;

/**
 * This class associates the metadata with the token.
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
public class OptionToken {

   private OptionMeta meta;

   private List<String> tokens;

   public OptionToken(OptionMeta meta, List<String> tokens) {
      this.meta = meta;
      this.tokens = tokens;
   }

   public OptionMeta getMeta() {
      return meta;
   }

   public List<String> getTokens() {
      return tokens;
   }

   @Override
   public String toString() {
      return new StringBuilder().append(String.format("meta: %s", meta.toString()))
            .append(String.format("tokens: %s%n", tokens)).toString();
   }
}
