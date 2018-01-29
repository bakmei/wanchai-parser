/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
 * This is the default implementation of the CLIParser interface. The goal of
 * this class is to parse the command tokens into {@link MetaModel}. First, it
 * creates the CommandModel from the annotated classes, and second, it populates
 * the options and parameters into the return object based on the CommandModel.
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
     * @param taggedClasses
     */
    public AbstractCLIParser(Class<?>... taggedClasses) {
        metaModel = MetaModelFactory.createModel(taggedClasses);
    }

    /**
     * Construct the parser with a collection package names which holds the
     * annotated Command classes.
     * 
     * @param scanPackageNames
     * @throws CLIParserException
     */
    public AbstractCLIParser(String... scanPackageNames) throws CLIParserException {
        this(findCommandTaggedClasses(Arrays.asList(scanPackageNames)));
    }

    /**
     * Entry point for the parser.
     */
    @Override
    public Object parse(String... tokens) throws CLIParserException {

        // Lookup the {@link CommandElement} which contains all the metadata for
        // the
        // tagged class.
        TokenModel tokenModel = TokenModelFactory.createTokenModel(metaModel, tokens);

        // instantiate the command object
        Object cmdObj;
        try {
            cmdObj = ReflectionUtil.createCommandObject(tokenModel.getCommandToken().getMeta().getCommandClass());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CLIParserException("Failed to create the command object.", e);
        }

        // update the values to the object
        try {
            updateObjectValues(cmdObj, tokenModel);
        } catch (IllegalAccessException e) {
            throw new CLIParserException("Failed to update values to the command object.", e);
        } catch (CLIParserException e) {
            throw e;
        }

        return cmdObj;
    }

    private void updateObjectValues(Object cmdObj, TokenModel tokenModel)
            throws IllegalAccessException, CLIParserException {
        updateOptionValues(cmdObj, tokenModel);
        updateParamValues(cmdObj, tokenModel);
    }

    private void updateOptionValues(Object cmdObj, TokenModel tokenModel)
            throws IllegalAccessException, CLIParserException {
        List<OptionToken> optionTokens = tokenModel.getOptionTokens();
        for (OptionToken optionModel : optionTokens) {
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
        }

    }

    private void updateParamValues(Object cmdObj, TokenModel tokenModel)
            throws IllegalAccessException, CLIParserException {
        List<ParamToken> paramTokens = tokenModel.getParamTokens();
        for (ParamToken paramModel : paramTokens) {
            ParamMeta paramMeta = paramModel.getMeta();
            if (paramMeta.isArray()) {
                ReflectionUtil.setValuesToArrayField(cmdObj, paramMeta.getField(), paramModel.getTokens());
            } else {
                ReflectionUtil.setValueToField(cmdObj, paramMeta.getField(), paramModel.getTokens().get(0));
            }
        }
    }

    protected static Class<?>[] findCommandTaggedClasses(List<String> packageNames) throws CLIParserException {
        try {
            return PackageScanUtil.findAnnotatedClasses(CLICommandTag.class, packageNames.toArray(new String[] {}))
                    .values().toArray(new Class<?>[] {});
        } catch (ClassNotFoundException | IOException e) {
            throw new CLIParserException(
                    String.format("Failure while scanning CLICommandTag classes from packages: %s", packageNames), e);
        }
    }

    @Override
    public Set<Class<?>> getAllCommandClasses() {
        return metaModel.getAllCommandClasses();
    }
}
