package backtrackingNBFS;

import java.util.LinkedList;
import java.util.List;

public class RestoreIPsProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		restoreIpsExample();
	}
	
	static void restoreIpsExample() {
		String s = "25525511135";
		List<String> finalResult = new LinkedList<>();
		
		restoreIps(s, 0, 1, "", finalResult);
		
		System.out.println(finalResult);
		
	}
	
	static void restoreIps(String s, int start, int seg, String prefix, List<String> finalResult) {
		
		if(start>=s.length()) {
			return;
		}
		
		if(seg==4) {
			if(isValid(s.substring(start))) {
				finalResult.add(prefix+"."+s.substring(start));
			}
			return;
		}
		
		
		for(int k=start+1, c=1; k<s.length() && c<4; k++,c++) {
			String sub = s.substring(start, k);
			if(isValid(sub)) {
				restoreIps(s, k, seg+1, seg==1?sub:prefix+"."+sub, finalResult);
			}
		}
	}
	
	static boolean isValid(String s) {
		// 23456 case
		if(s==null || s.length()>3) {
			return false;
		}
		
		// 003 case
		if(s.charAt(0)=='0' && s.length()>1) {
			return false;
		}
		
		int num = Integer.parseInt(s);
		if(num>=0 && num<= 255) {
			return true;
		}
		return false;
	}
}
