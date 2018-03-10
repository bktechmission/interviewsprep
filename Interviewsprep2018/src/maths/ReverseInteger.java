package maths;

public class ReverseInteger {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(reverse(123));
	}
	
	
	//   123  321   10  1000    overflow case
	static int reverse(int x) {
		
		int ret =0;  int overflowMaxCheck = Integer.MAX_VALUE/10;
		while(x!=0) {
			if(x>overflowMaxCheck) {
				return 0;
			}
			ret = ret*10+x%10;
			x/=10;
		}
		return ret;
	}

}
