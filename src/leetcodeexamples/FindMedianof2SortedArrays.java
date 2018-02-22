package leetcodeexamples;

public class FindMedianof2SortedArrays {
	public static void main(String[] args) {
		int[] a ={1,5,9,10,14,17,19};
		int[] b = {2,4};
		System.out.println("median is "+findMedian(a,b));
		
		System.out.println("5th element is : "+findKthElement(a, b, 5, 0, 0));
		
		System.out.println("median by partition is :  "+findMedianPartitionWay(a,b));
	}
	
	
	public static double findMedianPartitionWay(int[]a, int[]b) {
		int x = a.length;
		int y = b.length;
		if(x>y) {
			return findMedianPartitionWay(b,a);
		}
		
		int lowx = 0;
		int highx = x;		//    1 2 3 4 5 6      5  6  7 8 9 10 11 12
		
		while(lowx<=highx) {
			int px = (lowx+highx)/2;
			int py = (x+y+1)/2-px;
			
			int maxLeftX = (px==0)?Integer.MIN_VALUE:a[px-1];
			int minRightX = (px==x)?Integer.MAX_VALUE:a[px];
			
			
			int maxLeftY = (py==0)?Integer.MIN_VALUE:b[py-1];
			int minRightY = (py==y)?Integer.MAX_VALUE:b[py];
			
			if(maxLeftX<=minRightY && maxLeftY<=minRightX) {
				if((x+y)%2==0) {
					return (Math.max(maxLeftX, maxLeftY)+Math.min(minRightX, minRightY))/2.0;
				}else {
					return Math.max(maxLeftX, maxLeftY);
				}
			}else if(maxLeftX>minRightY) {
				highx = px-1;
			}else {
				lowx = px+1;
			}
			
		}
		throw new IllegalArgumentException();
	}
	public static double findMedian(int[]a, int[]b) {
		int totalSize = a.length+b.length;
		if(totalSize%2==0) {
			return (findKthElement(a,b,totalSize/2+1,0,0)
					+findKthElement(a,b,totalSize/2,0,0))/2.0;
		}
		else {
			return findKthElement(a,b,totalSize/2+1,0,0);
		}
	}
	
	public static int findKthElement(int[]a, int[]b, int k, int sa, int sb) {
		if(sa>=a.length) {
			return b[sb+k-1];
		}
		
		if(sb>=b.length) {
			return a[sa+k-1];
		}
		
		if(k==1) {
			return Math.min(a[sa], b[sb]);
		}
		
		int m1 = sa+k/2-1;
		int m2 = sb+k/2-1;
		
		int mida = m1<a.length?a[m1]:Integer.MAX_VALUE;
		int midb = m2<b.length?b[m2]:Integer.MAX_VALUE;
		
		if(mida<midb) {
			return findKthElement(a,b,k-k/2,m1+1,sb);
		}
		else {
			return findKthElement(a,b,k-k/2,sa,m2+1);
		}
	}
}

