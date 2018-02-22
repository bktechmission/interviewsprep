package leetcodeexamples;

import java.util.Random;

public class SinglyLinkedList {

	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		
		new Random().ints(1, 400).limit(10).forEach(x->add(new Node(x,null),list.header));
		printLinkedList(list.header);
		
		
	}
	
	static Node reverseLinkedList(Node node) {
		if(node.next!=null)
		{
			return node;
		}
		
		Node first = node;
		Node rest = node.next;
		Node lastNode = reverseLinkedList(rest);
		rest.next = first;
		first.next = null;
		return lastNode;
	}
	
	static class Node{
		int val;
		Node next;
		Node(int val, Node next){
			this.val = val;
			this.next = next;
		}
	}
	
	static void printLinkedList(Node header) {
		Node curr = header.next;
		while(curr!=null)
		{
			System.out.print(curr.val + "->");
			curr = curr.next;
		}
		System.out.print("null");
	}
	
	static class LinkedList{
		Node header = new Node(-1,null);
	}
	
	static void add(Node node, Node header)
	{
		Node curr = header;
		while(curr.next!=null)
		{
			curr = curr.next;
		}
		curr.next = node;
	}
}
