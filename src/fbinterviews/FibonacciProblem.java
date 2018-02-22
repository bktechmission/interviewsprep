package fbinterviews;

public class FibonacciProblem {
	public static void main(String[] args)
	{
		int n = 90;		//---> 100th Fibonacci number is 354224848179261915075 
		
		long start =  System.currentTimeMillis();
		long num = findNthFibonacciNumberWithLenearTime(n);
		long end = System.currentTimeMillis();
		System.out.println("time took in secs: " + (end-start)/1000 + ", number is " + num);
		

		start =  System.currentTimeMillis();
		num = findNthFibonacciNumberWithExponentialTime(n);
		end = System.currentTimeMillis();
		System.out.println("time took in secs: " + (end-start)/1000 + ", number is " + num);
		
		
	}
	
	// f(n) = f(n-1) + f(n-2)   O(2^n)  exponential
	public static long findNthFibonacciNumberWithExponentialTime(int n)
	{
		if(n==1) return 0;
		if(n==2) return 1;
		return findNthFibonacciNumberWithExponentialTime(n-1) + findNthFibonacciNumberWithExponentialTime(n-2);
		
	}
	// Iterative way : temp = a+b; a= b; b=temp;    O(n)
	public static long findNthFibonacciNumberWithLenearTime(int n)
	{
		long a = 0;
		long b = 1;
		if(n==1) return a;
		if(n==2) return b;
		
		int count = 2;
		while(count<n)
		{
			long temp = a+b;
			a = b;
			b = temp;
			count++;
		}
		
		return b;
			
	}
	
	
}
