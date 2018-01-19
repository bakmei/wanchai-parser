package com.dotdashline.tools.cliparser.samples.wording;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.dotdashline.tools.cliparser.tag.CLICommandTag;
import com.dotdashline.tools.cliparser.tag.CLIOptionTag;
import com.dotdashline.tools.cliparser.tag.CLIParamTag;

@CLICommandTag("SHOWME")
public class WordCommand {
	
	@CLIParamTag
	private String[] words;
	
	@CLIOptionTag("--reverse")
	private Boolean isReverse = false;

	public String execute() {
		String[] tmpWords;
		if (isReverse) {
			tmpWords = reverse(words);
		} else {
			tmpWords = clone(words);
		}
		StringBuilder sb = new StringBuilder();
		Arrays.asList(tmpWords).stream().forEach( x -> sb.append(" ").append(x));
		return sb.toString().substring(1);
	}

	private String[] reverse(String[] tmpWords) {
		List<String> list = Arrays.asList(tmpWords);
		Collections.reverse(list);
		return list.toArray(new String[list.size()]);
	}
	
	private String[] clone(String[] values) {
		String[] ret = new String[values.length];
		System.arraycopy(values, 0, ret, 0, values.length);
		return ret;
	}
}
