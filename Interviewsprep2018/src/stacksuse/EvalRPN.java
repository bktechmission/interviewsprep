package stacksuse;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class EvalRPN {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "4 13 5 / +";
		System.out.println("exp val : "+ evalRPN(s.split(" ")));
	}

	@FunctionalInterface
	interface Operator {
		int eval(int x, int y);
	}

	private static final Map<String, Operator> OPERATORS = new HashMap<String, Operator>();
	static {
		OPERATORS.put("+", (x,y)->x+y);
		OPERATORS.put("-", (x,y)->x-y);
		OPERATORS.put("*", (x,y)->x*y);
		OPERATORS.put("/", (x,y)->x/y);
	}
		
	static int evalRPN(String[] tokens) {
		Deque<Integer> stack = new LinkedList<>();
		for (String token : tokens) {
			token = token.trim();
			if (OPERATORS.containsKey(token)) {
				int y = stack.pop();
				int x = stack.pop();
				stack.push(OPERATORS.get(token).eval(x, y));
			} else {
				stack.push(Integer.parseInt(token));
			}
		}
		return stack.pop();
	}

}
