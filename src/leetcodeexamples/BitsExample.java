package leetcodeexamples;

public class BitsExample {
	public static void main(String[] args) {
		
		int n = 23454443;
		int m = 234;
		
		int i = 13;
		int j = 5;
		printBits(n);
		printBits(m);
		
		int max = ~0;
		int leftMask = ~(1<<i)+1;	// 2's compliment
		int rightMask = (1<<j)+max;
		
		printBits(leftMask);
		printBits(rightMask);
		
		int p = n & (leftMask | rightMask);
		
		printBits(p);
		int mask = (m<<j);
		p = p | mask;
		printBits(p);
		printBits(1<<i);
		
		countsDifferentBitsSwapRequired(n,m);
	}
	
	static void printBits(int n) {
		for(int i =31;i>=0;i--)
		{
			System.out.print((n>>>i) & 1);
		}
		System.out.println();
	}
	
	static boolean isPowerOf2(int n)		// 2^k
	{
		return (n&n-1)==0;	// n&-n==n
	}
	
	static boolean isEven(int n) {
		return (n&1)==0;
	}
	
	static void countsDifferentBitsSwapRequired(int a, int b) {
		int count = 0;
		for(int c=a^b; c!=0; c=c>>1)
		{
			count+=(c&1);
		}
		System.out.println("different bits are "+count);
	}
	
}
