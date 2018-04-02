package arraysexamples;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSequenceProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		longestConsecutiveSequence();
	}
	static void longestConsecutiveSequence() {
		int[] a = {100,4,200,1,3,2};
		int[] b = {100,4,200,1,3,2};
		
		Arrays.sort(a);
		int i = 0;
		int start = 0;
		int maxLen = 0;
		int maxIndex = 0;
		while(i<a.length-1) {
			if(a[i]==a[i+1] || a[i]==a[i+1]-1) {
				
			}else {
				if(maxLen<(i-start+1)) {
					maxLen = Math.max(maxLen, (i-start+1));
					maxIndex = (start);
				}
				start = i+1;
			}
			i++;
		}
		
		System.out.println("MaxLen found starts with element: "+a[maxIndex] + " maxLen is "+maxLen);
		
		int len = findLengthOfLongestSequence(a);
		System.out.println("MaxLen is "+len);
	}
	
	static int findLengthOfLongestSequence(int[]a) {
		Set<Integer> set = new HashSet<>();
		for(int x:a) {
			set.add(x);
		}
		
		int maxLen = 1;
		for(int e: a) {
			int left = e-1;
			int right = e+1;
			
			int count = 1;
			while(set.contains(left)) {
				count++;
				set.remove(left);
				left--;
			}
			
			while(set.contains(right)) {
				count++;
				set.remove(right);
				right++;
			}
			
			maxLen = Math.max(maxLen, count);	
			
		}
		return maxLen;
	}
}
