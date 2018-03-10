package dynamicprogramming;

import java.util.Arrays;

public class MinimumDeletionDistanceProblem {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println(deletionDistance("dogdogdogdogdogdogdogdogdogdog", "frogfrogfrogfrogfrogfrogfrogfrog"));
		long end = System.currentTimeMillis();
		System.out.println("time took was : " + (end - start) + " mills");
	}
	
	static int deletionDistanceUsingCounts(String str1, String str2) {
		char[] c1 = new char[256];
		char[] c2 = new char[256];
		for (char c : str1.toCharArray()) {
			c1[c]++;
		}
		for (char c : str2.toCharArray()) {
			c2[c]++;
		}
		int count = 0;
		for (int i = 0; i < 256; i++) {
			count += Math.abs(c1[i] - c2[i]);
		}
		return count;
	}
	 
	static int deletionDistance(String s1, String s2) {
		int[][] cache = new int[s1.length()][s2.length()];
		for (int[] t : cache) {
			Arrays.fill(t, -1);
		}
		return findDist(s1, s2, 0, 0, cache);
		//return findDist(s1,s2,0,0);
	}

	static int findDist(String s1, String s2, int i, int j, int[][] cache) {
		if (i == s1.length())
			return s2.length() - j;
		if (j == s2.length())
			return s1.length() - i;

		if (cache[i][j] != -1) {
			return cache[i][j];
		}
		int cost = 0;
		if (s1.charAt(i) == s2.charAt(j))
			cost = findDist(s1, s2, i + 1, j + 1, cache);
		else
			cost = Math.min(findDist(s1, s2, i + 1, j, cache), findDist(s1, s2, i, j + 1, cache)) + 1;

		cache[i][j] = cost;
		return cache[i][j];
	}

	static int findDist(String s1, String s2, int i, int j) {
		if (i == s1.length())
			return s2.length() - j;
		if (j == s2.length())
			return s1.length() - i;

		if (s1.charAt(i) == s2.charAt(j))
			return findDist(s1, s2, i + 1, j + 1);
		else
			return Math.min(findDist(s1, s2, i + 1, j), findDist(s1, s2, i, j + 1)) + 1;

	}


}
