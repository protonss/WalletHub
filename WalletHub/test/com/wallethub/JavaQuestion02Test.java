package com.wallethub;

import java.util.ArrayList;

import junit.framework.TestCase;

public class JavaQuestion02Test extends TestCase {

	public void test_type() throws Exception {
		assertNotNull(JavaQuestion02.class);
	}

	public void test_instantiation() throws Exception {
		JavaQuestion02 target = new JavaQuestion02();
		assertNotNull(target);
	}

	public void test_findComplementary() throws Exception {
		JavaQuestion02 target = new JavaQuestion02();
		int k = 7;
		int[] a = new int[]{0,1,2,7,10,3,4};
		
		ArrayList<int[]> expected = new ArrayList<int[]>();
		expected.add(new int[]{0,7});
		expected.add(new int[]{3,4});
		
		ArrayList<int[]> result = target.findComplementary(k, a);
		
		assertTrue(result.size()==expected.size());
	}

}
