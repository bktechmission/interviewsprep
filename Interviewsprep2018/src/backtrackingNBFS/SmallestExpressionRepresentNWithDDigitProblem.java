package backtrackingNBFS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SmallestExpressionRepresentNWithDDigitProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		smallestExpressionExample();
	}
	
	static void smallestExpressionExample() {
		
		int N = 128;
		int D = 2;
		List<String> finalAns = new ArrayList<String>();
		
		findExpressionBFS(N,D,finalAns);
		System.out.println("final expression is "+finalAns);
	}
	
	static void findExpressionBFS(int N, int D, List<String> finalAns){
		
		Queue<ExpressionValueNode> q = new LinkedList<>();
		
		q.offer(new ExpressionValueNode(D,(""+D),null));
		
		while(!q.isEmpty()) {
			ExpressionValueNode node = q.poll();
			
			if(node.val<=0) {
				continue;
			}
			
			if(node.val==N) {
				finalAns.add(node.expresion);
				return;
				// found ans here
				// break, as we are doing bfs so this will be first minimum level with ans
			}
			
			
			// try all the possible expression
			String expresion = node.expresion;
			
			// first add
			String newExpression = expresion.isEmpty()?(expresion+""+D):(expresion+"+"+D);
			ExpressionValueNode newNode = new ExpressionValueNode(node.val+D,newExpression,node);
			q.offer(newNode);
			
			
			// subtract if possible
			if(node.val-D>=1) {
				newExpression = expresion.isEmpty()?(expresion+""+D):(expresion+"-"+D);
				newNode = new ExpressionValueNode(node.val-D,newExpression,node);
				q.offer(newNode);
			}
			
			
			// * 
			newExpression = expresion.isEmpty()?(expresion+""+D):("("+expresion+")"+"*"+D);
			newNode = new ExpressionValueNode(node.val*D,newExpression,node);
			q.offer(newNode);
			
			
			// divide if possible
			if(node.val%D==0) {
				newExpression = expresion.isEmpty()?(expresion+""+D):("("+expresion+")"+"/"+D);
				newNode = new ExpressionValueNode(node.val/D,newExpression,node);
				q.offer(newNode);
			}
		}
		
	}
	
	static class ExpressionValueNode{
		int val;
		String expresion;
		ExpressionValueNode parent;
		ExpressionValueNode(int val, String expresion, ExpressionValueNode parent){
			this.val = val;
			this.expresion = expresion;
			this.parent = parent;
		}
	}

}
