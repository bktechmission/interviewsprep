package arraysexamples;

public class MediaOf2SortedArrays {
	public static void main(String[] args) {
		
	}
	
	static void medianOf2SortedArray(){
		int[] a = {12,23,34,45,46,47};
		int[] b = {1,3,5,6,8,11,13,15,18,19,21,24,26,29};
		
		//1,3,5,6,8,11,12,13,15,18,19,21,23,24,26,29,34,45,46,47;
		
		System.out.println(medianOf2SortedArrayHelper(a,b));
		
	}
	static double medianOf2SortedArrayHelper(int[]a, int[]b){
		int m = a.length;
		int n = b.length;
		if(m>n) {
			return medianOf2SortedArrayHelper(b,a);
		}
		
		int lowx = 0;
		int highx = m;
		
		while(lowx<=highx) {
			int partitionx = (lowx+highx)/2;
			int partitiony = (m+n+1)/2-partitionx;
			
			int maxLeftX = partitionx==0?Integer.MIN_VALUE: a[partitionx-1];
			int minRightX = partitionx==m?Integer.MAX_VALUE : a[partitionx];
			
			int maxLeftY = partitiony==0?Integer.MIN_VALUE: b[partitiony-1];
			int minRightY = partitiony==n?Integer.MAX_VALUE : b[partitiony];
			
			if(maxLeftX<=minRightY && maxLeftY<=minRightX) {
				if((m+n)%2==0) {
					return (Math.max(maxLeftX, maxLeftY)+Math.min(minRightX, minRightY))/2.0;
				}
				else {
					return (Math.max(maxLeftX, maxLeftY));
				}
			}
			else if(maxLeftX>minRightY) {
				highx = partitionx-1;
			}
			else {
				lowx = partitionx+1;
			}
		}
		
		throw new IllegalArgumentException();
	}
	
}
