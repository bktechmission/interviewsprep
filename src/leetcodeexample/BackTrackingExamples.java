package leetcodeexample;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class BackTrackingExamples {

	
	public static void main(String[] args) {
		
		
		solveWordLadderProblemExample();
	}
	
	static void solveWordLadderProblemExample() {
		String start = "hit";
		String end = "cog";
		List<String> dic = new LinkedList<>();
		dic.add("hot");
		dic.add("dot");
		dic.add("dog");
		dic.add("lot");
		dic.add("log");
		dic.add("cog");
		solveWordLadderProblem(start,end,dic);
	}
	static class WordTypeNode{
		String word;
		WordTypeNode parent;
		int level;
		WordTypeNode(String word, int level, WordTypeNode parent){
			this.word = word;
			this.level = level;
			this.parent = parent;
		}
		public String toString() {
			return word;
		}
	}
	
	static void solveWordLadderProblem(String start, String end, List<String> dic) 
	{
		List<List<String>> finalans = new LinkedList<>();
		
		Set<String> unvisited = new HashSet<>();
		Set<String> visited = new HashSet<>();
		
		unvisited.addAll(dic);
		
		Queue<WordTypeNode> nextToTry = new LinkedList<>();
		nextToTry.add(new WordTypeNode(start, 0, null));
		
		int minAnswerFoundLevel = -1;
		int prevLevel = -1;
		
		while(!nextToTry.isEmpty()) {
			WordTypeNode curNode = nextToTry.poll();
			String word = curNode.word;
			
			if(word.equalsIgnoreCase(end)) {
				if(minAnswerFoundLevel==-1) {
					minAnswerFoundLevel = curNode.level;
				}
				
				if(minAnswerFoundLevel!=-1&&minAnswerFoundLevel==curNode.level) {
					Deque<String> temp = new LinkedList<>();
					List<String> listTemp = new LinkedList<>();
					while(curNode!=null) {
						temp.add(curNode.word);
						curNode = curNode.parent;
					}
					int size = temp.size();
					for(int i=0;i<size;i++) {
						listTemp.add(temp.removeLast());
					}
					finalans.add(listTemp);
				}
				continue;
			}
			
			if(prevLevel<curNode.level) {
				unvisited.removeAll(visited);
			}
			
			if(minAnswerFoundLevel!=-1&&minAnswerFoundLevel<curNode.level)
				break;
			prevLevel = curNode.level;
			
			char[] carray = word.toCharArray();
			for(int i=0;i<carray.length;i++) {
				for(char c = 'a';c<='z';c++) {
					char temp = carray[i];
					if(temp==c)
						continue;
					carray[i] = c;
					String newWord = String.valueOf(carray);
					if(unvisited.contains(newWord)) {
						nextToTry.offer(new WordTypeNode(newWord, curNode.level+1, curNode));
						visited.add(newWord);
					}
					
					carray[i] = temp;
				}
			}
		}
		
		
		System.out.println("All paths are: " +finalans);
	}
}


