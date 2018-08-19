/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dotdashline.tools.cliparser.meta.MetaModel;
import com.dotdashline.tools.cliparser.meta.MetaModelFactory;
import com.dotdashline.tools.cliparser.meta.ParamMeta;
import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.token.OptionToken;
import com.dotdashline.tools.cliparser.token.ParamToken;
import com.dotdashline.tools.cliparser.token.TokenModel;
import com.dotdashline.tools.cliparser.token.TokenModelFactory;
import com.dotdashline.tools.cliparser.utils.PackageScanUtil;
import com.dotdashline.tools.cliparser.utils.ReflectionUtil;
import com.dotdashline.tools.cliparser.utils.TokenParsingUtil;

/**
 * This abstract class provides the basic implementation of the interface
 * {@link CLIParser}. The goal of this class is to parse the command tokens into
 * {@link MetaModel}. First, it creates the CommandModel from the annotated
 * classes, and second, it populates the options and parameters into the return
 * object based on the CommandModel.
 *
 * @author Raymond Tsang
 * @author Steven Liang
 *
 * @since 0.1
 */
public abstract class AbstractCLIParser implements CLIParser {

   /**
    * Contains the metadata of all the annotated classes. {@link CLICommandTag}
    */
   protected MetaModel metaModel;

   /**
    * Construct the parser with a collection of annotated Command classes. It
    * will ignore the element if it is not annotated as expected.
    * 
    * @param annotatedClasses
    * @throws CLIParserException
    */
   public AbstractCLIParser(Class<?>... annotatedClasses) throws CLIParserException {
      metaModel = MetaModelFactory.createModel(annotatedClasses);
   }

   /**
    * Construct the parser with a collection package names which holds the
    * annotated Command classes.
    * 
    * @param scanPackageNames
    * @throws CLIParserException
    */
   public AbstractCLIParser(String... scanPackageNames) throws CLIParserException {
      this(gatherCommandClasses(Arrays.asList(scanPackageNames)));
   }

   /**
    * The entry point.
    */
   @Override
   public Object parse(String... tokens) throws CLIParserException {

      if (tokens == null || tokens.length == 0) {
         throw new CLIParserException("Missing user input.", ErrorCode.NO_INPUT);
      }

      // create a {@link TokenModel} from user input
      TokenModel tokenModel = TokenModelFactory.createTokenModel(metaModel, tokens);

      // instantiate the corresponding command object
      Object cmdObj;
      try {
         cmdObj = ReflectionUtil.createCommandObject(tokenModel.getCommandToken().getMeta().getCommandClass());
      } catch (InstantiationException | IllegalAccessException e) {
         throw new CLIParserException("Failed to create the command object.  Maybe missing a default constructor?", e);
      }

      // populate the user input into the command object
      try {
         updateObjectValues(cmdObj, tokenModel);
      } catch (IllegalAccessException e) {
         throw new CLIParserException("Failed to update values to the command object.", e);
      } catch (CLIParserException e) {
         throw e;
      }

      return cmdObj;
   }

   /**
    * Read the user input from the {@link TokenModel} and populate into the
    * command object.
    *
    * @param cmdObj
    * @param tokenModel
    * @throws IllegalAccessException
    * @throws CLIParserException
    */
   private void updateObjectValues(Object cmdObj, TokenModel tokenModel)
         throws IllegalAccessException, CLIParserException {
      updateOptionValues(cmdObj, tokenModel);
      updateParamValues(cmdObj, tokenModel);
   }

   /**
    * Read the options input from the {@link TokenModel} and populate into the
    * command object.
    * 
    * @param cmdObj
    * @param tokenModel
    * @throws IllegalAccessException
    * @throws CLIParserException
    */
   private void updateOptionValues(Object cmdObj, TokenModel tokenModel)
         throws IllegalAccessException, CLIParserException {

      List<OptionToken> optionTokens = tokenModel.getOptionTokens();
      List<Exception> errors = new ArrayList<>();

      for (OptionToken optionModel : optionTokens) {
         try {
            switch (optionModel.getTokens().size()) {
            case 1:
               if (!optionModel.getMeta().isExclusive()) {
                  Object value = TokenParsingUtil.parseInclusiveOptionValue(optionModel.getMeta(),
                        optionModel.getTokens().get(0));
                  // if the value return null, then default the value to true.
                  // expects the option field is defined as boolean, the
                  // simple form.
                  ReflectionUtil.setValueToField(cmdObj, optionModel.getMeta().getField(),
                        value != null ? value : true);
               }
               break;
            case 2:
               if (optionModel.getMeta().isExclusive()) {
                  ReflectionUtil.setValueToField(cmdObj, optionModel.getMeta().getField(),
                        optionModel.getTokens().get(1));
               }
               break;
            default:
               // not setting anything
            }
         } catch (Exception e) {
            errors.add(e);
         }
      }
      if (!errors.isEmpty()) {
         throw new CLIParserException(ErrorCode.INVALID_OPTION, errors);
      }
   }

   /**
    * Read the parameters input from {@link TokenModel} and populate into the
    * command object.
    *
    * @param cmdObj
    * @param tokenModel
    * @throws IllegalAccessException
    * @throws CLIParserException
    */
   private void updateParamValues(Object cmdObj, TokenModel tokenModel)
         throws IllegalAccessException, CLIParserException {
      List<ParamToken> paramTokens = tokenModel.getParamTokens();
      List<Exception> errors = new ArrayList<>();
      for (ParamToken paramModel : paramTokens) {
         try {
            ParamMeta paramMeta = paramModel.getMeta();
            if (paramMeta.isArray()) {
               ReflectionUtil.setValuesToArrayField(cmdObj, paramMeta.getField(), paramModel.getTokens());
            } else {
               ReflectionUtil.setValueToField(cmdObj, paramMeta.getField(), paramModel.getTokens().get(0));
            }
         } catch (Exception e) {
            errors.add(e);
         }
      }
      if (!errors.isEmpty()) {
         throw new CLIParserException(ErrorCode.INVALID_PARAM, errors);
      }
   }

   /**
    * Gather all the classes which are annotated by {@link CLICommandTag}.
    *
    * @param packageNames
    * @return
    * @throws CLIParserException
    */
   protected static Class<?>[] gatherCommandClasses(List<String> packageNames) throws CLIParserException {
      try {
         return PackageScanUtil.findAnnotatedClasses(CLICommandTag.class, packageNames.toArray(new String[] {}))
               .values().toArray(new Class<?>[] {});
      } catch (ClassNotFoundException | IOException e) {
         throw new CLIParserException(
               String.format("Failure while scanning CLICommandTag classes from packages: %s", packageNames), e);
      }
   }
}
