package interviewsprep;

import java.util.Arrays;
import java.util.Random;

public class MatrixAlgos 
{
	public static void main(String[] args)
	{
		Random rand = new Random();
		int[][] a = new int[4][4];
		
		for(int i = 0; i<4; i++)
		{
			for(int j=0; j<a[0].length;j++)
			{
				a[i][j] = rand.nextInt(10);
			}
		}
		
		for(int i = 0; i<4; i++)
		{
			System.out.println();
			for(int j=0; j<a[i].length; j++)
			{
				System.out.print(a[i][j]+" ");
			}
		}
		System.out.println("\n\nAfter rotation to clockwise...");
		int[][] b = rotateMatrix(a);
		
		for(int i = 0; i<b.length; i++)
		{
			System.out.println();
			for(int j=0; j<b[i].length; j++)
			{
				System.out.print(b[i][j]+" ");
			}
		}
		
		
		System.out.println("\n\nBefore in place...");
		
		for(int i = 0; i<a.length; i++)
		{
			System.out.println();
			for(int j=0; j<a[i].length; j++)
			{
				System.out.print(a[i][j]+" ");
			}
		}
		
		
		rotateInPlace(a);
		
		System.out.println("\n\nAfter in place...");
		
		for(int i = 0; i<a.length; i++)
		{
			System.out.println();
			for(int j=0; j<a[i].length; j++)
			{
				System.out.print(a[i][j]+" ");
			}
		}
		
		int[] array = {1,2,3,4,5,6,7,8,9,10,11};
		System.out.println("\nBefore rotate is : "+ Arrays.toString(array));
		
		rotateArrayByKPositions(array,3);
		
		System.out.println("\nAfter rotate is : "+ Arrays.toString(array));
	}
	
	// 4X3 will become 3X4, row become cols
	public static int[][] rotateMatrix(int[][] a)
	{
		int rows = a.length;
		int cols = a[0].length;
		// row become cols
		int[][] b = new int[cols][rows];
		
		for(int i = 0; i<rows; i++)
		{
			for(int j=0; j<cols;j++)
			{
				b[j][rows-1-i] = a[i][j];
			}
		}
		
		return b;
	}
	
	
	public static void rotateInPlace(int[][] a)			//8X8
	{
		int n = a.length;				//8
		for(int layer=0; layer<n/2;layer++)
		{
			int first = layer;			//0
			int last = n-layer-1;		//7
			
			for(int i=first; i<last;i++)
			{
				int offset = i-first;
				int top = a[first][i];
				a[first][i] = a[last-offset][first];
				
				a[last-offset][first] = a[last][last-offset];
				
				a[last][last-offset] = a[i][last];
				
				a[i][last] = top;
				
			}
			
		}
	}
	
	public static void rotateArrayByKPositions(int[]a, int k)
	{
		swapArrays(a,0, k-1);
		swapArrays(a,k,(a.length-1));
		swapArrays(a,0,(a.length-1));
	}
	
	public static void swapArrays(int[]a, int start, int end)
	{
		while(start<end)
		{
			int temp = a[start];
			a[start] = a[end];
			a[end] = temp;
			start++;
			end--;
		}
	}
}
