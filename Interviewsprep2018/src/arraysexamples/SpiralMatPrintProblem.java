package arraysexamples;

import java.util.Arrays;

public class SpiralMatPrintProblem {


	  static int[] spiralCopy(int[][] inputMatrix) {
	    int m = inputMatrix.length; // number of rows
	    int n = inputMatrix[0].length; // number of columns
	    
	    int[] output =  new int[m*n];
	    int top = 0;
	    int bottom = m-1;
	    
	    int left = 0;
	    int right = n-1;
	    
	    int globalIndex = 0;
	    
	    int row = 0;
	    int col = 0;
	    while(true){
	      
	      row = top;
	      for(col=left;col<=right;col++){
	        output[globalIndex++] = inputMatrix[row][col];
	      }
	      top++;
	      if(top>bottom){
	        break;
	      }
	      
	      col=right;
	      for(row=top;row<=bottom;row++){
	        output[globalIndex++] = inputMatrix[row][col];
	      }
	      right--;
	      
	      if(left>right){
	        break;
	      }
	      
	      row=bottom;
	      for(col=right;col>=left;col--){
	        output[globalIndex++] = inputMatrix[row][col];
	      }
	      
	      bottom--;
	      
	      if(bottom<top){
	        break;
	      }
	      
	      
	      col=left;
	      for(row=bottom;row>=top;row--){
	        output[globalIndex++] = inputMatrix[row][col];
	      }
	      left++;
	      
	      if(right<left){
	        break;
	      }
	    }
	    
	    return output;
	  }

	  public static void main(String[] args) {
	       int[][] inputMatrix  = {{1,    2,   3,  4,    5},
	                       {6,    7,   8,  9,   10},
	                       {11,  12,  13,  14,  15},
	                       {16,  17,  18,  19,  20}};
	    
	    System.out.println("output is : "+Arrays.toString(spiralCopy(inputMatrix)));
	  }

}
