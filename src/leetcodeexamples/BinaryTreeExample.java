package leetcodeexamples;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import interviewsprep.HeapUsedProblems;

public class BinaryTreeExample {
/*
       153               
      / \       
     /   \      
    /     \     
   /       \    
   65       190       
  / \       \   
 /   \       \  
 21   93       371   
            /   
            322  
	*/		
	public static void main(String[] args) {
		BinaryTree<Integer> bt = new BinaryTree<>();
		Random r = new Random();
		int size = 7;
		List<Integer> a = r.ints(5,400).limit(size).boxed().collect(Collectors.toList());
		System.out.println(a);
		
		for(int i : a) {
			bt.root = insert(bt.root, i);
		}
		
		BTreePrinter.printNode(bt.root);
		System.out.print("PreOrderTraversal is : [");
		preOrder(bt.root);
		System.out.println("]");
		
		System.out.print("InOrderTraversal is : [");
		inOrder(bt.root);
		System.out.println("]");
		
		System.out.print("PostOrderTraversal is : [");
		postOrder(bt.root);
		System.out.println("]");
		
		System.out.print("BFS Traversal is : [");
		bfs(bt.root);
		System.out.println("]");
		
		Node<Integer> root1 = new Node<>(153);
		root1.left = new Node<>(65);
		root1.right = new Node<>(190);
		root1.left.left = new Node<>(21);
		root1.left.right = new Node<>(93);
		root1.left.left.right = new Node<>(55);
		
		root1.right.right = new Node<>(371);
		root1.right.left = new Node<>(160);
		root1.right.right.left = new Node<>(332);
		root1.right.right.left.right = new Node<>(366);
		root1.right.right.left.right.left = new Node<>(356);
		
		BTreePrinter.printNode(root1);
		System.out.print("PreOrderRecursive:->  ");
		preOrder(root1);
		System.out.println();
		System.out.print("PreOrderWithStack:->  ");
		preOrderTraversalWithStack(root1);
		System.out.println();
		
		System.out.print("InOrderRecursive:->  ");
		inOrder(root1);
		System.out.println();
		System.out.print("InOrderWithStack:->  ");
		inOrderTraversalWithStack(root1);
		System.out.print("\nInOrderWithThreadedBinaryTree:->  ");
		iterativeInOrderTravesalWithThreadedApproach(root1);
		System.out.println();
		
		
		System.out.print("PostOrderRecursive:->  ");
		postOrder(root1);
		System.out.println();
		System.out.print("PostOrderWith2Stack:->  ");
		postOrderTraversakWith2Stacks(root1);
		System.out.println();
		System.out.print("PostOrderWith1Stack:->  ");
		postOrderWith1Stack(root1);
		System.out.println();
		
		Node<Integer> root2 = new Node<>(153);
		insertIterative(root2,190);
		insertIterative(root2,21);
		insertIterative(root2,93);
		insertIterative(root2,55);
		insertIterative(root2,371);
		insertIterative(root2,160);
		insertIterative(root2,332);
		insertIterative(root2,336);
		insertIterative(root2,356);
		BTreePrinter.printNode(root2);
		
		System.out.println("deleting 21 fromBST");
		root2 = deleteFromBSTRecursive(root2,190);
		BTreePrinter.printNode(root2);
		
		Node<Integer> root3 = new Node<>(153);
		insertIterative(root3,190);
		insertIterative(root3,21);
		insertIterative(root3,93);
		insertIterative(root3,55);
		insertIterative(root3,371);
		insertIterative(root3,160);
		insertIterative(root3,332);
		insertIterative(root3,336);
		insertIterative(root3,356);
		System.out.println("deleting 371 fromBST");
		root3 = deleteFromBSTIterative(root3,190);
		BTreePrinter.printNode(root3);
		
		
		System.out.println("are two trees are identical : "+identicalTree(root2,root3));
		System.out.println("are two trees are Isomorphic : "+isIsomorphic(root2,root3));
		
		System.out.println("mirror image");
		createMirrorImageTree(root3);
		BTreePrinter.printNode(root3);

		System.out.println("are two trees are identical : "+identicalTree(root2,root3));
		System.out.println("are two trees are Isomorphic : "+isIsomorphic(root2,root3));
		
		
		
		// print views now
		printLeftViews(root3);
		printRightViews(root3);
		
		
		//print views recursive
		printLeftViewRecursive(root3);
		printRightViewRecursive(root3);
		
		bottomView(root2);
		bottomViewRecursive(root2);
		
		sumOfVerticals(root2);
		
		sumInRightDiagonals(root2);
		sumInLeftDiagonals(root2);
		
		printDiagonalsRightRecursive(root2);
		printDiagonalsLeftRecursive(root2);
		
		
		Node<Integer> node = searchInBST(root2,332);
		System.out.println("searched node is "+node.data);
		Node<Integer> pre = getInOrderPredecessorOfAGivenNode(root2, node);
		System.out.println("InOrderPredecessor of Node: "+node.data +"  is "+pre.data);
		pre = getInOrderPredecessorOfAGivenNodeWithParentPointer(node);
		System.out.println("InOrderPredecessor of Node: "+node.data +"  is "+pre.data);
		
		node = searchInBST(root2,356);
		System.out.println("searched node is "+node.data);
		Node<Integer> suc = getInOrderSuccessorOfAGivenNode(root2, node);
		System.out.println("InOrderSuccessor of Node: "+node.data +"  is "+suc.data);
		suc = getInOrderSuccessorOfAGivenNodeWithParentPointer(node);
		System.out.println("InOrderSuccessor of Node: "+node.data +"  is "+suc.data);
		
		System.out.println("floor for 358 is "+floor(root2,358));
		System.out.println("ceiling for 358 is "+ceiling(root2,358));
		
		// lowest common ancesstor problems
		System.out.println("lca for 336,160 "+lcaInBST(root2,336,160));
		System.out.println("lca for 336,160 "+lcaInBinaryTreeWithoutParentPointer(root2,336,160));
		System.out.println("lca for 336,160 "+lcaInBinaryTreeWithArrayStore(root2,336,160));
		System.out.println("lca for 336,160 "+lcaWithParentPointer(searchInBST(root2,336),searchInBST(root2,160)));
		
		BTreePrinter.printNode(root2);
		System.out.println("is valid BST root2 : "+isValidBST(root2));
		BTreePrinter.printNode(root3);
		System.out.println("is valid BST root3 : "+isValidBST(root3));
		
		System.out.println("is valid BST root3 : "+isValidBSTWithCheckOnInOrder(root2));
		
		Node<Integer> root4 = new Node<>(153);
		insertIterative(root4,190);
		insertIterative(root4,21);
		insertIterative(root4,93);
		insertIterative(root4,183);
		//insertIterative(root4,186);
		BTreePrinter.printNode(root4);
		System.out.println("isHeightBalanced : "+ isHeightBalancedBinaryTreeNSquareAlgo(root4));
		System.out.println("isHeightBalanced : "+ isHeightBalanced(root4));
		
		
		
		BTNode root5 = new BTNode(3);
		root5.left = new BTNode(10);
		root5.left.left = new BTNode(15);
		root5.left.right = new BTNode(3);
		root5.left.right.left = new BTNode(12);
		root5.left.right.right = new BTNode(11);
		
		root5.right = new BTNode(9);
		root5.right.left = new BTNode(1);
		root5.right.left.left = new BTNode(-5);
		root5.right.left.left.left = new BTNode(7);
		root5.right.left.left.left.left = new BTNode(-2);
		root5.right.left.right = new BTNode(12);
		
		BTreePrinterBT.printNode(root5);
		allPathsSumRootToLeaf(root5,28); // sum 28
		
		allPathsSumRootToAnyNode(root5,13); // sum 28
		System.out.println(3-((-10%3+3)%3));
		allPathsSumAnyNodeToAnyNode(root5,13);
		
		BTNode root6 = new BTNode(35);
		root6.right = new BTNode(34);
		root6.right.right = new BTNode(31);
		root6.right.left = new BTNode(21);
		root6.left = root5;
		BTreePrinterBT.printNode(root6);
		System.out.println("getDiamerterNSquare "+ getDiamerterNSquare(root6));
		System.out.println("getDiamerterNSquare "+ getDiameter(root6));
		
		BTreePreOrderTraversalIterator bpreitr = new BTreePreOrderTraversalIterator(root6);
		System.out.println("printing pre order using iterator : ->");
		while(bpreitr.hasNext()) {
			System.out.print(bpreitr.next().data+",");
		}
		System.out.println();
		
		BTreeInOrderIterator binoitr = new BTreeInOrderIterator(root6);
		System.out.println("printing in order using iterator : ->");
		while(binoitr.hasNext()) {
			System.out.print(binoitr.next().data+",");
		}
		System.out.println();
		
		
		BTreeLevelOrderTraversalIterator blvlitr = new BTreeLevelOrderTraversalIterator(root6);
		System.out.println("printing level order using iterator : ->");
		while(blvlitr.hasNext()) {
			System.out.print(blvlitr.next().data+",");
		}
		System.out.println();
		
		BTreePostOrderTraversalIterator bpostitr = new BTreePostOrderTraversalIterator(root6);
		System.out.println("printing post order using iterator : ->");
		while(bpostitr.hasNext()) {
			System.out.print(bpostitr.next().data+",");
		}
		System.out.println();
		
		
		System.out.println("Bottom Up using for loop on height & recursion");
		printLevelOrderTraversalLevelByLevelTopDown(root6);
		System.out.println();
		System.out.println("Bottom Up using queue and level order traversal with stack it instead of print");
		printLevelOrderTraversalLevelByLevelBottomUp(root6);
		
		
		System.out.println();
		System.out.println("node on right of 12 -> "+getNextNodeOnRight(root6,12));
		System.out.println("node on right of -5 -> "+getNextNodeOnRight(root6,-5));
		System.out.println("node on right of 3 -> "+getNextNodeOnRight(root6,3));
		
		System.out.println("priting boundaries");
		printBoundariesRootLeftBottomRight(root6);
		
		System.out.println("converting to sum tree");
		convertToSumTree(root6);
		BTreePrinterBT.printNode(root6);
		System.out.println("is to sum tree :-> "+isSumTreeOptimized(root6));
		
		BTNode root7 = new BTNode(15);
		root7.left = new BTNode(5);
		root7.right = new BTNode(-6);
		root7.left.left = new BTNode(-3);
		root7.left.right = new BTNode(1);
		root7.left.left.left = new BTNode(2);
		root7.left.left.right = new BTNode(6);
		root7.right.left = new BTNode(3);
		root7.right.left.left = new BTNode(2);
		root7.right.left.right = new BTNode(12);
		root7.right.right = new BTNode(9);
		root7.right.right.right= new BTNode(0);
		root7.right.right.right.left= new BTNode(-4);
		root7.right.right.right.right= new BTNode(-1);
		root7.right.right.right.right.left= new BTNode(-10);
		BTreePrinterBT.printNode(root7);
		
		
		BTNode root8 = new BTNode(4);
		root8.left = new BTNode(5);
		root8.right = new BTNode(3);
		root8.left.left = new BTNode(6);
		root8.left.right = new BTNode(8);
		BTreePrinterBT.printNode(root8);
		
		printEachPathAsDigit(root8);
		printEachPathAsDigitReverse(root8);
		
		
		System.out.println("Height is: "+getDiagonal(root8));
		
		System.out.println("Height is: "+getDiagonalOptimized(root8));
		
		
		System.out.println("height balanced :-> "+isHeightBalanced(root8));
		
		System.out.println("height balanced :-> "+isHeightBalancedOptimizedOne(root8));
		
		BTNode root9 = new BTNode(40);
		root9.left = new BTNode(30);
		root9.right = new BTNode(60);
		root9.left.left = new BTNode(16);
		root9.left.right = new BTNode(35);
		BTreePrinterBT.printNode(root9);

		
		System.out.println("isBST :-> "+isBSTValid(root9, null, null));
		System.out.println("isBST :-> "+isMonotonicIncreasing(root9, null));
		System.out.println("isBST :-> "+isBSTTree(root9));
		
	}
	
	static class GlobalMax {
		int val = Integer.MIN_VALUE;
	}
	
	static class GlobalMaxPath {
		List<BTNode> list = new ArrayList<>();
	}
	public static int getMaxSum(BTNode root) {
		GlobalMax mx = new GlobalMax();
		List<BTNode> finalList = new ArrayList<BTNode>();
		List<BTNode> globalFinalList = new ArrayList<BTNode>();
		
		getMaxSum(root,mx,finalList,globalFinalList);
		System.out.println(globalFinalList);
		System.out.println("max sum is :-> "+mx.val);
		return mx.val;
	}
	
	public static int getMaxSum(BTNode root, GlobalMax mx,List<BTNode> finalList,
			List<BTNode> globalFinalList) {
		if(root==null) {
			return 0;
		}
		
		if(root.left==null&root.right==null) {
			finalList.add(root);
			return root.data;
		}
		
		List<BTNode> listl = new ArrayList<>();
		List<BTNode> listr = new ArrayList<>();
		
		int l = getMaxSum(root.left,mx,listl,globalFinalList);
		int r = getMaxSum(root.right,mx,listr,globalFinalList);
		
		int singleMax = Math.max(root.data, root.data+Math.max(l, r));

		if(l+root.data==singleMax) {
			finalList.addAll(listl);
		}
		else if(r+root.data==singleMax) {
			finalList.addAll(listr);
		}
		
		finalList.add(root);
		
		if(singleMax<root.data+l+r) {
			if(root.data+l+r>mx.val) {
				globalFinalList.clear();
				for(int i=0;i<listl.size();i++) {
					globalFinalList.add(listl.get(i));
				}
				globalFinalList.add(root);
				for(int i=listr.size()-1;i>=0;i--) {
					globalFinalList.add(listr.get(i));
				}
			}
		}
		else {
			if(singleMax>mx.val) {
				globalFinalList.clear();
				globalFinalList.addAll(finalList);
			}
		}
		
		int totalMax = Math.max(singleMax, root.data+l+r);
		mx.val = Math.max(mx.val, totalMax);
		
		return singleMax;
	}
	
	public static void convertToSumTree(BTNode root) {
		convertToSumTreeRecursiveVariant2(root);
	}
	
	public static int convertToSumTreeRecursiveVariant2(BTNode root) {
		if(root==null) {
			return 0;
		}
		
		if(root.left==null && root.right==null) {
			return root.data;
		}
		
		int l = convertToSumTreeRecursiveVariant2(root.left);
		int r = convertToSumTreeRecursiveVariant2(root.right);
		
		int diff = l+r-root.data;
		if(diff>0) {
			root.data = root.data+diff;
		}
		else {
			incrementDownTillLeaves(root.left, -diff);
		}
		
		return 2*root.data;
		
	}
	
	public static boolean isHeightBalanced(BTNode root) {
		if(root==null) {
			return true;
		}
		
		int lheight = heightOf(root.left);
		int rheight = heightOf(root.right);
		
		return Math.abs(lheight-rheight)<=1
				&& isHeightBalanced(root.left)
				&& isHeightBalanced(root.right);
		
	}
	
	public static boolean isHeightBalancedOptimizedOne(BTNode root) {
		return isHeightBalancedOptimizedOneHelper(root,new Height());
	}
	
	public static boolean isHeightBalancedOptimizedOneHelper(BTNode root, Height h) {
		if(root==null) {
			h.height=0;
			return true;
		}
		
		Height leftHeight = new Height();
		Height rightHeight = new Height();
		
		boolean isLeftBalanced = isHeightBalancedOptimizedOneHelper(root.left,leftHeight);
		boolean isRightBalanced = isHeightBalancedOptimizedOneHelper(root.right,rightHeight);
		
		h.height = 1 + Math.max(leftHeight.height, rightHeight.height);
		
		return (isLeftBalanced && isRightBalanced)
				&& Math.abs(leftHeight.height-rightHeight.height)<=1;
		
	}
	
	public static boolean isBSTTreeOptimized(BTNode root) {
		return isBSTValid(root,null,null);
	}
	
	public static boolean isBSTValid(BTNode root,Integer min,Integer max) {
		if(root==null) {
			return true;
		}
		
		return ((min==null?true:root.data>min)&& (max==null?true:root.data<max))
				&& isBSTValid(root.left,min,root.data)
				&& isBSTValid(root.right,root.data,max);
	}
	
	public static boolean isMonotonicIncreasing(BTNode root, BTNode prev) {
		if(root==null) {
			return true;
		}
		
		if(isMonotonicIncreasing(root.left,prev)) {
			if(prev!=null && root.data<prev.data)
				return false;
			return isMonotonicIncreasing(root.right,root);
		}
		return false;
	}
	
	
	public static boolean isBSTTree(BTNode root) {
		if(root==null) {
			return true;
		}
		
		boolean leftSide = areAllNodesSmallerThanValue(root.left,root.data);
		boolean rightSide = areAllNodesGreatedThanValue(root.right,root.data);
		
		return (leftSide && rightSide)
				&& isBSTTree(root.left)
				&& isBSTTree(root.right);
	}
	
	public static boolean areAllNodesSmallerThanValue(BTNode root, int val) {
		if(root==null)
		{
			return true;
		}
		return root.data<val
				&& areAllNodesSmallerThanValue(root.left,val)
				&& areAllNodesSmallerThanValue(root.right,val);
	}
	
	public static boolean areAllNodesGreatedThanValue(BTNode root, int val) {
		if(root==null)
		{
			return true;
		}
		return root.data>val
				&& areAllNodesGreatedThanValue(root.left,val)
				&& areAllNodesGreatedThanValue(root.right,val);
	}
	
	public static int getDiagonal(BTNode root) {
		if(root==null) {
			return 0;
		}
		
		int lheight = heightOf(root.left);
		int rheight = heightOf(root.right);
		
		int ldia = getDiagonal(root.left);
		int rdia = getDiagonal(root.right);
		
		return Math.max(1+lheight+rheight, Math.max(ldia, rdia));
	}
	
	public static int getDiagonalOptimized(BTNode root) {
		Height h = new Height();
		return getDiaAndHeight(root,h);
	}
	
	
	static class Height{
		int height;
	}
	
	public static int getDiaAndHeight(BTNode root, Height h) {
		if(root==null) {
			h.height=0;
			return 0;
		}
		
		Height lh=new Height();
		Height rh = new Height();
		
		int ldia = getDiaAndHeight(root.left,lh);
		int rdia = getDiaAndHeight(root.right,rh);
		
		h.height = Math.max(lh.height,rh.height)+1;
		
		return Math.max(1+lh.height+rh.height, Math.max(ldia, rdia));
	}
	
	public static int heightOf(BTNode root) {
		if(root==null) {
			return 0;
		}
		
		int l = heightOf(root.left);
		int r = heightOf(root.right);
		
		return 1+Math.max(l,r);
	}
	
	// root is leftMost bit 23421 so 2 is root
	public static void printEachPathAsDigit(BTNode root) {
		printEachPathAsDigitPreOrder(root,0);
	}
	
	// root is rightmostMost bit 23421 so 1 is root
	public static void printEachPathAsDigitReverse(BTNode root) {
		Deque<Integer> stack = new LinkedList<>();
		printEachPathAsDigitPreOrderReverse(root, stack);
	}
	
	public static void printEachPathAsDigitPreOrder(BTNode root, int number) {
		if(root==null) {
			return;
		}
		if(isLeaf(root)) {
			number=number*10+root.data;
			System.out.println("final number is -> "+number);
			return;
		}
		
		number = number*10+root.data;
		printEachPathAsDigitPreOrder(root.left, number);
		printEachPathAsDigitPreOrder(root.right, number);
	}
	
	public static void printEachPathAsDigitPreOrderReverse(BTNode root, Deque<Integer> stack) {
		if(root==null) {
			return;
		}
		
		
		if(isLeaf(root)) {
			stack.push(root.data);
			int number=0;
			for(Integer i:stack) {
				number=number*10+i;
			}
			System.out.println("final number is -> "+number);
			stack.pop();
			return;
		}
		
		stack.push(root.data);
		printEachPathAsDigitPreOrderReverse(root.left, stack);
		printEachPathAsDigitPreOrderReverse(root.right, stack);
		stack.pop();
		
	}
	
	
	public static boolean isLeaf(BTNode root) {
		if(root==null ||(root.left==null&&root.right==null)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isSumTreeOptimized(BTNode root) {
		if(root==null||isLeaf(root)) {
			return true;
		}
		
		if(!isSumTreeOptimized(root.left))
		{
			return false;
		}
		if(!isSumTreeOptimized(root.right)) {
			return false;
		}
		
		int lft=0;
		if(root.left!=null && isLeaf(root.left)) {
			lft = root.left.data;
		}
		else if(root.left!=null) {
			lft = root.left.data*2;
		}
		
		int ryt=0;
		if(root.right!=null && isLeaf(root.right)) {
			ryt = root.right.data;
		}
		else if(root.right!=null){
			ryt = root.right.data*2;
		}
		return root.data==lft+ryt;
		
	}
	
	
	//https://www.geeksforgeeks.org/check-if-a-given-binary-tree-is-sumtree/
	public static boolean isSumTree(BTNode root) {
		if(root==null || root.left==null&&root.right==null) {
			return true;
		}
		
		int lsum = sum(root.left);
		int rsum = sum(root.right);
		
		return (root.data==lsum+rsum)
				&& isSumTree(root.left) && isSumTree(root.right);
	}
	
	
	public static int sum(BTNode root) {
		if(root==null) {
			return 0;
		}
		
		int lsum = sum(root.left);
		int rsum = sum(root.right);
		
		return root.data+lsum+rsum;
	}
	
	
	public static int convertToSumTreeRecursive(BTNode root) {
		if(root==null) {
			return 0;
		}
		
		if(root.left==null && root.right==null) {
			return root.data;
		}
		
		int l = convertToSumTreeRecursive(root.left);
		int r = convertToSumTreeRecursive(root.right);
		
		int sum = l+r;
		int oldData = root.data;
		
		int diff = sum-oldData;
		
		if(diff>=0) {
			root.data = root.data + diff;
		}
		else {
			incrementDownTillLeaves(root.left,-diff);
		}
		
		return root.data;
		
	}
	
	public static void incrementDownTillLeaves(BTNode root, int val) {
		while(root!=null) {
			root.data+=val;
			if(root.left!=null) {
				root=root.left;
			}
			else {
				root=root.right;
			}
		}
	}
	
	// O n sqaure algo better one was using queue offer and pop
	static void printLevelOrderTraversalLevelByLevelTopDown(BTNode root) {
		if(root==null) {
			return;
		}
		
		int height = getHeight(root);
		
		for(int i=height;i>=1;i--) {
			printLevelOrderByLevelNumber(root,i);
		}
	}
	
	static void printLevelOrderByLevelNumber(BTNode root, int level) {
		if(root==null)
			return;
		
		if(level==1) {
			System.out.print(root.data+", ");
			return;
		}
		
		printLevelOrderByLevelNumber(root.left,level-1);
		printLevelOrderByLevelNumber(root.right,level-1);
	}
	
	static BTNode getNextNodeOnRight(BTNode root, int key) {
		Level level = new Level();
		level.i = -1;
		return getNextNodeOnRightHelper(root,0,level,key);
	}

	static BTNode getNextNodeOnRightHelper(BTNode root, int level, Level setLevel, int key) {
		if(root==null)
			return null;
		
		if(root.data==key) {
			// found the key node from which right we need to look
			System.out.println("found level at "+level);
			setLevel.i = level;
			return null;
		}
		
		if(setLevel.i==level) {
			return root;
		}
		
		BTNode next = getNextNodeOnRightHelper(root.left,level+1,setLevel,key);
		if(next!=null)
			return next;
		
		return getNextNodeOnRightHelper(root.right,level+1,setLevel,key);
	}
	
	static void printLevelOrderTraversalLevelByLevelBottomUp(BTNode root) {
		Queue<BTNode> q = new LinkedList<>();
		Deque<BTNode> stack = new LinkedList<>();
		q.offer(root);
		while(!q.isEmpty()) {
			BTNode cur = q.poll();
			stack.push(cur);
			if(cur.right!=null) {
				q.offer(cur.right);
			}
			
			if(cur.left!=null) {
				q.offer(cur.left);
			}
			
		}
		while(!stack.isEmpty()) {
			System.out.print(stack.pop()+", ");
		}
	}
	
	static class BTreePostOrderTraversalIterator implements Iterator<BTNode>{
		Deque<BTNode> stack1 = new LinkedList<>();
		Deque<BTNode> stack2 = new LinkedList<>();
		BTNode root;
		BTreePostOrderTraversalIterator(BTNode root){
			this.root = root;
			BTNode cur = root;
			stack1.push(cur);
			while(!stack1.isEmpty()) {
				cur = stack1.pop();
				stack2.push(cur);
				if(cur.left!=null) {
					stack1.push(cur.left);
				}
				if(cur.right!=null) {
					stack1.push(cur.right);
				}
			}
		}
		@Override
		public boolean hasNext() {
			return !stack2.isEmpty();
		}
		@Override
		public BTNode next() {
			if(stack2.isEmpty())
				throw new EmptyStackException();
			return stack2.pop();
		}
	}
	
	public static void printBoundariesRootLeftBottomRight(BTNode root) {
		printTopToDownLeftBoundary(root);
		printLeftToRightLeaves(root.left);
		printLeftToRightLeaves(root.right);
		printBottomToUpRightBoundary(root.right);
	}
	
	static void printTopToDownLeftBoundary(BTNode root) {
		if(root==null ||( root.left==null&&root.right==null))
		{
			return;
		}
		
		System.out.print(root.data+", ");
		if(root.left!=null) {
			printTopToDownLeftBoundary(root.left);
		}
		else {
			printTopToDownLeftBoundary(root.right);
		}
	}
	
	static void printLeftToRightLeaves(BTNode root) {
		if(root==null)
		{
			return;
		}
		if(root.left==null&&root.right==null)
			System.out.print(root.data+", ");
		printLeftToRightLeaves(root.left);
		printLeftToRightLeaves(root.right);
	}
	
	static void printBottomToUpRightBoundary(BTNode root) {
		if(root==null ||( root.left==null&&root.right==null))
		{
			return;
		}
		
		if(root.right!=null) {
			printTopToDownLeftBoundary(root.right);
			
		}
		else {
			printTopToDownLeftBoundary(root.left);
		}
		
		System.out.print(root.data+", ");
	}
	
	
	static class BTreeLevelOrderTraversalIterator implements Iterator<BTNode>{
		Queue<BTNode> queue = new LinkedList<>();
		BTNode root;
		
		
		BTreeLevelOrderTraversalIterator(BTNode root){
			this.root = root;
			queue.add(root);
		}
		
		@Override
		public boolean hasNext() {
			return !queue.isEmpty();
		}

		@Override
		public BTNode next() {
			BTNode cur = queue.poll();
			if(cur.left!=null) {
				queue.offer(cur.left);
			}
			if(cur.right!=null) {
				queue.offer(cur.right);
			}
			return cur;
		}
		
	}
	
	static class BTreePreOrderTraversalIterator implements Iterator<BTNode>{
		Deque<BTNode> stack = new LinkedList<>();
		BTNode root;
		
		BTreePreOrderTraversalIterator(BTNode root){
			assertNotNull(root);
			this.root = root;
			stack.push(root);
		}
		
		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public BTNode next() {
			if(stack.isEmpty())
				throw new EmptyStackException();
			BTNode cur = stack.pop();
			if(cur.right!=null) {
				stack.push(cur.right);
			}
			if(cur.left!=null)
			{
				stack.push(cur.left);
			}
			return cur;
		}
		
	}
	
	static class BTreeInOrderIterator implements Iterator<BTNode> {
		Deque<BTNode> stack = new LinkedList<>();
		BTNode root;
		
		BTreeInOrderIterator(BTNode root){
			this.root = root;
			
			BTNode cur = root;
			stack.push(cur);
			pushTillLeftNull.accept(cur);
		}
		
		Consumer<BTNode> pushTillLeftNull = cur->{
			while(cur!=null) {
				cur = cur.left;
				if(cur!=null) {
					stack.push(cur);
				}
			}
		};
		
		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public BTNode next() {
			if(stack.isEmpty())
				throw new EmptyStackException();
			
			BTNode result = stack.pop();

			BTNode cur = result;
			cur = cur.right;
			if(cur!=null)
				stack.push(cur);
			pushTillLeftNull.accept(cur);
			
			return result;
		}
		
	}
	
	public static int getDiamerterNSquare(BTNode root) {
		if(root==null) {
			return 0;
		}
		
		int lheight = getHeight(root.left);
		int rheight = getHeight(root.right);
		
		int ldiameter = getDiamerterNSquare(root.left);
		int rdiameter = getDiamerterNSquare(root.right);
		
		return Math.max(1+(lheight+rheight), Math.max(ldiameter, rdiameter));
		
	}
	
	//O(N)
	public static int getHeight(BTNode root) {
		if(root==null) {
			return 0;
		}
		
		int lheight = getHeight(root.left);
		int rheight = getHeight(root.right);
		
		return 1+Math.max(lheight, rheight);
	}
	
	
	public static int getDiamerterOOfN(BTNode root, MaxHeight mh) {
		if(root==null) {
			mh.val = 0;		// set height 0
			return 0;	// return dia 0
		}
		
		MaxHeight lftHeight = new MaxHeight(); 
		MaxHeight rytHeight = new MaxHeight();
		
		int ldiameter = getDiamerterOOfN(root.left,lftHeight);
		int rdiameter = getDiamerterOOfN(root.right,rytHeight);
		
		mh.val = 1+Math.max(lftHeight.val,rytHeight.val);
		
		return Math.max(1+lftHeight.val+rytHeight.val,Math.max(ldiameter, rdiameter));
		
	}
	
	static class MaxHeight{
		int val;
	}
	public static int getDiameter(BTNode root) {
		MaxHeight mh = new MaxHeight();
		return getDiamerterOOfN(root,mh);
	}
	
	static class BTNode{
		int data;
		BTNode left,right;
		BTNode(int val){
			data = val;
		}
		
		@Override
		public String toString() {
			return String.valueOf(data);
		}
	}
	
	public static <T extends Comparable<? super T>> boolean hasPathSumRootToLeaf(Node<T> root, int sum) {
		if(root==null) {
			return sum==0;
		}
		int subSum = sum-Integer.valueOf((root.data.toString()));
		return hasPathSumRootToLeaf(root.left,subSum) 
					|| hasPathSumRootToLeaf(root.right,subSum);
	}
	
	public static void allPathsSumRootToLeaf(BTNode root, int sum) {
		List<List<BTNode>> finalResult = new ArrayList<List<BTNode>>();
		List<BTNode> perBranchResult = new ArrayList<BTNode>();
		allPathsSumRootToLeaf(root,sum,finalResult,perBranchResult);
		System.out.println("allPathsSumRootToLeaf:->");
		finalResult.stream().forEach(x->{
			System.out.println("List with sum = "+sum+" list is:->"+x.toString());
		});
		System.out.println();
	}
	
	public static void allPathsSumRootToLeaf(BTNode root, int sum,
			List<List<BTNode>> finalResult, List<BTNode> perBranchResult)
	{
		if(root==null) {
			return;
		}
		
		// lets add first to try it with all leaf going from this node
		perBranchResult.add(root);
		int subSum = sum-root.data;
		
		if(root.left==null&&root.right==null&subSum==0) {
			List<BTNode> newList = perBranchResult.stream().collect(Collectors.toList());
			finalResult.add(newList);
			return;
		}
		
		allPathsSumRootToLeaf(root.left,subSum,finalResult,perBranchResult);
		allPathsSumRootToLeaf(root.right,subSum,finalResult,perBranchResult);
		
		// now remove as we are going back from this node.
		perBranchResult.remove(perBranchResult.size()-1);
	}
	
	public static void allPathsSumRootToAnyNode(BTNode root, int sum) {
		List<List<BTNode>> finalResult = new ArrayList<List<BTNode>>();
		List<BTNode> perBranchResult = new ArrayList<BTNode>();
		allPathsSumRootToAnyNode(root,sum,finalResult,perBranchResult);
		
		System.out.println("allPathsSumRootToAnyNode:->");
		finalResult.stream().forEach(x->{
			System.out.println("List with sum = "+sum+" list is:->"+x.toString());
		});
		System.out.println();
	}
	
	public static void allPathsSumRootToAnyNode(BTNode root, int sum,
			List<List<BTNode>> finalResult, List<BTNode> perBranchResult)
	{
		if(root==null) {
			return;
		}
		
		// lets add first to try it with all leaf going from this node
		perBranchResult.add(root);
		int subSum = sum-root.data;
		
		if(subSum==0) {
			List<BTNode> newList = perBranchResult.stream().collect(Collectors.toList());
			finalResult.add(newList);
		}
		
		allPathsSumRootToAnyNode(root.left,subSum,finalResult,perBranchResult);
		allPathsSumRootToAnyNode(root.right,subSum,finalResult,perBranchResult);
		// now remove as we are going back from this node.
		perBranchResult.remove(perBranchResult.size()-1);
	}
	
	
	public static void allPathsSumAnyNodeToAnyNode(BTNode root, int sum) {
		List<List<BTNode>> finalResult = new ArrayList<List<BTNode>>();
		List<BTNode> perBranchResult = new ArrayList<BTNode>();
		allPathsSumAnyNodeToAnyNode(root,sum,finalResult,perBranchResult);
		System.out.println("allPathsSumAnyNodeToAnyNode:->");
		finalResult.stream().forEach(x->{
			System.out.println("List with sum = "+sum+" list is:->"+x.toString());
		});
		System.out.println();
	}
	
	public static void allPathsSumAnyNodeToAnyNode(BTNode root, int sum,
			List<List<BTNode>> finalResult, List<BTNode> perBranchResult)
	{
		if(root==null) {
			return;
		}
		
		// lets add first to try it with all leaf going from this node
		perBranchResult.add(root);
		int tempSum=0;
		for(int i=perBranchResult.size()-1;i>=0;i--) {
			tempSum+=perBranchResult.get(i).data;
			if(tempSum==sum) {
				List<BTNode> newList = new ArrayList<>();
				for(int j=i;j<perBranchResult.size();j++) {
					newList.add(perBranchResult.get(j));
				}
				finalResult.add(newList);
			}
		}
		
		allPathsSumAnyNodeToAnyNode(root.left,sum,finalResult,perBranchResult);
		allPathsSumAnyNodeToAnyNode(root.right,sum,finalResult,perBranchResult);
		// now remove as we are going back from this node.
		perBranchResult.remove(perBranchResult.size()-1);
	}
	
	public static <T extends Comparable<? super T>> boolean isHeightBalancedBinaryTreeNSquareAlgo(Node<T> root) {
		if(root==null) {
			return true;
		}
		return Math.abs(getHeight(root.left)-getHeight(root.right))<=1
				&& isHeightBalancedBinaryTreeNSquareAlgo(root.left)
				&& isHeightBalancedBinaryTreeNSquareAlgo(root.right);
	}
	
	public static <T extends Comparable<? super T>> boolean isHeightBalanced(Node<T>root) {
		int h = getMaxHeight(root);
		return h!=-1;
	}
	
	public static <T extends Comparable<? super T>> int getMaxHeight(Node<T> root) {
		if(root==null) {
			return 0;
		}
		int leftHeight = getMaxHeight(root.left);
		if(leftHeight==-1) return -1;
		int rightHeight = getMaxHeight(root.right);
		if(rightHeight==-1) return -1;
		 
		return Math.abs(leftHeight-rightHeight)>1?-1:1+Math.max(leftHeight, rightHeight);
	}
	
	public static <T extends Comparable<? super T>> boolean isValidBSTWithCheckOnInOrder(Node<T> root){
		Node<T> prevNode=null;
		return isMonotonicIncreasingNode(root, prevNode);
	}
	
	public static <T extends Comparable<? super T>> boolean isMonotonicIncreasingNode(Node<T> root, Node<T> prev) {
		if(root==null) {
			return true;
		}
		if(isMonotonicIncreasingNode(root.left,prev)) {
			if(prev!=null && prev.data.compareTo(root.data)>=0) {
				return false;
			}
			prev = root;
			return isMonotonicIncreasingNode(root.right,prev);
		}
		return false;
	}
	
	public static <T extends Comparable<? super T>> boolean isValidBST(Node<T> root) {
		return isValid(root, null, null);
	}
	
	public static <T extends Comparable<? super T>> boolean isValid(Node<T> root, T min, T max) {
		if(root==null) {
			return true;
		}
		
		return (min==null?true:(root.data.compareTo(min)>0) 
				&& max==null? true:(root.data.compareTo(max)<0))
						&&isValid(root.left,min,root.data)
						&&isValid(root.right,root.data, max);
	}
	
	
	public static <T extends Comparable<? super T>> Node<T> lcaInBinaryTreeWithArrayStore(Node<T> root, T data1, T data2){
		if(root==null) {
			return null;
		}
		
		List<Node<T>> list1 = new ArrayList<Node<T>>();
		List<Node<T>> list2 = new ArrayList<Node<T>>();
		findAndStorePath(root, data1, list1);
		findAndStorePath(root, data2, list2);
		
		if(list1.size()==0 || list2.size()==0) {
			return null;
		}
		
		int i=0;
		while(i<list1.size() && i<list2.size()) {
			if(list1.get(i).data.compareTo(list2.get(i).data)!=0) {
				break;
			}
			i++;
		}
		return list1.get(i-1);
	}
	
	public static <T extends Comparable<? super T>> void findAndStorePath(Node<T> root, T data, List<Node<T>> list) {
		while(root!=null) {
			list.add(root);
			if(root.data.compareTo(data)>0)
			{
				root = root.left;
			}
			else if(root.data.compareTo(data)<0) {
				root = root.right;
			}else {
				return;
			}
		}
		list.clear();
	}
	
	public static <T extends Comparable<? super T>> Node<T> lcaWithParentPointer(Node<T> node1, Node<T> node2) {
		if(node1==null || node2==null || node1.parent==null || node2.parent==null) {
			return null;
		}
		return findIntersectionPointOf2LinkedList(node1, node2);
	}
	
	public static <T extends Comparable<? super T>> Node<T> findIntersectionPointOf2LinkedList(Node<T> list1, Node<T> list2){
		int size1 = sizeOfLinkedList(list1);
		int size2 = sizeOfLinkedList(list2);
		int diff = size1-size2;
		if(diff<0) {
			return findIntersectionPointOf2LinkedList(list2, list1);
		}
		
		int count=0;
		
		while(count<diff) {
			list1 = list1.parent;
			count++;
		}
		while(list1!=null && list2!=null) {
			if(list1==list2) {
				return list1;
			}
			list1 = list1.parent;
			list2 = list2.parent;
		}
		return null;
	}
	
	public static <T extends Comparable<? super T>> int sizeOfLinkedList(Node<T> list) {
		int size=0;
		while(list!=null) {
			size++;
			list=list.parent;
		}
		
		return size;
	}
	
	public static <T extends Comparable<? super T>> Node<T> lcaInBinaryTreeWithoutParentPointer(Node<T> root, T data1, T data2){
		if(root==null) {
			return null;
		}
		
		if(root.data.compareTo(data1)==0 || root.data.compareTo(data2)==0) {
			return root;
		}
		
		Node<T> leftLca = lcaInBinaryTreeWithoutParentPointer(root.left,data1,data2);
		Node<T> rightLca = lcaInBinaryTreeWithoutParentPointer(root.right,data1,data2);
		
		if(leftLca!=null && rightLca!=null) {
			return root;
		}
		return leftLca!=null?leftLca:rightLca;
	}
	
	public static <T extends Comparable<T>> Node<T> lcaInBST(Node<T> root, T data1, T data2){
		if(root==null) {
			return null;
		}
		while(root!=null) {
			if(root.data.compareTo(data1)>0 && root.data.compareTo(data2)>0) {
				root = root.left;
			}
			else if(root.data.compareTo(data1)<0 && root.data.compareTo(data2)<0) {
				root = root.right;
			}
			else {
				return root;
			}
		}
		return null;
	}
	
	public static <T extends Comparable<? super T>> Node<T> searchInBST(Node<T>root, T val){
		while(root!=null) {
			if(root.data.compareTo(val)>0) {
				root = root.left;
			}
			else if(root.data.compareTo(val)<0) {
				root = root.right;
			}
			else {
				return root;
			}
		}
		return null;
	}
	
	// get Inorder Predecessor of a binary search tree without parent pointer
	// cases
		// case 1st: if left node exists: max on leftside
		// case 2nd: if left node does not exists, then go up high in chain
	public static <T extends Comparable<? super T>> Node<T> getInOrderPredecessorOfAGivenNode(Node<T> root, Node<T> node){
		if(node==null) {
			return node;
		}
		
		// check if left node exists
		if(node.left!=null) {
			return getMaxInBST(node.left);
		}
		
		// if not left node then go up, we dont have parent pointer to get last node we took right on
		// so idea is to seach that node and store its right turn always, when match found then return store
		Node<T> cur = root;
		Node<T> storeOfLastRightTurn = null;
		while(cur!=null) {
			if(cur.data.compareTo(node.data)>0) {
				cur = cur.left;
			}
			else if(cur.data.compareTo(node.data)<0) {
				storeOfLastRightTurn = cur;
				cur = cur.right;
			}
			else {
				// we found the match and storeOfLastRightTurn is the answer
				break;
			}
		}
		
		return storeOfLastRightTurn;
		
		
	}
	
	public static <T extends Comparable<? super T>> Node<T> floor(Node<T> root, T val){
		if(root==null) {
			return null;
		}
		
		// means search Node does not exists, we need to find greatest element lesser than val
		int diff = Integer.MAX_VALUE;
		Node<T> resultNode = null;
		Node<T> cur = root;
		while(cur!=null) {
			if(cur.data.compareTo(val)>0) {
				cur = cur.left;
			}
			else if(cur.data.compareTo(val)<0) {
				int curdiff = Integer.valueOf(val.toString()) - Integer.valueOf(cur.data.toString()).intValue();
				if(curdiff<diff) {
					resultNode = cur;
					diff = curdiff;
				}
				cur = cur.right;
				
			}else {
				resultNode = getInOrderPredecessorOfAGivenNode(root, cur);
				break;
			}
		}
		return resultNode;
	}
	
	public static <T extends Comparable<? super T>> Node<T> ceiling(Node<T> root, T val){
		if(root==null) {
			return null;
		}
		
		int diff = Integer.MAX_VALUE;
		Node<T> cur =root;
		Node<T> resultNode = null;
		while(cur!=null) {
			if(cur.data.compareTo(val)>0) {
				int curDiff = Integer.valueOf(cur.data.toString())-Integer.valueOf(val.toString());
				if(curDiff<diff) {
					resultNode = cur;
					diff=curDiff;
				}
				cur = cur.left;
				
			}
			else if(cur.data.compareTo(val)<0) {
				cur = cur.right;
			}
			else {
				resultNode = getInOrderSuccessorOfAGivenNode(root, cur);
				break;
			}
		}
		return resultNode;
	}
	
	public static <T extends Comparable<? super T>> Node<T> getInOrderPredecessorOfAGivenNodeWithParentPointer(Node<T> node){
		if(node==null) {
			return null;
		}
		
		// case 1st left node exists
		if(node.left!=null) {
			return getMaxInBST(node.left);
		}
		
		// case 2nd
		Node<T> p = node.parent;
		Node<T> cur = node;
		while(p!=null && p.right!=cur) {
			cur = p;
			p = p.parent;
		}
		return p;
		
	}
	
	public static <T extends Comparable<? super T>> Node<T> getInOrderSuccessorOfAGivenNode(Node<T> root, Node<T> node){
		if(root==null || node==null) {
			return null;
		}
		
		// case 1st right node is not null
		if(node.right!=null) {
			return getMinInBST(node.right);
		}
		
		// case 2nd right node is null, that means we have to go up and return last left turn we took
		// we dont have parent pointer so we search and remember every left turn taking node
		Node<T> store = null;
		Node<T> cur = root;
		while(cur!=null) {
			if(cur.data.compareTo(node.data)>0) {
				store = cur;
				cur = cur.left;
			}
			else if(cur.data.compareTo(node.data)<0) {
				cur = cur.right;
			}
			else {
				// we find the spot and break;
				break;
			}
		}
		return store;
	}
	
	public static <T extends Comparable<? super T>> Node<T> getInOrderSuccessorOfAGivenNodeWithParentPointer(Node<T> node){
		if(node==null) {
			return null;
		}
		
		// case 1st right node is not null
		if(node.right!=null) {
			return getMinInBST(node.right);
		}
		
		// case 2nd right node is null, that means we have to go up and return last left turn we took
		// we dont have parent pointer so we search and remember every left turn taking node
		Node<T> p = node.parent;
		while(p!=null&&p.left!=node) {
			node = p;
			p = p.parent;
		}
		return p;
	}
	
	public static <T extends Comparable<? super T>> void iterativeInOrderTravesalWithThreadedApproach(Node<T> root) {
		if(root==null) {
			return;
		}
		Node<T> cur = root;
		System.out.println();
		while(cur!=null) {
			// if there is not left, then print cur and recur on right
			if(cur.left==null) {
				System.out.print(cur.data+" ,");
				cur = cur.right;
			}
			else {
				// then gets the predecessor of cur which is left's right most
				Node<T> pre = cur.left;
				while(pre.right!=null && pre.right!=cur) {
					pre = pre.right;
				}
				if(pre.right==null) {
					pre.right = cur;
					cur = cur.left;
				}
				else {
					System.out.print(cur.data+", ");
					pre.right = null;
					cur = cur.right;
				}	
			}
		}
	}
	
	// sum of diagonals
	public static <T extends Comparable<? super T>> void sumInRightDiagonals(Node<T> root) {
		Queue<Node<T>> q1 = new LinkedList<>();
		Queue<Node<T>> q2 = new LinkedList<>();
		List<Node<T>> resultsInDiagonal = new LinkedList<>();
		q1.offer(root);
		int counter = 0;
		while(!q1.isEmpty()) {
			int sum=0; Node<T> cur=null;
			while(!q1.isEmpty() || cur!=null) {
				if(cur==null) {
					cur = q1.poll();
				}
				resultsInDiagonal.add(cur);
				sum+=Integer.valueOf(cur.data.toString());
				if(cur.left!=null) {
					q2.offer(cur.left);
				}
				cur = cur.right;
			}
			
			System.out.println("sum with diagonal elements: "+resultsInDiagonal.toString());
			System.out.println("sum at diagonal "+ ++counter+"  is "+sum);
			q1=q2;
			q2 = new LinkedList<>();
			resultsInDiagonal = new LinkedList<>();
		}
	}
	
	public static <T extends Comparable<? super T>> void sumInLeftDiagonals(Node<T> root) {
		Queue<Node<T>> q1 = new LinkedList<>();
		Queue<Node<T>> q2 = new LinkedList<>();
		List<Node<T>> resultsOnDiagonal = new LinkedList<>();
		q1.offer(root);
		while(!q1.isEmpty()) {
			int sum=0; Node<T> cur = null;
			while(!q1.isEmpty() || cur!=null) {
				if(cur==null) {
					cur = q1.poll();
				}

				sum+=Integer.valueOf(cur.data.toString());
				resultsOnDiagonal.add(cur);
				if(cur.right!=null) {
					q2.add(cur.right);
				}
				cur = cur.left;
			}
			System.out.println("Elements on Diagonal are "+resultsOnDiagonal.toString());
			System.out.println("sum is "+ sum);
			q1=q2;
			q2 = new LinkedList<>();
			resultsOnDiagonal = new LinkedList<>();
		}
	}
	
	public static <T extends Comparable<? super T>> void printDiagonalsRightRecursive(Node<T> root) {
		Map<Integer,List<Node<T>>> map = new TreeMap<>();
		
		System.out.print("Diagonal Right are: ");
		printDiagonalsRightLeRecursiveWithMap(root, map, 0);
		map.entrySet().stream().forEach(x->{
			System.out.println("Diagonal at index "+x.getKey().intValue()+"  diagonal elements are"+x.getValue().toString());
		});
	}
	
	public static <T extends Comparable<? super T>> void printDiagonalsRightLeRecursiveWithMap(Node<T> root, Map<Integer, List<Node<T>>> map, int curD) {
		if(root==null) {
			return;
		}
		
		List<Node<T>> list = map.get(curD);
		if(list==null) {
			list = new ArrayList<Node<T>>();
			map.put(curD, list);
		}
		list.add(root);
		
		printDiagonalsRightLeRecursiveWithMap(root.left,map,curD+1);
		printDiagonalsRightLeRecursiveWithMap(root.right,map,curD);
	}
	
	public static <T extends Comparable<? super T>> void printDiagonalsLeftRecursive(Node<T> root) {
		Map<Integer,List<Node<T>>> map = new TreeMap<>();
		
		System.out.print("Diagonal Left are: ");
		printDiagonalsLeftLeRecursiveWithMap(root, map, 0);
		map.entrySet().stream().forEach(x->{
			System.out.println("Diagonal at index "+x.getKey().intValue()+"  diagonal elements are"+x.getValue().toString());
		});
	}
	
	public static <T extends Comparable<? super T>> void printDiagonalsLeftLeRecursiveWithMap(Node<T> root, Map<Integer, List<Node<T>>> map, int curD) {
		if(root==null) {
			return;
		}
		
		List<Node<T>> list = map.get(curD);
		if(list==null) {
			list = new ArrayList<Node<T>>();
			map.put(curD, list);
		}
		list.add(root);
		printDiagonalsLeftLeRecursiveWithMap(root.right,map,curD+1);
		printDiagonalsLeftLeRecursiveWithMap(root.left,map,curD);
	}
	
	// sum of vertical nodes
	public static <T extends Comparable<? super T>> void sumOfVerticals(Node<T> root) {
		Map<Integer, List<Node<T>>> map = new TreeMap<>();
		bottomViewRecursiveWithMap(root, map, 0);
		map.entrySet().forEach(x->{
			System.out.println("Sum at Index "+x.getKey().intValue()
						+" sequence = "+x.getValue().toString()
						+" and sum is:"
						+x.getValue().stream()
						.map(node->node.data.toString())
						.collect(
								Collectors
								.summingInt(dataString->Integer.valueOf(dataString))
								)
						);
		});
		
	}
	
	// level by level, only print firstNode in that level
	public static <T extends Comparable<? super T>> void printLeftViews(Node<T> root) {
		Queue<Node<T>> q1 = new LinkedList<Node<T>>();
		Queue<Node<T>> q2 = new LinkedList<Node<T>>();
		q1.offer(root);
		List<Node<T>> result = new LinkedList<Node<T>>();
		while(!q1.isEmpty()) {
			result.add(q1.peek());
			while(!q1.isEmpty()) {
				Node<T> p = q1.poll();
				if(p.left!=null) {
					q2.offer(p.left);
				}
				if(p.right!=null) {
					q2.offer(p.right);
				}
			}
			q1= q2;
			q2= new LinkedList<Node<T>>();
		}
		
		System.out.print("Left View is -> [");
		result.stream().forEach(x->{
			System.out.print(x.data+", ");
		});
		System.out.println("]");
	}
	// level by level, only print firstNode in that level
	public static <T extends Comparable<? super T>> void printRightViews(Node<T> root) {
		Queue<Node<T>> q1 = new LinkedList<Node<T>>();
		Queue<Node<T>> q2 = new LinkedList<Node<T>>();
		q1.offer(root);
		List<Node<T>> result = new LinkedList<Node<T>>();
		while(!q1.isEmpty()) {
			result.add(q1.peek());
			while(!q1.isEmpty()) {
				Node<T> p = q1.poll();
				if(p.right!=null) {
					q2.offer(p.right);
				}
				if(p.left!=null) {
					q2.offer(p.left);
				}
			}
			q1=q2;
			q2=new LinkedList<Node<T>>();
			
		}
		
		System.out.print("Right View is -> [");
		result.stream().forEach(x->{
			System.out.print(x.data+", ");
		});
		System.out.println("]");
	}
	
	static class Level{
		int i;
	}
	
	public static <T extends Comparable<? super T>> void bottomView(Node<T> root) {
		Queue<Node<T>> q = new LinkedList<>();
		Map<Integer, List<Node<T>>> map = new TreeMap<Integer, List<Node<T>>>();
		q.offer(root);
		root.hd=0;
		while(!q.isEmpty()) {
			Node<T> p = q.poll();
			List<Node<T>> list = map.get(p.hd);
			if(list==null) {
				list = new ArrayList<Node<T>>();
				map.put(p.hd, list);
			}
			list.add(p);
			
			if(p.left!=null) {
				p.left.hd=p.hd-1;
				q.offer(p.left);
			}
			if(p.right!=null) {
				p.right.hd = p.hd+1;
				q.offer(p.right);
			}
		}
		
		map.entrySet().stream().forEach(x->{
			System.out.println("Node at horizontal distance: "+x.getKey().intValue()
					+" are: "+(x.getValue().toString()));
		});
	}
	
	public static <T extends Comparable<? super T>> void bottomViewRecursive(Node<T> root) {
		Map<Integer, List<Node<T>>> map = new TreeMap<Integer, List<Node<T>>>();
		bottomViewRecursiveWithMap(root,map,0);
		map.entrySet().stream().forEach(x->{
			System.out.println("Node at horizontal distance: "+x.getKey().intValue()
					+" are: "+(x.getValue().toString()));
		});
	}
	
	public static <T extends Comparable<? super T>> void bottomViewRecursiveWithMap(Node<T> root, Map<Integer, List<Node<T>>> map, int curHd) {
		if(root==null) {
			return;
		}
		
		List<Node<T>> list = map.get(curHd);
		if(list==null) {
			list = new ArrayList<Node<T>>();
			map.put(curHd, list);
		}
		list.add(root);
		bottomViewRecursiveWithMap(root.left,map,curHd-1);
		bottomViewRecursiveWithMap(root.right,map,curHd+1);
	}
	
	public static <T extends Comparable <? super T>> void printLeftViewRecursive(Node<T> root){
		Level levelSet = new Level();
		levelSet.i = 0;
		System.out.print("Left View is -> [");
		printLeftViewRecursiveWithLevel(root, levelSet, 0);
		System.out.println("]");
	}
	
	public static <T extends Comparable <? super T>> void printRightViewRecursive(Node<T> root){
		Level levelSet = new Level();
		levelSet.i = 0;
		System.out.print("Right View is -> [");
		printRightViewRecursiveWithLevel(root, levelSet, 0);
		System.out.println("]");
	}
	
	public static <T extends Comparable <? super T>> void printLeftViewRecursiveWithLevel(Node<T> root, Level levelSet, int curLevel){
		if(root==null) {
			return;
		}
		
		if(levelSet.i==curLevel) {
			System.out.print(root.data+", ");
			levelSet.i = levelSet.i + 1;
		}
		printLeftViewRecursiveWithLevel(root.left,levelSet,curLevel+1);
		printLeftViewRecursiveWithLevel(root.right,levelSet,curLevel+1);
	}
	
	public static <T extends Comparable <? super T>> void printRightViewRecursiveWithLevel(Node<T> root, Level levelSet, int curLevel){
		if(root==null) {
			return;
		}
		
		if(levelSet.i==curLevel) {
			System.out.print(root.data+", ");
			levelSet.i = levelSet.i + 1;
		}
		printLeftViewRecursiveWithLevel(root.right,levelSet,curLevel+1);
		printLeftViewRecursiveWithLevel(root.left,levelSet,curLevel+1);
	}
	
	public static <T extends Comparable<? super T>> void createMirrorImageTree(Node<T> root){
		if(root==null) {
			return;
		}
		
		createMirrorImageTree(root.left);
		createMirrorImageTree(root.right);
		Node<T> temp = root.left;
		root.left = root.right;
		root.right = temp;	
	}
	
	public static <T extends Comparable<? super T>> boolean identicalTree(Node<T> root1, Node<T> root2) {
		if(root1==null && root2==null) {
			return true;
		}
		if(root1==null || root2==null) {
			return false;
		}
		
		return (root1.data.compareTo(root2.data)==0)
				&& identicalTree(root1.left,root2.left) 
				&& identicalTree(root1.right,root2.right);
	}
	
	public static <T extends Comparable<? super T>> boolean isIsomorphic(Node<T> root1, Node<T> root2) {
		if(root1==null && root2==null) {
			return true;
		}
		
		if(root1==null || root2==null) {
			return false;
		}
		
		return (root1.data.compareTo(root2.data)==0)
				&& ((isIsomorphic(root1.left,root2.left) && isIsomorphic(root1.right,root2.right))
						|| (isIsomorphic(root1.left,root2.right) && isIsomorphic(root1.right,root2.left)));
				
	}
	
	public static <T extends Comparable<? super T>> Node<T> getMaxInBST(Node<T> root) {
		if(root!=null && root.right != null) {
			return getMaxInBST(root.right);
		}
		
		return root;
	}
	
	public static <T extends Comparable<? super T>> Node<T> getMinInBST(Node<T> root) {
		if(root!=null && root.left != null) {
			return getMinInBST(root.left);
		}
		
		return root;
	}
	
	public static <T extends Comparable<? super T>> Node<T> deleteFromBSTRecursive(Node<T> root, T val) {
		if(root==null) {
			return root;
		}
		
		if(root!=null) {
			if(root.data.compareTo(val)>0) {
				root.left = deleteFromBSTRecursive(root.left,val);
			}
			else if(root.data.compareTo(val)<0) {
				root.right = deleteFromBSTRecursive(root.right,val);
			}
			else {
				// check if is the leaf
				if(root.left==null&& root.right==null) {
					root=null;
				}
				else if(root.left==null) {
					Node<T> minOnRight = getMinInBST(root.right);
					root.data = minOnRight.data;
					root.rsize--;
					root.count--;
					root.right = deleteFromBSTRecursive(root.right, minOnRight.data);
				}
				else {
					Node<T> maxOnLeft = getMaxInBST(root.left);
					root.data = maxOnLeft.data;
					root.lsize--;
					root.count--;
					root.left = deleteFromBSTRecursive(root.left, maxOnLeft.data);
				}
			}
		}
		return root;
	}
	
	public static <T extends Comparable<? super T>> Node<T> deleteFromBSTIterative(Node<T> root, T val){
		if(root==null ||(root.left==null && root.right==null)) {
			return null;
		}
		
		Node<T> cur = root;
		
		Node<T> parent = null;
		while(cur!=null) {
			if(cur.data.compareTo(val)>0) {
				parent = cur;
				cur = cur.left;
			}
			else if(cur.data.compareTo(val)<0) {
				parent = cur;
				cur = cur.right;
			}
			else {
				// we found a spot to delete, break now
				break;
			}
		}
		
		// if element is not in BST, so nothing to delete, no op operation
		if(cur==null) {
			return root;
		}
		
		// if we found a leaf node match
		if(cur.left==null && cur.right==null) {
			if(parent.data.compareTo(cur.data)>0) {
				parent.left = null;
				parent.lsize--;
				parent.count--;
			}
			else if(parent.data.compareTo(cur.data)<0) {
				parent.right = null;
				parent.rsize--;
				parent.count--;
			}
		}
		else if(cur.left!=null) {
			parent = cur;
			Node<T> maxOnLeft = cur.left;
			while(maxOnLeft.right!=null) {
				parent = maxOnLeft;
				maxOnLeft = maxOnLeft.right;				
			}
			
			cur.data = maxOnLeft.data;
			
			if(parent==cur) {	// node we want to delete has no right, so above loop did not ran
				parent.left = maxOnLeft.left;
			}
			else {
				parent.right = maxOnLeft.left;
			}
			
		}
		else if(cur.right!=null) {
			parent = cur;
			Node<T> minOnRight = cur.right;
			while(minOnRight.left!=null) {
				parent = minOnRight;
				minOnRight = minOnRight.left;				
			}
			cur.data = minOnRight.data;
			if(parent==cur) {	// node we want to delete has no left, so above loop did not ran
				parent.right = minOnRight.right;
			}
			else {
				parent.left = minOnRight.right;
			}
		}
		
		return root;
		
	}
	
	public static <T extends Comparable<? super T>> void insertIterative(Node<T> root, T val) {
		if(root==null) {
			return;
		}
		Node<T> cur = root;
		Node<T> parent = null;
		while(cur!=null) {
			if(cur.data.compareTo(val)>0) {
				parent = cur;
				cur = cur.left;
			}
			else if(cur.data.compareTo(val)<0) {
				parent = cur;
				cur = cur.right;
			}
			else {
				return;
			}
		}
		if(parent!=null && parent.data.compareTo(val)>0) {
			Node<T> newNode = new Node<>(val);
			newNode.parent = parent;
			parent.left = newNode;
		}
		else if(parent!=null && parent.data.compareTo(val)<0){
			Node<T> newNode = new Node<>(val);
			newNode.parent = parent;
			parent.right = newNode;
		}
	}
	
	public static <T extends Comparable<? super T>> void postOrderTraversakWith2Stacks(Node<T> root) {
		if(root==null) {
			return;
		}
		
		Node<T> cur = root;
		Deque<Node<T>> stk1 = new LinkedList<Node<T>>();
		Deque<Node<T>> stk2 = new LinkedList<Node<T>>();
		stk1.push(root);
		
		while(!stk1.isEmpty()) {
			cur = stk1.pop();
			stk2.push(cur);
			
			if(cur.left!=null) {
				stk1.push(cur.left);
			}
			if(cur.right!=null) {
				stk1.push(cur.right);
			}
		}
		
		while(!stk2.isEmpty()) {
			cur = stk2.pop();
			System.out.print(cur.data+", ");
		}
		
	}
	
	public static <T extends Comparable<? super T>> void postOrderWith1Stack(Node<T> root) {
		if(root==null)
		{
			return;
		}
		
		Deque<Node<T>> stk = new LinkedList<Node<T>>();
		Node<T> cur = root;
		stk.push(cur);
		
		while(!stk.isEmpty()) {
			cur = stk.peek();
			if(cur.left!=null && !cur.left.isVisited) {
				cur = cur.left;
				stk.push(cur);
				continue;
			}
			
			if(cur.right!=null && !cur.right.isVisited) {
				cur = cur.right;
				stk.push(cur);
				continue;
			}
			cur = stk.peek();
			if(cur.left==null && cur.right==null) {
				System.out.print("root to leaf path -> ");
				stk.stream()
				.collect(Collectors.toCollection(ArrayDeque::new))
				.descendingIterator()
				.forEachRemaining(x->System.out.print(x.data+", "));
				System.out.println();
			}
			
			
			cur = stk.pop();
			System.out.print(cur.data+", ");

			System.out.println();
			cur.isVisited = true;
		}
	}
	
	
	public static <T extends Comparable<? super T>> void inOrderTraversalWithStack(Node<T> root) {
		if(root==null) {
			return;
		}
		System.out.println();
		Node<T> cur = root;
		Deque<Node<T>> stk = new LinkedList<Node<T>>();
		stk.push(root);
		
		while(!stk.isEmpty()) {
			while(cur!=null) {
				if(cur.left!=null) {
					stk.push(cur.left);
				}
				cur = cur.left;
			}
			
			cur = stk.pop();
			System.out.print(cur.data+", ");
			
			if(cur.right!=null) {
				stk.push(cur.right);
			}
			cur = cur.right;
		}
	}
	
	public static <T extends Comparable<? super T>> void preOrderTraversalWithStack(Node<T> root) {
		if(root==null) {
			return;
		}
		
		Node<T> cur = root;
		Deque<Node<T>> stk = new LinkedList<Node<T>>();
		stk.push(root);
		
		while(!stk.isEmpty()) {
			cur = stk.pop();
			System.out.print(cur.data+", ");
			if(cur.right!=null) {
				stk.push(cur.right);
			}
			if(cur.left!=null) {
				stk.push(cur.left);
			}
		}
	}
	
	
	// number of nodes from root to longest leaf
	static <T extends Comparable<? super T>> int getHeight(Node<T> root) {
		if(root==null)
			return 0;
		int lheight = getHeight(root.left);
		int rheight = getHeight(root.right);
		return 1+Math.max(lheight,rheight);
	
	}
	
	
	
	
	// number of nodes from root to longest leaf
	static <T extends Comparable<? super T>> int getSize(Node<T> root) {
		if(root==null)
			return 0;
		int lsize = getSize(root.left);
		int rsize = getSize(root.right);
		return (1+lsize+rsize);
	}
	
	
	static <T extends Comparable<? super T>> void bfs(Node<T> root) {
		if(root==null) {
			return;
		}
		
		Node<T> p = root;
		Queue<Node<T>> queue = new LinkedList<Node<T>>();
		queue.offer(root);
		while(!queue.isEmpty()) {
			p = queue.poll();
			System.out.print(p.data+", ");
			if(p.left!=null) {
				queue.offer(p.left);
			}
			if(p.right!=null) {
				queue.offer(p.right);
			}
		}
	}
	static <T extends Comparable<? super T>> void preOrder(Node<T> root) {
		if(root==null)
		{
			return;
		}
		
		System.out.print(root.data+", ");
		preOrder(root.left);
		preOrder(root.right);
	}
	
	static <T extends Comparable<? super T>> void postOrder(Node<T> root) {
		if(root==null) {
			return;
		}
		
		postOrder(root.left);
		postOrder(root.right);
		System.out.print(root.data+", ");
	}
	
	static <T extends Comparable<? super T>> void inOrder(Node<T> root) {
		if(root==null)
		{
			return;
		}
		inOrder(root.left);
		System.out.print(root.data+", ");
		inOrder(root.right);
	}
	
	static <T extends Comparable<T>>Node<T> insert(Node<T> root, T val) {
		if(root==null) {
			Node<T> newNode = new Node<>(val);
			return newNode;
		}
		
		if(val.compareTo(root.data)<0) {
			root.left = insert(root.left,val);
			root.left.parent = root;
			root.lsize++;
		}
		else if(val.compareTo(root.data)>0) {
			root.right = insert(root.right,val);
			root.right.parent = root;
			root.rsize++;
		}
		else {
			root.count++;
		}
		
		return root;
	}
	
	static class BinaryTree<T extends Comparable<? super T>>{
		Node<T> root;
	}
	
	static class Node<T extends Comparable<? super T>> implements Comparable<Node<T>>{
		T data;
		int lsize, rsize, count;
		Node<T> left, right, parent;
		boolean isVisited;
		int hd;
		
		Node(T val){
			this.data=val;
		}

		@Override
		public int compareTo(Node<T> o) {
			return this.data.compareTo(o.data);
		}
		
		@Override
		public String toString() {
			return data.toString();
		}
	}
	
	
	static class BTreePrinter {

	    public static <T extends Comparable<? super T>> void printNode(Node<T> root) {
	        int maxLevel = BTreePrinter.maxLevel(root);

	        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
	    }

	    private static <T extends Comparable<? super T>> void printNodeInternal(List<Node<T>> nodes, int level, int maxLevel) {
	        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
	            return;

	        int floor = maxLevel - level;
	        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
	        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
	        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

	        BTreePrinter.printWhitespaces(firstSpaces);

	        List<Node<T>> newNodes = new ArrayList<Node<T>>();
	        for (Node<T> node : nodes) {
	            if (node != null) {
	                System.out.print(node.data);
	                newNodes.add(node.left);
	                newNodes.add(node.right);
	            } else {
	                newNodes.add(null);
	                newNodes.add(null);
	                System.out.print(" ");
	            }

	            BTreePrinter.printWhitespaces(betweenSpaces);
	        }
	        System.out.println("");
	        for (int i = 1; i <= endgeLines; i++) {
	            for (int j = 0; j < nodes.size(); j++) {
	                BTreePrinter.printWhitespaces(firstSpaces - i);
	                if (nodes.get(j) == null) {
	                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
	                    continue;
	                }

	                if (nodes.get(j).left != null)
	                    System.out.print("/");
	                else
	                    BTreePrinter.printWhitespaces(1);

	                BTreePrinter.printWhitespaces(i + i - 1);

	                if (nodes.get(j).right != null)
	                    System.out.print("\\");
	                else
	                    BTreePrinter.printWhitespaces(1);

	                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
	            }

	            System.out.println("");
	        }

	        printNodeInternal(newNodes, level + 1, maxLevel);
	    }

	    private static void printWhitespaces(int count) {
	        for (int i = 0; i < count; i++)
	            System.out.print(" ");
	    }

	    private static <T extends Comparable<? super T>> int maxLevel(Node<T> node) {
	        if (node == null)
	            return 0;

	        return Math.max(BTreePrinter.maxLevel(node.left), BTreePrinter.maxLevel(node.right)) + 1;
	    }

	    private static <T> boolean isAllElementsNull(List<T> list) {
	        for (Object object : list) {
	            if (object != null)
	                return false;
	        }

	        return true;
	    }

	}
	
	
	static class BTreePrinterBT {

	    public static void printNode(BTNode root) {
	        int maxLevel = BTreePrinterBT.maxLevel(root);

	        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
	    }

	    private static void printNodeInternal(List<BTNode> nodes, int level, int maxLevel) {
	        if (nodes.isEmpty() || BTreePrinterBT.isAllElementsNull(nodes))
	            return;

	        int floor = maxLevel - level;
	        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
	        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
	        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

	        BTreePrinterBT.printWhitespaces(firstSpaces);

	        List<BTNode> newNodes = new ArrayList<BTNode>();
	        for (BTNode node : nodes) {
	            if (node != null) {
	                System.out.print(node.data);
	                newNodes.add(node.left);
	                newNodes.add(node.right);
	            } else {
	                newNodes.add(null);
	                newNodes.add(null);
	                System.out.print(" ");
	            }

	            BTreePrinterBT.printWhitespaces(betweenSpaces);
	        }
	        System.out.println("");
	        for (int i = 1; i <= endgeLines; i++) {
	            for (int j = 0; j < nodes.size(); j++) {
	            	BTreePrinterBT.printWhitespaces(firstSpaces - i);
	                if (nodes.get(j) == null) {
	                	BTreePrinterBT.printWhitespaces(endgeLines + endgeLines + i + 1);
	                    continue;
	                }

	                if (nodes.get(j).left != null)
	                    System.out.print("/");
	                else
	                	BTreePrinterBT.printWhitespaces(1);

	                BTreePrinterBT.printWhitespaces(i + i - 1);

	                if (nodes.get(j).right != null)
	                    System.out.print("\\");
	                else
	                	BTreePrinterBT.printWhitespaces(1);

	                BTreePrinterBT.printWhitespaces(endgeLines + endgeLines - i);
	            }

	            System.out.println("");
	        }

	        printNodeInternal(newNodes, level + 1, maxLevel);
	    }

	    private static void printWhitespaces(int count) {
	        for (int i = 0; i < count; i++)
	            System.out.print(" ");
	    }

	    private static int maxLevel(BTNode node) {
	        if (node == null)
	            return 0;

	        return Math.max(BTreePrinterBT.maxLevel(node.left), BTreePrinterBT.maxLevel(node.right)) + 1;
	    }

	    private static <T> boolean isAllElementsNull(List<T> list) {
	        for (Object object : list) {
	            if (object != null)
	                return false;
	        }

	        return true;
	    }

	}


}


