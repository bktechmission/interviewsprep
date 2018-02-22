package leetcodeexamples;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class NumbersExamples {
	public static void main(String[] args) {
		uglyNumbers();
	}
	
	
	public static void uglyNumbers() {
		int n = 50;
		
		int i=0;
		int j=0;
		int k=0;
		
		int count=1;
		int[] result = new int[n+1];
		result[0]=1;
		
		while(count<=n) {
			int x = result[i]*2;
			int y = result[j]*3;
			int z = result[k]*5;
			
			int min = Math.min(x, Math.min(y, z));
			result[count++] = min;
			if(min==x) {
				i++;
			}
			if(min==y) {
				j++;
			}
			if(min==z) {
				k++;
			}
		}
		System.out.println(Arrays.toString(result));
		uglyNumbersUsingHeap();
		
		System.out.println(isUgly(40));
		
		divideInPrimes(40);
		
		divideIn2sPowers(41);
		
		
	}
	
	// generic for primes array
	public static void uglyNumbersUsingHeap() {
		int n=50;
		Queue<Integer> pq = new PriorityQueue<>();
		Set<Integer> set = new HashSet<Integer>();
		pq.add(1);
		int[] result = new int[n+1];
		int[] primes = {2,3,5};
		int count=0;
		while(!pq.isEmpty()&&count<=n) {
			int p = pq.poll();
			result[count++]=p;
			
			for(int i=0;i<primes.length;i++) {
				int num = p*primes[i];
				if(!set.contains(num)) {
					pq.offer(num);
					set.add(num);
				}
			}
		}
		
		System.out.println(Arrays.toString(result));
		System.err.println("priority queue size is:  "+pq.size());
	}
	
	static void divideIn2sPowers(int n) {
		int pow=0;
		System.out.print(n+" in 2's powers: ->  [");
		while(n>0) {
			if((n&1)==1) {
				System.out.print("2^"+pow+"+");
			}
			pow+=1;
			n>>>=1;
		}
		System.out.print("]");
	}
	
	public static boolean isUgly(int n) {
		if(n==1)
			return true;
		
		while(n%2==0) {
			n=n/2;
		}
		while(n%3==0) {
			n=n/3;
		}
		
		while(n%5==0) {
			n=n/5;
		}
		return n==1;
	}
	
	public static void divideInPrimes(int n) {
		Map<Integer, Integer> map = new HashMap<>();
		int count=0;
		while(n%2==0) {
			n>>=1;
			count++;
		}
		if(count>0) {
			map.put(2, count);
		}
		
		count=0;
		while(n%3==0) {
			n>>=1;
			count++;
		}
		if(count>0) {
			map.put(3, count);
		}
		int inc=2;
		for(int i=5;i<=n;i+=inc) {
			count=0;
			while(n%i==0) {
				n/=i;
				count++;
			}
			if(count>0) {
				map.put(i, count);
			}
			inc^=2^4;
		}
		System.out.print(n+" can be divided in primes powers as:-> ");
		map.entrySet().forEach(x->{
			System.out.print(x.getKey()+"^"+x.getValue()+"+");
		});
		System.out.println();
	}
	//    2 3 5
}
