package interviewsprep;

import java.util.Stack;

public class QueueUsingStack<T> {
	private Stack<T> oldStack;
	private Stack<T> newStack;
	
	public QueueUsingStack()
	{
		oldStack = new Stack<T>();
		newStack = new Stack<T>();
	}
	
	public boolean enqueue(T element)
	{
		boolean ret = true;
		try
		{
			newStack.push(element);
		}
		catch(Exception e)
		{
			ret = false;
			System.out.println("Error Occurred");
		}
		return ret;
	}
	
	public T dequeue()
	{
		T topElement = null;
		if(oldStack.empty())
		{
			// data transfer from new to old first
			while(!newStack.empty())
			{
				oldStack.push(newStack.pop());
			}
		}
		if(!oldStack.empty())
		{
			topElement = oldStack.pop();
		}
		return topElement;
	}
	
	public boolean empty()
	{
		if(newStack.empty() && oldStack.empty())
		{
			return true;
		}
		return false;
	}
	
	public int size()
	{
		return newStack.size() + oldStack.size();
	}
	
	public static void main(String[] args)
	{
		QueueUsingStack<Integer> queue = new QueueUsingStack<Integer>();
		System.out.println(queue.size());
		
		queue.enqueue(1);
		queue.enqueue(2);
		System.out.println(queue.dequeue());
		System.out.println(queue.dequeue());
		System.out.println(queue.dequeue());
	}
}
