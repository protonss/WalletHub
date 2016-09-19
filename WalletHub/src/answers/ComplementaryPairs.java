package answers;

import java.util.ArrayList;
import java.util.List;

public class ComplementaryPairs {

	public List<Integer[]> findComplementary(final int k, final Integer[] a) {
		List<Integer[]> comps = new ArrayList<Integer[]>();

		for (int i = 0; i < a.length - 1; i++) {

			// j = i + 1 bellow means I loop over the rest of the array, not over the entire array
			for (int j = i + 1; j < a.length; j++) {
				if (k == a[i] + a[j]) {
					Integer[] r = new Integer[] { a[i], a[j] };
					if (!comps.contains(r))
						comps.add(r);
				}
			}
		}
		return comps;
	}

}
