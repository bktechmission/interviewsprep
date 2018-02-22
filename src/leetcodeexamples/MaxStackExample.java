package leetcodeexamples;

import java.util.EmptyStackException;

public class MaxStackExample {
	public static void main(String[] args) {
		MaxStack stk = new MaxStack();
		stk.push(3);
		stk.push(15);
		stk.push(10);
		stk.push(12);
		System.out.println(stk.max());
		
		stk.push(100);
		stk.push(120);
		
		stk.push(2);
		stk.push(1);
		
		System.out.println(stk.max());
		
		System.out.println("poped:"+stk.pop());
		System.out.println("poped:"+stk.pop());
		System.out.println(stk.max());
		
		System.out.println("poped:"+stk.pop());
		System.out.println("poped:"+stk.pop());
		System.out.println(stk.max());
		System.out.println("poped:"+stk.pop());
		System.out.println("poped:"+stk.pop());
		System.out.println(stk.max());
	}
}

class MaxStack{
	class Node{
		private int val;
		private Node next;
		private Node oldMax;
	}
	private Node stack;
	private Node max;
	
	public MaxStack() {}
	
	public void push(int x) {
		Node n = new Node();
		n.val = x;
		if(stack==null) {
			stack=n;
		}
		else {
			n.next = stack;
			stack = n;
		}
		
		if(max==null|| max.val<n.val) {
			n.oldMax = max;
			max = n;
		}
	}
	
	public int pop() {
		if(stack==null) throw new EmptyStackException();
		Node n = stack;
		stack = n.next;
		if(n.oldMax!=null) {
			max = n.oldMax;
		}
		return n.val;
	}
	
	
	public int max() {
		if(stack==null) throw new EmptyStackException();
		
		return max.val;
	}
	
}