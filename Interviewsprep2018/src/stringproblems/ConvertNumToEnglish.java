package stringproblems;

public class ConvertNumToEnglish {

	public static void main(String[] args) {
		convertNumToEnglishExample();
	}
	
	static String[] THOUSANDS = {"","THOUSAND","MILLION","BILLION"};
	
	static void convertNumToEnglishExample() {
		int num = 1234567;
		
		String out = "";
		int i = 0;
		while(num!=0) {
			out = helperForBelowThousands(num%1000) + " "+THOUSANDS[i] +" " + out;
			num /= 1000;
			i++;
		}
		System.out.println("In Englis is: "+out);
	}
	
	static String helperForBelowThousands(int n) {
		if (n < 20) {	// 15
            return units[n];
        }

        if (n < 100) {		//99
            return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
        }

        if (n < 1000) {	//535
            return units[n / 100] + " hundred" + ((n % 100 != 0) ? " " : "") + helperForBelowThousands(n % 100);
        }
        return "";
	}
	
	public static final String[] units = {
            "", "one", "two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen",
            "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    public static final String[] tens = {
            "",        // 0
            "",        // 1
            "twenty",  // 2
            "thirty",  // 3
            "forty",   // 4
            "fifty",   // 5
            "sixty",   // 6
            "seventy", // 7
            "eighty",  // 8
            "ninety"   // 9
    };
}
