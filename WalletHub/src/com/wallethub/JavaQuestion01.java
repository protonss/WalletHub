package com.wallethub;

public class JavaQuestion01 {

	public boolean isPalindrome(final String str) {
		return str.equals(this.reverse(str));
	}

	/**
	 * I created two methods for basically two reasons 1) To make my code more
	 * easily testable 2) To ensure decoupling
	 * 
	 * @param str
	 * @return String
	 */
	public String reverse(final String str) {
		char tmp = '\0';
		char[] astr = str.toCharArray();
		int l = astr.length;
		// iterates up to the half of the string
		// better performance for extremely big strings
		for (int i = 0; i < (int) l / 2; i++) {
			tmp = astr[l - i - 1];
			astr[l - i - 1] = astr[i];
			astr[i] = tmp;
		}
		return new String(astr);
	}

	/**
	 * Another approach could be the code below But I think that's not the
	 * objective of this test
	 * 
	 * @param str
	 * @return String
	 */
	public String reverse2(String str) {
		return new StringBuilder(str).reverse().toString();
	}
}
