/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.scripting.groovy.internal;

import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolDependencies;

import java.io.IOException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Miguel Pastor
 */
@RunWith(PowerMockRunner.class)
public class GroovyExecutorTest {

	@BeforeClass
	public static void setUpClass() {
		ToolDependencies.wireCaches();
	}

	@Before
	public void setUp() {
		_scriptingExecutor = new GroovyExecutor();
	}

	@Test
	public void testBindingInputVariables() throws Exception {
		execute(
			new HashMap<String, Object>() {
				{
					put("variable", "string");
				}
			},
			Collections.emptySet(), "binding-input");
	}

	@Test
	public void testRuntimeError() throws Exception {
		try {
			execute(
				Collections.emptyMap(), Collections.emptySet(),
				"runtime-error");

			Assert.fail("Should throw RuntimeException");
		}
		catch (RuntimeException re) {
		}
	}

	@Test
	public void testSimpleScript() throws Exception {
		execute(Collections.emptyMap(), Collections.emptySet(), "simple");
	}

	@Test
	public void testSyntaxError() throws Exception {
		try {
			execute(
				Collections.emptyMap(), Collections.emptySet(), "syntax-error");

			Assert.fail("Should throw UnsupportedOperationException");
		}
		catch (UnsupportedOperationException uoe) {
		}
	}

	protected Map<String, Object> execute(
			Map<String, Object> inputObjects, Set<String> outputNames,
			String fileName)
		throws Exception {

		return _scriptingExecutor.eval(
			null, inputObjects, outputNames, getScript(fileName + ".groovy"));
	}

	protected String getScript(String name) throws IOException {
		return StringUtil.read(
			getClass().getResourceAsStream("dependencies/" + name));
	}

	private ScriptingExecutor _scriptingExecutor;

}