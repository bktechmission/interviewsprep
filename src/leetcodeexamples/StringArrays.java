package leetcodeexamples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StringArrays {
	public static void main(String[] args) {
		System.out.println(isAnagram("AABCD","abcda"));
		
		System.out.println(compressAndSort("abccdbbcccddef"));
		
		System.out.println(compress("abccdbbccccccccddddddddeeeeeeeeeeeecccddef"));
		List<String> words = Arrays.asList("AbcDef","ABCfed","abcijk","abcjki","abcefd","abecdf","abcrst");
		
		Map<String, List<String>> result = groupAnagrams(words);
		
		result.entrySet().forEach(x->{
			System.out.println("key: "+x.getKey() + ", value is: "+x.getValue());
		});
		
		List<String> s = Arrays.asList(("Bhupender., software bhupender engineer "
				+ "computer engineer bhupender software "
				+ "computer google engineer doctor; bhupender kumar panwar "
				+ "standford Stanford computer").split("[\\s\\.]+"));
		
		List<String> wordsKthMostfreq = kthMostFrequenetWords(s,10);
		wordsKthMostfreq.forEach(System.out::println);
		
		List<String> permutations = permutation("12345",3);
		
		int i=1;
		for(String p:permutations) {
			System.out.println(i+++". "+p);
		}
		
		System.out.println("\n\nnow combination:->");
		List<String> comsults = combine("12345",3);
		i=1;
		for(String p:comsults) {
			System.out.println(i+++". "+p);
		}
		
		System.out.println("\n\nnow permutation on [] :->");
		
		int[] a = {1,2,3,4,5};
		i=1;
		List<int[]> presults = permutation(a,3);
		for(int[] k:presults) {
			System.out.println(i+++". "+Arrays.toString(k));
		}
		
		
		System.out.println("\n\nnow combination on [] :->");
		
		
		i=1;
		List<int[]> comresults = combination(a,3);
		for(int[] k:comresults) {
			System.out.println(i+++". "+Arrays.toString(k));
		}
		

		
		//printAllSets(a);
	}
	
	// for each ele we have 2 choices either incl or exclude in final ans
	public static void printAllSets(int[]a) {
		List<int[]> results = new ArrayList<>();
		int[] result  = new int[a.length];
		allSets(a,result,results,0);
		System.out.println("Printing all sets");
		int c = 1;
		for(int[] k:results) {
			System.out.print(c++ +".");
			for(int l:k) {
				if(l!=-1) {
					System.out.print(l+",");
				}else {
					System.out.print("null,");
				}
				
			}
			System.out.println();
		}
	}
	
	public static void allSets(int[]a, int[] result, List<int[]> results, int index) {
		if(index==a.length) {
			results.add(result.clone());
		}
		else {
			result[index] = -1;
			allSets(a,result,results, index+1);
			result[index] = a[index];
			allSets(a,result,results, index+1);
		}
	}
	// rotate array  256 charset lowercase
	public static boolean isAnagram(String s1, String s2) {
		if(s1.length()!=s2.length())
		{
			return false;
		}
		
		if(s1==s2||s1.equalsIgnoreCase(s2)) {
			return true;
		}
		
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();
		int[] counts = new int[256];
		for(int i=0;i<s1.length();i++) {
			counts[s1.charAt(i)]++;
			counts[s2.charAt(i)]--;
		}
		
		for(int i:counts) {
			if(i!=0) return false;
		}
		
		return true;
	}
	
	// string compression
	//what is charset 256 ascii or 128 simple ascii or Java 2 bytes
	// capitalization
	public static String compressAndSort(String s) {
		if(s.length()<=1) {
			return s;
		}
		s = s.toLowerCase();
		int[] counts = new int[256];
		for(char c: s.toCharArray()) {
			counts[c]++;
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<counts.length;i++) {
			if(counts[i]!=0) {
				sb.append((char)i);
				sb.append(counts[i]);
			}
		}
		return sb.toString();
		
	}
	
	public static String compress(String s) {
		if(s.length()<=1) {
			return s;
		}
		
		s = s.toLowerCase();
		
		int count=1;
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<s.length()-1;i++) {
			if(s.charAt(i)==s.charAt(i+1)) {
				count++;
			}
			else {
				sb.append(s.charAt(i));
				sb.append(count);
				count=1;
			}
		}
		
		sb.append(s.charAt(s.length()-1));
		sb.append(count);
		System.out.println(sb.toString());
		
		return sb.toString().length()>s.length()?s:sb.toString();
	}
	
	public static Map<String, List<String>> groupAnagrams(List<String> list){
		Map<String, List<String>> result =  new HashMap<>();
		for(String word: list) {
			word = word.toLowerCase();
			//char[] c = word.toCharArray();
			//Arrays.sort(c);
			//String key = new String(c);
			String key = compressAndSort(word);
			List<String> wordList = result.get(key);
			if(wordList==null) {
				wordList = new ArrayList<>();
				result.put(key, wordList);
			}
			wordList.add(word);
		}
		
		return result;
	}
	
	static class TermCount{
		String word;
		int count;
		
		TermCount(String word, int count){
			this.word = word;
			this.count = count;
		}
		
		public String getWord() {
			return this.word;
		}
	}
	
	public static List<String> kthMostFrequenetWords(List<String> words, int k){
		Map<String, Long> wordFreqMap = words.stream()
				.map(x->x.trim().replaceAll("[\\s\\.,:]+", "").toLowerCase().trim())
				.filter(x->x.length()>0)
				.map(x-> new TermCount(x,1))
				.collect(Collectors.groupingBy(TermCount::getWord,Collectors.counting()));
		
		List<Map.Entry<String, Long>> list = new ArrayList<>(wordFreqMap.entrySet());
		Collections.sort(list,(e1,e2)->{
			return e2.getValue().compareTo(e1.getValue());
		});
		List<String> returnResult = list.stream()
											.limit(k=list.size()>k?k:list.size())
											.map(e->e.getKey()+":"+e.getValue())
											.collect(Collectors.toList());;
		
		return returnResult;
		
	}
	
	public static List<int[]> permutation(int[] a, int r) {
		List<int[]> results = new ArrayList<int[]>();
		int[] data = new int[r];
		permutation(a, results, 0, data, r, 0);
		return results;
	}
	
	public static void permutation(int[] a, List<int[]> results, int start,
			int[]data, int r, int index) {
		if(index==r) {
			results.add(data.clone());
		}
		else {
			for(int i=start;i<a.length;i++) {
				swap(a,i,start);
				data[index] = a[start];
				permutation(a,results,start+1,data,r, index+1);
				swap(a,i,start);
			}
		}	
	}
	
	public static List<int[]> combination(int[] a, int r) {
		List<int[]> results = new ArrayList<int[]>();
		int[] data = new int[r];
		combination(a, results, 0, data, r,0);
		return results;
	}
	
	public static void combination(int[] a, List<int[]> results, int start,
			int[]data, int r, int index) {
		if(index==r) {
			results.add(data.clone());
		}
		else {
			for(int i=start;i<a.length;i++) {
				//swap(a,i,start);
				data[index] = a[i];
				combination(a,results,i+1,data,r,index+1);
				//swap(a,i,start);
			}
		}	
	}
	
	public static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	
	public static List<String> permutation(String s, int r){
		List<String> results = new ArrayList<String>();
		permute("",s,results,r);
		return results;
	}
	
	
	public static List<String> combine(String s, int r){
		List<String> results = new ArrayList<String>();
		combine("",s,results,r);
		return results;
	}
	
	public static void permute(String prefix, String suffix, List<String> results,int r) {
		if(prefix.length()==r) {
			results.add(prefix);
			return;
		}
		
		for(int i=0;i<suffix.length();i++) {
			permute(prefix+suffix.charAt(i),
					suffix.substring(0, i)+suffix.substring(i+1),results,r);
		}
	}

	
	public static void combine(String prefix, String suffix, List<String> results, int r) {
		if(prefix.length()==r) {
			results.add(prefix);
			return;
		}
		
		for(int i=0;i<suffix.length();i++) {
			combine(prefix+suffix.charAt(i),suffix.substring(i+1),results,r);
		}
	}
	
	
}
