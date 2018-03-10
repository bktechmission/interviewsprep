package recursion;

import java.util.Deque;
import java.util.LinkedList;

public class TowerOfHanoiProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		towerOfHanoiExample();
	}
	
	static void towerOfHanoiExample() {
		
		Deque<Integer> src = new LinkedList<>();
		src.push(3);
		src.push(2);
		src.push(1);

		Discs srcDisc = new Discs("A",src);
		Discs helperDisc = new Discs("H",new LinkedList<>());
		Discs destDisc = new Discs("D",new LinkedList<>());
		
		
		System.out.println("src : "+ srcDisc.stack);
		System.out.println("dest : "+ destDisc.stack);
		movePlates(srcDisc,destDisc,helperDisc,src.size());
		
		System.out.println("src : "+ srcDisc.stack);
		System.out.println("dest : "+ destDisc.stack);
	}
	
	static class Discs{
		String name;
		Deque<Integer> stack;
		Discs(String name, Deque<Integer> stack){
			this.name = name;
			this.stack = stack;
		}
	}
	
	static void movePlates(Discs srcDisc, Discs destDisc, Discs helperDisc, int n) {
		if(n==1) {
			System.out.println("Moving disc "+srcDisc.name +" to "+destDisc.name);
			destDisc.stack.push(srcDisc.stack.pop());
			return;
		}
		
		// first assumption, lets says we can move n-1 top discs to helper
		movePlates(srcDisc, helperDisc, destDisc, n-1);
		
		// left with nth disc on src, so we can just move this disc to dest manually
		System.out.println("Moving disc "+srcDisc.name +" to "+destDisc.name);
		destDisc.stack.push(srcDisc.stack.pop());
		
		// now we have to n-1 discs from helper to dest that we placed at line 50th
		movePlates(helperDisc,destDisc,srcDisc,n-1);
	}

}
