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
		int len = str.length();
		char[] ca = new char[len];
		for (int i = 0; i < (int) (len+1) / 2; i++) { //loop over the half string
			ca[i] = str.charAt(len - (i + 1));
			ca[len - (i + 1)] = str.charAt(i);
		}
		return new String(ca);
	}

}
