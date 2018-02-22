package stringtesting;

import java.util.BitSet;

public class StringsInterviewQuestions {
	public static void main(String[] args) {
		String testString = "sesaam";
		System.out.println(removeDups(testString));
		System.out.println(removeDupsEff(testString));
	}

	public static String removeDups(String str) {
		char[] dups = str.toCharArray();
		int tail = 1;
		
		for(int i = 1; i < str.length(); i++)
		{
			int j;
			for(j = 0 ; j < tail; j++)
			{
				if(dups[j] == dups[i])
				{
					break;
				}
			}
			if(j == tail)
			{
				dups[tail] = dups[i];
				tail++;
			}
		}
		dups[tail] = ',';
		return new String(dups);
		
		
	}

	public static String removeDupsEff(String str) {
		StringBuilder sb = new StringBuilder();
		BitSet bst = new BitSet();
		
		bst.set(str.charAt(0));
		sb.append(str.charAt(0));
		for(int i = 1; i < str.length(); i++)
		{
			if(!bst.get(str.charAt(i)))
			{
				sb.append(str.charAt(i));
				bst.set(str.charAt(i));
			}
		}

		return sb.toString();
	}

}
