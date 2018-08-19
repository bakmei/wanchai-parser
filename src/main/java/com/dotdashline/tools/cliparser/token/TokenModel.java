/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.dotdashline.tools.cliparser.CLIParserException;
import com.dotdashline.tools.cliparser.ErrorCode;
import com.dotdashline.tools.cliparser.meta.CommandMeta;
import com.dotdashline.tools.cliparser.meta.MetaModel;
import com.dotdashline.tools.cliparser.meta.OptionMeta;
import com.dotdashline.tools.cliparser.meta.ParamMeta;
import com.dotdashline.tools.cliparser.utils.CollectionUtil;
import com.dotdashline.tools.cliparser.utils.TokenParsingUtil;

/**
 * This class is the root level of the model which associates the metadata with
 * the incoming tokens.
 * 
 * Depends on the input tokens, this model may only associate with a subset of
 * the metadata. e.g. The metadata has n defined options, however, the tokens
 * only contains n-1 of them. Therefore, only n-1 {@link OptionToken} will be
 * created and only n-1 {@link OptionMeta} will be associated.
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
public class TokenModel {

   private MetaModel metaModel;
   private String[] tokens;

   private CommandToken commandToken;
   private List<OptionToken> optionTokens = new ArrayList<>();
   private List<ParamToken> paramTokens = new ArrayList<>();

   TokenModel(MetaModel metaModel, String[] tokens) throws CLIParserException {
      this.metaModel = metaModel;
      if (tokens == null || tokens.length == 0) {
         throw new CLIParserException(ErrorCode.NO_INPUT);
      }
      this.tokens = tokens;
      init(CollectionUtil.arrayToQueue(this.tokens));
   }

   public CommandToken getCommandToken() {
      return commandToken;
   }

   public List<OptionToken> getOptionTokens() {
      return optionTokens;
   }

   public List<ParamToken> getParamTokens() {
      return paramTokens;
   }

   @Override
   public String toString() {
      return new StringBuilder().append(String.format("metaModel: %s", metaModel.toString()))
            .append(String.format("tokens: %s%n", tokens)).append(String.format("commandToken: %s%n", commandToken))
            .append(String.format("optionTokens: %s%n", optionTokens))
            .append(String.format("paramTokens: %s%n", paramTokens)).toString();
   }

   private void init(Queue<String> tokens) throws CLIParserException {
      createCommandToken(tokens);
      createOptionTokens(commandToken, tokens);
      createParamTokens(commandToken, tokens);
   }

   private void createCommandToken(Queue<String> tokens) throws CLIParserException {
      CommandMeta commandMeta;
      if (tokens == null || tokens.isEmpty()) {
            throw new CLIParserException(ErrorCode.NO_INPUT);
      }
      String token = tokens.poll();
      if ((commandMeta = metaModel.getCommand(token)) == null) {
          throw new CLIParserException(ErrorCode.INVALID_COMMAND);
      }
      commandToken = new CommandToken(commandMeta, token);
   }

   private void createOptionTokens(CommandToken commandToken, Queue<String> tokens) {
      Map<OptionMeta, List<String>> output = new HashMap<>();
      TokenParsingUtil.parseOptionTokens(tokens, commandToken.getMeta(), output);
      output.entrySet().stream().forEach(e -> optionTokens.add(new OptionToken(e.getKey(), e.getValue())));
   }

   /**
    * Associates the ParamMeta and transfer the rest of the elements from the
    * queue into a list.
    *
    * @param tokens
    */
   private void createParamTokens(CommandToken commandToken, Queue<String> tokens) {
      List<ParamMeta> paramMeta = commandToken.getMeta().getParamMeta();
      if (tokens == null || tokens.isEmpty() || paramMeta == null || paramMeta.isEmpty()) {
         return;
      }

      // The paramMeta list is ordered by the sequence field.
      // For each param meta, match with the tokens in the queue.
      // If the param type is an array, assign the rest of the tokens to the
      // field.
      paramMeta.stream().filter(x -> !tokens.isEmpty()).forEach(x -> {
         if (x.isArray()) {
            paramTokens.add(new ParamToken(x, CollectionUtil.queueToList(tokens)));
         } else {
            paramTokens.add(new ParamToken(x, Arrays.asList(tokens.poll())));
         }
      });
   }
}
