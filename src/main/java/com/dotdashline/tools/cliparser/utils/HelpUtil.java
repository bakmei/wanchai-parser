/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.dotdashline.tools.cliparser.utils;

import java.util.List;

import com.dotdashline.tools.cliparser.meta.CommandMeta;
import com.dotdashline.tools.cliparser.meta.MetaModel;
import com.dotdashline.tools.cliparser.meta.OptionMeta;
import com.dotdashline.tools.cliparser.meta.ParamMeta;

public class HelpUtil {

    public static String getHelp(List<CommandMeta> cmdMetas) {
        StringBuilder ret = new StringBuilder();
        cmdMetas.stream().map(x -> ret.append(getHelp(x)));
        return ret.toString();
    }

    public static String getHelp(CommandMeta cmdMeta) {
        StringBuilder ret = new StringBuilder();

        // command
        ret.append(cmdMeta.getName()).append(": ").append(cmdMeta.getDescription());
        ret.append(ReflectionUtil.newLine);
        
        // options
        ret.append("options: ");
        cmdMeta.getOptionMeta().stream().map(x -> ret.append(getOptionHelp(x)));
        ret.append(ReflectionUtil.newLine);
        
        // params
        ret.append("parameters: ");
        cmdMeta.getParamMeta().stream().map(x -> ret.append(getParameterHelp(x)));
        ret.append(ReflectionUtil.newLine);

        return ret.toString();
    }

    public static String getOptionHelp(OptionMeta optMeta) {
        return optMeta.getPrefixes() + ": " + optMeta.getDescription();
    }

    public static String getParameterHelp(ParamMeta paramMeta) {
        return paramMeta.getName() + ": " + paramMeta.getDescription();
    }

    public static void showAllCommands(MetaModel metaModel) {
        metaModel.getAllCommandMetas().stream().forEach(x -> showSingleCommand(x));
    }

    public static void showSingleCommand(CommandMeta cmdMeta) {
        showToConsole(getHelp(cmdMeta));
    }

    public static void showToConsole(String stringFormat, Object... argu) {
        System.out.println(String.format(stringFormat, argu));

    }
}
