package backtrackingNBFS;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class RemoveInValidParenthesisProbelm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		removeInvalidParenthesisExample();
	}
	
	static void removeInvalidParenthesisExample() {
		String str = "(b)(a))(e)" ;
		
		List<String> finalResult = new LinkedList<>();
		Set<String> visited = new HashSet<>();
		
		//DFS way: does not gaurantee minimum level reaching first so we will be updaing whenever we find another minimum ans
		removeInvalidParenthesisDFS(str, 0, finalResult, 0, visited);
		System.out.println(finalResult);
		
		
		// BFS way: always assure you will reach minimum level answer first
		finalResult = new LinkedList<>();
		removeInvalidParenthesisBFS(str,finalResult);
		System.out.println(finalResult);
		
	}
	
	static int minLevelFound = Integer.MAX_VALUE;
	static void removeInvalidParenthesisDFS(String str, int start, 
			List<String> finalResult, int level,
			Set<String> visited) {
		
		// base case 1st: whenever a string becomes empty that means nothing to break further
		if(str.length()==0||level>minLevelFound||visited.contains(str))
		{
			return;
		}
		
		// now check if we reach answere node
		if(isValidParenthesis(str)) {
			if(minLevelFound >= level) {
				minLevelFound = level;
				finalResult.add(str);
			}
			return;
		}
		
		
		// Else otherwise first marks this node as visited
		visited.add(str);

		// we can explore all neighbors of this node; 
		for(int i = start; i<str.length(); i++) {
			// lets remove ith parenthesis
			if(!isParenthesis(str.charAt(i))) {
				continue;
			}
			
			// let remove that bracket and try to solve
			String temp = str.substring(start, i) + str.substring(i+1);
			removeInvalidParenthesisDFS(temp, 0, finalResult, level+1, visited);
		}
		
		//visited.remove(str); as we reach from other path it will not be valid anymore still
	}
	
	static class PNode{
		String str;
		int level;
		PNode(String str, int level) {
			this.str = str;
			this.level = level;
		}
	}
	
	static void removeInvalidParenthesisBFS(String str,List<String> finalResult) {
		Queue<PNode> q = new LinkedList<>();
		Set<String> visited = new HashSet<>();
		
		minLevelFound = Integer.MAX_VALUE;
		q.offer(new PNode(str,0));
		
		while(!q.isEmpty()) {
			PNode node = q.poll();
			String word = node.str;
			
			// if we are visiting already visited node
			if(word.length()==0||node.level>minLevelFound||visited.contains(word))
			{
				continue;
			}

			
			// check if we have reached the end node, note down its level too, 
			// as it BFS so we are sure we will have minimum level reached first
			if(isValidParenthesis(word)) {
				if(minLevelFound==Integer.MAX_VALUE) {
					minLevelFound = node.level;
				}
				
				if(minLevelFound==node.level) {
					finalResult.add(word);
				}
				
				continue;
			}
			
			
			// otherwise add this node to visited
			visited.add(word);
	
			// try to explore all possible paths from this node as neighbors
			// as we are using str substring so we dont need to reset chars for back tracking as
			// string creates always a new string for new node;
			for(int i=0;i<word.length();i++) {
				String subWord = str.substring(0,i)+str.substring(i+1);
				q.offer(new PNode(subWord,node.level+1));
			}
			//visited.remove(word);
		}
	}
	
	
	static boolean isValidParenthesis(String str) {
		int count = 0;
		for(int i=0;i<str.length();i++) {
			if(str.charAt(i)=='(') {
				count++;
			}
			else if(str.charAt(i)==')') {
				count--;
			}
			if(count<0) {
				return false;
			}
		}
		
		return count==0;
	}
	
	static boolean isParenthesis(char c)
	{
	    return ((c == '(') || (c == ')'));
	}

}
