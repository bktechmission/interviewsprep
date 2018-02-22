package tests;

import java.util.Locale;

public class test1 {
    private static final int SHORT_GUID_LENGTH = 32;
	public static void main(String[] args)
	{
		System.out.println("Hello World");
		String guid = "2577a5b243744de6b3a7134b7fb3bab3";
		String guid1 = getGUID(guid);
         
        System.out.println(guid);
        System.out.println(guid1);
        System.out.println(Locale.US);
	}
	
    private static String convertToLongGuid(String shortguid) {
        final char[] charShort = shortguid.toCharArray();
        final char[] charLong = new char[36];
        System.arraycopy(charShort, 0, charLong, 0, 8);
        System.arraycopy(charShort, 8, charLong, 9, 4);
        System.arraycopy(charShort, 12, charLong, 14, 4);
        System.arraycopy(charShort, 16, charLong, 19, 4);
        System.arraycopy(charShort, 20, charLong, 24, 12);
        setDelimiters(charLong);
        return new String(charLong);
    }

    private static void setDelimiters(char[] charLong) {
        charLong[8] = '-';
        charLong[13] = '-';
        charLong[18] = '-';
        charLong[23] = '-';
    }

    public static String getGUID(String shortGuid) {
        return isShortGUID(shortGuid) ? convertToLongGuid(shortGuid) : shortGuid;
    }
    private static boolean isShortGUID(String guid) {
        return null != guid && SHORT_GUID_LENGTH == guid.length();
    }
}
