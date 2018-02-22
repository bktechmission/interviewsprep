package tests;

public class KnuthMorrisPrattAlgo 
{
	public static void main(String[] args)
	{
		String pattern = "ABCDABD";
		String text =  "ABC ABCDAB ABCDABCDABDE";
		int [] f = preprocessThePattern(pattern);		// compute the failure table
		findTheMatch(f,pattern.toCharArray(),text.toCharArray());		// O(n) function
		convertDecimalToHex(10);
		
	}
	
	private static boolean findTheMatch(int[]f, char[] p, char[] t)
	{
		int j = 0;
		for(int i=0;i<t.length;i++)
		{
			while(j>0 && t[i]!=p[j])
				j = f[j-1];
			
			// increment j only when match is found
			if(t[i]==p[j])
				j++;
			
			if(j==p.length)
			{
				System.out.println("Find the match start at: "+ (i-(j-1)) + " end at: " + (i-1));
				return true;
			}
		}
		return false;
	}
	
	private static int[] preprocessThePattern(String pattern)
	{
		char[] p = pattern.toCharArray();
		int[] f = new int[p.length];
		
		f[0] = 0;
		int j = 0;
		for(int i=1;i<p.length;i++)
		{
			while(j>0 && p[i]!=p[j])
				j = f[j-1];
			
			// increment j only when match is found
			if(p[i]==p[j])
				j++ ;
			
			f[i] = j;
			
			System.out.println(j);
		}
		return f;
		
	}

	private static void convertDecimalToHex(int n)
	{
		String hexString = "0123456789ABCDEF";
		// 0000 0000 0000 00101
		for(int i = 7; i>=0;i--)
		{
			System.out.print(hexString.charAt((n>>>(i*4)) & 0xF));
		}
	}
}
