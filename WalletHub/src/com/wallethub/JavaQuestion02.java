package com.wallethub;

import java.util.ArrayList;

public class JavaQuestion02 {

	public ArrayList<int[]> findComplementary(int k, int[] a) {
		ArrayList<int[]> comps = new ArrayList<int[]>();

		for (int i = 0; i < a.length - 1; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (k == a[i] + a[j])
					comps.add(new int[] { a[i], a[j] });
			}
		}
		return comps;
	}

}
