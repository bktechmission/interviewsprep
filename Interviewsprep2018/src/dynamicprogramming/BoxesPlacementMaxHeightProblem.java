package dynamicprogramming;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

// https://www.geeksforgeeks.org/dynamic-programming-set-21-box-stacking-problem/
public class BoxesPlacementMaxHeightProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boxesPlacementsExample();
	}
	
	static void boxesPlacementsExample() {
		Box[] boxes = {new Box(4, 6, 7), new Box(1, 2, 3), new Box(4, 5, 6), new Box(10, 12, 32)};
		Box[] allRotations = new Box[boxes.length*3];	// each box can be rotaed in 3 ways keeping l>w
		
		createAllRotationOfBoxes(boxes, allRotations);
		maxHeight(allRotations);
		
	}
	
	
	static class Box implements Comparable<Box>{
		int height;
		int width;
		int length;
		
		Box(){
			
		}
		
		Box(int length, int width, int height){
			this.length = length;
			this.width = width;
			this.height = height;
		}

		@Override
		public int compareTo(Box o) {
			return (o.length*o.width - this.length*this.width);
		}
		
		public String toString() {
			return "{l:"+this.length+", w:"+this.width+", h:"+this.height+"}";
		}

	}
	
	static Box createRotation(int side1, int side2, int height){
		if(side1>=side2) {
			return new Box(side1, side2, height);
		}
		else {
			return new Box(side2, side1, height);
		}
	}

	
	static void createAllRotationOfBoxes(Box[] boxes, Box[] allRotations) {
		int i=0;
		for(Box bx: boxes) {
			allRotations[i++] = createRotation(bx.length,bx.width,bx.height);
			allRotations[i++] = createRotation(bx.width,bx.height,bx.length);
			allRotations[i++] = createRotation(bx.height,bx.length,bx.width);
		}
	}
	
	static int maxHeight(Box[] boxes) {
		
		Arrays.sort(boxes);	// nlogn
		
		int[] heights = new int[boxes.length];
		int[] results = new int[boxes.length];
		
		for(int i = 0; i<boxes.length; i++) {
			heights[i] = boxes[i].height;
			results[i] = i;
		}
		
		// O(n^2)
		for(int i=1; i<boxes.length; i++) {
			for(int j=0; j<i; j++) {
			// 4  2  1 can't go on top of 5 2 3 as width is equal, top box must have l and w strictly less than bottom box
				if(boxes[i].length < boxes[j].length && boxes[i].width < boxes[j].width 
					&& heights[i] < heights[j]+boxes[i].height) {		
					
					heights[i] = heights[j]+boxes[i].height;
					results[i] = j;
					
				}
			}
		}
		
		int maxHeight = Integer.MIN_VALUE;
		int indexOfMaxHeightFound = 0;
		for(int i=0;i<heights.length;i++) {
			if(maxHeight < heights[i]) {
				maxHeight = heights[i];
				indexOfMaxHeightFound = i;
			}
		}
		
		
		System.out.println("MaxHeigth Found is "+maxHeight);
		printPlacements(boxes, results, heights, indexOfMaxHeightFound);
		return maxHeight;
	}
	
	static Deque<Box> printPlacements(Box[] boxes, int[] results, int[] heights, int i) {
		
		int start = i;
		Deque<Box> stack = new LinkedList<>();
		while(start!=results[start]) {
			stack.push(boxes[start]);
			start = results[start];
		}
		stack.push(boxes[start]);
		
		System.out.println("Placements is : ");
		for(Box bx: stack) {
			System.out.print(bx);
		}
		return stack;
	}
	
}
