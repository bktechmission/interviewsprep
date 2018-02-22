package tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;



public class TwoSums {
	private static int currentMedian = 0;
	private static final Queue<Integer> leftMaxHeap = new PriorityQueue<Integer>((e1,e2)->e2.compareTo(e1));
	private static final Queue<Integer> rightMinHeap = new PriorityQueue<Integer>();
	private static final String ALPHABET_62_BASE_ENCODING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final int BASE_62 = ALPHABET_62_BASE_ENCODING.length();
	private static final String HEXSTRING = "0123456789abcdef";
	private static final int HEXBASE = HEXSTRING.length();
	
	
	public static void main(String[] args)
	{
		int[] a = {1,2,3,4,5,6,7,8,9,10,11,12};
		rotateArray(a,3);
		Arrays.stream(a).forEach(x->System.out.print(x +","));
		int[] k1 ={1,45,32,2,19,21};
		int[] k2 = {54,45,90,2,21,34,98,78,91,121,1,78,65,56,111};
		intersectionOfUnsortedArray(k1,k2);
		intersectionOfUnsortedArrayWithBinarySearch(k1,k2);
		System.out.println("\n\n\n");
		Arrays.sort(k2);
		Arrays.stream(k1).forEach(x->{System.out.print(x+" ");});
		System.out.println();
		Arrays.stream(k2).forEach(x->{System.out.print(x+" ");});
		intersectionOfArrayWithMergeStep(k1,k2);
		
		System.out.println("\n\nBEfore\n\n");
		Arrays.stream(k1).forEach(x->{System.out.print(x+" ");});
		System.out.println();
		Arrays.stream(k2).forEach(x->{System.out.print(x+" ");});
		System.out.println("\n\nAfter\n\n");
		merge2ArraysInPlace(k1,k2);
		Arrays.stream(k1).forEach(x->{System.out.print(x+" ");});
		System.out.println();
		Arrays.stream(k2).forEach(x->{System.out.print(x+" ");});
		int[] c = {2,5,1,1,3,4,2,1,2,8,9,3,4,2};
		System.out.println();
		getMedianOfStreamOfInteger(c);
		//int n = removeDuplicates(c);
		int n = removeDupOofN(c);
		for(int i=0;i<n;i++)
		{
			System.out.print(c[i]+" ");
		}
		int[] a1 = {1,3,5,6,10,21,25};
		System.out.println("\nbsearch index "+ bsearchInsertPos(a1,7));
		
		int[] b1 = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9};
		System.out.println("\nfindmin is: "+findMin(b1));
		
		String hexString = decimalToHex(32);
		System.out.println("decimal to hex : "+hexString);
		
		int num = hexToInt(hexString);
		System.out.println("hex to decimal : "+num);
		
		long dbId = 4567891287L;
		String shortURL = urlEncoder(dbId);
		String shortDomain = "http://tinyurl.com/";
		System.out.println("tiny url : "+shortDomain+shortURL);
		
		long revertedDBId = urlDecoder(shortURL);
		System.out.println("tiny url to dbId : "+revertedDBId);
		
		System.out.println("decimalToBinary : "+deciamlToBinary(15));
		
		System.out.println("negabinary : "+negaBinary(10));
		
		System.out.println("negaBinary to int "+toBinaryFromNegaBinary(negaBinary(10)));
		
		int[] v = {12,4,5,-45,60,2};
		maxProfit(v);
		
		maxContiguousArray(v);
		
		int[] c1 = {2,34,12,64,78,10,100,90,1,3,5,6,7,78};
		radixSort(c1);
		Arrays.stream(c1).forEach(x->System.out.print(x+" "));
		
		int[] d1 = {1,2,3,4,5,6,7,8};
		int[] d2 = {9,10,11,12,13,14,15,16};
		System.out.println();
		System.out.println(medianOf2SortedArraySameSize(d1,d2,0,d1.length-1,0,d2.length-1));
		int[] d3 = {9};
		System.out.println(meadianOf2SortedArrayWithDiffSizes(d1,d3));
		Queue<Integer> pq = new LinkedList<Integer>();
		long t =10;
		int f = (int)t;
	}
	
	public int[] twoSumUnsorted(int[]a,int target) //O(n)time, O(n) space
	{
		Map<Integer,Integer> mapOfValToIndices = new HashMap<Integer,Integer>();
		for(int i=0;i<a.length;i++)
		{
			int x = a[i];
			if(mapOfValToIndices.containsKey((target-x)))
			{
				return new int[]{mapOfValToIndices.get(target-x)+1,i+1};
			}
			else{
				mapOfValToIndices.put(x, i);
			}
		}
		throw new IllegalArgumentException("No Such Element Found");
	}
	
	public int[] twoSumSortedArray(int[]a,int target)  // nlongn, O(1)
	{
		for(int i=0;i<a.length;i++)
		{
			int x = a[i];
			int j = bsearch(a,target-x,i+1);  // logn
			if(j!=-1)
				return new int[] {i+1,j+1};
		}
		throw new IllegalArgumentException("No Such Element Found");
	}
	
	private int bsearch(int[]a,int key, int start)  // iterative algo in long step find an element, only condition is array sorted
	{
		int L = start;
		int R = a.length-1;
		while(L<R)
		{
			int M = (L+R)/2;
			if(a[M]<key)
			{
				L = M+1;
			}
			else
			{
				R = M;
			}
		}
		return (L==R && a[L]==key)?L:-1;
	}
	
	public int[] twoSumSortedArrayWithOrderN(int[]a,int target)  // O(N), O(1)
	{
		// two pointer approach
		//a[i]+a[j] < target	i++
		// a[i]+a[j] > target   j--
		// a[i]+a[j] ==  target return {i+1,j+1};
		int i = 0, j = a.length-1;
		while(i<j)
		{
			if(a[i]+a[j]<target)
				i++;
			else if(a[i]+a[j]>target)
				j--;
			else
				return new int[]{i+1,j+1};
		}
		throw new IllegalArgumentException("No Such Element Found");
	}
	
	
	public int lengthOfLargestContiguousSumInArray(int[]a)
	{
		// In brute force
		// we take one element e, cal sum with each element e1,e2,e3 in sequence like e,e1 e,e1,e2 e,e1,e2,e3
		// it is an O(n^3) algo
		
		// We have another algo from Kadane's algo
		// keep track of maxCur and maxGlobal
		int maxCur = a[0], maxGlobal =a[0];
		for(int i=1;i<a.length;i++)
		{
			maxCur = Math.max(maxCur+a[i],a[i]);
			maxGlobal = Math.max(maxCur, maxGlobal);
		}
		return maxGlobal;
		
		
	}
	
	static void maxProfit(int[] a)
	{
		int diff = a[1]-a[0];
		int j = 0, buyIndex=0,sellIndex=1;
		int minimum = a[0];
		for(int i=1;i<a.length;i++)
		{
			if(a[i]-minimum > diff)
			{
				diff = a[i]-minimum;
				buyIndex = j;
				sellIndex = i;
			}
			if(a[i]<minimum)
			{
				minimum = a[i];
				j = i;
			}
		}
		System.out.println("BuyAt: "+a[buyIndex]+" and sellAt: "+a[sellIndex] +" and gain profit of: "+diff);
	}
	
	static void maxContiguousArray(int[]a)
	{
		int i=0,j=0;
		int start=0, end=0;
		int curSum=0,globalMax=0;
		for(i=0;i<a.length;i++)
		{
			curSum+=a[i];
			if(curSum>globalMax)
			{
				globalMax = curSum;
				start = j;
				end = i;
			}
			if(curSum<0)
			{
				curSum =0;
				j=i+1;
			}
		}
		
		System.out.println("start at: "+start+", end at: "+end +" and maxSum is:  "+globalMax);
	}
	
	public int lengthOfLargestContiguosProductInArray(int[]a)
	{
		int maxCur = a[0], minCur = a[0], maxGlobal=a[0];
		for(int i=1;i<a.length;i++)
		{
			int mn=minCur, mx=maxCur;
			maxCur = Math.max(Math.max(mx*a[i],a[i]),mn*a[i]);
			minCur = Math.min(Math.min(mx*a[i], a[i]),mn*a[i]);
			maxGlobal = Math.max(maxCur, maxGlobal);
		}
		return maxGlobal;
	}
	
	
	static void rotateArray(int[] a, int k)
	{
		int n = a.length;
		int indexFromStart = n-k;
		reverse(a,0,indexFromStart-1);
		reverse(a,indexFromStart,n-1);
		reverse(a,0,n-1);
	}
	
	static void reverse(int[]a,int start, int end)
	{
		while(start<end)
		{
			int temp = a[start];
			a[start++] = a[end];
			a[end--] = temp;
		}
	}
	
	// O(N)  O(N) space
	static void intersectionOfUnsortedArray(int[]a, int[]b)
	{
		// Brute force is: 2 loops: outer loop for each i, look for i+1 to n if there is any element exists O(N^2)
		System.out.println("\n\n\n");
		if(a.length<b.length)
		{
			final Set<Integer> set = Arrays.stream(a).boxed().collect(Collectors.toSet());
			Arrays.stream(b).boxed().filter(x->set.contains(x)).forEach(x->System.out.print(x +" "));
		}
		else
		{
			final Set<Integer> set = Arrays.stream(b).boxed().collect(Collectors.toSet());
			Arrays.stream(a).boxed().filter(x->set.contains(x)).forEach(x->System.out.print(x +" "));
		}
	}
	
	// O(NlogN)  O(1)
	static void intersectionOfUnsortedArrayWithBinarySearch(int[]a, int[]b)
	{
		System.out.println("\n\n\n");
		if(a.length<b.length)
		{
			Arrays.sort(a);
			Arrays.stream(b).boxed().filter(x-> (Arrays.binarySearch(a, x)>=0)).forEach(x->System.out.print(x+" "));
		}
		else
		{
			Arrays.sort(b);
			Arrays.stream(a).boxed().filter(x-> (Arrays.binarySearch(b, x)>=0)).forEach(x->System.out.print(x+" "));
		}
	}
	
	
	
	// O(a+b) merge step filter out common element
	static void intersectionOfArrayWithMergeStep(int[]a,int[]b)
	{
		int i=0;
		int j=0;
		System.out.println("\n\n\n");
		while(i<a.length && j<b.length)
		{
			if(a[i]<b[j])
				i++;
			else if(a[i]>b[j])
				j++;
			else{
				System.out.print(a[i] + " ");
				i++;
				j++;
			}
		}
	}
	
	static void merge2ArraysInPlace(int[]a,int[]b)
	{
		int m = a.length, n =b.length;
		for(int i=n-1;i>=0;i--)
		{
			int j = m-1;
			int last = a[j];
			boolean foundASpot = false;
			while(j>=0)
			{
				if(a[j]>b[i])
				{	foundASpot = true;
					// means b[i] lie somewhere inside of a, start making space for it by moving elements towards end.
					if(j!=m-1)		// make sure jth spot is not last index of a
						a[j+1] = a[j];
				}
				else
					break;	// found jth spot to put a[i]
				j--;
			}
			if(foundASpot)
			{
				if(j!=m-1)
					a[j+1] = b[i];
				else
					a[j] = b[i];
				b[i] = last;
			}
		}
	}
	
	
	static void getMedianOfStreamOfInteger(int[] a)
	{
		for(int i=0;i<a.length;i++)
		{
			System.out.println("Median is : "+ getMedian(a[i]));
		}
	}
	static int getMedian(int x)
	{
		int diff = leftMaxHeap.size()-rightMinHeap.size();
		int signal = diff>0?1:(diff<0?-1:0);
		switch(signal)
		{
		case 1:
			if(x<currentMedian)
			{
				rightMinHeap.offer(leftMaxHeap.poll());
				leftMaxHeap.offer(x);
			}
			else{
				rightMinHeap.offer(x);
			}
			currentMedian = (leftMaxHeap.peek()+rightMinHeap.peek())/2;
			break;
		case 0:
			if(x<currentMedian)
			{
				leftMaxHeap.offer(x);
				currentMedian = leftMaxHeap.peek();
			}
			else
			{
				rightMinHeap.offer(x);
				currentMedian = rightMinHeap.peek();
			}
			break;
		case -1:
			if(x<currentMedian)
			{
				leftMaxHeap.offer(x);
			}
			else
			{
				leftMaxHeap.offer(rightMinHeap.poll());
				rightMinHeap.offer(x);
			}
			currentMedian = (leftMaxHeap.peek()+rightMinHeap.peek())/2;
			break;
		}
		return currentMedian;
	}
	
	// Without anything extra, inplace O(n^2)
	static int removeDuplicates(int[]a)
	{
		int tail=1;
		for(int i=1;i<a.length;i++)
		{
			int j=0;
			while(j<tail)
			{
				if(a[j] == a[i])
				{
					break;
				}
				j++;
			}
			if(j==tail)
			{
				a[tail] = a[i];
				tail++;
			}
		}
		return tail;
	}
	
	
	// O(N) use hashset to keep track of element found so far.
	static int removeDupOofN(int[]a)
	{
		Set<Integer> set = new HashSet<>();
		int tail = 0;
		for(int i=0;i<a.length;i++)
		{
			if(!set.contains(a[i]))
			{
				set.add(a[i]);
				a[tail] = a[i];
				tail++;
			}
		}
		return tail;
	}
	
	static int bsearchInsertPos(int[]a, int x)
	{
		int l = 0;
		int r = a.length-1;
		while(l<r)
		{
			int m = (l+r)/2;
			if(a[m]<x)
				l = m+1;
			else
				r = m;
		}
		return a[l]<x?l+1:l;
	}
	
	static int bsearch(int[]a, int x)
	{
		int l = 0;
		int r = a.length-1;
		while(l<r)
		{
			int m = (l+r)/2;
			if(a[m]<x)
				l = m+1;
			else 
				r = m;
		}
		return (l==r && a[l]==x)?l:-1;
	}
	
	static int findMin(int[] a)
	{
		int l=0;
		int r=a.length-1;
		while(l<r&&a[l]>=a[r])
		{
			int m = (l+r)/2;
			if(a[m]>a[r])
				l = m+1;
			else
				r = m;
		}
		return a[l];
		
	}
	
	int bsearchRotated(int[]a, int l, int r, int x)
	{
		if(l>r)
			return -1;
		int m = (l+r)/2;
		if(a[m]==x)
			return m;
		if(a[m]>=a[l])
		{
			if(x>=a[l] && x<=a[m])
				return bsearchRotated(a,l,m-1,x);
			return bsearchRotated(a,m+1,r,x);
		}
		if(x>=a[m]&&x<=a[r])
			return bsearchRotated(a,m+1,r,x);
		return bsearchRotated(a,l,m-1,x);
	}
	
	// O(d*(n+base))  where d = logb(k)   k=maxNumber   k<=n*c
	static void radixSort(int[]a)
	{
		int max = max(a);
		for(int exp=1;max/exp>0;exp*=10)
		{
			countingSort(a,exp,a.length);
		}
	}
	
	static void countingSort(int[]a,int exp,int n)
	{
		int[] output = new int[n];
		int[] count = new int[10];
		
		// do the counting for each bucket
		for(int i=0;i<a.length;i++)
		{
			count[(a[i]/exp)%10]++;
		}
		
		// now use count to make actual index
		for(int i=1;i<count.length;i++)
		{
			count[i]+=count[i-1];
		}
		
		// start from last index of a[i], get its index from count-1 and then count--
		for(int i=n-1;i>=0;i--)
		{
			output[count[(a[i]/exp)%10]-1] = a[i];
			count[(a[i]/exp)%10]--;
		}
		
		for(int i=0;i<a.length;i++)
		{
			a[i] = output[i];
		}
	}
	static int max(int[]a)
	{
		int max = Integer.MIN_VALUE;
		for(int i=0;i<a.length;i++)
		{
			if(max<a[i])
				max = a[i];
		}
		return max;
	}
	
	static String urlEncoder(long n)
	{
		StringBuilder sb = new StringBuilder();
		while(n!=0)
		{
			long rem = n%BASE_62;
			sb.append(ALPHABET_62_BASE_ENCODING.charAt((int)rem));
			n/=BASE_62;
		}
		return sb.reverse().toString();
	}
	
	static long urlDecoder(String s)
	{
		long num = 0L;
		for(int i=0;i<s.length();i++)
		{
			num = num*BASE_62 + ALPHABET_62_BASE_ENCODING.indexOf(s.charAt(i));
		}
		return num;
	}
	
	static String decimalToHex(int num)
	{
		StringBuilder sb = new StringBuilder();
		while(num!=0)
		{
			int rem = num%HEXBASE;
			sb.append(HEXSTRING.charAt(rem));
			num/=HEXBASE;
		}
		return sb.reverse().toString();
	}
	
	static int hexToInt(String hexString)
	{
		int num = 0;
		for(int i=0;i<hexString.length();i++)
		{
			num = num*HEXBASE + HEXSTRING.indexOf(hexString.charAt(i));
		}
		return num;
	}
	
	static String deciamlToBinary(int num)
	{
		StringBuilder sb = new StringBuilder();
		while(num!=0)
		{
			int rem = num%2;
			sb.append("01".charAt(rem));
			num/=2;
		}
		return sb.reverse().toString();
	}
	
	
	static String negaBinary(int num)
	{
		StringBuilder sb = new StringBuilder();
		int base = -2;
		while(num!=0)
		{
			int rem = num%base;
			num = num/base;
			if(rem<0)
			{
				rem-=base;
				num+=1;
			}
			sb.append("01".charAt(rem));
		}
		return sb.reverse().toString();
	}
	
	static int toBinaryFromNegaBinary(String s)
	{
		int num = 0;
		int base = -2;
		for(int i=0;i<s.length();i++)
		{
			num=num*base+"01".indexOf(s.charAt(i));
		}
		return num;
	}
	
	// Brute force takes all n(n-1)/2 substrings do n logn for palindrome check O(N^3)
	// DP problem we know  O(N^2) amd O(N^2) space
	//  1. single character is palindrome, 
	//  2. Also dp[i][j] = dp[i+1][j-1] && str.chartAt(i)==str.charAt(j) 
	//  3. adjacent chars d[i][i+1] = str.charAt(i)==str.charAt(i+1)
	String longestPalindrome(String s)
	{
		boolean[][] dp = new boolean[s.length()][s.length()];
		int N = s.length();
		// single char is palindrome
		for(int i=0;i<s.length();i++)
		{
			dp[i][i] = true;
		}
		
		String ret = new String() + s.charAt(0);
		int max =1;
		
		for(int i=N-2;i<=0;i--)
		{
			for(int j=i+1;j<N;j++)
			{
				// adjacent chars
				if((j-i)==1)
				{
					dp[i][j] = s.charAt(i)==s.charAt(j);
				}
				else
				{
					// no adjacent chars
					dp[i][j] = s.charAt(i)==s.charAt(j) && dp[i+1][j-1];
				}
				// check if we have (j-i+1) as maxlength palindrome
				if(dp[i][j] && (j-i+1)>max)
				{
					max = j-i+1;
					ret = s.substring(i, j+1);
				}
			}
		}
		return ret;
	}
	
	
	// Expand Around Center O(N^2) time and O(1) space.
	// Idea is there are n and n-1 centers so total 2n-1 centers around which palindrome can be there
	String longestPalindromeExpansionWay(String s)
	{
		int start=0, end =0;
		for(int i=0;i<s.length();i++)
		{
			int len1 = expandAroundCenter(s,i,i);
			int len2 = expandAroundCenter(s,i,i+1);
			int len = Math.max(len1, len2);
			if(len>(end-start))
			{
				start = i-(len-1)/2;
				end = i+(len)/2;
			}
		}
		return s.substring(start,end+1);
	}
	
	int expandAroundCenter(String s, int l, int r)
	{
		while(l>=0 && r<s.length() && s.charAt(l)==s.charAt(r))
		{
			l--;
			r++;
		}
		
		return r-l-1;
	}
	
	// Max Reach Jump algo
	// idea is keep track of max reach so far by max(maxReachSoFar,a[i]+i)
	int maxReach(int[] a)
	{
		int reach = 0;
		int step=0;
		int tempreach = 0;
		for(int i=0;i<a.length;i++)
		{
			if(i>reach)
			{
				reach = tempreach;
				step++;
				if(reach>=(a.length-1))
					break;
			}
			tempreach = Math.max(reach,a[i]+i);
		}
		return step;
	}
	
	static int medianOf2SortedArraySameSize(int[] a, int[]b,int aStart,int aEnd, int bStart, int bEnd)
	{
		int N = aEnd-aStart+1;
		if(N<1)
			return -1;
		if(N==1)
			return (Math.max(a[aStart],b[bStart]));
		if(N==2)
			return (Math.max(a[aStart], b[bStart])+Math.min(a[aEnd], b[bEnd]))/2;
		
		int m1 = median(a,aStart,N);
		int m2 = median(b,bStart,N);
		if(m1==m2)
			return m1;
		else if(m1<m2)
		{
			if(N%2==0)
				return medianOf2SortedArraySameSize(a, b, aStart+N/2-1, aEnd,bStart,bStart+N/2);
			return medianOf2SortedArraySameSize(a, b, aStart+N/2, aEnd,bStart,bStart+N/2);
		}
		else
		{
			if(N%2==0)
				return medianOf2SortedArraySameSize(a, b, aStart, aStart+N/2,bStart+N/2-1,bEnd);
			return medianOf2SortedArraySameSize(a, b, aStart, aStart+N/2,bStart+N/2,bEnd);
			
		}
		
	}
	static int median(int[]a,int start,int N)
	{
		if(N%2==0)
		{
			return (a[start+(N/2-1)]+a[start+N/2])/2;
		}
		else
			return a[start+N/2];
	}
	
	static int meadianOf2SortedArrayWithDiffSizes(int[]a,int[]b)
	{
		int m = a.length, n=b.length;
		if((m+n)%2!=0)
		{
			return findKthNumber(a,b,(m+n)/2,0,m-1, 0, n-1);
		}
		else 
		{
			return (findKthNumber(a,b,(m+n)/2-1,0,m-1, 0, n-1)+findKthNumber(a,b,(m+n)/2,0,m-1, 0, n-1))/2;
		}
	}
	
	static int findKthNumber(int[]a, int[]b, int k, int aStart, int aEnd, int bStart, int bEnd)
	{
		int alen = aEnd-aStart+1;
		int blen = bEnd-bStart+1;
		
		if(alen==0)
			return b[k-1];
		if(blen==0)
			return a[k-1];
		
		if(k==0)
			return a[aStart]>b[bStart]?b[bStart]:a[aStart];
			
		int aMid = k*(alen)/(alen+blen);
		int bMid = k - aMid - 1;
		
		aMid = aMid+aStart;
		bMid = bMid + bStart;
		
		if(a[aMid]>b[bMid])
		{
			k = k-(bMid-bStart+1);
			aEnd = aMid;
			bStart = bMid+1;
			
		}
		else
		{
			k = k-(aMid-aStart+1);
			bEnd = bMid;
			aStart= aMid+1;
		}
		
		return findKthNumber(a,b,k,aStart,aEnd,bStart,bEnd);
	}
}
