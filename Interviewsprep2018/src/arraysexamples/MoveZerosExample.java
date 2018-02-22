package arraysexamples;

import java.util.Arrays;

public class MoveZerosExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		moveZerosExample();
	}
	
	static void moveZerosExample(){
		int[]a = {0,1,2,3,0,0,0,4,5,0,6,7,0,0,8,9};
		moveZerosToFront(a);
		System.out.println("Arays to String : "+Arrays.toString(a));
		int[]b = {0,1,2,3,0,0,0,4,5,0,6,7,0,0,8,9};
		moveZerosToEnd(b);
		System.out.println("Arays to String : "+Arrays.toString(b));
	}
	
	static void moveZerosToFront(int[]a) {
		int pos=a.length-1;
		
		for(int i=a.length-1;i>=0;i--) {
			if(a[i]==0) {
				continue;
			}
			a[pos] = a[i];
			pos--;
		}
		while(pos>=0) {
			a[pos]=0;
			pos--;
		}
	}
	
	static void moveZerosToEnd(int[]a) {
		int pos=0;
		for(int i=0;i<a.length;i++) {
			if(a[i]==0)
			{
				continue;
			}
			a[pos]=a[i];
			pos++;
		}
		
		while(pos<a.length) {
			a[pos]=0;
			pos++;
		}
	}

}
