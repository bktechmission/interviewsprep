package Coding;


public class ThreadPoolTesting {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//ExecutorService executor = Executors.newFixedThreadPool(10);
		//System.out.println("");
		
		
		int type =95 + 32;
		
		
		int addressMask = (int)Math.pow(2,RegionType.ADDRESS.ordinal());
		System.out.println("Address Mask is : " + RegionType.ADDRESS.ordinal());
		
		
		int newWay = type ^ addressMask;
		//int newWay = type & ~( 1<< RegionType.ADDRESS.ordinal());
		System.out.println("new way is : " +  newWay);
		if((((32+95) >>> RegionType.ADDRESS.ordinal())  & 1) == 1)
		{
			System.out.println("yes");
		}
		
		int[] a = {95, 31, 128, 223, 65, 63, 255, 14, 158, 25, 30, 32, 351, 26, 24, 10};
		for(int i=0; i<a.length; i++)
		{
			if(((a[i] >>> RegionType.ADDRESS.ordinal())  & 1) == 1)
			{
				System.out.println(a[i]);
			}
			
		}
		
	}

}
