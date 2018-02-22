package fbinterviews;

public class BitManipulations {
	public static void main(String[] args)
	{
		subStringOfBitsProblem();
		System.out.println(isDivisibleBy2(10));
		System.out.println(isDivisibleBy3(17));
		swapOddPlacesBitsWithEvenPlacesBitsProblem(6478309);
	}
	
	// all elements are 2 times except one, return that one element
	public static int findUniqueElementInDuplicates(int[] a)
	{
		int result = 0;
		for(int x:a)
			result^=x;
		return result;
	}
	
	public static void swapOddPlacesBitsWithEvenPlacesBitsProblem(int n)
	{
		System.out.println("before swap bits are:  " + getBitsString(n));
		
		int newNum = ((n&0xaaaaaaaa)>>>1)
				| ((n&0x55555555)<<1);
		System.out.println("after swap bits are:   " + getBitsString(newNum));
	}
	
	public static void subStringOfBitsProblem()
	{
		System.out.println("~0 is : "+ getBitsString(~0));
		System.out.println("-1 is : " + getBitsString(-1));
		
		int n = 6478309;
		System.out.println("n is : " + getBitsString(n));
		
		
		int m = 121;
		System.out.println("m is : " + getBitsString(m));
		
		int endPos = 12;
		int startPos = 6;
		
		// Generate the leftmask
		//                    |||||||
		// 00000000011000101101100111100101
		// 11111111111111111110000000000000
		int leftMask = ~((1<<endPos) + (~0));    //--------->     0000 1000 0000 + 1111 1111 1111 => ~(0000 0111 1111) => 1111 1000 0000
		int rightMask = (1<<startPos) + (~0);	//---------->	  0000 0000 0100 + 1111 1111 1111 => 					  0000 0000 0011
		int totalMask = leftMask | rightMask;	//---------->     1111 1000 0000 | 0000 0000 0011 => 1111 1000 0011
		int newNum = (n&totalMask) | (m<<startPos);
		System.out.println("new num is : " + getBitsString(newNum));
	}
	
	public static boolean isDivisibleBy2(int n)
	{
		boolean ret =false;
		if(n<0) n*=-1;
		ret = ((n&1)==0)?true:false;
		return ret;
	}
	
	// diff between odd places 1s with even places 1
	public static boolean isDivisibleBy3(int n)
	{
		if(n<0) n*=-1;
		// breaking condition for recursion
		if(n==0) return true;
		if(n==1) return false;
		
		int even = 0;
		int odd = 0;
		
		while(n>0)
		{
			if((n&1)==1)
			{
				odd++;
			}
			n>>=1;
			if((n&1)==1)
			{
				even++;
			}
			n>>=1;
		}
		return isDivisibleBy3(Math.abs(odd-even));
	}
	
	public static String getBitsString(int n)
	{
		StringBuilder sb = new StringBuilder();
		for(int i=31;i>=0;i--)
		{
			sb.append((n>>>i)&1);
		}
		return sb.toString();
	}
}
