package binarytrees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

import org.junit.Assert;


public class BinaryTreeExamples {
	public static void main(String[] args) {
		binaryTreeExamples();
	}
	
	static void binaryTreeExamples(){
		final BinaryTree bt = new BinaryTree();
		Random random = new Random();
		random.ints(10,35,200).forEach(x->{
			bt.root = insert(bt.root,x);
		});
		
		//BTreePrinterBT.printNode(bt.root);
		
		BTNode root2  = new BTNode(145);
		root2.left = new BTNode(82);
		root2.left.left = new BTNode(67);
		root2.left.left.left = new BTNode(43);
		root2.left.right = new BTNode(118);
		root2.left.right.right = new BTNode(139);
		root2.right = new BTNode(149);
		root2.right.right = new BTNode(176);
		root2.right.right.right = new BTNode(196);
		root2.right.right.right.left = new BTNode(179);
		
		BTreePrinterBT.printNode(root2);
		
		System.out.println("find 139   : "+searchInBSTIterative(root2,139).data);
		
		deleteInBST(root2,145);
		BTreePrinterBT.printNode(root2);
		insert(root2,145);
		BTreePrinterBT.printNode(root2);
		insertInBSTIterative(root2,173);
		BTreePrinterBT.printNode(root2);
		iterativeDelete(root2,176);
		BTreePrinterBT.printNode(root2);
		
		System.out.println("\nTree Traversals:-> ");
		
		System.out.print("\nPreOrder Traversals:-> ");
		List<Integer> preList=new ArrayList<>();
		preOrder(root2,preList);
		
		System.out.print("\nInOrder Traversals:-> ");
		List<Integer> inList=new ArrayList<>();
		inOrder(root2,inList);
		
		System.out.print("\nPostOrder Traversals:-> ");
		List<Integer> posList=new ArrayList<>();
		postOrder(root2,posList);
		
		BTNode root3 = constructBinarySearchTreeFromInAndPre(inList.toArray(new Integer[0]), preList.toArray(new Integer[0]), 0, preList.size()-1);
		BTreePrinterBT.printNode(root3);
		
		posIndex=posList.size()-1;
		BTNode root4 = constructBinarySearchTreeFromInAndPos(inList.toArray(new Integer[0]), posList.toArray(new Integer[0]), 0, posList.size()-1);
		BTreePrinterBT.printNode(root4);
		
		System.out.println("Identical Tress root2, root3 and root4 : "+isIdenticalTree(root2, root3) +"  , "+isIdenticalTree(root2, root4));
		
		lelvelOrderTraversal(root4);
		lelvelOrderZigZagTraversal(root4);
		
		BTNode root6  = new BTNode(22);
		insert(root6,30);
		insert(root6,17);
		insert(root6,12);
		insert(root6,50);
		insert(root6,34);
		insert(root6,41);
		insert(root6,12);
		insert(root6,9);
		insert(root6,10);
		
		BTreePrinterBT.printNode(root6);
		
		BTNode inOrPre = inOrderPredecessor(root6,search(root6, 34));
		System.out.println("preOrderShould be 30 : "+inOrPre);
		inOrPre = inOrderPredecessor(root6,search(root6, 17));
		Assert.assertEquals("in order predecessor is not 12",12, inOrPre.data);
		System.out.println("\nprinting 3 distance way from root: -> ");
		printKDownNodes(root6,3);
		
		printLeftRightWay(root6);
		BTreePrinterBT.printNode(root6);
		printBoundaries(root6);
		System.out.println("\nis 17 50 sibling "+isSibling(root6,17,50));
		
		nextRightSideNode(root6,12);
		
		lcaWithArrays(root6,41,50);
		
		System.out.println("lca efficient is : "+lca(root6,41,50));
		BTNode findNode = search(root6,50);
		findNode.right = new BTNode(55);
		
		findNode = search(root6,12);
		findNode.right = new BTNode(13);
		
		BTreePrinterBT.printNode(root6);
		cousinesofANode(root6,9);
		
		constructBSTFromSortedArray();
		createBSTFromSrotedLinkedList();
	}
	
	static BTNode head, prev;
	static void constructBSTFromSortedArray() {

		Random random = new Random();
		int[]a = random.ints(10,5,25).toArray();
		int start = 0;
		int end = a.length-1;
		Arrays.sort(a);
		BTNode root = bstFromArray(a,start,end);

		System.out.println("\nArrays is "+ Arrays.toString(a));
		System.out.println("BST is ->");
		BTreePrinterBT.printNode(root);
		
	}
	
	
	static BTNode bstFromArray(int[]a, int start, int end){
		if(end<start)
			return null;
		
		int mid = (start+end)/2;
		BTNode root = new BTNode(a[mid]);
		root.left = bstFromArray(a,start,mid-1);
		root.right = bstFromArray(a,mid+1,end);
		return root;
	}
	
	static ListNode headShared;
	static void createBSTFromSrotedLinkedList() {
		Random rand = new Random();
		LinkedListClass lkdlist = new LinkedListClass();
		rand.ints(15,5,50).forEach(x->{
			lkdlist.head = insertInLinkedList(lkdlist.head,x);
		});
		System.out.println("before merge sort : ");
		printLinkedList(lkdlist.head);
		ListNode head = mergeSort(lkdlist.head);
		System.out.println("\nLinkedList is -> ");
		printLinkedList(head);
		int start=0;
		int end = sizeOfLinkedList(head)-1;
		headShared = head;
		BTNode root = bstFromSortedLinkedList(sizeOfLinkedList(head));
		BTNode root2 = bstFromSortedLinkedListNLogN(head,null);
		System.out.println("\nBST is ->");
		BTreePrinterBT.printNode(root);
		BTreePrinterBT.printNode(root2);
		lkdlist.head = null;
		rand.ints(15,5,50).forEach(x->{
			lkdlist.head = insertInLinkedList(lkdlist.head,x);
		});
		lkdlist.head = sortedLinkedList(lkdlist.head);
		System.out.println("\nLinkedList is -> ");
		printLinkedList(lkdlist.head);
		
		
		lkdlist.head = shuffleLinkedList(lkdlist.head);
		System.out.println("\nAfter Shuffle LinkedList is -> ");
		printLinkedList(lkdlist.head);
	}
	
	
	static ListNode shuffleLinkedList(ListNode head) {
		if(head==null||head.next==null) {
			return head;
		}
		
		int size = sizeOfLinkedList(head);
		
		int iteration = 10;
		Random rand = new Random();
		while(iteration-->=0) {
			int k = rand.nextInt(size-1)+1;
			
			ListNode l1 = head;
			ListNode l2 = splitAtKthNode(head, k);
			l1=reverseLinkedList(l1);
			l2=reverseLinkedList(l2);
			head = shuffleMerge(l1, l2);
		}
		
		return head;
	}
	
	static ListNode reverseLinkedList(ListNode head) {
		ListNode cur = head;
		ListNode prev = null;
		while(cur!=null) {
			ListNode next = cur.next;
			cur.next=prev;
			prev =cur;
			cur=next;
		}
		return prev;
	}
	
	static ListNode splitAtKthNode(ListNode head, int k) {
		ListNode cur = head;
		int count=0;
		while(k>count) {
			cur = cur.next;
			count++;
		}
		
		ListNode next = cur.next;
		cur.next = null;
		return next;
	}
	
	static ListNode shuffleMerge(ListNode l1, ListNode l2) {
		if(l1==null) {
			return l2;
		}
		if(l2==null) {
			return l1;
		}
		
		ListNode dummy = new ListNode(-1);
		ListNode cur = dummy;
		while(l1!=null&&l2!=null) {
			ListNode n1 = l1;
			l1 = l1.next;
			n1.next=null;
			cur.next=n1;
			cur=cur.next;
			
			ListNode n2 = l2;
			l2=l2.next;
			n2.next=null;
			cur.next=n2;
			cur=cur.next;
		}
		
		if(l1!=null) {
			cur.next=l1;
		}
		if(l2!=null) {
			cur.next=l2;
		}
		return dummy.next;
	}
	
	static ListNode sortedLinkedList(ListNode head) {
		if(head==null||head.next==null) {
			return head;
		}
		
		ListNode cur = head;
		ListNode result = cur;
		cur = cur.next;
		result.next=null;
		
		while(cur!=null) {
			ListNode next=cur.next;
			result = insertInSortedList(result,cur);
			cur = next;
		}
		
		return result;
	}
	
	
	static ListNode insertInSortedList(ListNode list, ListNode newNode) {
		ListNode dummy = new ListNode(-1);
		dummy.next = list;
		ListNode cur = dummy;
		while(cur.next!=null&&cur.next.data<=newNode.data) {
			cur=cur.next;
		}
		newNode.next = cur.next;
		cur.next = newNode;
		return dummy.next; 
	}
	
	static void printLinkedList(ListNode head) {
		System.out.print("{");
		while(head!=null) {
			System.out.print(head.data+"->");
			head = head.next;
		}
		System.out.print("null");
	}
	
	static int sizeOfLinkedList(ListNode head) {
		int count = 0;
		while(head!=null) {
			count++;
			head=head.next;
		}
		
		return count;
	}
	
	static ListNode mergeSort(ListNode head) {
		if(head==null|head.next==null) {
			return head;
		}
		
		
		ListNode midNode = getMidNode(head);
		ListNode rightPart = midNode.next;
		ListNode leftPart = head;
		midNode.next=null;
		
		leftPart = mergeSort(leftPart);
		rightPart = mergeSort(rightPart);
		return merge2SortedLinkedList(leftPart, rightPart);
	}
	
	static ListNode merge2SortedLinkedList(ListNode l1, ListNode l2) {
		
		ListNode dummy = new ListNode(-1);
		ListNode cur = dummy;
		while(l1!=null&&l2!=null) {
			if(l1.data<l2.data) {
				ListNode next = l1.next;
				cur.next = l1;
				cur = cur.next;
				cur.next=null;
				l1 = next;
			}
			else if(l1.data>l2.data) {
				ListNode next = l2.next;
				cur.next = l2;
				cur = cur.next;
				l2=next;
				cur.next=null;
			}
			else {
				ListNode next = l1.next;
				cur.next = l1;
				cur = cur.next;
				cur.next=null;
				l1=next;
				l2=l2.next;
			}
		}
		
		if(l1!=null) {
			cur.next=l1;
		}
		if(l2!=null)
		{
			cur.next=l2;
		}
		
		return dummy.next;
	}
	
	static ListNode getMidNode(ListNode head) {
		ListNode slow = head;
		ListNode fast = head.next;
		while(fast!=null&&fast.next!=null) {
			slow = slow.next;
			fast=fast.next.next;
		}
		return slow;
	}
	
	static class LinkedListClass{
		ListNode head;
	}
	
	static ListNode insertInLinkedList(ListNode head, int val) {
		if(head==null) {
			return new ListNode(val);
		}
		ListNode node = new ListNode(val);
		node.next = head;
		head = node;
		return head;
	}
	
	static class ListNode{
		int data;
		ListNode next;
		
		ListNode(int data){
			this.data = data;
		}
		
		public String toString() {
			return String.valueOf(data);
		}
	}
	
	
	
	static BTNode bstFromSortedLinkedListNLogN(ListNode head, ListNode tail){
		if(head==tail)
			return null;
		
		ListNode slow=head, fast = head;
		while(fast!=tail&&fast.next!=tail) {
			slow=slow.next;
			fast=fast.next.next;
		}
		
		BTNode root = new BTNode(slow.data);
		root.left = bstFromSortedLinkedListNLogN(head,slow);
		root.right = bstFromSortedLinkedListNLogN(slow.next,tail);
		return root;
	}
	
	static BTNode bstFromSortedLinkedList(int n){
		if(n<=0)
			return null;
		
		BTNode left = bstFromSortedLinkedList(n/2);
		BTNode root = new BTNode(headShared.data);
		root.left = left;
		headShared = headShared.next;
		root.right = bstFromSortedLinkedList(n-n/2-1);
		return root;
	}
	
	
	static BTNode lca(BTNode root, int val1, int val2) {
		if(root==null) {
			return null;
		}
		
		if(root.data==val1||root.data==val2) {
			return root;
		}
		
		BTNode l = lca(root.left,val1,val2);
		BTNode r = lca(root.right,val1,val2);
		
		if(l!=null && r!=null) {
			return root;
		}
		
		return l==null?r:l;
	}
	
	
	static void cousinesofANode(BTNode root, int key) {
		
		int level = findLevel(root, key,0);
		if(level==-1 || level==0) {
			return;
		}
		printAllNodesAtLevelWithKey(root.left,key ,level, 1,  root);
		printAllNodesAtLevelWithKey(root.right,key , level, 1, root);
		
	}
	
	static void printAllNodesAtLevelWithKey(BTNode root,int key, int level, int curLevel, BTNode parent) {
		if(root==null) {
			return;
		}
		
		if(curLevel==level
				&& root.data!=key
				&& (parent.right==null||parent.right.data!=key)
				&& (parent.left==null||parent.left.data!=key)) {
			System.out.print(root.data+", ");
		}
		printAllNodesAtLevelWithKey(root.left,key,level,curLevel+1,root);
		printAllNodesAtLevelWithKey(root.right,key,level,curLevel+1,root);
		
	}
	
	static int findLevel(BTNode root, int key, int curLevel) {
		if(root==null) {
			return -1;
		}
		
		if(root.data==key) {
			return curLevel;
		}
		
		int level = findLevel(root.left,key,curLevel+1);
		if(level!=-1) {
			return level;
		}
		return findLevel(root.right,key,curLevel+1);
	}
	
	static void lcaWithArrays(BTNode root, int val1, int val2) {
		List<BTNode> list1 = new ArrayList<>();
		List<BTNode> list2 = new ArrayList<>();
		findAndReturnListWithAncestor(root,val1,list1);
		findAndReturnListWithAncestor(root,val2,list2);
		
		int m = list1.size();
		int n = list2.size();
		
		int prev=-1;
		for(int i=0;i<Math.min(m, n);i++) {
			if(list1.get(i)!=list2.get(i)) {
				if(prev==-1) {
					System.out.println("no lca exists. between "+val1+" and "+val2);
				}
				System.out.println("lca is "+list1.get(prev));
				break;
			}
			prev=i;
		}
		if(prev==Math.min(m, n)-1) {
			System.out.println("lca is "+list1.get(prev));
		}
	}
	
	static boolean findAndReturnListWithAncestor(BTNode root, int key, List<BTNode> finalResult){
		if(root==null) {
			return false;
		}
		
		finalResult.add(root);
		if(root.data==key) {
			return true;
		}
		
		boolean ans = findAndReturnListWithAncestor(root.left, key, finalResult);
		if(!ans) {
			ans = findAndReturnListWithAncestor(root.right, key, finalResult);
		}
		
		if(!ans)
			finalResult.remove(finalResult.size()-1);
		return ans;
	}
	
	static void nextRightSideNode(BTNode root, int key) {
		MatchedLevel levelKeyMatch = new MatchedLevel();
		BTNode nextRight = nextRight(root,key,levelKeyMatch,0);
		System.out.println("\nfound next right is : "+nextRight);
	}
	
	static class MatchedLevel{
		int level=-1;
	}
	
	static BTNode nextRight(BTNode root, int key, MatchedLevel levelKeyMatch, int curLevel) {
		if(root==null)
			return null;
		
		if(root.data==key) {
			levelKeyMatch.level = curLevel;
			return null;
		}
		
		if(levelKeyMatch.level==curLevel) {
			return root;
		}
		
		BTNode left = nextRight(root.left,key,levelKeyMatch,curLevel+1);
		if(left!=null)
			return left;
		return nextRight(root.right,key,levelKeyMatch,curLevel+1);
	}
	
	
	static boolean isSibling(BTNode root, int c1, int c2) {
		BTNode p1 = findParent(root,c1);
		BTNode p2 = findParent(root,c2);
		return p1==p2;
		
	}
	
	static BTNode findParent(BTNode root, int val) {
		if(root==null) {
			return null;
		}
		
		if(root.left!=null && root.left.data==val) {
			return root;
		}
		else if(root.right!=null && root.right.data==val) {
			return root;
		}
		
		BTNode left = findParent(root.left,val);
		if(left!=null)
			return left;
		return findParent(root.right,val);
		
	}
	
	static void printBoundaries(BTNode root){
		System.out.println("\nPrinting Boundaries:-> ");
		printLeftTopToBottom(root);
		printLeavesLeftToRight(root.left);
		printLeavesLeftToRight(root.right);
		printRightBottomToTop(root.right);
	}
	
	static void printLeavesLeftToRight(BTNode root) {
		if(root==null) {
			return;
		}
		
		if(isLeaf(root)) {
			System.out.print(root.data+", ");
		}
		if(root.left!=null)
			printLeavesLeftToRight(root.left);
		else
			printLeavesLeftToRight(root.right);
		
	}
	
	static void printRightBottomToTop(BTNode root) {
		if(root==null || isLeaf(root)) {
			return;
		}
		
		if(root.right!=null) 
			printRightBottomToTop(root.right);
		else 
			printRightBottomToTop(root.left);
		System.out.print(root.data+", ");
	}
	
	static void printLeftTopToBottom(BTNode root) {
		if(root==null || isLeaf(root)) {
			return;
		}
		
		System.out.print(root.data+", ");
		if(root.left!=null) printLeftTopToBottom(root.left);
		else printLeftTopToBottom(root.right);
		
	}
	
	public static void printKDownNodes(BTNode root, int k) {
		if(root==null)
			return;
		
		if(k==0) {
			System.out.print(root.data+", ");
		}
		
		printKDownNodes(root.left,k-1);
		printKDownNodes(root.right,k-1);
		
	}
	
	public static void printLeftRightWay(BTNode root) {

		System.out.println("\nprinting left and rightmost of each level:-> ");
		Queue<BTNode> q = new LinkedList<BTNode>();
		q.offer(root);
		
		while(!q.isEmpty()) {
			int size = q.size();
			int n=size;
			
			while(n-->0) {
				BTNode node = q.poll();
				// print leftmost
				boolean first=false;
				if(n==size-1) {
					first = true;
					System.out.print(node.data+", ");
				}
				
				// print rightmost
				if(!first&&n==0) {
					System.out.print(node.data+", ");
				}
				
				// push to queues
				if(node.left!=null) {
					q.offer(node.left);
				}
				
				if(node.right!=null) {
					q.offer(node.right);
				}
			}
			System.out.println();
		}
		
	}
	
	
	public static BTNode inOrderPredecessorWithoutParentPointer(BTNode root, BTNode node) {
		if(root==null || node==null) {
			return null;
		}
		BTNode cur = node;
		// if there is left child to that node then go left and then rightmost
		if(cur.left!=null) {
			BTNode leftChild = cur.left;
			while(leftChild.right!=null) {
				leftChild=leftChild.right;
			}
			return leftChild;
		}
		else {
			BTNode parent=cur.parent;
			while(parent!=null&&parent.right!=cur) {
				cur = parent;
				parent = cur.parent;
			}
			return parent;
		}
		
	}
	
	
	public static BTNode inOrderPredecessor(BTNode root, BTNode node) {
		if(root==null || node==null) {
			return null;
		}
		BTNode cur = node;
		// if there is left child to that node then go left and then rightmost
		if(cur.left!=null) {
			BTNode leftChild = cur.left;
			while(leftChild.right!=null) {
				leftChild=leftChild.right;
			}
			return leftChild;
		}
		else {
			BTNode parent=cur.parent;
			while(parent!=null&&parent.right!=cur) {
				cur = parent;
				parent = cur.parent;
			}
			return parent;
		}
		
	}
	
	static void lelvelOrderTraversal(BTNode root) {
		Queue<BTNode> q = new LinkedList<>();
		q.offer(root);
		BTNode cur = root;
		int index=1;
		while(!q.isEmpty()) {
			int size = q.size();
			System.out.print("Level"+index+++" :");
			while(size-->0) {
				cur = q.poll();
				System.out.print(cur.data+",");
				if(cur.left!=null) {
					q.offer(cur.left);
				}
				
				if(cur.right!=null) {
					q.offer(cur.right);
				}
			}
			System.out.println();
		}
		System.out.println("done level order");
		
	}
	
	static void lelvelOrderZigZagTraversal(BTNode root) {
		Deque<BTNode> sl = new LinkedList<>();
		sl.push(root);
		BTNode cur = root;
		int index=1;
		Deque<BTNode> sr = new LinkedList<>();
		while(!sl.isEmpty()||!sr.isEmpty()) {
			System.out.print("\nLelvel"+index+++" :");
			while(!sl.isEmpty()) {
				cur=sl.pop();
				System.out.print(cur.data+",");
				if(cur.right!=null) {
					sr.push(cur.right);
				}
				if(cur.left!=null) {
					sr.push(cur.left);
				}
			}
			System.out.print("\nLelvel"+index+++" :");
			while(!sr.isEmpty()) {
				cur=sr.poll();
				System.out.print(cur.data+",");
				if(cur.left!=null) {
					sl.push(cur.left);
				}
				if(cur.right!=null) {
					sl.push(cur.right);
				}
			}
			
		}
	}
	
	static boolean constainsTree(BTNode root1, BTNode root2) {
		if(root2==null) {
			return true;
		}
		
		else return subTree(root1, root2);
	}
	
	static boolean subTree(BTNode root1, BTNode root2) {
		if(root1==null) {
			return false;
		}
		if(root1.data==root2.data) {
			return isIdenticalTree(root1, root2);
		}
		else {
			return subTree(root1.left,root2) || subTree(root1.right,root2);
		}
	}
	
	static boolean isIdenticalTree(BTNode root1, BTNode root2) {
		if(root1==null && root2==null) {
			return true;
		}
		else if(root1==null||root2==null) {
			return false;
		}
		else {
			return (root1.data==root2.data)
					&& isIdenticalTree(root1.left,root2.left)
					&& isIdenticalTree(root1.right,root2.right);
		}
	}
	
	static int preIndex=0;
	static BTNode constructBinarySearchTreeFromInAndPre(Integer[] in, Integer[] pre, int istart, int iend) {
		if(preIndex==pre.length||iend<istart) {
			return null;
		}
		
		BTNode newNode = new BTNode(pre[preIndex++]);
		
		if(istart==iend) {
			return newNode;
		}
		
		int i=istart;
		for(;i<=iend;i++) {
			if(in[i]==newNode.data) {
				break;
			}
		}
		
		if(i<=iend) {
			newNode.left = constructBinarySearchTreeFromInAndPre(in, pre, istart,i-1);
			newNode.right = constructBinarySearchTreeFromInAndPre(in, pre, i+1,iend);
		}
		
		return newNode;
	}
	
	static int posIndex=0;// will be initialized to pos.length before call
	static BTNode constructBinarySearchTreeFromInAndPos(Integer[] in, Integer[] pos, int istart, int iend) {
		if(posIndex<0||iend<istart) {
			return null;
		}
		
		BTNode newNode = new BTNode(pos[posIndex--]);
		if(istart==iend) {
			return newNode;
		}
		
		int i=istart;
		for(;i<=iend;i++) {
			if(in[i]==newNode.data) {
				break;
			}
		}
		
		if(i<=iend) {
			newNode.right = constructBinarySearchTreeFromInAndPos(in, pos, i+1,iend);
			newNode.left = constructBinarySearchTreeFromInAndPos(in, pos, istart,i-1);
		}
		
		return newNode;
	}
	
	static void preOrder(BTNode root,List<Integer> list) {
		if(root==null) {
			return;
		}
		System.out.print(root.data+", "); list.add(root.data);
		preOrder(root.left,list);
		preOrder(root.right,list);
	}
	
	static void inOrder(BTNode root,List<Integer> list) {
		if(root==null) {
			return;
		}
		
		inOrder(root.left,list);
		System.out.print(root.data+", "); list.add(root.data);
		inOrder(root.right,list);
	}
	
	static void postOrder(BTNode root,List<Integer> list) {
		if(root==null) {
			return;
		}
		
		postOrder(root.left,list);
		postOrder(root.right,list);
		System.out.print(root.data+", "); list.add(root.data);
	}
	
	
	static BTNode insertInBSTIterative(BTNode root, int data) {
		if(root==null) {
			return new BTNode(data);
		}
		
		BTNode parent = null;
		BTNode cur = root;
		while(cur!=null) {
			if(cur.data>data) {
				parent = cur;
				cur = cur.left;
			}
			else if(cur.data<data){
				parent = cur;
				cur = cur.right;
			}
			else {
				return root;
			}
		}
		
		// if we are inserting at leaf spot
		if(parent.left==null&&parent.right==null) {
			if(parent.data>data) {
				parent.left = new BTNode(data);
			}
			else {
				parent.right = new BTNode(data);
			}
		}
		else if(parent.left==null) {
			parent.left = new BTNode(data);
		}
		else {
			parent.right = new BTNode(data);
		}
		
		return root;
	}
	
	
	static BTNode iterativeDelete(BTNode root, int data) {
		if(root==null || root.data==data) {
			return null;
		}
		
		BTNode parent = null;
		BTNode cur = root;
		while(cur!=null) {
			if(cur.data>data) {
				parent = cur;
				cur = cur.left;
			}
			else if(cur.data<data) {
				parent = cur;
				cur = cur.right;
			}
			else {
				break;
			}
		}
		
		// no such data exists, we can return from here
		if(cur==null) {
			return root;
		}
		
		if(isLeaf(cur)) {
			if(parent.data>data) {
				parent.left = null;
			}
			else {
				parent.right = null;
			}
		}
		// if either one left is null then replace with right
		else if(cur.left==null) {
			BTNode temp = cur.right;
			cur.data = temp.data;
			cur.left = temp.left;
			cur.right = temp.right;
			temp.right=temp.left=null;
		}
		//if either one right is null then replace with left
		else if(cur.right==null) {
			BTNode temp = cur.left;
			cur.data = temp.data;
			cur.left = temp.left;
			cur.right = temp.right;
			temp.right=temp.left=null;
		}
		else {
			parent = cur;
			BTNode p = cur.right;
			while(p.left!=null) {
				parent = p;
				p = p.left;
			}
			cur.data = p.data;
			if(parent==cur) {
				parent.right = p.right;
			}
			else {
				parent.left = p.right;
			}
		}
		
		return root;
	}
	
	static boolean isLeaf(BTNode node) {
		return node==null ||(node.left==null&&node.right==null);
	}
	
	// delete a node from BST
	static BTNode deleteInBST(BTNode root, int key) {
		if(root==null) {
			return null;
		}
		
		if(root.data>key) {
			root.left = deleteInBST(root.left, key);
		}
		else if(root.data<key) {
			root.right = deleteInBST(root.right, key);
		}
		else {
			if(root.left!=null && root.right!=null) {
				BTNode right = getMin(root.right);
				root.data = right.data;
				root.right = deleteInBST(root.right, root.data);
			}
			else {
				root = root.left!=null?root.left:root.right;
			}
		}
		return root;
	}
	
	
	static BTNode getMin(BTNode root) {
		if(root==null) {
			return null;
		}
		
		while(root.left!=null) {
			root=root.left;
		}
		return root;
	}
	
	// recursive logn
	static BTNode search(BTNode root, int key) {
		if(root==null) {
			return null;
		}
		
		if(root.data==key) {
			return root;
		}
		else if(root.data>key) {
			return search(root.left,key);
		}
		else {
			return search(root.right,key);
		}
		
	}
	
	// iterative
	static BTNode searchInBSTIterative(BTNode root, int key) {
		while(root!=null) {
			if(root.data==key) {
				break;
			}
			else if(root.data>key) {
				root = root.left;
			}
			else {
				root = root.right;
			}
		}
		return root;
	}

	static BTNode insert(BTNode root, int val){
		if(root==null) {
			BTNode node = new BTNode(val);
			return node;
		}
		if(root.data>val) {
			root.left = insert(root.left,val);
			root.left.parent = root;
		}
		else if(root.data<val) {
			root.right = insert(root.right,val);
			root.right.parent = root;
		}	
		return root;
	}
	
	static class BinaryTree{
		BTNode root;
	}

	static class BTNode {
		int data;
		BTNode left,right;
		BTNode parent;
		
		BTNode(int data){
			this.data =data;
		}
		
		public String toString() {
			return String.valueOf(this.data);
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
