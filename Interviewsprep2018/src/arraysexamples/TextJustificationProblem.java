package arraysexamples;

import java.util.LinkedList;
import java.util.List;

public class TextJustificationProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		textJustifyExample();
	}
	
	static void textJustifyExample() {
		String[] words = {"This", "is", "an","example", "of","text","justification."};
		
		List<String> finalResult = new LinkedList<>();
		
		justifyText(words, 0, finalResult, 15);
		
		for(String s: finalResult) {
			
			System.out.println("\""+s+"\"");
		}
		
	}
	
	static void justifyText(String[] words, int start, List<String> finalResult, int L) {
		if(start >= words.length) {
			return;
		}
		
		int totalWithSpaces = 0;
		int totalWordsLength = 0;
		
		int next = -1;
		int i = start;
		
		while(i < words.length && totalWithSpaces < L) {
			totalWithSpaces += words[i].length();
			
			if(totalWithSpaces>L) {
				next = i;
				break;
			}
			
			totalWordsLength +=  words[i].length();
			
			// add space 1 to totalWithSpaces
			totalWithSpaces++;
			i++;
			
		}
		
		if(next==-1) {
			next = i;
		}
		
		addWordsToList(words, start, next, finalResult, L, totalWordsLength);
		
		justifyText(words, next, finalResult, L);
		
	}
	
	static void addWordsToList(String[] words, int start, int next, List<String> finalResult, int L, int totalWordsLength) {
		// calculate number of space slots
		int slots = next - start - 1;
		StringBuilder sb = new StringBuilder();
		
		// check if only single word or last line
		if(slots == 0 || next == words.length) {
			for(int j=start; j<next;j++) {
				sb.append(words[j]);
				
				if(j == next-1) {
					break;
				}
				
				sb.append(" ");
			}
			
			int trailingspaces = L - totalWordsLength - slots;
			
			for(int i=0; i<trailingspaces; i++) {
				sb.append(" ");
			}
			finalResult.add(sb.toString());
		}
		else {
			int avgspaces = (L - totalWordsLength) / slots;
			int extraspaces =  (L - totalWordsLength) % slots;
			
			for(int j=start; j<next; j++) {
				sb.append(words[j]);
				
				if(j == next-1) {
					break;
				}
				
				for(int i=0; i<avgspaces; i++) {
					sb.append(" ");
				}
				
				if(extraspaces > 0) {
					sb.append(" ");
					extraspaces--;
				}
			}
			finalResult.add(sb.toString());
			
		}
	}
}
