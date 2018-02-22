package programcreek;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import javax.lang.model.element.Element;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

import programcreek.SinglyinkedList.Node;

public class LinkedListPrograms {
	static final Unsafe unsafe = getUnsafe();
	static final boolean is64bit = true;
	public static void main(String[] args) {
		SinglyinkedList<Integer> list1 = new SinglyinkedList<>();
		new Random().ints(0,350).limit(10).forEach(e->list1.add(e));
		list1.printLinkedList();
		//ClassLoader classLoader = LinkedListPrograms.class.getClassLoader();
		//System.out.println(classLoader);
		
		// reverse a linked list
		System.out.println();
		reverseList(list1.getHeader());
		list1.printLinkedList();
		System.out.println();
		
		// using recursion
		reverseListRecursion(list1.getHeader(),"notail");
		list1.printLinkedList();
		System.out.println();
		
		// using tail recursion
		reverseListRecursion(list1.getHeader(),"tail");
		list1.printLinkedList();
		System.out.println();
		
		//System.out.println("factorial without tail is "+ fact(10));
		//System.out.println();
		
		System.out.println("factorial tail recursion is "+ fact(10,1));
		System.out.println();
		
		
		//reverse in k groups
		list1.getHeader().next = reverseInGroupOfK(list1.getHeader().next,3);
		list1.printLinkedList();
		System.out.println();
		
		// detect cycle in linked list
		SinglyinkedList<Integer> list2 = new SinglyinkedList<>();
		list2.add(3);
		list2.add(4);
		list2.add(5);
		Node n = new Node(6);
		Node last = getLastNode(list2.getHeader());
		last.next = n;
		list2.add(7);
		list2.add(8);
		list2.add(9);
		list2.printLinkedList();
		System.out.println();
		
		// now make cycle
		last = getLastNode(list2.getHeader());
		last.next = n;
		
		System.out.println();
		
		// now detect a cycle
		Node cycleNode = detectNodeForCycle(list2.getHeader().next);
		
		System.out.println("Hashcode :       "+cycleNode.hashCode());
	    System.out.println("Hashcode :       "+System.identityHashCode(cycleNode));
	    System.out.println("Hashcode (HEX) : "+Integer.toHexString(cycleNode.hashCode()));

	    //toString
	    System.out.println("toString :       "+String.valueOf(cycleNode));

	    printAddresses("Address", cycleNode);
	    
	    
	    // mth elemenet from last
	    System.out.println("mth element from Last "+mthElementFromLast(list1.getHeader().next,4).val);
	    System.out.println();
	    
	    // just print in reverse order
	    list1.printLinkedList();
	    System.out.println();
	    printInReverseOrder(list1.getHeader().next);
		System.out.println();
		
		//intersection point of 2 linkedlists Y shapped
		// first make lists with intersections
		// detect cycle in linked list
		SinglyinkedList<Integer> list3 = new SinglyinkedList<>();
		list3.add(3);
		list3.add(4);
		list3.add(5);
		list3.add(6);
		list3.add(7);

		SinglyinkedList<Integer> list4 = new SinglyinkedList<>();
		list4.add(12);
		
		
		SinglyinkedList<Integer> list5 = new SinglyinkedList<>();
		list5.add(99);
		list5.add(98);
		list5.add(97);
		list5.add(96);
		list5.add(95);
		
		last = getLastNode(list3.getHeader());
		last.next = list5.getHeader().next;
		
		last = getLastNode(list4.getHeader());
		last.next = list5.getHeader().next;
		
		Node interectionNode = getIntersectionPointOf2LinkedList(list3.getHeader().next,list4.getHeader().next);
		printInReverseOrder(interectionNode);
		System.out.println();
		
		
		// sort a linked list using insertion sort, create a new list
		System.out.println("......\n\nDoing insertion sort now...");
		list1.printLinkedList();
		insertionSortLinkedList(list1);
		list1.printLinkedList();
		
		// sort a linked list using merge sort, create a new list
		SinglyinkedList list6= new SinglyinkedList();
		System.out.println("......\n\nDoing merge sort now...");
		new Random().ints(0,350).limit(13).forEach(e->list6.add(e));
		list6.printLinkedList();
		mergeSortLinkedList(list6);
		list6.printLinkedList();
		// sorted intersection 16 — SortedIntersect() http://cslibrary.stanford.edu/105/LinkedListProblems.pdf
		
		// add to the head;
		push(list6.getHeader().next.next.next.next, new Node(100));
		list6.printLinkedList();
		
		deleteNode(list6.getHeader().next.next.next);
		list6.printLinkedList();
		SinglyinkedList l7 = new SinglyinkedList();
		
		l7.getHeader().next = alternatingSplit(list6.getHeader().next);
		list6.printLinkedList();
		l7.printLinkedList();

		
		System.out.println("doing move  now");
		l7.getHeader().next = moveNodes(list6.getHeader().next,l7.getHeader().next);
		l7.printLinkedList();
		list6.printLinkedList();
		
		
		System.out.println("doing split now");
		l7.getHeader().next = alternatingSplit(list6.getHeader().next);
		list6.printLinkedList();
		l7.printLinkedList();
		
		
		list6.getHeader().next = shuffleMerge(list6.getHeader().next, l7.getHeader().next);
		list6.printLinkedList();
		
		//
		System.out.println("\n\nshuffling list.... ");
		System.out.println("Before shuffling : ");
		list6.printLinkedList();
		shuffleList(list6);
		System.out.println("After shuffling : ");
		list6.printLinkedList();
		
		
		// intersectio of 2 sorted lists
		SinglyinkedList<Integer> l1 = new SinglyinkedList<>();
		l1.add(3);
		l1.add(4);
		l1.add(5);
		l1.add(6);
		l1.add(10);
		l1.add(10);
		l1.add(10);
		l1.add(14);
		l1.add(19);
		l1.add(21);
		l1.add(23);
		l1.add(29);

		
		SinglyinkedList<Integer> l2 = new SinglyinkedList<>();
		l2.add(1);
		l2.add(4);
		l2.add(8);
		l2.add(10);
		l2.add(10);
		l2.add(10);
		l2.add(12);
		l2.add(13);
		l2.add(21);
		l2.add(29);
		System.out.println("printing now intersections of sorted linsked list");
		l1.printLinkedList();
		l2.printLinkedList();
		Node result = insersectionOf2SortedListsWithoutRecursion(l1.getHeader().next,l2.getHeader().next);
		printList(result);
		
		
		System.out.println("Swapping places... ");
		l2.printLinkedList();
		Node swapped = swapEvenOddPlaces(l2.getHeader().next);
		l2.printLinkedList();
		printList(swapped);
		
		System.out.println("merging k lists...");
		List<Node> lists = new ArrayList<>();
		SinglyinkedList<Integer> ml1 = new SinglyinkedList<>();
		new Random().ints(0,350).limit(10).forEach(e->ml1.add(e));
		mergeSortLinkedList(ml1);
		SinglyinkedList<Integer> ml2 = new SinglyinkedList<>();
		new Random().ints(0,350).limit(10).forEach(e->ml2.add(e));
		mergeSortLinkedList(ml2);
		
		SinglyinkedList<Integer> ml3 = new SinglyinkedList<>();
		new Random().ints(0,350).limit(10).forEach(e->ml3.add(e));
		mergeSortLinkedList(ml3);
		
		SinglyinkedList<Integer> ml4 = new SinglyinkedList<>();
		new Random().ints(0,350).limit(10).forEach(e->ml4.add(e));
		mergeSortLinkedList(ml4);
		
		SinglyinkedList<Integer> ml5 = new SinglyinkedList<>();
		new Random().ints(0,350).limit(10).forEach(e->ml5.add(e));
		mergeSortLinkedList(ml5);
		lists.add(ml1.getHeader().next);
		lists.add(ml2.getHeader().next);
		lists.add(ml3.getHeader().next);
		lists.add(ml4.getHeader().next);
		lists.add(ml5.getHeader().next);

		//Node mergeKlist = mergeMListsWithHeap(lists);
		Node mergeKlist = mergeMListsWithNmLogm(lists);
		printList(mergeKlist);
		System.out.println("\n\n\n\n");
		
		
		// now for top k docs, each list is sorted in reverse order of score from each server
		System.out.println("getting top 10 docs : from below docs");
		Random randgen = new Random();
		int randomlistsizerange = 100;
		
		ListOfDocs lofd1= new ListOfDocs();
		List<Double> scores1 = new ArrayList<Double>();
		
		randgen.doubles().limit(randgen.nextInt(randomlistsizerange)).forEach(x->scores1.add(x));
		scores1.stream().sorted(Comparator.reverseOrder()).forEach(x -> lofd1.add(randgen.nextInt(10000),x));
		
		ListOfDocs lofd2= new ListOfDocs();
		List<Double> scores2 = new ArrayList<Double>();
		
		randgen.doubles().limit(randgen.nextInt(randomlistsizerange)).forEach(x->scores2.add(x));
		scores2.stream().sorted(Comparator.reverseOrder()).forEach(x -> lofd2.add(randgen.nextInt(10000),x));

		
		ListOfDocs lofd3= new ListOfDocs();
		List<Double> scores3 = new ArrayList<Double>();
		
		randgen.doubles().limit(randgen.nextInt(randomlistsizerange)).forEach(x->scores3.add(x));
		scores3.stream().sorted(Comparator.reverseOrder()).forEach(x -> lofd3.add(randgen.nextInt(10000),x));

		
		ListOfDocs lofd4= new ListOfDocs();
		List<Double> scores4 = new ArrayList<Double>();
		
		randgen.doubles().limit(randgen.nextInt(randomlistsizerange)).forEach(x->scores4.add(x));
		scores4.stream().sorted(Comparator.reverseOrder()).forEach(x -> lofd4.add(randgen.nextInt(10000),x));

		
		ListOfDocs lofd5= new ListOfDocs();
		List<Double> scores5 = new ArrayList<Double>();
		
		randgen.doubles().limit(randgen.nextInt(randomlistsizerange)).forEach(x->scores5.add(x));
		scores5.stream().sorted(Comparator.reverseOrder()).forEach(x -> lofd5.add(randgen.nextInt(10000),x));

		System.out.print(String.format("List1 size[%d]:-> ",lofd1.size()));
		lofd1.printList();
		System.out.print(String.format("List2 size[%d]:-> ",lofd2.size()));
		lofd2.printList();
		System.out.print(String.format("List3 size[%d]:-> ",lofd3.size()));
		lofd3.printList();
		System.out.print(String.format("List4 size[%d]:-> ",lofd4.size()));
		lofd4.printList();
		System.out.print(String.format("List5 size[%d]:-> ",lofd5.size()));
		lofd5.printList();
		
		List<DocsNode> listsFromServers = new ArrayList<DocsNode>();
		listsFromServers.add(lofd1.getStartNode());
		listsFromServers.add(lofd2.getStartNode());
		listsFromServers.add(lofd3.getStartNode());
		listsFromServers.add(lofd4.getStartNode());
		listsFromServers.add(lofd5.getStartNode());
	
		//printing top k docs
		DocsNode resultOfTopK = getTopKRankedDocs(listsFromServers, 5);
		System.out.println("printing top 5 docs");
		printList(resultOfTopK);
		
		// Nodes with random pointer
		// create first nodes
		NodeWithRandomPointer node1 = new NodeWithRandomPointer("1","1");
		NodeWithRandomPointer node2 = new NodeWithRandomPointer("2","2");
		NodeWithRandomPointer node3 = new NodeWithRandomPointer("3","3");
		NodeWithRandomPointer node4 = new NodeWithRandomPointer("4","4");
		NodeWithRandomPointer node5 = new NodeWithRandomPointer("5","5");
		NodeWithRandomPointer node6 = new NodeWithRandomPointer("6","6");
		
		// attach next
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = node6;
		
		// attach random
		node1.random = node5;
		node2.random = node3;
		node3.random = node3;
		node4.random = node3;
		node5.random = node1;
		node6.random = node2;
		
		printList(node1);
		
		NodeWithRandomPointer clonedNode = cloneListWithRandomPointer(node1);
		printList(clonedNode);
		
		clonedNode =  (node1);
		printList(clonedNode);
		
	}
	
	static class NodeWithRandomPointer{
		String val;
		String id;
		NodeWithRandomPointer next;
		NodeWithRandomPointer random;
		
		NodeWithRandomPointer(String val, String id){
			this.val = val;
			this.id = id;
		}
		
		public String toString() {
			return "[id:"+id+",val:"+val+", random:"+random.val+"]";
		}
		
	}
	
	static void printList(NodeWithRandomPointer node) {
		NodeWithRandomPointer cur = node;
		while(cur!=null) {
			System.out.print(cur+"->");
			cur = cur.next;
		}
		System.out.print("null\n");
	}
	
	
	public static NodeWithRandomPointer cloneListWithRandomPointer(NodeWithRandomPointer l1) {
		NodeWithRandomPointer cur = l1;
		NodeWithRandomPointer dummy = new NodeWithRandomPointer("0","0");
		NodeWithRandomPointer result = dummy;
		Map<String, NodeWithRandomPointer> map = new HashMap<String, NodeWithRandomPointer>();
		while(cur!=null) {
			NodeWithRandomPointer temp = map.get(cur.id);//ifExists(dummy.next,cur.id+"`");
			if(temp==null)
			{
				temp = new  NodeWithRandomPointer(cur.val+"`", cur.id+"`");
				map.put(cur.id, temp);
			}
			result.next = temp;
			result = result.next;
			
			NodeWithRandomPointer temprandom = map.get(cur.random.id);
			if(temprandom==null)
			{
				temprandom = new  NodeWithRandomPointer(cur.random.val+"`", cur.random.id+"`");
				map.put(cur.random.id, temprandom);
				
			}
					//ifExists(dummy.next,cur.random.id);
			result.random = temprandom;
			cur = cur.next;
		}
		return dummy.next;
	}
	
	public static NodeWithRandomPointer cloneListWithRandomPointerInPlace(NodeWithRandomPointer l1) {
		NodeWithRandomPointer cur = l1;
		
		// we have 1->1`->2->2`->3->3`->null
		while(cur!=null) {
			NodeWithRandomPointer next = cur.next;
			cur.next = new NodeWithRandomPointer(cur.val+"`",cur.id+"`");
			cur.next.next = next;
			cur = next;
		}
		
		// now we will update random node
		// cur.next.random = cur.random.next
		cur = l1;
		while(cur!=null) {
			cur.next.random = cur.random.next;
			cur = cur.next.next;
		}
		
		
		// now we need to split and retain orginal list as well copy list seprate
		NodeWithRandomPointer dummy = new NodeWithRandomPointer("0","0");
		
		cur = l1;
		NodeWithRandomPointer copy = cur.next;
		dummy.next = copy;
		
		while(cur!=null) {
			cur.next = cur.next!=null?cur.next.next:null;
			copy.next = copy.next!=null?copy.next.next:null;
			copy = copy.next;
			cur = cur.next;
		}
		
		return dummy.next;
	}
	
	public static NodeWithRandomPointer ifExists(NodeWithRandomPointer node, String id) {
		NodeWithRandomPointer cur = node;
		while(cur!=null) {
			if(cur.id.equals(id)) {
				return cur;
			}
		}
		return null;
	}
	
	public static void printList(DocsNode node)
	{
		int count = 1;
		while(node!=null)
		{
			System.out.println((count++)+". "+node);
			node = node.next;
		}
		System.out.println();
	}
	
	public static class ListOfDocs{
		DocsNode head = new DocsNode(0,0);
		public void add(int val, double score){
			DocsNode cur = head;
			while(cur.next!=null) {
				cur = cur.next;
			}
			cur.next = new DocsNode(val,score);
		}
		
		void printList() {
			DocsNode cur = head.next;
			while(cur!=null) {
				System.out.print(cur+"->");
				cur = cur.next;
			}
			System.out.print("null");
			System.out.println();
		}
		
		int size() {
			DocsNode cur = head.next;
			int count = 0;
			while(cur!=null) {
				count++;
				cur = cur.next;
			}
			return count;
		}
		
		DocsNode getStartNode() {
			return head.next;
		}
	}
	
	// sorted intersection of 2 lists
	public static Node insersectionOf2SortedLists(Node l1, Node l2) {
		/* base case */
		if(l1==null || l2==null)
		{
			return null;
		}
		
		if(l1.val.compareTo(l2.val)==0) {
			l1.next = insersectionOf2SortedLists(l1.next,l2.next);
			return l1;
		}
		else if(l1.val.compareTo(l2.val)>0) {
			return insersectionOf2SortedLists(l1,l2.next);
		}
		else {
			return insersectionOf2SortedLists(l1.next,l2);
		}
	}
	

	// sorted intersection of 2 lists
	public static Node insersectionOf2SortedListsWithoutRecursion(Node l1, Node l2) {
		/* base case */
		Node dummy = new Node(-1);
		Node cur = dummy;
		while(l1!=null && l2!=null) {
			if(l1.val.compareTo(l2.val)==0) {
				if(cur.val.compareTo(l1.val)!=0) {
					cur.next = l1;
					cur = cur.next;
				}
				l1=l1.next;
				l2=l2.next;
				cur.next  = null;
			}else if(l1.val.compareTo(l2.val)>0) {
				l2=l2.next;
			}
			else {
				l1=l1.next;
			}
		}
		return dummy.next;
	}
	
	public static Node alternatingSplit(Node firstNode) {// think we are moving node from l1 to l2
		Node dummyNodeDest = new Node(-1);
		Node src = firstNode;
		Node dest = dummyNodeDest;
		if(src.next==null)
		{
			return null;
		}
		while(src!=null) {
			dest.next = src.next;
			src.next = src.next!=null?src.next.next:null;
			src = src.next;
			dest = dest.next;
		}
		
		return dummyNodeDest.next;
	}
	
	public static void shuffleList(SinglyinkedList list) {
		Node cur = list.getHeader().next;
		list.getHeader().next = shuffle(cur);
	}
	
	public static Node shuffle(Node firstNode) {
		int size = getSizeOfList(firstNode);
		int counter = 0;
		while(counter<1) {
			int i = new Random().nextInt(size);
			Node mid = findKthFrontAndBackSplit(firstNode, i);
			
			Node right = mid.next;
			Node left = firstNode;
			mid.next = null;
			
			// before recursion, just reverse the lists
			right = reverseListUsingRecursion(right);
			left = reverseListUsingRecursion(left);
			
			
			// then do shuffle merge
			firstNode = shuffleMerge(left, right);
			counter++;
		}
		
		return firstNode;
	}
	
	public static Node swapEvenOddPlaces(Node head) {
		Node cur = head;
		Node dummy = new Node(-1);
		Node prev = dummy;
		while(cur!=null && cur.next!=null) {
			Node q = cur.next;
			Node r = q.next;

			q.next = cur;
			cur.next = r;
			
			prev.next = q;
			prev = cur;
			cur = cur.next;
				
		}
		return dummy.next;
	}
	
	// merge k lists
	public static Node mergeKLists(List<Node>lists) 
	{
		int size = lists.size();
		Node result = lists.get(0);
		for(int i=1;i<size;i++) {
			result = merge(result, lists.get(i));
		}
		return result;
	}
	public static final Comparator<Node> NODECOMPARATOR = new Comparator<Node>() {
		public int compare(Node o1, Node o2) {
			return o1.val.compareTo(o2.val);
		}
	};
	public static Node mergeMListsWithHeap(List<Node>lists) {
		int size = lists.size();	// M is the number of lists
		
		Queue<Node> q = new PriorityQueue<Node>(size,NODECOMPARATOR);
		
		for(Node n:lists) {		// priority queue of m elements, helps for m way merge
			q.offer(n);
		}
		
		Node dummy = new Node(-1);
		Node cur = dummy;
		while(!q.isEmpty()) {
			Node top = q.poll();
			cur.next = top;
			cur = cur.next;
			
			if(top.next!=null) {
				q.offer(top.next);
			}
		}
		
		
		return dummy.next;
	}
	
	public static Node mergeMListsWithNmLogm(List<Node> lists) {
		int size = lists.size();
		int right = size -1;
		while(right!=0) {
			int i=0;
			while(i<right){
				lists.set(i, merge(lists.get(i++), lists.get(right--)));
			}
		}
		
		return lists.get(0);
	}
	
	public static DocsNode getTopKRankedDocs(List<DocsNode> lists, int k){
		int size = lists.size();	// each list is from a server sorted by score in decreasing order
		
		Queue<DocsNode> q = new PriorityQueue<>(size, Comparator.comparingDouble(DocsNode::getScore).reversed());
		
		for(DocsNode docsNode: lists) {
			if(docsNode!=null) {
				q.offer(docsNode);
			}
		}
		
		DocsNode dummy = new DocsNode(0,0);
		DocsNode cur = dummy; int count = 0;
		while(!q.isEmpty()) {
			DocsNode d = q.poll();
			cur.next = d;
			count++;
			cur = cur.next;

			if(count==k)
			{
				cur.next = null;
				break;
			}
			
			if(d.next!=null) {
				q.offer(d.next);
			}
		}
		return dummy.next;
	}
	
	public static class DocsNode{
		Double score;
		String text;
		String id;
		String url;
		DocsNode next;
		
		DocsNode(int id, double score){
			this.score = score;
			this.id = String.valueOf(id);
			this.text = String.valueOf(id);
			this.url = String.valueOf(id);
		}
		
		public String toString() {
			return "[id:"+id+",text:"+text+",url:"+url+",score"+score+"]"; 
		}
		
		Double getScore() {
			return score;
		}
	}
	
	public static int getSizeOfList(Node firstNode) {
		Node cur = firstNode;
		int count = 0;
		while(cur!=null) {
			count++;
			cur = cur.next;
		}
		return count;
	}
	
	public static Node findKthFrontAndBackSplit(Node firstNode, int k) {
		Node cur = firstNode;
		int count = 0;
		while(cur!=null && count<k) {
			cur = cur.next;
			count++;
		}
		return cur;
	}
	
	public static Node shuffleMerge(Node list1, Node list2)
	{
		Node dummyNode  = new Node(-1);
		Node result = dummyNode;
		while(list1!=null && list2!=null) {
			result.next = list1;
			list1 = list1.next;
			result = result.next;
			result.next = null;
			
			result.next = list2;
			list2 = list2.next;
			result = result.next;
			result.next = null;
		}
		if(list1!=null) {
			result.next = list1;
		}
		if(list2!=null) {
			result.next = list2;
		}
		return dummyNode.next;
	}
	
	public static Node moveNodes(Node src, Node dest) {
		Node dummyNode = new Node(-1);
		dummyNode.next = dest;
		Node head = dummyNode;
		
		while(src!=null) {
			Node tail = head.next;	// store rest that is tail from dest
			head.next = src;
			src = src.next;
			head.next.next = tail;		// this is where we attach tail to node, we never move cur
		}
		return dummyNode.next;
	}
	
	
	public static void deleteNode(Node node) {
		Node temp = node.next;
		node.val = temp!=null?temp.val:null;
		node.next = temp!=null?temp.next:null;
		if(temp!=null) {
			temp.next = null;
		}
	}
	
	public static void push(Node node, Node newNode) {
		Node dummy = new Node(newNode.val);
		newNode.val = node.val;
		newNode.next = node.next;
		node.next = newNode;
		node.val = dummy.val;

	}
	public static void mergeSortLinkedList(SinglyinkedList l1) {
		Node firstNode = l1.getHeader().getNext();
		if(firstNode == null || firstNode.next==null) {
			return;
		}
		l1.getHeader().next = mergeSortList(firstNode);
	}
	
	public static Node mergeSortList(Node firstNode) {
		if(firstNode.next==null)
		{
			return firstNode;
		}
		
		Node midNode = findMid(firstNode);
		
		Node right  = midNode.next;
		Node left = firstNode;
		midNode.next = null;
		
		// recursive call to list itself to reach to end with one node
		left = mergeSortList(left);
		right = mergeSortList(right);
		
		Node result = merge(left, right);
		return result;
	}
	
	public static Node merge(Node l1, Node l2) {
		Node dummy = new Node(-1);
		Node cur = dummy;
		
		while(l1!=null&&l2!=null) {
			if(l1.val.compareTo(l2.val)==0) {
				if(cur.val.compareTo(l1.val)!=0) {
					Node n = new Node(l1.val);
					cur.next = n;
					cur = cur.next;
				}		
				l1 = l1.next;
				l2 = l2.next;
			}
			else if(l1.val.compareTo(l2.val)<0) {
				if(cur.val.compareTo(l1.val)!=0) {
					Node n = new Node(l1.val);
					cur.next = n;
					cur = cur.next;
				}
				l1 = l1.next;
			}
			else {
				if(cur.val.compareTo(l2.val)!=0) {
					Node n = new Node(l2.val);
					cur.next = n;
					cur = cur.next;
				}
				l2 = l2.next;
			}
		}
		if(l1!=null) {
			cur.next = l1;
		}
		if(l2!=null) {
			cur.next = l2;
		}
		return dummy.next;
		
	}
	
	public static Node findMid(Node firstNode) {
		Node fast = firstNode.next;
		Node slow = firstNode;
		while(fast!=null && fast.next!=null) {
			fast = fast.next.next;
			slow = slow.next;
		}
		return slow;
	}
	
	public static void insertionSortLinkedList(SinglyinkedList l1) {
		Node firstNode = l1.getHeader().next;
		if(firstNode==null || firstNode.next==null) {
			return;
		}
		l1.getHeader().next = insertionSort(firstNode);
	}
	
	public static Node insertionSort(Node firstNode) {
		SinglyinkedList l1 = new SinglyinkedList();
		l1.add(firstNode.val);
		
		Node cur = firstNode.next;
		while(cur!=null) {
			l1.getHeader().next = addInSortedList(l1.getHeader().next,cur);
			cur = cur.next;
		}
		
		return l1.getHeader().next;
	}
	
	public static Node addInSortedList(Node cur, Node newNode) {
		Node dummy = new Node(-1);
		dummy.next = cur;
		cur = dummy;
		
		while(cur.next!=null) {
			if(cur.next.val.compareTo(newNode.val)>0) {
				break;
			}
			cur = cur.next;
		}
		
		Node x = new Node(newNode.val);
		x.next = cur.next;
		cur.next = x;
		
		return dummy.next;
	}
	
	
	public static Node getIntersectionPointOf2LinkedList(Node l1, Node l2) {
		int n1 = getSize(l1);		// make sure l1 is always bigger list
		int n2 = getSize(l2);
		int diff = n1-n2;
		if(diff<0) {
			getIntersectionPointOf2LinkedList(l2,l1);
		}
		
		int count = 0;
		Node c1 = l1;
		Node c2 = l2;
		while(count!=diff)
		{
			printAddresses("Intersection list started Address c1", c1);
			c1=c1.next;
			count++;
		}
		
		while(c1!=null && c2!=null)
		{
			printAddresses("Intersection list started both Address c1", c1);
			printAddresses("Intersection list started both Address c2", c2);
			if(c1==c2)
			{
				return c1;
			}
			c1=c1.next;
			c2=c2.next;
		}
		return null;
	}
	
	private static int getSize(Node l1) {
		Node curr = l1;
		int count=0;
		while(curr!=null) {
			count++; curr=curr.next;
		}
		return count;
	}
	
	public static void printInReverseOrder(Node node)
	{
		if(node==null)
		{
			return;
		}
		printInReverseOrder(node.next);
		System.out.print(node.val + "->");
		
	}
	public static void printList(Node node)
	{
		while(node!=null)
		{
			System.out.print(node.val + "->");
			node = node.next;
		}
		System.out.print("null"); 
		System.out.println();
	}
	
	public static Node mthElementFromLast(Node firstNode, int m) {
		int count = 0;
		Node curr = firstNode;
		while(curr!=null && count <m)
		{
			curr = curr.next;
			count++;
		}
		
		Node backPointer = firstNode;
		while(curr!=null)
		{
			curr = curr.next;
			backPointer = backPointer.next;
		}
		return backPointer;
	}
	public static Node detectNodeForCycle(Node firstNode) {
		Node fast = firstNode;
		Node slow = firstNode;
		boolean cycleDetected = true;
		while(true) {
			if(fast==null || fast.next==null) {
				cycleDetected = false;
				break;
			}
			fast = fast.next.next;
			slow = slow.next;
			printAddresses("Address fast", fast);
			printAddresses("Address slow", slow);
			if(fast==slow) {

				break;
			}
			
		}
		if(cycleDetected)
		{
			slow = firstNode;
			while(fast!=slow) {
				fast = fast.next;
				slow = slow.next;
			}
			System.out.println("Cycle Detect at Node value "+fast.getVal());
			return fast;
		}
		else {
			System.out.println("No Cycle");
			return null;
		}
		
	}
	public static Node reverseInGroupOfK(Node root, int k) {
		Node curr = root;
		Node prev = null;
		int count =0;
		while(curr!=null && count<k) {
			Node next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
			count++;
		}
		
		if(curr!=null) {
			root.next = reverseInGroupOfK(curr, k);
		}
		return prev;
	}
	
	// reverse a linked list;
	public static void reverseList(Node<Integer> header) {
		Node<Integer> prev=null,curr;
		curr = header.next;
		while(curr!=null) {
			Node<Integer> next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		header.next = prev;
	}
	
	public static Node getLastNode(Node header)
	{
		Node curr = header.next;
		while(curr.next!=null)
		{
			curr = curr.next;
		}
		return curr;
	}
	
	// reverse a linked list;
	public static void reverseListRecursion(Node<Integer> header,String type) {
		if("tail".equalsIgnoreCase(type))
		{
			header.next = reverseListUsingTailRecursion(header.next,null);
		}
		else{
			header.next = reverseListUsingRecursion(header.next);
		}
	}
	
	public static Node<Integer> reverseListUsingRecursion(Node<Integer> node) {
		// termination of recursion here
		if(node==null||node.next==null)
		{
			return node;
		}
		
		Node<Integer> first = node;
		Node<Integer> rest = node.next;
		
		Node<Integer> lastNode = reverseListUsingRecursion(rest);
		
		rest.next = first;
		first.next = null;
		
		return lastNode;
	}
	
	public static Node<Integer> reverseListUsingTailRecursion(Node<Integer> curr, Node<Integer> prev) {
		if(curr.next==null)
		{
			curr.next = prev;
			return curr;
		}
		
		// same as whole loop version
		Node<Integer> next = curr.next;
		curr.next = prev;
		prev = curr;
		
		// last statement is recursion so it is tail recursion, no stack overflow exception ever
		return reverseListUsingTailRecursion(next,curr);
	}
	
	
	// tail recursion for factorial
	static int fact(int n) {
		if(n==1) {
			return 1;
		}
		
		return n*fact(n-1);
	}
	
	// tail recursion for factorial
	static int fact(int n, int p) {
		if(n==1) {
			return p;
		}
		
		return fact(n-1,p*n);
	}
	
	public static void printAddresses(String label, Object... objects) {
	    System.out.print(label + ": 0x");
	    long last = 0;
	    int offset = unsafe.arrayBaseOffset(objects.getClass());
	    int scale = unsafe.arrayIndexScale(objects.getClass());
	    switch (scale) {
	    case 4:
	        long factor = is64bit ? 8 : 1;
	        final long i1 = (unsafe.getInt(objects, offset) & 0xFFFFFFFFL) * factor;
	        System.out.print(Long.toHexString(i1));
	        last = i1;
	        for (int i = 1; i < objects.length; i++) {
	            final long i2 = (unsafe.getInt(objects, offset + i * 4) & 0xFFFFFFFFL) * factor;
	            if (i2 > last)
	                System.out.print(", +" + Long.toHexString(i2 - last));
	            else
	                System.out.print(", -" + Long.toHexString( last - i2));
	            last = i2;
	        }
	        break;
	    case 8:
	        throw new AssertionError("Not supported");
	    }
	    System.out.println();
	}
	
	private static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}

























class SinglyinkedList <E extends Comparable<? super E>>{
	private transient Node<E> head = new Node<E>(null);
	
	public Node<E> getHeader() {
		return head;
	}
	
	public void add(E elmenet) {
		Node n = new Node(elmenet);
		Node cur = head;
		while(cur.next!=null) {
			cur =cur.next;
		}
		cur.next = n;
	}
	
	public void printLinkedList() {
		Node<E> cur = head.next;
		while(cur!=null)
		{
			System.out.print(cur.val +"->");
			cur = cur.next;
		}
		System.out.print("null");
		System.out.println();
	}

	public <T,U> T[] toArray(T[] a) {
		return (T[])java.lang.reflect.Array.newInstance(
						a.getClass().getComponentType(), 10);
	}
	
	static class Node<E extends Comparable<? super E>>{
		E val;
		Node<E> next;
		
		public E getVal() {
			return val;
		}
		public void setVal(E val) {
			this.val = val;
		}
		public Node<E> getNext() {
			return next;
		}
		public void setNext(Node<E> next) {
			this.next = next;
		}
		Node(E val){
			this.val = val;
		}
	}
}
