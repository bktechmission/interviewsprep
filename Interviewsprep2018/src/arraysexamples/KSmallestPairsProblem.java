package arraysexamples;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class KSmallestPairsProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ksmallestPairsExample();
	}
	
	static void ksmallestPairsExample() {
		List<int[]> pairsResult = new LinkedList<>();
		
		int k = 3;
		int[] a1 = {1,5,11};
		int[] a2 = {2,4,9};
		
		k = Math.min(a1.length*a2.length, k);
		
		int[] idx = new int[a1.length];
		while(k>0) {
			int min = Integer.MAX_VALUE;
			int t = -1;
			for(int i=0; i<a1.length; i++) {
				if(idx[i]<a2.length && a1[i]+a2[idx[i]] < min) {
					min = a1[i]+a2[idx[i]];
					t = i;
				}
			}
			
			pairsResult.add(new int[] {a1[t],a2[idx[t]]});
			idx[t]++;
			k--;
		}
		
		System.out.println("\nk smallest pairs are : ");
		for(int[] p: pairsResult) {
			System.out.print("{"+Arrays.toString(p)+"},");
		}
		
	}

}
