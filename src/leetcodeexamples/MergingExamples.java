package leetcodeexamples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Random;


public class MergingExamples {
	public static void main(String[] args) {
		//mergeKLinkedListExample(15);
		
		//mergeSortExample();
		quickSortExample();
		//heapSortExample();
		//insertionSortExample();
	}
	
	static void insertionSortExample() {
		int[] a = new Random().ints(20,30,200).toArray();
		System.out.println("Before insertion sort: ");
		System.out.println(Arrays.toString(a));
		insertionSort(a);
		System.out.println("isSortedOverall: "+isSortedIncreasingOrder(a));
		System.out.println(Arrays.toString(a));
	}
	
	public static void insertionSort(int[]a) {
		for(int i=1;i<a.length;i++) {
			int j=i;
			while(j>0) {
				if(a[j]<a[j-1]) {
					swap(a,j,j-1);
				}
				j--;
			}
		}
	}
	
	static void heapSortExample() {
		int[] a = new Random().ints(20,30,200).toArray();
		System.out.println("Before heap sort: ");
		System.out.println(Arrays.toString(a));
		heapSort(a);
		System.out.println("isSortedOverall: "+isSortedIncreasingOrder(a));
		System.out.println(Arrays.toString(a));
	}
	
	static void heapSort(int[]a) {
		maxheapify(a);		// O(n)
		// now take an element from top and replace at the end and do sinkdown
		for(int i=a.length-1;i>=0;i--) {
			int temp = a[i];
			a[i] = a[0];
			a[0] = temp;
			sinkDown(a,0,i);
		}
		
	}
	
	static void maxheapify(int[]a) {
		for(int i=a.length/2-1;i>=0;i--) {
			sinkDown(a,i, a.length);
		}
	}
	
	// adjust the pos element
	static void sinkDown(int[]a, int pos, int size) {
		while(pos<size/2) {
			int leftChild = 2*pos+1;
			int rightChild = 2*pos+2;
			int selectedChild = leftChild;
			// select the max among 2 child, but first check if right child exists
			if(rightChild<size&&a[rightChild]>a[leftChild]) {
				selectedChild = rightChild;
			}
			
			if(a[pos]>a[selectedChild]) {
				break;
			}
			
			swap(a,pos,selectedChild);
			pos=selectedChild;
		}
	}
	
	static void quickSortExample(){
		int[] a = new Random().ints(20,30,200).toArray();
		System.out.println("Before quick sort: ");
		System.out.println(Arrays.toString(a));
		quickSortArray(a);
		System.out.println("isSortedOverall: "+isSortedIncreasingOrder(a));
		System.out.println(Arrays.toString(a));
	}
	
	static void mergeSortExample() {
		Random rand = new Random();
		int[] a = new int[50];
		int[] temp = rand.ints(20,30,200).toArray();
		mergeSortAnArray(temp);
		System.out.println(Arrays.toString(temp));
		for(int i=0;i<temp.length;i++) {
			a[i] = temp[i];
		}
		
		int[] b =  rand.ints(10,30,200).toArray();
		mergeSortAnArray(b);
		System.out.println(Arrays.toString(b));
		
		mergeSort(a,b,temp.length);
		System.out.println(Arrays.toString(a));
	
		
	}
	
	public static boolean isSortedIncreasingOrder(int[]a) {
		for(int i=0;i<a.length-1;i++) {
			if(a[i]>a[i+1]) {
				System.out.println("failed at "+i);
				return false;
			}
		}
		return true;
	}
	
	
	
	static void quickSortArray(int[] a) {
		quicksorting(a,0,a.length-1);
	}
	
	static void quicksorting(int[]a, int low, int high) {
		if(low<high) {
			int pivotIndex = partition(a,low,high);
			quicksorting(a,low,pivotIndex-1);
			quicksorting(a,pivotIndex+1,high);
		}
	}
	
	private static int partition(int[] a, int start, int end) {
		Random rand = new Random();
		int randIndex = rand.nextInt(end-start+1)+start;
		swap(a,randIndex,end);
		
		int pivot = a[end];
		
		int right = end-1;
		int left = start;
		while(left<=right) {
			while(left<=right&&a[left]<pivot) {
				left++;
			}
			while(left<=right&&a[right]>=pivot) {
				right--;
			}
			if(left<right) {
				swap(a,left,right);
			}
			
		}
		swap(a,left,end);
		return left;
	}
	
	private static void swap(int[]a, int i,int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	static void mergeSortAnArray(int[] a){
		mergeSort(a,0,a.length-1);
	}
	
	static void mergeSort(int[]a, int low, int high) {
		if(low<high) {
			int mid = (low+high)/2;
			mergeSort(a,low, mid);
			mergeSort(a,mid+1, high);
			merge(a,low,mid,high);
		}
	}
	
	static void merge(int[]a, int low, int mid, int high) {
		int len = high-low+1;
		int[] temp = new int[len];
		
		int k=0;
		int i=low;
		int j=mid+1;
		
		while(i<=mid&&j<=high) {
			if(a[i]<a[j]) {
				temp[k++] = a[i++];
			}
			else if(a[i]>a[j]) {
				temp[k++] = a[j++];
			}else {
				temp[k++] = a[i++];
				j++;
			}
		}
		
		while(i<=mid) {
			temp[k++] = a[i++];
		}
		while(j<=high) {
			temp[k++] = a[j++];
		}
		
		int l=low;
		for(int p=0;p<len;p++) {
			a[l++] = temp[p];
		}
	}
	
	// a is large enough to hold combine data
	static void mergeSort(int[]a,int[]b, int asize){
		
		int leftMostStart = asize+b.length-1;
		int astart=asize-1;
		int bstart=b.length-1;
		
		while(astart>=0 && bstart>=0) {
			System.out.println("astrta bstart " +astart + "  "+ bstart);
			if(a[astart]>b[bstart]) {
				a[leftMostStart--] = a[astart--];
			}else {
				a[leftMostStart--] = b[bstart--];
			}
		}
		
		while(bstart>=0) {
			a[leftMostStart--] = b[bstart--];
		}
	}
	
	// merge k linked lists
	public static void mergeKLinkedListExample(int numberOfLinkedLists) {
		Random rand = new Random();
		Random randSize = new Random();
		
		List<Node> lists = new ArrayList<>(Collections.nCopies(numberOfLinkedLists, null));
		int[] idx = { 0 };
		for(int i=0;i<numberOfLinkedLists;i++) {
			rand.ints(randSize.nextInt(10),20,200).forEach(x->{
				lists.set(idx[0], insert(lists.get(idx[0]),x));
			});

			lists.set(i, insertionSort(lists.get(i)));
			idx[0]++;
		}
		
		lists.forEach(x->{
			printLinkedList(x);
		});
		
		System.out.println("\n\nmerged k lists:->");
		printLinkedList(mergeKListsWithInPlace(lists));
	}
	
	public static Node mergeKListsNkSquares(List<Node> lists) {
		if(lists.size()<=1) {
			return lists.get(0);
		}
		
		Node result = null;
		for(Node l:lists) {
			result = merge(result, l);
		}
		return result;
	}
	// O(nklogk)   space  O(k)
	public static Node mergeKListsUsingPriorityQueue(List<Node> lists) {
		if(lists.size()<=1) {
			return lists.get(0);
		}
		
		Queue<Node> pq = new java.util.PriorityQueue<>(lists.size(),(e1,e2)->e1.val-e2.val);
		
		for(Node n:lists) {
			if(n!=null) {
				pq.offer(n);
			}
		}
		
		Node dummyNode = new Node(-1);
		Node cur = dummyNode;
		while(!pq.isEmpty()) {
			Node pqNode = pq.poll();
			cur.next=pqNode;
			if(pqNode.next!=null) {
				pq.offer(pqNode.next);
			}
			cur = cur.next;
			cur.next = null;
		}
		
		return dummyNode.next;
	}
	
	
	// in place nklogk
	public static Node mergeKListsWithInPlace(List<Node> lists) {
		if(lists.size()<=1) {
			return lists.get(0);
		}
		
		int low = 0;
		int high = lists.size()-1;
		while(high!=0) {
			while(low<high) {
				lists.set(low, merge(lists.get(low),lists.get(high)));
				low++; high--;
			}
			low = 0;
		}
		return lists.get(0);
	}
	
	static Node mergeSort(Node head){
		if(head==null||head.next==null) {
			return head;
		}
		
		Node slow = findSplitMidNode(head);
		Node left = head;
		Node right = slow.next;
		slow.next = null;
		
		left = mergeSort(left);
		right = mergeSort(right);
		
		return merge(left,right);
	}
	
	static void printLinkedList(Node head) {
		Node cur = head;
		System.out.print("\nList is :-> [");
		while(cur!=null) {
			System.out.print(cur.val+"->");
			cur = cur.next;
		}
		System.out.print("null]");
	}
	
	static Node findSplitMidNode(Node head) {
		if(head==null || head.next==null) {
			return head;
		}
		
		Node slow=head, fast=head.next;
		
		while(fast!=null && fast.next!=null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		
		return slow;
	}
	
	static Node merge(Node l1, Node l2) {
		if(l1==null) {
			return l2;
		}
		if(l2==null) {
			return l1;
		}
		Node dummynode = new Node(-1);
		Node cur = dummynode;
		while(l1!=null && l2!=null) {
			if(l1.val<l2.val) {
				cur.next = l1;
				l1 = l1.next;
			}
			else if(l1.val>l2.val){
				cur.next = l2;
				l2 = l2.next;
			}
			else {
				cur.next = l1;
				l1=l1.next;
				l2=l2.next;
			}
			cur = cur.next;
		}
		
		if(l1!=null){
			cur.next = l1;
		}
		if(l2!=null){
			cur.next = l2;
		}
		
		return dummynode.next;
	}
	
	static Node insertionSort(Node head) {
		if(head==null||head.next==null) {
			return head;
		}
		
		Node cur = head.next;
		Node result = head;
		result.next = null;
		
		while(cur!=null) {
			Node next = cur.next;
			result = insertInSortedList(result,cur);
			cur = next;
		}
		
		return result;
	}
	
	static Node insertInSortedList(Node head, Node n) {
		Node dummyNode = new Node(-1);
		dummyNode.next = head;
		Node cur = dummyNode;
		while(cur.next!=null && cur.next.val<n.val) {
			cur = cur.next;
		}
		n.next = cur.next;
		cur.next = n;
		return dummyNode.next;
	}
	
	static Node insert(Node head, int val){
		if(head==null) {
			head=new Node(val);
		}
		else {
			Node newNode = new Node(val);
			newNode.next = head;
			head = newNode;
		}
		return head;
	}
	
	static class Node{
		int val;
		Node next;
		Node(int val){
			this.val = val;
		}
		
		public String String() {
			return String.valueOf(val);
		}
	}
	
}
