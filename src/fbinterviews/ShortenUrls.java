package fbinterviews;

// Store URL on disk, get their auto incremented row Id as int

public class ShortenUrls {

	public static final String CONVERTERSTRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final int BASE = CONVERTERSTRING.length();
	public static void main(String[] args)
	{
		int id = Integer.MAX_VALUE;
		String x = encode(id);
		System.out.println(x);
		System.out.println(decode(x));
		
	}
	
	// using N Base 10 to X Base 16 Converter
	public static String encode(int n)
	{
		StringBuilder sb = new StringBuilder();
		while(n>0)
		{
			int remainder = n%BASE;
			sb.append(CONVERTERSTRING.charAt(remainder));
			n/=BASE;
			
		}
		
		return sb.reverse().toString();
	}
	
	public static int decode(String s)
	{
		int num = 0;
		
		for(int i=0;i<s.length();i++)
		{
			num = num*BASE + CONVERTERSTRING.indexOf(s.charAt(i));
		}
		return num;
	}
}
