package arraysexamples;

import java.util.Arrays;

public class RotateImageProblem {
	public static void main(String[] args) {
		rotateImageExample();
	}
	
	
	static void rotateImageExample() {
		int[][] a = {
						{1,2,3,4},
						{5,6,7,8},
						{9,10,11,12},
						{13,14,15,16}
					};
		
		rotateInPlaceImage(a);
		for(int[] p: a) {
			System.out.println(Arrays.toString(p));
		}
		
	}
	
	static void rotateInPlaceImage(int[][]a) {
		int n = a.length;
		for(int layer = 0; layer<n/2;layer++) {
			int first = layer;
			int last = n-layer-1;
			for(int i=first; i<last; i++) {
				int offset = i-first;
				int top = a[first][i];
				a[first][i] = a[last-offset][first];
				a[last-offset][first] = a[last][last-offset];
				a[last][last-offset] =  a[i][last];
				a[i][last] = top;
			}
		}
		
	}
}
