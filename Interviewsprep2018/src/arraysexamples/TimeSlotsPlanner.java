package arraysexamples;

import java.util.Arrays;

public class TimeSlotsPlanner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] slotsA = {{10, 50}, {60, 120}, {140, 210}};
		int[][] slotsB = {{0, 15}, {60, 70}};
		System.out.println(Arrays.toString(meetingPlanner(slotsA, slotsB, 8)));
	}

	static int[] meetingPlanner(int[][] slotsA, int[][] slotsB, int dur) {
		int i=0;
		int j=0;
		while(i<slotsA.length && j<slotsB.length){
			int[] a = slotsA[i];
			int[] b = slotsB[j];

			if(isThereAnyOverlap(a,b) && isFreeDurationExists(a,b,dur)){
				// check is duration can exist in both
				int start = Math.max(a[0],b[0]);
				int end = start+dur;
				return new int[] {start,end};
			}

			if(a[1]<b[1]){
				i++;
			}
			else{
				j++;
			}
		}
		return new int[] {};
	}

	static boolean isFreeDurationExists(int[]a,int[]b, int dur){
		int maxStart = Math.max(a[0],b[0]);
		int minEnd = Math.min(a[1],b[1]);

		int bandOfFree = minEnd-maxStart;
		return bandOfFree>=dur;
	}


	static boolean isThereAnyOverlap(int[]a, int[]b){
		return !(a[0]>b[1]||b[0]>a[1]);
	}

}
