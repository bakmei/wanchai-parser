package com.dotdashline.tools.cliparser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultCLIParserTest {

	private CLIParser parser;

	@Before
	public void setUp() throws Exception {
		// any class is fine
		parser = new DefaultCLIParser(DefaultCLIParserTest.class);
	}

	@Test
	public void test_null_cmd() {
		try {
			parser.parse(null);
		} catch (CLIParserException ex) {
			Assert.assertEquals(ErrorCode.NO_INPUT, ex.getErrorCode());
		}
	}

	@Test
	public void test_empty_cmd() {
		try {
			parser.parse(new String[] {});
			Assert.fail("SHould have thrown CLIParserException.");
		} catch (CLIParserException ex) {
			Assert.assertEquals(ErrorCode.NO_INPUT, ex.getErrorCode());
		}
	}

	@Test
	public void test_missing_cmd() {
		try {
			Assert.assertNull(parser.parse(new String[] { "undefined" }));
		} catch (CLIParserException ex) {
			Assert.assertEquals(ErrorCode.INVALID_COMMAND, ex.getErrorCode());
		}
	}
}
