package maths;

import java.util.ArrayList;
import java.util.List;

public class PlusOne {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<>();
		list.add(2);
		list.add(4);
		list.add(7);
		list.add(9);
		plusOneExample(list);
		System.out.println(list);
	}
	
	static void plusOneExample(List<Integer>list) {
		for(int i=list.size()-1;i>=0;i--) {
			int digit = list.get(i);
			if(digit<9) {
				list.set(i, digit+1);
				return;
			}
			else {
				list.set(i, 0);
			}
		}
		list.add(0);
		list.set(0, 1);
	}
}
