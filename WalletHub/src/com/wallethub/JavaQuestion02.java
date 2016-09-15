package com.wallethub;

import java.util.ArrayList;
import java.util.List;

public class JavaQuestion02 {

	public List<int[]> findComplementary(final int k, final int[] a) {
		List<int[]> comps = new ArrayList<int[]>();

		for (int i = 0; i < a.length - 1; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (k == a[i] + a[j])
					comps.add(new int[] { a[i], a[j] });
			}
		}
		return comps;
	}

}
