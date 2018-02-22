package interviewsprep;

import java.util.LinkedList;
import java.util.Queue;

public class StackUsingQueue<T>{
	private Queue<T> newQueue;
	
	public StackUsingQueue()
	{
		newQueue = new LinkedList<T>();
	}
	
	public boolean push(T ele)
	{
		boolean ret = true;
		try
		{
			newQueue.add(ele);
		}
		catch(Exception e)
		{
			ret = false;
		}
		return ret;
		
	}
	
	
	public T pop()
	{
		T element = null;
		Queue<T> oldQueue = new LinkedList<T>();
		if(!newQueue.isEmpty())
		{
			int count  = 0;
			int length = newQueue.size();
			while(!newQueue.isEmpty())
			{ 	count++;
				if(count == length)
				{
					element = newQueue.remove();
				}
				else
				{
					oldQueue.add(newQueue.remove());
				}
				
			}
			newQueue = oldQueue;
		}
		
		return element;
	}
	
	public boolean empty()
	{
		return newQueue.size() == 0;
	}
	
	public int size()
	{
		return newQueue.size();
	}
	
	public static void main(String[] args)
	{
		StackUsingQueue<Integer> stack = new StackUsingQueue<Integer>();
		System.out.println(stack.empty());
		
		stack.push(1);
		stack.push(2);
		System.out.println(stack.pop());
		stack.push(3);
		stack.push(100);
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
	}
	
}
