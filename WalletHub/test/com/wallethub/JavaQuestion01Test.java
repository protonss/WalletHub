package com.wallethub;

import junit.framework.TestCase;

public class JavaQuestion01Test extends TestCase {

	public void test_type() throws Exception {
		assertNotNull(JavaQuestion01.class);
	}

	public void test_instantiation() throws Exception {
		JavaQuestion01 target = new JavaQuestion01();
		assertNotNull(target);
	}

	public void test_reverse_A$String() throws Exception {
		JavaQuestion01 target = new JavaQuestion01();
		assertEquals("654321", target.reverse("123456"));
		assertEquals("1234567", target.reverse("7654321"));
		assertEquals("_WalletHub", target.reverse("buHtellaW_"));
		assertEquals("!@#$%^&*()", target.reverse(")(*&^%$#@!"));
	}

	public void test_isPalindrome() throws Exception {
		JavaQuestion01 target = new JavaQuestion01();
		assertFalse(target.isPalindrome("home"));
		assertFalse(target.isPalindrome("1121"));
		assertTrue(target.isPalindrome("poiiop"));
		assertTrue(target.isPalindrome("123454321"));
		assertTrue(target.isPalindrome("@#$%y%$#@"));
		assertTrue(target.isPalindrome("1221"));
	}

}
