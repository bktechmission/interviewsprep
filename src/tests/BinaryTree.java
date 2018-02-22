package tests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

class TreeNode{
	Integer data;
	TreeNode left,right,parent;
	public TreeNode(Integer data)
	{
		this.data = data;
	}
}

class MyInteger{
	int val;
	public MyInteger(int x){
		val=x;
	}
	public String toString()
	{
		return String.valueOf(val);
	}
}

public class BinaryTree {
	static Integer count = new Integer(0);
	public static void main(String[] args)
	{
		int[] a = {30, 20, 10,40,2,25,30,80,1,10,90};
		maxProfit(a);
		MyInteger i =  new MyInteger(0);
		permutationsOfAString("","abcd",i);
		//perm2("abcd");
		//subSequences("abcd");
		//subString("abcde");
		//callme(count);
		//System.out.println(count);
		
		char x='c';
		Character c1 = x;
		Character c2 = x;
		System.out.println(c1==c2);
		
		String rid ="rid";
		String s1 = rid.intern();
		String s2 = rid;
		System.out.println(s1==s2);
		
	}
	
	static void callme(Integer x)
	{
		Integer y = x;
		x++;
		x++;
		System.out.println("in call me " + x + "  y is "+y);
	}
	static void callme(MyInteger x)
	{
		MyInteger y = x;
		x.val++;
		x.val++;
		System.out.println("in call me " + x + "  y is "+y);
	}
	
	// Tree Traversal
	void preOder(TreeNode root)
	{
		// +ab	Top Down
		if(root!= null)
		{
			// prcess the node data
			System.out.print(root.data +" ");
			preOder(root.left);
			preOder(root.right);
		}
	}
	
	void inOrder(TreeNode root)
	{
		//a+b	Bottom Up
		if(root!=null)
		{
			inOrder(root.left);
			System.out.print(root.data +" ");
			inOrder(root.right);
		}
	}
	
	void postOrder(TreeNode root)
	{
		if(root!=null)
		{
			postOrder(root.left);
			postOrder(root.right);
			System.out.print(root.data + " ");
		}
	}
	
	void preOderStackWay(TreeNode root)
	{
		// req +ab
		// O(height) space, O(n) time
		Stack<TreeNode> s = new Stack<TreeNode>();
		TreeNode cur = root;
		s.push(cur);
		while(!s.isEmpty())
		{
			cur = s.pop();
			System.out.println(cur.data+" ");
			
			if(cur.right!=null)
			{
				s.push(cur.right);
			}
			
			if(cur.left!=null)
			{
				s.push(cur.left);
			}
		}
	}
	
	void inOrderStackWay(TreeNode root)
	{
		Stack<TreeNode> s = new Stack<>();
		TreeNode cur = root;
		s.push(cur);
		while(!s.isEmpty())
		{
			while(cur!=null)
			{
				cur = cur.left;
				if(cur!=null)
					s.push(cur);
			}
			cur = s.pop();
			System.out.println(cur.data+" ");
			cur = cur.right;
		}
	}
	
	void postOrderStackWay(TreeNode root)
	{
		Stack<TreeNode> s1 = new Stack<TreeNode>();
		Stack<TreeNode> s2 = new Stack<TreeNode>();
		TreeNode cur = root;
		s1.push(cur);
		while(!s1.isEmpty())
		{
			cur = s1.pop();
			s1.push(cur);
			if(cur.left!=null)
				s1.push(cur);
			if(cur.right!=null)
				s1.push(cur.right);
		}
		while(!s2.isEmpty())
		{
			System.out.println(s2.pop() + " ");
		}
	}
	
	TreeNode maximumIterative(TreeNode root)
	{
		/*
		 * Iterative way
		 */
		while(root.right!=null)
		{
			root=root.right;
		}
		return root;
	}
	
	TreeNode maximumRecursive(TreeNode root)
	{
		if(root==null || root.right==null)
			return root;
		return maximumRecursive(root.right);
	}
	
	TreeNode minimumIterative(TreeNode root)
	{
		while(root.left!=null)
		{
			root=root.right;
		}
		return root;
	}
	
	TreeNode minimumRecursive(TreeNode root)
	{
		if(root==null || root.left==null)
			return root;
		return minimumRecursive(root.left);
	}
	
	TreeNode insert(TreeNode root, Integer x)
	{
		if(root==null)
		{
			root = new TreeNode(x);
		}
		else
		{
			if(root.data>x)
			{
				root.left = insert(root.left,x);
				root.left.parent = root;
			}
			else if(root.data<x)
			{
				root.right = insert(root.right,x);
				root.right.parent = root;
			}
		}
		return root;
	}
	
	TreeNode delete(TreeNode root, Integer x)
	{
		if(root==null)
			return root;
		else
		{
			if(root.data>x)
				root.left = delete(root.left,x);
			else if(root.data<x)
				root.right = delete(root.right,x);
			else
			{
				// delete both left and right exists
				if(root.left!=null && root.right!=null)
				{
					TreeNode temp = leftMost(root.right);
					root.data = temp.data;
					root.right = delete(root.right,x);
				}
				else if(root.left==null)
					root = delete(root.right,x);
				else 
					root = delete(root.left,x);
			}
		}
		return root;
	}
	/*
	 * Assume parent pointer is there, and BST
	 */
	TreeNode nextInOrderSuccessor(TreeNode root)
	{
		if(root!=null)
		{
			//case 1st right is not NULL
			if(root.parent==null || root.right!=null)
				return leftMost(root.right);
			// case 2nd root right is null, so we need to go up the hierarchy
			else
			{
				TreeNode p = null;
				while((p=root.parent)!=null)
				{
					if(p.left==root)
						break;
					root=p;
				}
				return p;
			}
			
		}
		return null;
	}
	
	TreeNode nextInOrderSuccessorWithoutParentPointer(TreeNode root, TreeNode node)
	{
		if(node!=null)
		{
			// case 1st: right side is not null
			if(node.right!=null)
				return leftMost(node.right);
			else
			{
				TreeNode successor = null;
				while(root!=null)
				{
					if(root.data>node.data)
					{
						successor = root;
						root= root.left;
					}
					else if(root.data<node.data)
						root = root.right;
					else
						break;
				}
				return successor;
			}
		}
		return null;
	}
	
	// Parent Pointer is given
	TreeNode previousInOrderPredecessor(TreeNode node)
	{
		if(node!=null)
		{
			// case 1st: left of node is NOT null
			if(node.left!=null)
				return rightMost(node.left);
			else
			{
				TreeNode p = null;
				while((p=node.parent)!=null)
				{
					if(p.right==node)
						return p;
					node=p;
				}
				return p;
			}
		}
		return null;
	}
	// No Parent Pointer is given
	TreeNode previousInOrderPredecessor(TreeNode root, TreeNode node)
	{
		if(node!=null)
		{
			// case 1st: left side is not null
			if(node.left!=null)
				return rightMost(node.left);
			else
			{
				TreeNode pre = null;
				while(root!=null)
				{
					if(root.data>node.data)
					{
						root = root.left;
					}
					else if(root.data<node.data)
					{
						pre = root;
						root = root.right;
					}
					else 
						return pre;
				}
			}
		}
		return null;
	}
	
	// without parent pointer, can access n1 data and n2 data and is a BST
	TreeNode LCAInBST(TreeNode root, TreeNode n1, TreeNode n2)
	{
		while(root!=null)
		{
			Integer val = root.data; 
			if(val>n1.data && val>n2.data)
				root = root.left;
			else if(val<n1.data && val <n2.data)
				root = root.right;
			else
				return root;
		}
		return null;
	}
	
	// parent pointer is given, ONLY Binary Tree, can not access n1 and n2 data
	// Approach 1st: have a hashSet, put each ancesstor of n1 in it, then start with n2 to its ancesstor and if found a match just return it. Might need access to data
	// Approach 2nd: Intersection of linked list
	TreeNode lcaInBinaryTreeWithParentPointer(TreeNode n1, TreeNode n2)
	{
		int d1 = depth(n1);
		int d2 = depth(n2);
		
		int diff = 0;
		if(d1>=d2)
		{
			diff = d1-d2;
			return intersectionOfYShapedLinkedList(n1,n2,diff);
		}
		else
		{
			diff = d2-d1;
			return intersectionOfYShapedLinkedList(n2,n1,diff);
		}
			
	}
	
	TreeNode intersectionOfYShapedLinkedList(TreeNode l1, TreeNode l2, int diff)
	{
		while(diff>0)
		{
			l1=l1.parent;
			diff--;
		}
		while(l1!=null && l2!=null)
		{
			if(l1==l2)
				return l1;
			l1=l1.parent;
			l2=l2.parent;
		}
		return null;
		
	}
	
	int depth(TreeNode node)
	{
		int depth=-1;
		while(node!=null)
		{
			depth++;
			node = node.parent;
		}
		return depth;
	}
	
	// Only binary tree
	// Approach 1st: 2pass : No pointer to parent, we need root then as we will traverse down to find n1 and n2 in pass 1st and 2nd, store their paths and then find first match in that array: 
	TreeNode findLCAWithArrayIntersection(TreeNode root, TreeNode n1, TreeNode n2)
	{
		LinkedList<TreeNode> path1 = new LinkedList<TreeNode>();
		LinkedList<TreeNode> path2 = new LinkedList<TreeNode>();
		if(!findData(root,n1,path1) || !findData(root,n2,path2))
			return null;
		for(int i=0;i<path1.size()&&i<path2.size();i++)
		{
			if(path1.get(i)!=path2.get(i))
				return path1.get(i);	
		}
		return null;
	}
	
	boolean findData(TreeNode root, TreeNode n1, LinkedList<TreeNode> path)
	{
		// if we reached leaf null return false, we exhausted this path, back track after this
		if(root==null)
			return false;
		path.addLast(root);
		if(root.data==n1.data)
			return true;

		if((root.left!=null && findData(root.left,n1,path))
				|| (root.right!=null && findData(root.right,n1,path)))
			return true;
		path.removeLast();
		return false;
	}
	
	TreeNode findLCA(TreeNode root, TreeNode n1, TreeNode n2)
	{
		if(root==null)
			return null;
		if(root==n1 || root==n2)
			return root;
		TreeNode ln = findLCA(root.left,n1,n2);
		TreeNode rn = findLCA(root.right,n1,n2);
		if(ln!=null && rn!=null)
			return root;
		else if(ln!=null)
			return ln;
		else
			return rn; 
	}
	
	
	TreeNode leftMost(TreeNode root)
	{
		while(root.left!=null)
		{
			root.left = root.left;
		}
		return root;
	}
	
	TreeNode rightMost(TreeNode root)
	{
		while(root.right!=null)
		{
			root = root.right;
		}
		return root;
	}
	
	boolean searchInBST(TreeNode root, Integer key)
	{
		if(root==null)
			return false;
		if(root.data==key)
			return true;
		else if(root.data>key)
			return searchInBST(root.left,key);
		else
			return searchInBST(root.right,key);
	}
	
	boolean searchInBinaryTree(TreeNode root, Integer key)
	{
		if(root==null)
			return false;
		if(root.data == key)
			return true;
		return searchInBinaryTree(root.left,key) || searchInBinaryTree(root.right, key);
	}
	
	static int maxProfit(int[] a)
	  {
	    int maxdiff = a[1]-a[0];
	    int minimum = a[0];
	    int buyIndex = 0;
	    int sellIndex = 1;
	    int j = 0;
	    for(int i=1;i<a.length;i++)
	    {
	      if(a[i]-minimum > maxdiff)
	      {
	        buyIndex = j;
	        sellIndex = i;
	        maxdiff = a[i]-minimum;
	      }
	      if(a[i]<minimum)
	      {
	        j = i;
	        minimum = a[i];
	      }
	    }
	    System.out.println("buy at "+buyIndex+" sell at "+sellIndex +", profit is "+maxdiff);
	    return maxdiff;
	  }
	
	// inStart=0, inEnd=in.length-1, 
	TreeNode contructBinaryTreeFromInAndPre(int[] in, int[] pre, int inStart, int inEnd, Integer preIndex)
	{
		// check inStart>inEnd return null
		if(inStart>inEnd)
			return null;
		// Root is always First one in pre order
		TreeNode root = new TreeNode(pre[preIndex]);
		preIndex++;
		if(inStart==inEnd)
			return root;
		int inIndex = search(in,root.data, inStart, inEnd);
		root.left = contructBinaryTreeFromInAndPre(in,pre,inStart,inIndex-1,preIndex);
		root.right = contructBinaryTreeFromInAndPre(in,pre,inIndex+1,inEnd,preIndex);
		return root;
	}
	
	// inStart=0, inEnd=in.length postIndex=post.length-1
	TreeNode constructBinaryTreeFromInAndPost(int[] in, int[] post, int inStart, int inEnd, Integer postIndex)
	{
		if(inStart>inEnd)
			return null;
		TreeNode root = new TreeNode(post[postIndex]);
		postIndex--;
		if(inStart==inEnd)
			return root;
		int inIndex = search(in,root.data,inStart, inEnd);
		root.left = constructBinaryTreeFromInAndPost(in,post,inStart,inIndex-1,postIndex);
		root.right = constructBinaryTreeFromInAndPost(in, post, inIndex+1,inEnd,postIndex);
		return root;
		
	}
	
	
	// BST : we will pass in Integer.MIN_VALUE, Integer.MAX_VALUE, key = pre[preIndex]
	TreeNode constructBSTFromPreOrder(int[] pre,Integer preIndex, int key, int min, int max, int size)
	{
		if(preIndex>=size)
			return null;
		
		TreeNode root = null;
		if(key>min && key<max)
		{
			root = new TreeNode(key);
			preIndex++;
			if(preIndex<size)
			{
				root.left = constructBSTFromPreOrder(pre,preIndex,pre[preIndex],min,key,size);
				root.right = constructBSTFromPreOrder(pre,preIndex,pre[preIndex],key,max,size);
			}
		}
		return root;
	}
	
	TreeNode constructBSTFromPostOrder(int[]post,Integer postIndex, int key, int min, int max, int size)
	{
		if(postIndex<0)
			return null;
		TreeNode root = null;
		if(key>min && key<max)
		{
			root = new TreeNode(post[postIndex]);
			postIndex--;
			if(postIndex>0)
			{
				root.left = constructBSTFromPostOrder(post, postIndex,post[postIndex],min,key,size);
				root.right = constructBSTFromPostOrder(post, postIndex, post[postIndex], key, max, size);
			}
		}
		return root;
	}
	
	int search(int[]in,int key,int start, int end)
	{
		int i = 0;
		for(i=start;i<=end;i++)
		{
			if(in[i]==key)
				break;
		}
		return i;
	}
	
	void mirror(TreeNode root)
	{
		if(root!=null)
		{
			mirror(root.left);
			mirror(root.right);
			TreeNode temp = root.left;
			root.left = root.right;
			root.right = temp;
		}
	}
	
	void deleteTree(TreeNode root)
	{
		if(root!=null)
		{
			deleteTree(root.left);
			deleteTree(root.right);
			root = null;
		}
	}
	
	void printSpiralTree(TreeNode root)
	{
		Stack<TreeNode> s1 = new Stack<TreeNode>();	// right to left
		Stack<TreeNode> s2 = new Stack<TreeNode>(); // left to right
		s1.push(root);
		while(!s1.isEmpty() || !s2.isEmpty())
		{
			while(!s1.isEmpty())
			{
				TreeNode x = s1.pop();
				System.out.print(x.data + " ");
				if(x.right!=null)
					s2.push(x.right);
				if(x.left!=null)
					s2.push(x.left);
			}
			System.out.println();
			while(!s2.isEmpty())
			{
				TreeNode x = s2.pop();
				System.out.print(x.data + " ");
				if(x.left!=null)
					s1.push(x.left);
				if(x.right!=null)
					s1.push(x.right);
			}
		}
	}
	
	void printRootToLeaf(TreeNode root, int pathLen, List<Integer> buffer)
	{
		if(root==null)
			return;
		buffer.set(pathLen++, root.data);
		if(isLeaf(root))
			printPath(buffer, pathLen);
		else
		{
			printRootToLeaf(root.left,pathLen,buffer);
			printRootToLeaf(root.right,pathLen, buffer);
		}
	}
	
	void printPath(List<Integer> list, int pathLen)
	{
		for(int i=0;i<pathLen;i++)
		{
			System.out.println(list.get(i)+" ");
		}
	}
	
	boolean isLeaf(TreeNode root)
	{
		if(root==null)
			return false;
		if(root.left!=null && root.right!=null)
			return true;
		return false;
	}
	
	void printNodesXAxisWise(TreeNode root)
	{
		if(root==null)
			return;
		Queue<TreeNodeWithPriority> pq = new PriorityQueue<TreeNodeWithPriority>();
		processNodesByColumns(root,0,0,pq);
		pq.stream().forEach(System.out::println);
	}
	
	void processNodesByColumns(TreeNode root, int x, int y, Queue<TreeNodeWithPriority> pq)
	{
		if(root==null)
			return;
		pq.offer(new TreeNodeWithPriority(root, x, y));
		processNodesByColumns(root.left,x-1,y+1,pq);
		processNodesByColumns(root.right,x+1,y+1,pq);
		
	}
	
	void verticalSums(TreeNode root)
	{
		if(root==null)
			return;
		Map<Integer,Integer> hm = new HashMap<Integer, Integer>();
		verticalSumUtil(root,0,hm);
		hm.keySet().stream().forEach(System.out::println);
	}
	
	void verticalSumUtil(TreeNode root, int hd, Map<Integer,Integer>hm)
	{
		if(root==null)
			return;
		verticalSumUtil(root.left,hd-1,hm);
		int prev = hm.containsKey(hd)?hm.get(hd):0;
		hm.put(hd, prev+root.data);
		verticalSumUtil(root.right,hd+1,hm);
	}
	
	// Approach 1st O(N^2)
	boolean isBST(TreeNode root)
	{
		if(root==null)
			return true;
		return isSubTreeSmallerThan(root.left,root.data)
				&& isSubTreeGreaterThan(root.right,root.data)
				&& isBST(root.left) && isBST(root.right);
	}
	
	boolean isSubTreeSmallerThan(TreeNode root, int data)
	{
		if(root==null)
			return true;
		return root.data<data
				&&isSubTreeSmallerThan(root.left,data) && isSubTreeSmallerThan(root.right, data);
	}
	
	boolean isSubTreeGreaterThan(TreeNode root, int data)
	{
		if(root==null)
			return false;
		return root.data>data
				&&isSubTreeGreaterThan(root.left,data) && isSubTreeGreaterThan(root.right,data);
	}
	
	boolean isBSTNew(TreeNode root)
	{
		return isBSTHelper(root,Integer.MIN_VALUE,Integer.MAX_VALUE);
	}
	
	boolean isBSTHelper(TreeNode root, Integer min, Integer max)
	{
		if(root==null)
			return true;
		return (min==null || root.data>min) && (max==null || root.data<max)
				&& isBSTHelper(root.left,min,root.data)
				&& isBSTHelper(root.right,root.data,max);
	}
	
	boolean isBSTNew2(TreeNode root)
	{
		TreeNode prev = null;
		return isMonotonicIncreasing(root, prev);
		
	}
	boolean isMonotonicIncreasing(TreeNode root, TreeNode prev)
	{
		if(root==null) return true;
		if(isMonotonicIncreasing(root.left,prev))
		{
			if(prev!=null && root.data<=prev.data) return false;
			prev = root;
			return isMonotonicIncreasing(root.right,prev);
		}
		return false;
	}
	
	int maxDepth(TreeNode root)
	{
		if(root==null)
			return 0;
		return Math.max(maxDepth(root.left), maxDepth(root.right))+1;
	}
	
	int minDepth(TreeNode root)
	{
		if(root==null)
			return 0;
		if(root.left==null)
			return minDepth(root.right)+1;
		if(root.right==null)
			return minDepth(root.left)+1;
		return Math.min(minDepth(root.left),minDepth(root.right))+1;
	}
	
	/*
	 * GOAL: Reach the first leaf node in BFS, that will be the minimum depth of tree
	 * pop each element from Queue and check if it leaf
	 * if we reach the rightmost node, please update the rightmost node and increase the depth by 1
	 */
	int minDepthBFSWay(TreeNode root)
	{
		if(root==null)
			return 0;
		Queue<TreeNode> pq = new LinkedList<>();
		pq.add(root);
		int depth = 1;
		TreeNode rightMost = root;
		while(!pq.isEmpty())
		{
			TreeNode node = pq.poll();
			// if this is a leaf break here as we reached first leave in BFS and this is the minimum depth
			if(node.left==null && node.right==null)
				break;
			if(node.left!=null) pq.add(node.left);
			if(node.right!=null) pq.add(node.right);
			
			//check if this node is rightmost if it is then increase depth by 1
			if(node==rightMost)
			{
				depth++;
				rightMost = node.right!=null?node.right:node.left;
			}
		}
		return depth;
	}
	
	public class TreeNodeWithPriority implements Comparable<TreeNodeWithPriority>
	{
		TreeNode node; int x, y;
		public TreeNodeWithPriority(TreeNode node, int x, int y) {
			this.node = node;
			this.x = x;
			this.y = y;
		}
		@Override
		public int compareTo(TreeNodeWithPriority o) {
			if(this.x==o.x) return Integer.compare(this.y,o.y);
			return Integer.compare(this.x,o.x);
		}
	}
	
	static void permutationsOfAString(String prefix, String s,MyInteger count)
	{
		int N = s.length();
		if(N==0)
		{
			count.val++;
			System.out.println(count+". "+prefix);
		}
		else
		{
			for(int i=0;i<N;i++)
			{
				permutationsOfAString(prefix+s.charAt(i),s.substring(0, i)+s.substring(i+1),count);
			}
		}
	}
	
	public static void perm2(String s) {
        char[] a = s.toCharArray();
        perm2(a, a.length);
    }

    private static void perm2(char[] a, int n) {
        if (n == 1) {
            System.out.println(a);
            return;
        }
        for (int i = 0; i < n; i++) {
            swap(a, i, n-1);
            perm2(a, n-1);
            swap(a, i, n-1);
        }
    }  

    // swap the characters at indices i and j
    private static void swap(char[] a, int i, int j) {
        char c = a[i];
        a[i] = a[j];
        a[j] = c;
    }
	
    public static void subStringOfLengthK(String prefix, String remaining, int k) {
        if (k == 0) {
            System.out.println(prefix);
            return;
        }
        if (remaining.length() == 0) return;
        subStringOfLengthK(prefix + remaining.charAt(0), remaining.substring(1), k-1);
        subStringOfLengthK(prefix, remaining.substring(1), k);
    }
    
    public static void subString(String s)
    {
    	for(int from=0;from<s.length();from++)
    	{
    		for(int to=from+1;to<=s.length();to++)
    		{
    			System.out.println(s.substring(from, to));
    		}
    	}
    }
    
    public static void subSequences(String s) {
        int p = 1<<s.length();
        for(int out = 0;out<p;out++)
        {
        	for(int j=0;j<s.length();j++)
        	{
        		if((out&(1<<j))!=0)
        		{
        			System.out.print(s.charAt(j));
        		}
        	}
        	System.out.println();
        }
    }
}

class BSTIterator implements Iterator<TreeNode>{
	private Stack<TreeNode> stack = new Stack<TreeNode>();
	private TreeNode currentNode;
	private BSTIterator(TreeNode root)
	{
		currentNode = root;
	}
	public boolean hasNext()
	{
		return !stack.isEmpty() || currentNode!=null;
	}
	
	public TreeNode next()
	{
		while(currentNode!=null)
		{
			stack.push(currentNode);
			currentNode = currentNode.left;
		}
		TreeNode node = stack.pop();
		currentNode = node.right;
		return node;
	}
	public static BSTIterator iterator(TreeNode root) {
		return new BSTIterator(root);
	}
}
