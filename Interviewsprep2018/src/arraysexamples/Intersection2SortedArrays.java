package arraysexamples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Intersection2SortedArrays {
	 static int[] findDuplicates(int[] arr1, int[] arr2) {
		    // your code goes here
		    // merge approach
		    
		    int i = 0;    //arr1
		    int j = 0;    //arr2
		    
		    List<Integer> out = new ArrayList<>();
		    
		    while(i<arr1.length && j<arr2.length){
		      if(arr1[i]==arr2[j]){
		        out.add(arr1[i]);
		        i++;j++;
		      }
		      else if (arr1[i]<arr2[j]){
		        i++;
		      }
		      else{
		        j++;
		      }
		    }
		    
		    int[] outArr = new int[out.size()];
		    for(int k =0;k<out.size();k++){
		      outArr[k] = out.get(k);
		    }
		    
		    return outArr;
		  }

		  public static void main(String[] args) {
		    int[] arr1 = {1, 2, 3, 5, 6, 7}, arr2 = {3, 6, 7, 8, 20};
		    System.out.println(Arrays.toString(findDuplicates(arr1,arr2)));
		  }

}
