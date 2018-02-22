package fbinterviews;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class TopKWords 
{
	public static final Random RANDOM = new Random();
	public static final String TEXT = "Computer Engineer\nComputer Science\nEngineer is good\nComputer is better for Engineer\nScience Revolutions\nMaths and Science\nComputer do the magic\nMagic in Science and Computer";
	public static void main(String[] args)
	{
		Map<String, Integer> wordFreq = new HashMap<String, Integer>();
		Scanner sc = new Scanner(TEXT);
		while(sc.hasNext())
		{
			String word = sc.next();
			word = word.toLowerCase().trim();
			wordFreq.put(word, wordFreq.get(word) == null ? 1 : (wordFreq.get(word)+1));
		}
		sc.close();
		
		long start = System.nanoTime();
		System.out.println("Top 3 words Using B Tree Map of all elements: ");
		printTop3Words_UsingTreeMap(wordFreq);
		long end = System.currentTimeMillis();
		System.out.println("Time took: " + (end-start) + "ms");
		
		start = System.nanoTime();
		System.out.println("\n\nTop 3 words Using Min Heap of 3 elements: ");
		printTop3Words_UsingMinHeapOf3Elemts(wordFreq);
		end = System.currentTimeMillis();
		System.out.println("Time took: " + (end-start) + "ms");
		
		long time = 1426437354594L;
		Date date = new Date(time);
		System.out.println("date : " + date);
		
		String d = "12343232.32332";
		int x = ((Number)Float.valueOf(d)).intValue();
		System.out.println(x);

		long v = RANDOM.nextLong();
		System.out.println("Random date is: " + v);
		
		Date randomDate = new Date(v);
		System.out.println("Random date is: " + randomDate);
		
	}
	
	
	public static void printTop3Words_UsingTreeMap(Map<String, Integer> wordFreq)
	{
		
		// Use Tree which will use Freq as Key and List as Value for Sorted by Freq in Decreasing Order
		Map<Integer, Set<String>> sortedFreqWordListMap = new TreeMap<Integer, Set<String>>(Collections.reverseOrder());
		for(Map.Entry<String, Integer> entry: wordFreq.entrySet())
		{
			Integer freq = entry.getValue();
			String word = entry.getKey();
			//System.out.println("word:"+ word +"; freq:"+ freq);
			
			if(!sortedFreqWordListMap.containsKey(freq))
			{
				sortedFreqWordListMap.put(freq, new HashSet<String>());
			}
			
			sortedFreqWordListMap.get(freq).add(word);

		}
		
		int count = 0;
		
		// Print top 3 most frequent Words
		for(Map.Entry<Integer, Set<String>> entry: sortedFreqWordListMap.entrySet())
		{
			System.out.println("Freq: "+ entry.getKey() + ", value: " + entry.getValue());
			count++;
			if(count == 3)
				break;
		}	
	}
	

	public static void printTop3Words_UsingMinHeapOf3Elemts(Map<String, Integer> wordFreq)
	{
		
		// Use Min Heap of Top 3 Elements, always maintain it
		Queue<HeapFreqWordEntry> minheap = new PriorityQueue<HeapFreqWordEntry>();
		for(Map.Entry<String, Integer> entry: wordFreq.entrySet())
		{
			
			Integer freq = entry.getValue();
			String word = entry.getKey();
			HeapFreqWordEntry heapEntry = new HeapFreqWordEntry(freq, word);
			//System.out.println("word:"+ word +"; freq:"+ freq);
			
			// First cerate min heap of 3 elements
			if(minheap.size()<3)
			{
				minheap.add(heapEntry);
			}
			else	
			{
				// Check top element, if it is less than current element, swap it, call heapify
				HeapFreqWordEntry tempEntry = minheap.peek();
				if(tempEntry.getFreq() < freq)
				{
					tempEntry = minheap.poll();
					minheap.add(new HeapFreqWordEntry(freq, word));
				}
			}
		}
		
		
		
		// Print top 3 most frequent Words
		for(HeapFreqWordEntry heapEntry : minheap)
		{
			System.out.println("Freq: "+ heapEntry.getFreq() + ", value: " + heapEntry.getWord());
		}	
	}
	
	
	
}


class HeapFreqWordEntry implements Comparable<HeapFreqWordEntry>{
	
	Integer freq;

	String word;
	
	public Integer getFreq() {
		return freq;
	}

	public void setFreq(Integer freq) {
		this.freq = freq;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	
	public HeapFreqWordEntry(Integer freq, String word)
	{
		this.freq = freq;
		this.word = word;
	}

	@Override
	public int compareTo(HeapFreqWordEntry o) 
	{
		return this.freq.compareTo(o.freq);
	}
	
	
	
	
}
