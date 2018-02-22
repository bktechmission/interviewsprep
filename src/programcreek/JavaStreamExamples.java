package programcreek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class JavaStreamExamples {
	private static final Set<String> stopWords = new HashSet<String>();
	static {
		stopWords.add("is");
		stopWords.add("in");
		stopWords.add("the");
		stopWords.add("from");
		stopWords.add("an");
		stopWords.add("for");
		stopWords.add("a");
		stopWords.add("at");
		
	}
	public static void main(String[] args) throws IOException {
		String s = "Bhupender is a software engineer."
				+ "Graduated from Arizona State. Bhupender develop software."
				+ "Software development is an art. Job openings at Google software field."
				+ "Bhupender applied for software jobs. Jobs in software is treding";
		Stream<String> wordsStream = Arrays.stream(s.trim().split("[\\s\\.]"));
		Stream<String> stream = wordsStream
				.map(word->word.replaceAll("[^a-zA-Z]", "").trim().toLowerCase())		// punctuations and , . removal
				.filter(x->(!stopWords.contains(x)&&x.length()>0))	;	// stop words elimination// remove any zero length words
		try(PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get("bagofwords.txt")))){
			stream.forEach(pw::println);
		}
		

		try(BufferedReader br=Files.newBufferedReader(Paths.get("WholeSentences.txt"))){
			Map<String, Long> freqMap = br.lines().parallel()
										.flatMap(line->Arrays.stream(line.trim().split("[\\s\\.]")))
										.map(word->word.replaceAll("[^a-zA-Z]", "").trim().toLowerCase())
										.filter(word->(word.length()>0&&!stopWords.contains(word)))						
										.map(x->new WordEntry(x,1L))
										.collect(Collectors.groupingBy(WordEntry::getWord,Collectors.counting()));
			System.out.println(freqMap);
			
			Map<String, Long> topFreqMap = freqMap.entrySet().stream()
			.sorted(Map.Entry.<String,Long>comparingByValue().reversed())
			.limit(4)
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1,v2)->v1+v2, LinkedHashMap::new));
			System.out.println(topFreqMap);

		}
		
		
		
		/*long x = 2305843009213693951L;
		long start = System.currentTimeMillis();
		boolean result = isNotPrime.test(x);
		long end = System.currentTimeMillis();
		System.out.println("time took was "+(end-start)/1000 +" seconds");
		if(result) {
			System.out.println("not prime");
		}
		else {
			System.out.println("prime");
		}*/
		
		
		int num = 15435213;
		// print all prmie factors with powers
		Map<Integer, Integer> map = printPrimeFactors(num);
		map = map.entrySet().stream()
				.sorted(Map.Entry.<Integer, Integer>comparingByKey())
				.collect(Collectors.toMap(Map.Entry::getKey, 
											Map.Entry::getValue,
											 (oldValue, newValue) -> oldValue,
											LinkedHashMap::new)
						);
		System.out.println(map);
		
		
		
		// print all prime numbers till n
		long start = System.currentTimeMillis();
		
		//List<Integer> results = printAllPrimes(num);
		List<Integer> results = printAllPrimesEfficient(num);
		long end = System.currentTimeMillis();
		int i=0; int groupOf = 10;
		/*while(i<results.size()) {
			int endIndex = (i+groupOf<results.size())?i+groupOf:results.size();
			//System.out.println(results.subList(i, endIndex));
			i+=groupOf;
		}*/
		
		System.out.println("time took was "+(end-start)/1000 +" seconds");
	}
	
	
	public static List<Integer> printAllPrimes(int num) {
		System.out.println("started printAllPrimes");
		List<Integer> results = new ArrayList<>();
		if(num>=2) {
			results.add(2);
		}
		if(num>=3) {
			results.add(3);
		}
		
		for(int i=4;i<=num;i++) {
			if(!isNotPrime.test((long)i)) {
				results.add(i);
			}
		}
		System.out.println("ended printAllPrimes");
		return results;
	}
	
	public static List<Integer> printAllPrimesEfficient(int num) {
		List<Integer> results = new ArrayList<>();
		System.out.println("started printAllPrimesEfficient");
		int maxBitsPerEntry = 64;
		long[] prime = new long[((num+1)/maxBitsPerEntry)+1];
		prime[0] = prime[0]|1L<<0;		// 0 is not prime
		prime[0] = prime[0]|1L<<1;		// 1 is not prime
		for(int p=2;p*p<=num;p++) {
			if((prime[p/maxBitsPerEntry]&(1L<<(p%maxBitsPerEntry)))==0) {
				for(int pmultiples=p*2;pmultiples<=num;pmultiples=pmultiples+p) {
					prime[pmultiples/maxBitsPerEntry] = prime[pmultiples/maxBitsPerEntry]|(1L<<(pmultiples%maxBitsPerEntry));
				}
			}
		}
		
		for(int i=2;i<=num;i++) {
			if((prime[i/maxBitsPerEntry]&(1L<<(i%maxBitsPerEntry)))==0) {
				//System.out.println(i);
				results.add(i);
			}
		}
		
		System.out.println("ended printAllPrimesEfficient");
		return results;
	}
	
	public static Map printPrimeFactors(int num) {
		Map<Integer, Integer> map = new HashMap<>();
		if(num<4) {
			map.put(num, 1);
			return map;
		}
		boolean foundFactor = false;
		int count = 0;
		while(num%2==0)
		{
			foundFactor = true;
			count++;
			num/=2;
		}
		if(foundFactor) {
			map.put(2, count);
		}
		
		for(int i=3;i*i<=num;i+=2) {
			foundFactor = false;
			count = 0;
			while(num%i==0)
			{
				foundFactor = true;
				count++;
				num/=2;
			}
			if(foundFactor) {
				map.put(i, count);
			}
		}
		
		if(num>1) {
			map.put(num, 1);
		}
		return map;
	}
	
	public static final Predicate<Long> isNotPrime = n->LongStream.range(2, (int) Math.sqrt(n)+1)
			//.peek(num -> System.out.println("will filter " + num +" in thread is "+Thread.currentThread().getName()))
			.unordered()
			.parallel()
			.filter(mod->(n%mod==0))
			.findFirst().isPresent();
	
	
	static class WordEntry{
		String word;
		Long count;
		
		public WordEntry(String word2, Long i) {
			this.word = word2;
			this.count = i;
		}

		public String getWord() {
			return word;
		}
	}
}
