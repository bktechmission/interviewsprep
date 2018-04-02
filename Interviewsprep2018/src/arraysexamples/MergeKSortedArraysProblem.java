package arraysexamples;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class MergeKSortedArraysProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		mergeKSortedArraysExample();
	}
	
	static void mergeKSortedArraysExample(){
		int k = 10;
		int c = 20;
		int[][] mat = new int[k][c];
		Random rand = new Random();
		for(int i=0;i<k;i++) {
			for(int j=0;j<c;j++) {
				mat[i][j] = rand.nextInt(400);
			}
			Arrays.sort(mat[i]);
		}
		int[] result = mergeKSortedArrays(mat);
		System.out.println(Arrays.toString(result));
		
	}
	static int[] mergeKSortedArrays(int[][]mat) {
		int k = mat.length;
		int c = mat[0].length;
		int[] result = new int[k*c];
		
		Queue<ArrayContainer> pq = new PriorityQueue<>();
		
		int i = 0 ;
		while(i<k) {
			pq.offer(new ArrayContainer(mat[i],0));
			i++;
		}
		
		int index = 0;
		while(index<result.length) {
			ArrayContainer ac = pq.poll();
			result[index] = ac.arr[ac.i];
			
			if(ac.i<ac.arr.length-1) {
				pq.offer(new ArrayContainer(ac.arr,ac.i+1));
			}
			index++;
		}
		return result;
		
	}
	
	
	
	static class ArrayContainer implements Comparable<ArrayContainer>{
		int[] arr;
		int i;
		public ArrayContainer(int[]arr, int i) {
			this.arr = arr;
			this.i = i;
		}
		
		@Override
		public int compareTo(ArrayContainer o) {
			// TODO Auto-generated method stub
			return arr[i]-o.arr[o.i];
		}
	}

}
