package stacksuse;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ValidParenthesesProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		validateParenthesisExample();
		
		String s = "()()(())))(())(((()))))(()";//"(())(()";
		System.out.println(lengthOfLongestValidParenthesis(s));
		
		countBracketsNeeded();
	}
	
	static void countBracketsNeeded() {
		String s = "(()((("; // needed 4 4 close 0 open
		
		System.out.println(countOfOpenCloseNeededToMakeBalance(s));
	}
	
	static void validateParenthesisExample() {
		String s = "()[]{(([]))}";
		System.out.println(validateParenthesis(s));
		
	}
	
	static boolean validateParenthesis(String s) {
		Map<Character, Character> map = new HashMap<>();
		
		map.put('(', ')');
		map.put('{', '}');
		map.put('[', ']');
		
		Deque<Character> stack = new LinkedList<>();
		
		for(int i=0;i<s.length();i++) {
			char c = s.charAt(i);
			if(map.containsKey(c)) {
				stack.push(c);
			}else if(map.values().contains(c)) {
				if(!stack.isEmpty()&&map.get(stack.peek())==c)
					stack.pop();
				else
					return false;
				
			}
		}
		
		return stack.isEmpty();
		
	}
	
	static int lengthOfLongestValidParenthesis(String s) {
		Deque<int[]> stack = new LinkedList<>();
		int maxLen = 0;
		
		for(int i=0;i<s.length();i++) {
			char c = s.charAt(i);
			if(c=='(') {
				int[] a = {i,0};
				stack.push(a);
			}
			else {
				// if stack is empty or stack top is already closing
				if(stack.isEmpty()||stack.peek()[1]==1) {
					int[] a = {i,1};
					stack.push(a);
				}else {
					// right match open close
					stack.pop();
					int curLen=0;
					if(stack.isEmpty()) {
						curLen = i+1;
					}
					else {
						curLen = i-stack.peek()[0];
					}
					maxLen = Math.max(maxLen, curLen);
				}
			}
		}
		
		return maxLen;
	}
	
	static int countOfOpenCloseNeededToMakeBalance(String s) {
		int count=0;
		int closeCountNotBalanced = 0;
		for(char c: s.toCharArray()) {
			if(c=='(') {
				count++;
			}
			else {
				if(count!=0) {
					count--;
				}
				else {
					closeCountNotBalanced++;
				}
			}
		}
		
		return count+closeCountNotBalanced;
		
	}
}
