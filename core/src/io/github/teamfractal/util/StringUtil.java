package io.github.teamfractal.util;

/**
 * Some string extension classes
 */
public class StringUtil {
	/**
	 * Capitalise the string by make first letter capital.
	 * @param str     The input string.
	 * @return        The capitalised string.
	 */
	public static String Capitalise (String str) {
		if (str.length() <= 1) {
			return str.toUpperCase();
		}

		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}
