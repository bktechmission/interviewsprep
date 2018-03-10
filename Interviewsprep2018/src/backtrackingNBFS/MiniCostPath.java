package backtrackingNBFS;

public class MiniCostPath {
	 Node root;
	  static class Node {
	      
	    int cost;
	    Node[] children;
	    Node parent;

	    Node(int cost) {
	      this.cost = cost;
	      children = null;
	      parent = null;
	    }
	  }

	  static class SalesPath {
	    
	    static int minCostPath = Integer.MAX_VALUE;
	    int getCheapestCost(Node rootNode) {
	      // your code goes here
	      if(rootNode==null){
	        return -1;
	      }
	     
	      getMiniCost(rootNode, 0);
	      return minCostPath;
	    }
	    
	    void getMiniCost(Node rootNode, int cost){
	      if(rootNode.children==null){
	        cost += rootNode.cost;
	        minCostPath = Math.min(minCostPath,cost);
	        //cost -= rootNode.cost; // backtrack
	        return;
	      }
	      
	      if(cost>=minCostPath){
	        return;
	      }
	      
	      cost += rootNode.cost;
	      for(Node child: rootNode.children){
	        getMiniCost(child,cost);
	      }
	      //cost -= rootNode.cost;
	    }
	  }
	    
	  /*********************************************
	   * Driver program to test above method     *
	   *********************************************/

	  public static void main(String[] args) {
		 
	  }
	
}
