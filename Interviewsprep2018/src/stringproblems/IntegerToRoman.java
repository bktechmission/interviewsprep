package stringproblems;

import java.util.HashMap;
import java.util.Map;

public class IntegerToRoman {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		convertoRommanExample();
	}
	
	
	// romans are read from right to left
	// I  V  X   L   C  D  M
	static String[] symbols = {
			"M", "CM", "D",  "CD",
			"C",  "XC", "L",  "XL",
			"X",  "IX", "V",  "IV",
			"I"
	};
	
	static int[] values = {
		1000, 900, 500, 400,
		100,  90,  50,  40,
		10,	  9,   5,   4,
		1
	};
	
	
	// 1-3999
	static void convertoRommanExample() {
		int num = 2567;
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<values.length; i++) {
			int k = num/values[i];
			for(int j=0;j<k;j++) {
				sb.append(symbols[i]);
				num-=values[i];
			}
		}
		
		System.out.println("Number in roman is : "+sb.toString());
		
		System.out.println("Number is : "+ romanToInteger(sb.toString()));
	}
	
	// romans are in decreasing order so when we find prev<cur that means it is a subtraction case
	static int romanToInteger(String s) {
		int result = 0;
		
		int prev = 0;
		int i = 0;
		while(i<s.length()) {		//XIX =  19  ==>  10+ 1 + (10-2) ==> 19
			int cur = symbToValueMap.get(s.charAt(i));
			result += (cur>prev) ? (cur-2*prev) : cur;
			prev = cur;
			i++;
		}
		
		return result;
	}
	
	static final Map<Character,Integer> symbToValueMap = new HashMap<>();
	static {
		symbToValueMap.put('I',1);
		symbToValueMap.put('V',5);
		symbToValueMap.put('X',10);
		symbToValueMap.put('L',50);
		symbToValueMap.put('C',100);
		symbToValueMap.put('D',500);
		symbToValueMap.put('M',1000);
	}
}
