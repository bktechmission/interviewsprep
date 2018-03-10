package linkedlists;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class LinkedListProblems {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//linkedListExample();
		//addTwoNumbersExample();
		mergeKSortedList();
	}
	
	static void linkedListExample(){
		MyLinkedList list1 = new MyLinkedList();
		list1.addAtFront(34);
		list1.addAtFront(23);
		list1.addAtFront(45);
		list1.addAtFront(21);
		list1.addAtFront(12);
		list1.addAtFront(50);
		list1.addAtFront(29);
		
		MyLinkedList list2 = new MyLinkedList();
		list2.addAtFront(32);
		list2.addAtFront(21);
		list2.addAtFront(70);
		list2.addAtFront(59);
		list2.addAtFront(34);
		list2.addAtFront(220);
		list2.addAtFront(92);
		
		
		list1.head = mergeSort(list1.head);
		list2.head = insertionSort(list2.head);
		
		printLinkedList(list1.head);
		printLinkedList(list2.head);
		
		ListNode merged = merge(list1.head, list2.head);
		printLinkedList(merged);
		
		ListNode reversed = reverseList(merged);
		printLinkedList(reversed);
		
		reversed = reverseListRec(reversed);
		printLinkedList(reversed);
		
		reversed = reverseListTailRec(reversed,null);
		printLinkedList(reversed);
		
		ListNode swappedList  = swapNodesInPair(reversed);
		printLinkedList(swappedList);
	}
	
	static void mergeKSortedList() {
		List<ListNode> list = new ArrayList<ListNode>();
		int k = 10;
		Random rand = new Random();
		for(int i=0;i<k;i++) {
			int n = rand.nextInt(30);
			int iter = 0;
			MyLinkedList mylist = new MyLinkedList();
			while(iter!=n) {
				int num = rand.nextInt(500);
				mylist.addAtFront(num);
				iter++;
			}
			
			mylist.head = mergeSort(mylist.head);
			list.add(mylist.head);
		}
		
		for(int i = 0;i<list.size();i++) {
			printLinkedList(list.get(i));
		}
		
		
		//ListNode mergedKLists =  mergeKListsWithNK2(list);
		//ListNode mergedKLists = mergeKListsWithPriorityQueue(list);
		ListNode mergedKLists = mergeKListsWithLeftRightPick(list);
		printLinkedList(mergedKLists);
	}
	
	
	// o(n k^2)     2n+3n+4n+...+kn  =  n(2+3+4+5..+k)  = nk^2
	static ListNode mergeKListsWithNK2(List<ListNode> list) {
		ListNode result = list.get(0);
		for(int i=1;i<list.size();i++) {
			result = merge(result, list.get(i));
		}
		return result;
	}
	
	
	//  O(nklogk)   k(n).logk   BUT space used is O(K) for priority Queue
	static ListNode mergeKListsWithPriorityQueue(List<ListNode> list) {
		Queue<ListNode> pq = new PriorityQueue<>(list.size(),listNodeComparator);		// min heap priority queue
		
		for(int i=0;i<list.size();i++) {
			ListNode node = list.get(i);
			if(node!=null) {
				pq.offer(node);
			}
		}
		
		// we have each k lists first node now in minimum priority queue
		// just take element out and add to final output and then add its next node if not null
		ListNode resultHolder = new ListNode(0);
		ListNode cur = resultHolder;
		while(!pq.isEmpty()) {
			ListNode node = pq.poll();
			if(node.next!=null) {
				pq.offer(node.next);
			}
			cur.next = node;
			cur = cur.next;
		}
		return resultHolder.next;
	}
	
	
	// O(nklogk)  no space in place
	static ListNode mergeKListsWithLeftRightPick(List<ListNode> list) {
		int left = 0;
		int right = list.size()-1;
		while(right!=0) {
			left=0;
			while(left<right) {
				ListNode mergedList = merge(list.get(left), list.get(right));
				list.set(left, mergedList);
				left++;
				right--;
			}
		}
		return list.get(0);
	}
	
	
	private static final Comparator<ListNode> listNodeComparator = new Comparator<ListNode>() {
		@Override
		public int compare(ListNode n1, ListNode n2) {
			return n1.data-n2.data;
		}
	};
	
	//      1->2->3->4  
	// prev p->q->r        prev.next=q  q.next=p  p.next=r   prev=p,  p=r  2->1->3->4->
	static ListNode swapNodesInPair(ListNode head) {
		
		if(head==null||head.next==null) {
			return head;
		}
		
		ListNode dummyNode =  new ListNode(0);
		ListNode prev = dummyNode;
		dummyNode.next = head;
		
		ListNode p = head;
		while(p!=null&&p.next!=null) {
			ListNode q = p.next; 
			ListNode r = p.next.next;
			
			prev.next = q;
			q.next = p;
			p.next = r;
			
			prev = p;
			p = r;
			
		}
		return dummyNode.next;
	}
	
	static void addTwoNumbersExample() {
		MyLinkedList list1 = new MyLinkedList();
		list1.addAtFront(3);
		list1.addAtFront(4);
		list1.addAtFront(2);
		
		MyLinkedList list2 = new MyLinkedList();
		list2.addAtFront(4);
		list2.addAtFront(6);
		list2.addAtFront(5);
		
		printLinkedList(list1.head);
		printLinkedList(list2.head);
		
		ListNode addedList = addTwoLists(list1.head,list2.head);
		printLinkedList(addedList);
	}
	
	static ListNode addTwoLists(ListNode l1, ListNode l2) {
		int carry = 0;
		ListNode dummyNode = new ListNode(-1);
		ListNode cur = dummyNode;
		while(l1!=null||l2!=null) {
			int x = l1!=null?l1.data:0;
			int y = l2!=null?l2.data:0;
			int sum = x+y+carry;
			int digit = sum%10;
			carry = sum/10;
			ListNode node = new ListNode(digit);
			cur.next = node;
			cur = cur.next;
			if(l1!=null) {
				l1 = l1.next;
			}
			if(l2!=null) {
				l2=l2.next;
			}
		}
		return dummyNode.next;
	}
	
	static ListNode reverseList(ListNode head) {
		if(head==null||head.next==null) {
			return head;
		}
		
		ListNode cur = head;
		ListNode prev = null;
		
		while(cur!=null) {
			ListNode next = cur.next;
			cur.next = prev;
			prev = cur;
			cur = next;
		}
		return prev;
		
	}
	
	static ListNode reverseListRec(ListNode head) {
		if(head.next==null) {
			return head;
		}
		
		ListNode front = head;
		ListNode rest = head.next;
		ListNode ret = reverseListRec(rest);
		rest.next = front;
		front.next = null;
		return ret;
	}
	
	static ListNode reverseListTailRec(ListNode head, ListNode prev) {
		if(head.next==null) {
			head.next = prev;
			return head;
		}
		
		ListNode cur = head;
		ListNode next = head.next;
		cur.next = prev;
		return reverseListTailRec(next,cur);
	}
	
	
	static void printLinkedList(ListNode head) {
		ListNode cur = head;
		System.out.print("\n{");
		while(cur!=null) {
			System.out.print(cur.data+"->");
			cur = cur.next;
		}
		System.out.print("}");
	}
	
	
	static ListNode mergeSort(ListNode head) {
		if(head==null||head.next==null) {
			return head;
		}
		
		ListNode midPoint = frontBackSplit(head);
		ListNode left = head;
		ListNode right = midPoint.next;
		midPoint.next = null;
		
		left = mergeSort(left);
		right = mergeSort(right);
		
		return merge(left,right);
	}
	
	static ListNode frontBackSplit(ListNode head) {
		ListNode slow = head;
		ListNode fast = head.next;
		while(fast!=null&&fast.next!=null) {
			slow= slow.next;
			fast = fast.next.next;
		}
		return slow;
	}
	
	static ListNode insertionSort(ListNode head) {
		if(head==null|head.next==null) {
			return head;
		}
		
		ListNode cur = head;
		ListNode result = cur;
		cur = cur.next;
		result.next = null;
		
		while(cur!=null) {
			ListNode next = cur.next;
			result = insertInSortedList(result,cur);
			cur = next;
		}
		return result;
	}
	
	static ListNode insertInSortedList(ListNode result, ListNode node) {
		ListNode dummyNode = new ListNode(-1);
		ListNode cur = dummyNode;
		cur.next = result;
		while(cur.next!=null && cur.next.data<node.data) {
			cur = cur.next;
		}
		node.next = cur.next;
		cur.next = node;
		return dummyNode.next;
	}

	static ListNode merge(ListNode l1, ListNode l2) {
		
		ListNode dummyNode = new ListNode(-1);
		ListNode cur = dummyNode;
		while(l1!=null&&l2!=null) {
			if(l1.data<l2.data) {
				cur.next = l1;
				l1=l1.next;
			}
			else if(l1.data>l2.data) {
				cur.next = l2;
				l2=l2.next;
			}
			else {
				cur.next = l1;
				l1 = l1.next;
				l2 = l2.next;
			}
			cur = cur.next;
		}
		if(l1!=null) {
			cur.next = l1;
		}
		if(l2!=null) {
			cur.next = l2;
		}
		return dummyNode.next;
	}
	
	static class ListNode{
		int data;
		ListNode next;
		ListNode(int data){
			this.data = data;
		}
	}

	static class MyLinkedList{
		ListNode head;
		ListNode tail;

		void addAtFront(int data){
			ListNode node = new ListNode(data);
			node.next = head;
			if(head==null) {
				head = tail = node;
			}
			else {
				head = node;
			}
		}
		
		void addAtBack(int data) {
			ListNode node = new ListNode(data);
			if(head==null) {
				head = tail = node;
			}
			else {
				tail.next = node;
			}
		}
	}
	
}


