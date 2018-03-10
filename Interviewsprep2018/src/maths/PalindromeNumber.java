package maths;

public class PalindromeNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(isPalindromeNumber(356653));
	}
	
	static boolean isPalindromeNumber(int n) {
		int temp = n;
		int div = 1;
		while(temp/div>10) {		// 435  div=1  100
			div*=10;
		}
		
		while(temp>0) {
			int left = temp/div;
			int right = temp%10;
			if(left!=right) {
				return false;
			}
			temp%=div;
			temp/=10;
			div/=100;
		}
		return true;
	}
}
