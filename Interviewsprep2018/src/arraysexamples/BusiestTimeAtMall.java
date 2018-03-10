package arraysexamples;

public class BusiestTimeAtMall {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] data = {{1487799425, 14, 1}, 
                			{1487799425, 4,  0},
                			{1487799425, 2,  0},
                			{1487800378, 10, 1},
                			{1487801478, 18, 0},
                			{1487801478, 18, 1},
                			{1487901013, 5,  0},
                			{1487901211, 14,  1},
                			{1487901211, 7,  0},
                			{1487901211, 2,  0},
                			{1487901215, 6,  1},
                			{1487901215, 10,  0}};
		System.out.println(findBusiestPeriod(data));
	}
	
	  static int findBusiestPeriod(int[][] data) {
		    // your code goes here
		    int maxCountSofar = 0;
		    int maxCountTime = -1;
		    int curCount = 0;
		    int prevTimestamp = -1;
		    for(int[] point : data){     
		      int time = point[0];
		      int count = point[1];
		      int isEnter = point[2];  // 1 enter 0 exit
		      
		      if(prevTimestamp!=-1 && prevTimestamp!=time && curCount>maxCountSofar){
		        maxCountSofar = curCount;
		        maxCountTime = prevTimestamp;
		      }
		      
		      if(prevTimestamp!=-1&&prevTimestamp!=time){
		         System.out.println("t:"+prevTimestamp+", c:"+curCount);
		      }
		    
		      curCount = isEnter==1? curCount+count:curCount-count;
		 
		      prevTimestamp = time;
		    }
		    
		     if(curCount>maxCountSofar){
		        maxCountSofar = curCount;
		        maxCountTime = prevTimestamp;
		        
		      }
		    System.out.println("t:"+prevTimestamp+", c:"+curCount);
		    
		    return maxCountTime;
		  }

	
	
	

}
