package io.github.teamfractal.util;

import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link StringUtil}
 */
public class StringUtilTest {
	@Test
	public void capitaliseShouldBeDoneProperly(){
		assertEquals("Java", StringUtil.Capitalise("java"));
		assertEquals("JAVA", StringUtil.Capitalise("jAVA"));
		assertEquals("J", StringUtil.Capitalise("j"));
	}
}
