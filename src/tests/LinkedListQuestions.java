package tests;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class LinkedListQuestions {
	static class ListNode {
		int data;
		ListNode next, arbiter;
		ListNode(int data)
		{
			this.data = data;
		}
	}
	
	public static void main(String[] args)
	{
		ListNode head = generatePalindromeList();
		printMyLinkedList(head);
		System.out.println("\n"+isPalindromeInPlace(head));
		
		System.out.println("Before rotation: ");
		printMyLinkedList(head);
		head = rotateList(head,3);
		System.out.println("\nafter rotation: ");
		printMyLinkedList(head);
		head = generateOddLinkedListForMe();
		printMyLinkedList(head);
		head = randomMerge2Lists(head);
		printMyLinkedList(head);
		head = generateOddLinkedListForMe();
		printMyLinkedList(head);
		zigzagList(head);
		printMyLinkedList(head);
		
		head = generateOddLinkedListForMe();
		printMyLinkedList(head);
		rearrangeListWithOddTogetherAndEvenTogether(head);
		printMyLinkedList(head);
		
		ListNode b1=generateLinkedListForMe();
		printMyLinkedList(b1);
		b1=mergeSortList(b1);
		printMyLinkedList(b1);
		
		ListNode l1=new ListNode(2);
		ListNode h1=l1;
		l1.next = new ListNode(4);
		l1=l1.next;
		l1.next=new ListNode(3);
		l1=l1.next;
		
		ListNode l2=new ListNode(5);
		ListNode h2=l2;
		l2.next = new ListNode(9);
		l2=l2.next;
		l2.next=new ListNode(9);
		l2=l2.next;
		
		h2=addTwoLinkedLists(h1,h2);
		printMyLinkedList(h2);
		
		h2=swapNodesInPair(h2);
		printMyLinkedList(h2);
		
	}
	
	static void printMyLinkedList(ListNode head)
	{
		ListNode cur = head;
		System.out.println();
		while(cur!=null)
		{
			System.out.print(cur.data+"->");
			cur = cur.next;
		}
		System.out.print("null");
		System.out.println();
	}
	
	static ListNode rotateList(ListNode head, int k)
	{
		if(head==null || head.next==null)
			return head;
		
		ListNode tail = head;
		int len = 1;
		while(tail.next!=null)
		{
			tail = tail.next;
			len++;
		}
		tail.next = head;
		k=k%len;
		for(int i=0;i<len-k;i++)
		{
			head = head.next;
			tail= tail.next;
		}
		
		tail.next=null;
		return head;
		
	}
	
	static ListNode generateLinkedListForMe()
	{
		Random rd = new Random();
		ListNode returnHeadNode = null;
		ListNode node = new ListNode(rd.nextInt(100));
		returnHeadNode = node;
		for(int i=0;i<10;i++)
		{
			node.next = new ListNode(rd.nextInt(100));
			node = node.next;
		}
		return returnHeadNode;
	}
	
	static ListNode generatePalindromeList()
	{
		ListNode returnHeadNode = null;
		ListNode node = new ListNode(1);
		returnHeadNode = node;
		node.next = new ListNode(2);
		node = node.next;
		
		node.next = new ListNode(3);
		node = node.next;
		
		node.next = new ListNode(4);
		node = node.next;
		
		node.next = new ListNode(5);
		node = node.next;

		
		node.next = new ListNode(4);
		node = node.next;
		
		node.next = new ListNode(3);
		node = node.next;
		
		node.next = new ListNode(2);
		node = node.next;
		
		node.next = new ListNode(1);
		node = node.next;
		
		return returnHeadNode;
		
	}
	
	static ListNode generateEvenLinkedListForMe()
	{
		ListNode node = new ListNode(2);
		ListNode returnHeadNode = null;
		returnHeadNode = node;
		for(int i=4;i<30;i=i+2)
		{
			node.next = new ListNode(i);
			node = node.next;
		}
		return returnHeadNode;
	}
	
	static ListNode generateOddLinkedListForMe()
	{
		ListNode node = new ListNode(3);
		ListNode returnHeadNode = null;
		returnHeadNode = node;
		for(int i=5;i<30;i=i+2)
		{
			node.next = new ListNode(i);
			node = node.next;
		}
		return returnHeadNode;
	}

	static boolean isPalindromeList(ListNode head)
	{
		ListNode cur = head;
		Stack<Integer> s1 =  new Stack<Integer>();
		
		int len=0;
		while(cur!=null)
		{
			len++;
			cur=cur.next;
		}
		
		cur = head;
		int mid = len/2;
		int count = 0;
		while(cur!=null&&count!=mid)
		{
			s1.push(cur.data);
			cur = cur.next;
			count++;
		}
		if(len%2!=0)
			cur=cur.next;
		
		ListNode ahead = cur;
		while(ahead!=null)
		{
			int data = s1.pop();
			if(data!=ahead.data)
				return false;
			ahead = ahead.next;
		}
		return true;
	}
	
	static boolean isPalindromeInPlace(ListNode head)
	{
		if(head==null || head.next==null)
			return true;
		
		ListNode slow = head;
		ListNode fast = head;
		
		while(fast!=null && fast.next!=null && fast.next.next!=null)
		{
			slow=slow.next;
			fast = fast.next.next;
		}
		// slow always point to either mid or mid+1 for odd numbers
		
		ListNode s1 = slow.next;
		s1=reverseListIterative(s1);
		
		slow = head;
		ListNode tempHolder = s1;
		while(s1!=null)
		{
			if(slow.data!=s1.data)
			{
				reverseListIterative(tempHolder);
				return false;
				
			}
			slow=slow.next;
			s1 = s1.next;
		}
		reverseListIterative(tempHolder);
		return true;
	}
	
	static ListNode reverseListRecursive(ListNode s)
	{
		if(s.next==null)
			return s;
		ListNode ret = reverseListRecursive(s.next);
		s.next.next=s;
		s.next=null;
		return ret;
	}
	
	static ListNode reverseListIterative(ListNode head)
	{
		ListNode prev = null;
		ListNode cur = head;
		while(cur!=null)
		{
			ListNode next = cur.next;
			cur.next = prev;
			prev = cur;
			cur = next;
		}
		return prev;
	}
	
	// List is sorted in increasing order 
	// input 1->2->3->4->5->6->7->null
	// output 7->1->6->2->5->3->4->null
	static ListNode randomMerge2Lists(ListNode head)
	{
		// idea is to travel till mid
		ListNode slow = head;
		ListNode fast = head;
		while(fast!=null&&fast.next!=null)
		{
			slow = slow.next;
			fast = fast.next.next;
		}

		ListNode p1 = head;
		ListNode p2 = slow.next;
		slow.next = null;
		
		p2=reverseListRecursive(p2);
		ListNode ret = p1;
		
		// merge here
		while(p2!=null)
		{
			ListNode temp1 = p1.next;
			ListNode temp2 = p2.next;
			
			p1.next=p2;
			p2.next=temp1;
			
			p1 = temp1;
			p2 = temp2;
		}
		return ret;
	}
	
	
	static void zigzagList(ListNode head)
	{
		if(head==null || head.next==null)
			return;
		boolean flag = true;
		ListNode cur = head;
		while(cur.next!=null)
		{
			if(flag)		// respect the <
			{
				if(cur.data>cur.next.data)
				{
					int temp = cur.data;
					cur.data = cur.next.data;
					cur.next.data = temp;
				}
			}
			else		// respect the >=
			{	
				if(cur.data<cur.next.data)
				{
					int temp = cur.data;
					cur.data = cur.next.data;
					cur.next.data = temp;
				}
			}
			flag=!flag;
			cur = cur.next;
		}
	}
	
	
	static void rearrangeListWithOddTogetherAndEvenTogether(ListNode head)
	{
		if(head==null || head.next==null)
			return;
		ListNode odd = head;
		ListNode even = head.next;
		
		ListNode evenFirst = even;
		while(true)
		{
			if(even.next==null)
			{
				odd.next = evenFirst;
				break;
			}
			
			odd.next=even.next;
			odd = even.next;
			
			
			if(odd.next==null)
			{
				even.next=null;
				odd.next=evenFirst;
				break;
			}
			
			even.next=odd.next;
			even=odd.next;
		}
	}
	
	static void addArbiterNodeMaxAhead(ListNode head)
	{
		if(head==null||head.next==null)
			return;
		ListNode cur = head;
		cur = reverseListRecursive(cur);
		ListNode tempHolder =cur;
		ListNode max = cur;
		cur=cur.next;
		while(cur!=null)
		{
			cur.arbiter=max;
			if(max.data<cur.data)
				max = cur;
			cur = cur.next;
		}
		reverseListRecursive(tempHolder);
	}
	
	static void sortOnAlreadSortedList(ListNode head)
	{
		if(head==null || head.next==null)
			return;
		
		ListNode prev = head;
		ListNode cur = head.next;
		while(cur!=null)
		{
			if(prev.data>cur.data)
			{
				prev.next=cur.next;
				cur.next=head;
				head = cur;
				cur=prev;
			}
			else{
				prev=cur;
			}
			cur=cur.next;
		}
		
	}
	
	static void pushNode(ListNode head, ListNode node)
	{
		node.next=head;
		head=node;
	}
	
	static void removeDuplicates(ListNode head)
	{
		if(head==null|| head.next==null)
			return;
		ListNode cur=head.next;
		ListNode prev=head;
		while(cur!=null)
		{
			ListNode runner=head;
			while(runner!=cur)
			{
				if(runner.data==cur.data)
				{
					prev.next=cur.next;
					break;
				}
				runner = runner.next;
			}
			if(runner==cur)
			{
				prev = cur;
			}
			cur = cur.next;
		}
	}
	
	static void removeDuplicatesHashSet(ListNode head)
	{
		if(head==null || head.next==null)
			return;
		Set<Integer> set = new HashSet<Integer>();
		ListNode cur = head;
		ListNode prev = null;
		while(cur!=null)
		{
			if(set.contains(cur.data))
			{
				prev.next = cur.next;
			}
			else
			{
				set.add(cur.data);
				prev=cur;
			}
			cur=cur.next;
		}
		
	}
	
	static void insertionSort(ListNode head)
	{
		if(head==null || head.next==null)
			return;
		
		ListNode cur = head.next;
		ListNode result = head;
		while(cur!=null)
		{
			ListNode next = cur.next;
			insertOnSort(result,cur);
			cur = next;
		}
	}
	
	static void insertOnSort(ListNode head, ListNode node)
	{
		if(head==null || head.data>node.data)
		{
			node.next=head;
			head=node;
		}
		else
		{
			ListNode cur=head;
			while(cur.next!=null)
			{
				if(cur.next.data>node.data)
				{
					break;
				}
				else
				{
					cur=cur.next;
				}
			}
			node.next = cur.next;
			cur.next=node;
		}
	}
	
	static ListNode mergeSortList(ListNode head)
	{
		if(head==null|| head.next==null)
			return head;
		
		//calculate mid
		ListNode slow=head;
		ListNode fast =head;
		while(fast!=null&&fast.next!=null&&fast.next.next!=null)
		{
			slow=slow.next;
			fast=fast.next.next;
		}
		
		// slow points to mid then new list start
		ListNode l=head;
		ListNode r=slow.next;
		slow.next=null;
		l=mergeSortList(l);
		r=mergeSortList(r);
		return mergeTwoLists(l,r);
	}
	
	
	static ListNode mergeTwoLists(ListNode l1, ListNode l2)
	{
		ListNode dummyNode = new ListNode(0);
		ListNode p = dummyNode;
		while(l1!=null&&l2!=null)
		{
			if(l1.data<l2.data)
			{
				p.next=l1;
				l1=l1.next;
			}
			else if(l1.data>l2.data)
			{
				p.next=l2;
				l2=l2.next;
			}
			else
			{
				p.next=l1;
				l1=l1.next;
				l2=l2.next;
			}
			p=p.next;
		}
		
		if(l1!=null)
			p.next=l1;
		else
			p.next=l2;
		return dummyNode.next;
	}
	
	static ListNode addTwoLinkedLists(ListNode l1, ListNode l2)
	{
		ListNode dummyNode = new ListNode(0);
		ListNode p = dummyNode;
		int carry=0;
		while(l1!=null || l2!=null)
		{
			int x = l1!=null?l1.data:0;
			int y = l2!=null?l2.data:0;
			int sum = carry+x+y;
			carry=sum/10;
			p.next = new ListNode(sum%10);
			p=p.next;
			if(l1!=null)
				l1=l1.next;
			if(l2!=null)
				l2=l2.next;
		}
		if(carry>0)
			p.next=new ListNode(carry);
		return dummyNode.next;
	}
	
	static ListNode swapNodesInPair(ListNode head)
	{
		if(head==null||head.next==null)
			return head;
		ListNode dummyNode = new ListNode(0);
		ListNode prev=dummyNode;
		ListNode p = head;
		while(p!=null&&p.next!=null)
		{
			ListNode q=p.next;
			ListNode r=q.next;
			q.next=p;
			prev.next=q;
			p.next=r;
			prev = p;
			p = r;
		}
		return dummyNode.next;
	}
	
	static final Comparator<ListNode> listComparator = new Comparator<ListNode>(){
		@Override
		public int compare(ListNode o1, ListNode o2) {
			return Integer.compare(o1.data, o2.data);
		}
	};
	
	//nklogK time and O(k) Queue space
	static ListNode mergeKLists(List<ListNode> lists)
	{
		if(lists.isEmpty())
			return null;
		Queue<ListNode> pq = new PriorityQueue<>(lists.size(),listComparator);
		for(ListNode node:lists)
		{
			pq.offer(node);
		}
		
		ListNode dummyNode = new ListNode(0);
		ListNode p = dummyNode;
		while(!pq.isEmpty())
		{
			ListNode node = pq.poll();
			p.next=node;
			p=p.next;
			if(node.next!=null)
				pq.offer(node.next);
		}
		return dummyNode.next;
	}
	
	// O(nKlogK) time and O(1) space
	static ListNode mergerKListsInPlace(List<ListNode> lists)
	{
		if(lists.isEmpty())
			return null;
		int end = lists.size()-1;
		while(end>0)
		{
			int begin =0;
			while(begin<end)
			{
				lists.set(begin, mergeTwoLists(lists.get(begin), lists.get(end)));
				begin++;
				end--;
			}
		}
		return lists.get(0);
	}
	
	static ListNode copyRandomList(ListNode head)
	{
		if(head==null)
			return null;
		Map<ListNode, ListNode> lookup = new HashMap<ListNode, ListNode>();
		
		ListNode p = head;
		
		ListNode dummyNode = new ListNode(0);
		ListNode q = dummyNode;
		
		while(p!=null)
		{
			ListNode copy = new ListNode(p.data);
			lookup.put(p, copy);
			
			q.next=copy;
			
			q=q.next;
			p=p.next;
		}
		
		p=head;
		q=dummyNode;
		while(p!=null)
		{
			ListNode curNodeCopyArbiter = lookup.get(p.arbiter);
			q.next.arbiter=curNodeCopyArbiter;
			
			p=p.next;
			q=q.next;
		}
		return dummyNode.next;
		
	}
	
	static ListNode copyRandomListInPlace(ListNode head)
	{
		if(head==null)
			return null;
		
		ListNode p = head;
		while(p!=null)
		{
			ListNode copy = new ListNode(p.data);
			copy.next=p.next;
			p.next=copy;
			p=copy.next;
		}
		
		p=head;
		while(p!=null)
		{
			p.next.arbiter=p.arbiter!=null?p.arbiter.next:null;
		}
		
		p=head;
		ListNode returnHead = head.next;
		while(p!=null)
		{
			ListNode copy = p.next;
			p.next=copy.next;
			p = copy.next;
			copy.next = p!=null?p.next:null;
		}
		return returnHead;
	}
	
	private Map<Character, Integer> map =
			new HashMap<Character, Integer>() {{
			put('I', 1); put('V', 5); put('X', 10);
			put('L', 50); put('C', 100); put('D', 500);
			put('M', 1000);
			}};
	public int romanToInt(String s) 
	{
		int prev = 0, total = 0;
		for (char c : s.toCharArray()) {
		int curr = map.get(c);
		total += (curr > prev) ? (curr - 2 * prev) : curr;
		prev = curr;
		}
		return total;
	}			
	
	
	private static final int[] values = {
		1000, 900, 500, 400,
		100, 90, 50, 40,
		10, 9, 5, 4,
		1
		};
	private static final String[] symbols = {
		"M", "CM", "D", "CD",
		"C", "XC", "L", "XL",
		"X", "IX", "V", "IV",
		"I"
		};
	public String intToRoman(int num) {
		StringBuilder roman = new StringBuilder();
		int i = 0;
		while (num > 0) 
		{
			int k = num / values[i];
			for (int j = 0; j < k; j++) 
			{
				roman.append(symbols[i]);
				num -= values[i];
			}
			i++;
		}
		return roman.toString();
	}
}
