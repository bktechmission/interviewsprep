package leetcodeexample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class DPProblems {
    private static final String TROVER_CONTROL_AB_NAME = "trover_attribution";
    private static final String TOURISMMEDIA = "tm";
    private static final String TOURISMMEDIA_WITH_TROVER = "tm_trover";
    private static final Integer BUCKET_ONE = 1;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		minimumEditDistanceExample();
		maxSumSubArrayExample();
		climbingStarisExample();
		stockBuySellExample();
		zeroOneKnapsackProblem();
		flattenListExample();
		//System.out.println(find(100,4));
		//
		//printAllSubsetsExample();
		//staticTester();
		nestedListExample();
		convertToRPN("200*(102-(10/5))+60-40/(40-20)");
		Inner a = new Inner();
		a.foo = "me";
		
		Nested b = new Nested();
		b.inner = a;
		
		Outer c = new Outer();
		c.nested = b;
		
		minPathSumExample();
		islandRelatedProblems();
		contStepsProblem();
		
		
		Map<String, Integer> map = new HashMap<>();
		map.put("me",123);
		map.put("hi", 1);
		map.put("trover_attribution", 1);
		SomeContext sc = new SomeContext();
		sc.map = map;
		boolean isTrover = Optional.ofNullable(sc.getContext())
        .map(Map::entrySet)
        .filter(entries -> !entries.isEmpty())
        .map(entries -> isTroverEnabled(entries))
        .orElse(false);
		System.out.println("set is "+isTrover);
	}
	
	public static boolean isTroverEnabled(Set<Map.Entry<String, Integer>> map) {
		for(Map.Entry<String, Integer> entry: map) {
            if(TROVER_CONTROL_AB_NAME.equalsIgnoreCase(entry.getKey())
                    &&BUCKET_ONE.equals(entry.getValue()))
                return true;
        }
		return false;
	}
	
	static class SomeContext{
		Map<String, Integer> map;
		public void setContext(Map<String, Integer> map) {
			this.map=map;
		}
		public Map<String, Integer> getContext(){
			return this.map;
		}
	}
	
	public static void contStepsProblem() {
		int steps = 40;
		System.out.println("count steps : "+countStepsTopDownDP(steps));
	}
	
	private static int countStepsFibo(int steps) {
		if(steps<0) {
			return 0;
		}
		
		if(steps<=1) {
			return 1;
		}
		
		return countStepsFibo(steps-1) + countStepsFibo(steps-2) + countStepsFibo(steps-3);
	}
	
	
	private static int countStepsTopDownDP(int steps) {
		int[] cache = new int[steps+1];
		return countStepsTopDownDP(steps,cache);
	}
	
	private static int countStepsTopDownDP(int steps, int[]cache) {
		if(steps<0) {
			return 0;
		}
		
		if(steps<=1) {
			return 1;
		}
		
		if(cache[steps]==0) {
			cache[steps] = countStepsTopDownDP(steps-1,cache) + countStepsTopDownDP(steps-2,cache) + countStepsTopDownDP(steps-3,cache);
		}
		
		return cache[steps];
	}
	
	private static int countStepsIterDP(int steps) {
		if(steps<0) {
			return 0;
		}
		
		if(steps<=1) {
			return 1;
		}
		
		int[]cache = new int[steps+1];
		cache[0] = 1;
		cache[1] = 1;
		cache[2] = 2;
		for(int i=3;i<=steps;i++) {
			cache[i] = cache[i-1] + cache[i-2] + cache[i-3];
		}
		return cache[steps];
	}
	
	public static void islandRelatedProblems() {
		int m = 8;
		int n = 5;
		int size = m*n;
		int[][] mat = new int[m][n];
		
		Random rand = new Random();
		for(int i=0;i<size-size/2;i++) {
			int x = rand.nextInt(size);
			int r = x/n;
			int c = x%n;
			mat[r][c]=1;
		}
		
		for(int i=0;i<mat.length;i++) {
			System.out.println(Arrays.toString(mat[i]));
		}
		
		System.out.println(getBiggestRegion(mat));
	}
	private static int[] dx = {-1,-1,-1,0,0,1,1,1};
	private static int[] dy = {-1,0,1,-1,1,-1,0,1};
	
	private static int getBiggestRegionHelper(int r, int c, int[][]mat) {
		if(!isValid(r,c,mat)) {
			return 0;
		}
		mat[r][c] = 0;
		
		int size=1;
		for(int i=0;i<dx.length;i++) {
			for(int j=0;j<dy.length;j++) {
				int newr = r+dx[i];
				int newc = c+dy[j];
				size+=getBiggestRegionHelper(newr,newc,mat);
			}
		}
		return size;
		
	}
	private static boolean isValid(int r, int c, int[][]mat) {
		if(r<0||r>=mat.length||c<0||c>=mat[0].length||mat[r][c]==0) {
			return false;
		}
		
		return true;
	}
	
	private static int getBiggestRegion(int[][] mat) {
		int maxRegion = 0;
		
		int count = 0;
		for(int i=0;i<mat.length;i++) {
			for(int j=0;j<mat[0].length;j++) {
				if(mat[i][j]==1) {
					maxRegion = Math.max(maxRegion,getBiggestRegionHelper(i,j,mat));
					count++;
				}
			}
		}
		
		System.out.println("total regions found: "+count);
		
		return maxRegion;
	}
	
	public static void minPathSumExample() {
		int[][] grid = new int[30][10];
		Random rand = new Random();
		for(int i=0;i<grid.length;i++) {
			for(int j=0;j<grid[0].length;j++) {
				grid[i][j] = rand.nextInt(100);
			}
		}
		
		for(int[] x:grid) {
			//System.out.println(Arrays.toString(x));
		}
		
		System.out.println(countPaths(grid));
		System.out.println(minPathSum(grid));
	}
	
	public static int countPaths(int[][]a) {
		int[][] cache = new int[a.length][a[0].length];
		
		for(int[] p:cache) {
			Arrays.fill(p, -1);
			System.out.println(Arrays.toString(p));
		}
		System.out.println();
		//return countPathsHelper(0,0,a);
		//return countPathsHelper(0,0,a,cache);
		return countPathsHelperIterative(a);
	}
	
	private static int countPathsHelper(int r, int c, int[][]grid) {
		if(!isInsideGrid(r,c,grid)) {
			return 0;
		}
		
		if(r==grid.length-1&&c==grid[0].length-1) {
			return 1;
		}
		
		return countPathsHelper(r+1,c,grid) + countPathsHelper(r,c+1,grid);
	}
	
	private static int countPathsHelper(int r, int c, int[][]grid, int[][]cache) {
		if(!isInsideGrid(r,c,grid)) {
			return 0;
		}
		if(cache[r][c]!=-1) {
			return cache[r][c];
		}
		
		if(r==grid.length-1&&c==grid[0].length-1) {
			return 1;
		}
		cache[r][c] = countPathsHelper(r+1,c,grid,cache) + countPathsHelper(r,c+1,grid,cache);
		return cache[r][c];
	}
	
	private static int countPathsHelperIterative(int[][]grid) {
		int m = grid.length;
		int n = grid[0].length;
		int[][]cache = new int[m][n];
		
		int i=m-1;
		int j=0;
		for(j=n-1;j>=0;j--) {
			cache[i][j]=1;
		}
		
		j=n-1;
		for(i=m-1;i>=0;i--) {
			cache[i][j]=1;
		}
		for(i=0;i<m;i++) {
			System.out.println(Arrays.toString(cache[i]));
		}
		for(i=m-2;i>=0;i--) {
			for(j=n-2;j>=0;j--) {
				cache[i][j]=cache[i+1][j]+cache[i][j+1];
			}
		}
		for(i=0;i<m;i++) {
			System.out.println(Arrays.toString(cache[i]));
		}
			
		return cache[0][0];
	}
	
	
	public static int minPathSum(int[][]grid) {
		return minPathSumHelperHelper(grid);
		//return minPathSumHelper(0,0,grid);
	}
	
	public static int minPathSumHelper(int r, int c, int[][]grid) {
		if(!isInsideGrid(r,c,grid)) {
			return Integer.MAX_VALUE;
		}
		
		if(r==grid.length-1&&c==grid[0].length-1) {
			return grid[r][c];
		}
		
		return grid[r][c]+ Math.min(minPathSumHelper(r+1,c,grid), minPathSumHelper(r,c+1,grid));
	}
	
	public static int minPathSumHelperHelper(int[][]grid) {
		int m = grid.length;
		int n = grid[0].length;
		int[][]cache = new int[m][n];
		cache[0][0] = grid[0][0];
		for(int i=1;i<m;i++) {
			cache[i][0] = cache[i-1][0] + grid[i][0];
		}
		
		for(int j=1;j<n;j++) {
			cache[0][j] = cache[0][j-1] + grid[0][j];
		}
		
		for(int i=1;i<m;i++) {
			for(int j=1;j<n;j++) {
				cache[i][j] = Math.min(cache[i-1][j],cache[i][j-1]) + grid[i][j];
			}
		}
		for(int i=0;i<m;i++) {
			System.out.println(Arrays.toString(cache[i]));
		}
		
		return cache[m-1][n-1];
	}
	
	private static boolean isInsideGrid(int r, int c, int[][]grid) {
		if(r<0||r>=grid.length||c<0||c>=grid[0].length) {
			return false;
		}
		return true;
	}
	
	
	public int calc(String s) {
		int md=-1;	//	0 is m 1 is div
		int sign = 1;// 1 is +ve -1 is -ve
		int result =0;
		int prev=0;
		for(int i=0;i<s.length();i++) {
			char c = s.charAt(i);
			if(Character.isDigit(c)) {
				int num = c-'0';
				while(++i<s.length()&&Character.isDigit(s.charAt(i))) {
					num+=num*10+s.charAt(i)-'0';
				}
				i--; // back to last digit of number
				
				if(md==0) {
					prev = prev*num;
					md=-1;
				}
				else if(md==1) {
					prev = prev/num;
					md=-1;
				}
				else {
					prev=num;
				}
			}
			else if(c=='/') {
				md=1;
			}else if(c=='*') {
				md=0;
			}else if(c=='+') {
				result = result+sign*prev;
				sign=1;
			}else if(c=='-') {
				result = result+sign*prev;
				sign=-1;
			}
		}
		result = result + sign*prev;
		return result;
	}
	
	// "2+3*5/(5-10)"
	static void convertToRPN(String s) {
		s.replaceAll(" ", "");
		StringBuilder sb = new StringBuilder();
		StringBuilder temp= new StringBuilder();
		Deque<Character> stack = new LinkedList<>();
		for(int i=0;i<s.length();i++) {
			if(Character.isDigit(s.charAt(i))) {
				//int var = s.charAt(i)-'0';
				temp.append(s.charAt(i));
				while(++i<s.length()&&Character.isDigit(s.charAt(i))) {
					//var+=var*10+s.charAt(i)-'0';
					temp.append(s.charAt(i));
				}
				i--;
				sb.append(temp.toString()+" " );
				temp.setLength(0);
				//System.out.println(sb.toString());
			}
			else if(isAnOperand(s.charAt(i))) {
				while(!stack.isEmpty()&&!isOpenParanthesis(stack.peek())&&isHigherPrecedence(stack.peek(),s.charAt(i))) {
					Character stackElement = stack.pop();
					sb.append(stackElement+" ");
					//System.out.println(sb.toString());
				}
				stack.push(s.charAt(i));
				//System.out.println(stack);
			}
			else if(isOpenParanthesis(s.charAt(i))) {
				stack.push(s.charAt(i));
				//System.out.println(stack);
			}
			else if(isClosingParanthesis(s.charAt(i))) {
				while(!stack.isEmpty()&&!isOpenParanthesis(stack.peek())) {
					Character stackElement = stack.pop();
					sb.append(stackElement+" ");
				}
				stack.pop();
			}
		}
		
		while(!stack.isEmpty()) {
			Character stackElement = stack.pop();
			sb.append(stackElement+" ");
		}
		
		System.out.println("RPN is "+sb.toString() + " result is ");
		evaluateRPN(sb.toString());
	}
	
	static void evaluateRPN(String s) {
		String operators = "+-/*";
		String[] a = Arrays.stream(s.split(" ")).map(x->x.trim()).toArray(String[]::new);
		Deque<Integer> stack = new LinkedList<>();
		Integer result = null;
		for(int i=0;i<a.length;i++) {
			String e = a[i];
			if(!operators.contains(e)) {
				stack.push(Integer.parseInt(e));
			}
			else {
				Integer y = stack.pop();
				Integer x = stack.pop();
				switch(e) {
				case "+":
					result = x+y;
					stack.push(result);
					break;
				case "-":
					result = x-y;
					stack.push(result);
					break;
				case "*":
					result = x*y;
					stack.push(result);
					break;
				case "/":
					result = x/y;
					stack.push(result);
					break;
				}
			}
		}
		System.out.println("final result is : "+stack.peek());
	}
	
	static Map<Character, Integer>  mapofPrecedenc = new HashMap<>();
	static{
		mapofPrecedenc.put('*', 2);
		mapofPrecedenc.put('/', 2);
		mapofPrecedenc.put('+', 1);
		mapofPrecedenc.put('-', 1);
	}
	
	static boolean isOpenParanthesis(char c) {
		if(c=='('||c=='{'||c=='[') {
			return true;
		}
		return false;
	}
	
	static boolean isClosingParanthesis(char c) {
		if(c==')'||c=='}'||c==']') {
			return true;
		}
		return false;
	}
	
	static boolean isHigherPrecedence(char top, char c) {
		Integer preTop = mapofPrecedenc.get(top);
		Integer preC = mapofPrecedenc.get(c);
		if(preTop==null||preC==null) {
			return false;
		}
		if(preTop>=preC) {
			return true;
		}
		return false;
	}
	
	static boolean isAnOperand(char c) {
		if(c=='+'||c=='-'||c=='*'||c=='/') {
			return true;
		}
		return false;
	}
	
	static class Outer {
	    Nested nested;
	    Nested getNested() {
	        return nested;
	    }
	}
	static class Nested {
	    Inner inner;
	    Inner getInner() {
	        return inner;
	    }
	}
	static class Inner {
	    String foo;
	    String getFoo() {
	        return foo;
	    }
	}
	
	static void nestedListExample() {
		List<NestedInteger> l1 = new LinkedList<>();
		l1.add(new NestedInteger(1));
		NestedInteger a = new NestedInteger(l1);
		a.add(new NestedInteger(1));
		
		
		List<NestedInteger> list = new LinkedList<>();
		list.add(a );
		list.add(new NestedInteger(2));
		
		List<NestedInteger> l2 = new LinkedList<>();
		l2.add(new NestedInteger(1));
		NestedInteger b = new NestedInteger(l2);
		b.add(new NestedInteger(1));
		list.add(b);
		System.out.println("nested List : "+list);
		sumNestedList(list);
		sumInReverseOrder(list);
	}
	
	static void sumInReverseOrder(List<NestedInteger> nestedList) {
		if(nestedList==null||nestedList.size()==0) {
			return;
		}
		
		Map<Integer, List<NestedWithDepth>> mapLayerToNum = new HashMap<>();
		Deque<NestedWithDepth> stack = new LinkedList<>();
		for(NestedInteger ni:nestedList) {
			NestedWithDepth newNode = new NestedWithDepth();
			newNode.ni = ni;
			newNode.depth = 1;
			stack.push(newNode);
		}
		
		int maxLevel = Integer.MIN_VALUE;
		while(!stack.isEmpty()) {
			NestedWithDepth node = stack.pop();
			maxLevel = Math.max(maxLevel, node.depth);
			
			if(node.ni.isInteger()) {
				if(!mapLayerToNum.containsKey(node.depth)) {
					List<NestedWithDepth> list =  new ArrayList<>();
					list.add(node);
					mapLayerToNum.put(node.depth, list);
				}else {
					List<NestedWithDepth> list = mapLayerToNum.get(node.depth);
					list.add(node);
				}
			}
			else {
				for(NestedInteger ni: node.ni.getList()) {
					NestedWithDepth newNode = new NestedWithDepth();
					newNode.ni = ni;
					newNode.depth = node.depth+1;
					stack.push(newNode);
				}
			}
			
		}
		
		int sum=0;
		for(int i=maxLevel;i>=1;i--) {
			for(NestedWithDepth node:mapLayerToNum.get(i)) {
				sum+=node.ni.getInteger()*(maxLevel-i+1);
			}
		}
		System.out.println("Sum is in reverse depth : "+sum);
	}
	
	static class NestedWithDepth{
		NestedInteger ni;
		int depth;
	}
	
	static int sumNestedListDFS(List<NestedInteger> nestedList, int depth) {
		if(nestedList==null||nestedList.size()==0) {
			return 0;
		}
		int sum=0;
		for(NestedInteger ni:nestedList) {
			if(ni.isInteger()) {
				sum+=ni.getInteger();
			}else {
				sum+=sumNestedListDFS(ni.getList(),depth+1);
			}
		}
		return sum;
	}
	
	static void sumNestedList(List<NestedInteger> nestedList) {
		Queue<NestedWithDepth> q = new LinkedList<NestedWithDepth>();
		for(NestedInteger ni: nestedList) {
			NestedWithDepth nid = new NestedWithDepth();
			nid.ni = ni;
			nid.depth = 1;
			q.add(nid);
		}
		
		int sum=0;
		while(!q.isEmpty()) {
			NestedWithDepth node = q.poll();
			if(node.ni.isInteger()) {
				sum+=node.ni.getInteger()*node.depth;
			}else {
				for(NestedInteger ni: node.ni.getList()) {
					NestedWithDepth newNode = new NestedWithDepth();
					newNode.ni=ni;
					newNode.depth=node.depth+1;
					q.offer(newNode);
				}
			}
		}
		System.out.println("sum is "+sum);
	}
	
	static class NestedInteger{
		private List<NestedInteger> list;
		private Integer integer;
		
		public NestedInteger() {
			
		}
		
		public NestedInteger(Integer a) {
			this.integer = a;
		}
		
		
		public NestedInteger(List<NestedInteger> list) {
			this.list = list;
		}
		
		public void add(NestedInteger nestedInteger) {
			if(this.list!=null) {
				this.list.add(nestedInteger);
			}
			else {
				this.list = new ArrayList<>();
				this.list.add(nestedInteger);
			}
		}
		
		public void setInteger(int num) {
			this.integer = num;
		}

		
		public boolean isInteger() {
			return integer!=null;
		}
		
		public Integer getInteger() {
			return integer;
		}
		
		public List<NestedInteger> getList(){
			return list;
		}
		
		public String toString() {
			return printNi(this,new StringBuilder());
		}
		
		private String printNi(NestedInteger thisNi, StringBuilder sb) {
			if(thisNi.isInteger()) {
				sb.append(thisNi.getInteger());
			}
			if(thisNi.getList()!=null&& thisNi.getList().size()>0) {
				sb.append("[");
				for(NestedInteger ni: thisNi.getList()) {
					if(ni.isInteger()) {
						sb.append(ni.getInteger());
						sb.append(",");
					}
					else {
						printNi(ni,sb);
					}
				}
				sb.append("]");
			}
			
			return sb.toString();
		}
	}
	
	public static int depthSum(List<NestedInteger> nestedList) {
		return depthSumHelper(nestedList, 1);
	}
	
	private static int depthSumHelper(List<NestedInteger> nestedList, int depth) {
		if(nestedList==null||nestedList.size()==0) {
			return 0;
		}
		int sum=0;
		for(NestedInteger ni: nestedList) {
			if(ni.isInteger()) {
				sum+=ni.getInteger()*depth;
			}
			else {
				sum+=depthSumHelper(ni.getList(),depth+1);
			}
		}
		
		return sum;
	}
	
	
	static void printAllSubsetsExample(){
		int[]a = {2,3,5,6,7,9};
		List<Integer[]> finalList = new LinkedList<>();
		Integer[] resultSet = new Integer[a.length];
		generatePowerSet(a,0,resultSet,finalList);
		int i=0;
		for(Integer[] array:finalList ) {
			System.out.print("\n"+ ++i+". {");
			for(Integer x: array) {
				if(x!=null) {
					System.out.print(x+",");
				}
			}
			System.out.println("}");
		}
	}
	
	static void generatePowerSet(int[]a, int start, Integer[] resultSet,List<Integer[]> finalList) {
		if(start==a.length) {
			finalList.add(resultSet.clone());
			return;
		}
		
		resultSet[start] = a[start];
		generatePowerSet(a, start+1,resultSet,finalList);
		
		resultSet[start] = null;
		generatePowerSet(a, start+1,resultSet,finalList);
		
	}
	
	static long minCoinCount = Integer.MAX_VALUE;
	static List<Integer> minSet = null;
	
	static List<Integer> find(int M, int N)
	{
		List<Integer> currSet = new LinkedList<Integer>();
		
		int[] values = new int[M + 1];
		for (int i = 0; i <= M; i++)
		{
			values[i] = i;
		}
		
		//findHelper(N, currSet,values,M, 0);
		coinChangeHelper(N, M,currSet,values, 0);
		System.out.println("done with set: "+minSet.toString() +" min count "+minCoinCount);
		return minSet;
	}
	
	static void coinChangeHelper(int N, int maxCoin, List<Integer> curSet, int[] values, long coinCount){
		if(N==0) {
			if(minCoinCount>coinCount)
			{
				minCoinCount = coinCount;
				minSet = new LinkedList<>(curSet);
			}
			return;
		}
		
		if(N==1) {
			maxCoin = 1;
		}
		
		int[] newValues = new int[values.length];
		for(int i = maxCoin;i>=1;i--) {
			int curCoinCount=0;
			for(int j=1;j<values.length;j++) {
				int c = values[j]/i;
				newValues[j] = values[j]-i*c;
				curCoinCount+=c;
			}
			curSet.add(i);
			coinChangeHelper(N-1, i-1, curSet, newValues, coinCount+curCoinCount);
			curSet.remove(curSet.size()-1);
		}
	}
	
	static void findHelper(int N, List<Integer> currSet, int[] values, int maxCoin, int coinCount)
	{
		if (N == 0)
		{
			if (coinCount < minCoinCount)
			{
				//System.out.println("min "+minCoinCount);
				minCoinCount = coinCount;
				minSet = new LinkedList<>(currSet);
				
				//System.out.println("current min set found "+currSet.toString() + " minSet "+minSet+"  mincount "+minCoinCount );
			}
			return;
		}
		
		if (N == 1)
		{
			maxCoin = 1;
		}
		
		int[] newValues = new int[values.length];
		for (int i = maxCoin; i >= 1; i--)
		{
			int currCoinCount = 0;
			for (int j = 1; j < values.length; j++)
			{
				int c = values[j] / i;
				currCoinCount += c;
				newValues[j] = values[j] - i * c;
			}
			
			currSet.add(i);
			findHelper(N - 1, currSet, newValues, i - 1, coinCount + currCoinCount);
			currSet.remove(currSet.size() - 1);
		}
	}
	
	static void flattenLinkedList(Node head){
		Queue<Node> queue = new LinkedList<>();
		queue.offer(head);
		while(!queue.isEmpty()) {
			Node front = queue.poll();
			while(front.next!=null) {
				if(front.down!=null) {
					queue.offer(front.down);
				}
				front = front.next;
			}
			
			if(front.down!=null) queue.offer(front.down);
			if(!queue.isEmpty())	front.next = queue.peek();
		}
	}
	
	static void flattenLinkedListDFS(Node head){
		Deque<Node> stack = new LinkedList<>();
		stack.push(head);
		while(!stack.isEmpty()) {
			Node top = stack.pop();
			while(top.down!=null) {
				if(top.next!=null) {
					stack.push(top.next);
				}
				top.next = top.down;
				top = top.down;
			}
			if(top.next!=null)	stack.push(top.next);
			if(!stack.isEmpty())	top.next = stack.peek();
		}
		
	}
	
	static class A{
		int a;
		A next;
		A(int a){
			this.a=a;
		}
	}
	static A testMe = null;
	static void staticTester() {
		System.out.println("\ntestMe"+ staticHelper());
	}
	static int counter = 0;
	static int staticHelper() {
		if(counter++>=10) {
			return counter;
		}
		
		testMe = new A(counter);
		A remeberTo = testMe;
		testMe.a = testMe.a+staticHelper();
		System.out.println("test.a "+testMe.a +"  remebered one is "+remeberTo.a);
		return remeberTo.a;
	}
	static void flattenListExample() {
		// Creating above example list
	    Node head = new Node(1);
	    head.next = new Node(2);
	    head.next.next = new Node(3);
	    head.next.next.next = new Node(4);
	    head.next.down = new Node(7);
	    head.next.down.down = new Node(9);
	    head.next.down.down.down = new Node(14);
	    head.next.down.down.down.down
	                                     = new Node(15);
	    head.next.down.down.down.down.next
	                                     = new Node(23);
	    head.next.down.down.down.down.next.down
	                                      = new Node(24);
	    head.next.down.next =  new Node(8);
	    head.next.down.next.down =  new Node(16);
	    head.next.down.next.down.down =  new Node(17);
	    head.next.down.next.down.down.next
	                                      = new Node(18);
	    head.next.down.next.down.down.next.next
	                                      = new Node(19);
	    head.next.down.next.down.down.next.next.next
	                                      = new Node(20);
	    head.next.down.next.down.down.next.next.next.down
	                                      =  new Node(21);
	    head.next.down.next.next = new Node(10);
	    head.next.down.next.next.down = new Node(11);
	 
	    head.next.down.next.next.next = new Node(12);
	 
	    // Flatten list and print modified list
	    //flattenLinkedList(head);  //flattenListBFSIterative();
	    flattenLinkedListDFS(head);
	    printFlattenNodes(head);
	}
	
	static Node lastNode = null;
	static Node flattenList(Node node) {
		if(node==null) {
			return null;
		}
		
		lastNode = node;
		Node next = node.next;
		
		if(node.down!=null) {
			node.next = flattenList(node.down);
		}
		
		if(next!=null) {
			lastNode.next = flattenList(next);
		}
		return node;
	}
	
	static void flattenListDFSIterative(Node node) {
		if(node==null) {
			return;
		}
		
		Deque<Node> stack = new LinkedList<>();
		stack.push(node);
		while(!stack.isEmpty()) {
			Node top = stack.pop();
			while(top.down!=null) {
				if(top.next!=null) stack.push(top.next);
				top.next = top.down;
				top = top.down;
			}
			
			if(top.next!=null) stack.push(top.next);
			if(!stack.isEmpty())	top.next = stack.peek();
		}
	}
	
	static void flattenListBFSIterative(Node node) {
		if(node==null) {
			return;
		}
		
		Queue<Node> queue = new LinkedList<>();
		queue.offer(node);
		while(!queue.isEmpty()) {
			Node front = queue.poll();
			while(front.next!=null) {
				if(front.down!=null) queue.offer(front.down);
				front = front.next;
			}
			
			if(front.down!=null) queue.offer(front.down);
			if(!queue.isEmpty())	front.next = queue.peek();
		}
	}
	
	static void printFlattenNodes(Node head) {
		System.out.println("LinkedList:-> ");
		while(head!=null) {
			System.out.print(head.data+"->");
			head = head.next;
		}
	}
	
	static class Node
	{
		int data;
		Node next, down;
		Node(int data){
			this.data = data;
		}
		
		public String toString() {
			return String.valueOf(data);
		}
	}
	
	static void zeroOneKnapsackProblem() {
		int[] wt = {1,3,4,5};
		int[] val = {1,4,5,7};
		int totalweight = 7;
		
		// col are wts 0 to total weight
		int[][] dp = new int[wt.length][totalweight+1];
		
		for(int i=0;i<wt.length;i++) {
			for(int j=0;j<=totalweight;j++) {
				if(j==0) {
					dp[i][j] = 0;
				}
				else if(i==0) {
					if(wt[i]>j) {
						dp[i][j] = 0;
					}
					else {
						dp[i][j] = val[i];
					}
				}
				else {
					if(wt[i]>j) {
						dp[i][j] = dp[i-1][j];
					}
					else {
						dp[i][j] = Math.max(dp[i-1][j],val[i]+dp[i-1][j-wt[i]]);
					}
				}
			}
		}
		
		
		string_ValueOfMatrix(dp);
		
	}
	static void stockBuySellExample() {
		int[]a= {7, 1, 5, 3, 6, 4};
		int[]b= {1,2,3,4,5,6,7};
		System.out.println("Price array is "+Arrays.toString(b));
		maxProfitWithAtMost1Transaction(b);
		maxProfitAsManyTransactions(b);
	}
	
	static void maxProfitWithAtMost1Transaction(int[]a) {
		if(a==null||a.length==1) {
			return;
		}
		
		int minWhereWeBuyFirst = a[0];
		int maxProfit=0;
		int minIndex=0;
		int buyIndex=0;
		int sellIndex=-1;
		
		for(int i=1;i<a.length;i++) {
			if(minWhereWeBuyFirst>a[i]) {
				minWhereWeBuyFirst = a[i];
				minIndex = i;
			}else {
				int curProfit = a[i]-minWhereWeBuyFirst;
				if(curProfit>maxProfit) {
					maxProfit = curProfit;
					sellIndex = i;
					buyIndex = minIndex;
				}
			}
		}
		
		if(maxProfit!=0) {
			System.out.println("With Atmost single transactions: ");
			System.out.println("buy at "+(buyIndex+1)+" day and sell at "+(sellIndex+1)+ " day and make profit "+maxProfit);
			
		}
	}
	
	static class BSP{
		int buyIndex;
		int sellIndex;
		int profit;
	}
	// find first decreasing, reach min to buy
	// then look for increasing reach max to sell
	static void maxProfitAsManyTransactions(int[] a)
	{
		int i=0;
		List<BSP> list = new ArrayList<BSP>();
		while(i<a.length) {
			// first we look for decresing sequence
			while(i<a.length-1&&a[i]>a[i+1]) {
				i++;
			}
			int buyIndex=i;
			if(i>=a.length-1) {
				break;
			}
			
			// now look for increasing sequence
			while(i<a.length-1&&a[i]<a[i+1]) {
				i++;
			}
			if(i!=buyIndex&&i!=a.length) {
				int sellIndex = i;
				BSP bsp = new BSP();
				bsp.buyIndex = buyIndex;
				bsp.sellIndex = sellIndex;
				bsp.profit = a[bsp.sellIndex]-a[bsp.buyIndex];
				list.add(bsp);
			}
			i++;
		}
		
		System.out.println("\nWith Atmost as many transactions: ");
		for(BSP bsp:list) {
			System.out.println("buy at "+(bsp.buyIndex+1)+" day and sell at "+(bsp.sellIndex+1)+ " day and make profit "+bsp.profit);
		}
	}
	
	
	static void climbingStarisExample(){
		int n=4;
		int k=2;
		System.out.println("climb stairs :. "+ countWaysClimbingNStairsKSteps(n+1,k));
		System.out.println("climb stairs :. "+countWaysClimbingNStairs1or2Steps(n+1));
	}
	
	static int countWaysClimbingNStairsKSteps(int n, int k){
		int[] res = new int[n+1];
		res[0] = 0;
		res[1] = 1;
		for(int i=2;i<=n;i++) {
			res[i]=0;
			for(int j=1;j<=k&&j<=i;j++) {
				res[i]+=res[i-j];
			}
		}
		return res[n];
	}
	
	static int countWaysClimbingNStairs1or2Steps(int n){
		if(n<=1) {
			return n;
		}
		return countWaysClimbingNStairs1or2Steps(n-1)+countWaysClimbingNStairs1or2Steps(n-2);
	}
	
	
	public static void maxSumSubArrayExample() {
		int[] a =  {3,-2,1,4,-2,-9,10,-6,-2,10};
		System.out.println("max sum of subarray is:.   "+maxSumSubArrayHelper(a,0,a.length-1));
		System.out.println("Arrays is : "+Arrays.toString(a));
		maxSumSubArrayKadanesAlgo(a);
		maxSumArrayLessCode(a);
		int[]b= {1,-2,2,5};
		maxProdductArrayLessCode(b);
	}
	
	static int maxSumSubArrayKadanesAlgo(int[]a) {
		int k = isAllNegative(a);
		if(k!=0) {
			return k;
		}
		
		int maxGlobal=0, maxEndingHere=0;
		int sumStart=0;
		int sumEnd=0;
		int j=0;
		for(int i =0;i<a.length;i++) {
			maxEndingHere+=a[i];
			if(maxEndingHere>maxGlobal) {
				maxGlobal = maxEndingHere;
				sumStart = j;
				sumEnd = i;
			}
			if(maxEndingHere<0) {
				maxEndingHere=0;
				j=i+1;
			}
		}
		
		System.out.println("maxSum is "+maxGlobal + "start at: "+sumStart + " end at: "+sumEnd);
		return maxGlobal;
	}
	
	static int isAllNegative(int[]a) {
		int k=Integer.MIN_VALUE;
		for(int i=0;i<a.length;i++) {
			if(a[i]>=0) {
				return 0;
			}
			else {
				k=Math.max(k, a[i]);
			}
		}
		return k;
	}
	
	
	static void maxSumArrayLessCode(int[] a) {
        int maxGlobal = a[0], maxEndingHere = a[0];
		for(int i=1;i<a.length;i++) {
			maxEndingHere = Math.max(maxEndingHere+a[i], a[i]);
            maxGlobal = Math.max(maxEndingHere,maxGlobal);
		}
		System.out.println("Max Sum is : ."+maxGlobal);
	}
	
	static void maxProdductArrayLessCode(int[] a) {
		int maxGlobal = a[0], maxEndingHere = a[0], minEndingHere = a[0];
		for(int i=1;i<a.length;i++) {
			int m = maxEndingHere;
			int n = minEndingHere;
			maxEndingHere = Math.max(Math.max(m*a[i], n*a[i]),a[i]);
			minEndingHere =  Math.min(Math.min(m*a[i], n*a[i]),a[i]);
			maxGlobal = Math.max(maxGlobal, maxEndingHere);
		}
		System.out.println("Max Product is : ."+maxGlobal);
	}
	
	//T(n) = 2T(n/2) + Θ(n)  
	// can be solved either using Recurrence Tree method or Master method
	//   Θ(nLogn).
	static int maxSumSubArrayHelper(int[]a, int start, int end) {
		// Base Case: Only one element
		if(start==end) {
			return a[start];
		}
		
		int mid = (start+end)/2;
		return Math.max(Math.max(maxSumSubArrayHelper(a, start, mid), 
								 maxSumSubArrayHelper(a, mid+1, end)), 
								 crossSum(a, start, mid, end));
	}
	
	static int crossSum(int[]a, int start, int mid, int end) {
		//easily find the crossing sum in linear time. 
		//The idea is simple, find the maximum sum 
			//fix starting from mid point and ending at some point on left of mid, 
		//then find the maximum sum 
			//fix starting from mid + 1 and ending with sum point on right of mid + 1. 
		//Finally, combine the two and return.
		int leftAtCurMid = Integer.MIN_VALUE; int rightAtCurMid=Integer.MIN_VALUE;
		int sum=0;
		for(int i=mid;i>=start;i--) {
			sum+=a[i];
			leftAtCurMid = Math.max(leftAtCurMid, sum);
		}
		sum=0;
		for(int i=mid+1;i<=end;i++) {
			sum+=a[i];
			rightAtCurMid = Math.max(rightAtCurMid, sum);
		}
		return leftAtCurMid+rightAtCurMid;
	}
	
	static void minimumEditDistanceExample() {
		String word1 = "adceg";
		String word2 = "abcfg";
		System.out.println(minimumEditDistance(word1,word2));
	}
	
	static int minimumEditDistance(String word1, String word2){
		
		int m = word1.length();
		int n = word2.length();
		
		int[][]dp=new int[m+1][n+1];
		
		for(int j=0;j<=n;j++) {
			dp[0][j]=j;
		}
		
		for(int i=0;i<=m;i++) {
			dp[i][0]=i;
		}
		
		
		for(int i=1;i<=m;i++) {
			for(int j=1;j<=n;j++) {
				if(word1.charAt(i-1)==word2.charAt(j-1)) {
					dp[i][j] = dp[i-1][j-1];
				}
				else {
					dp[i][j] = Math.min(Math.min(dp[i-1][j-1],dp[i][j-1]),dp[i-1][j])+1;
				}
			}
		}
		
		string_ValueOfMatrix(dp);
		
		return dp[m][n];
		
	}
	
	static void string_ValueOfMatrix(int[][]matrix) {
		System.out.println("Matrix is: -> ");
		for(int i=0;i<matrix.length;i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}
	}
}
