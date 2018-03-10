package stringproblems;

import java.util.Arrays;

public class Permutations {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		setGenerationExmaple();
		printAllSubsequences();
		permuteExample();
		combinationExample();
	}
	
	static void combinationExample() {
		System.out.println("Printing all nCr Combinations: ");
		int[] a = {1,2,3};
		int r = 2;
		printNCR(a,new int[r],0,0,r);
		
	}
	
	static void permuteExample() {
		System.out.println("Printing all nPn n! Permutations : ");
		String s = "123";
		printAllPermutation(s,"");
	}
	
	static void printAllSubsequences() {
		System.out.println("Printing all Subsequences: ");
		String s = "123";
		printAllSubsequences(s,"");
	}

	
	static void setGenerationExmaple() {
		System.out.println("Printing all Sets : ");
		int[]a = {1,2,3};
		generatePowerSet(a,new int[a.length],0);
	}
	static void generatePowerSet(int[]a, int[]out, int i){
		if(i==a.length) {
			printSet(out, -1);
			return;
		}
		
		out[i] = -1;
		generatePowerSet(a,out,i+1);
		
		out[i] = a[i];
		generatePowerSet(a,out,i+1);
	}
	
	static void printAllSubsequences(String s, String prefix) {
		if(s.length()==0) {
			System.out.println("{"+prefix+"}");
			return;
		}
		
		// let exclude first char
		printAllSubsequences(s.substring(1),prefix);
		
		// let include the first char
		printAllSubsequences(s.substring(1),prefix+s.charAt(0));
	}
	
	static void printAllPermutation(char[] a, int start, int n) {
		if(start==a.length) {
			System.out.println(Arrays.toString(a));
		}
		
		for(int i=start;i<n;i++) {
			swap(a,i,start);
			printAllPermutation(a,start,n);
			swap(a,i,start);
		}
	}
	
	static void printAllPermutation(String s, String prefix) {
		if(s.length()==0) {
			System.out.println(prefix);
		}
		
		for(int i=0;i<s.length();i++) {
			printAllPermutation(s.substring(0, i)+s.substring(i+1),prefix+s.charAt(i));
		}
	}
	
	static void swap(char[]a,int i, int j) {
		char temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	static void printSet(int[]a,int del) {
		System.out.print("{");
		for(int x:a) {
			if(x!=del) {
				System.out.print(x+",");
			}
		}
		System.out.println("}");
	}
	
	static void printNCR(int[]a, int[]data, int dindex, int astart, int r) {
		if(dindex==r) {
			System.out.println(Arrays.toString(data));
			return;
		}
		
		for(int i=astart; i<a.length && a.length-i >= r-dindex; i++) {
			data[dindex] = a[i];
			printNCR(a,data,dindex+1,i+1,r);
		}
	}
}
