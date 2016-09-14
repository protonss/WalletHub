package com.wallethub;

import static org.junit.Assert.assertNotEquals;

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
		assertEquals("123", target.reverse("321"));
		assertEquals("12", target.reverse("21"));
		assertEquals("1", target.reverse("1"));
		assertEquals(" 1", target.reverse("1 "));
		assertEquals(" ", target.reverse(" "));
		assertEquals("", target.reverse(""));
		assertEquals("321", target.reverse(target.reverse("321")));
		assertEquals("54321", target.reverse("12345"));
		assertEquals("1234567", target.reverse("7654321"));
		assertEquals("_WalletHub", target.reverse("buHtellaW_"));
		assertEquals("!@#$%^&*()", target.reverse(")(*&^%$#@!"));
		assertEquals("!@#$%^&*()_+=-0987654321", target.reverse("1234567890-=+_)(*&^%$#@!"));
		assertEquals("[];',./", target.reverse("/.,';]["));
		assertEquals("{}|:<>?", target.reverse("?><:|}{"));

		assertNotEquals("54321", target.reverse("54321"));
		assertNotEquals("54321", target.reverse("1234"));
		assertNotEquals("54321", target.reverse("123456"));
		assertNotEquals("54321", target.reverse(" 54321"));
		assertNotEquals("54321", target.reverse("554321"));
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
