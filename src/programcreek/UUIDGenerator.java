package programcreek;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

public class UUIDGenerator {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		UUID uiid = UUID.randomUUID();	// 32 hex or 128 bits
		System.out.println(uiid.toString());
		
		//generate your own: very heavy take lot of time
		long start = System.currentTimeMillis();
		SecureRandom prng = new SecureRandom();
		long end = System.currentTimeMillis();
		System.out.println("total time is "+(end-start));
		
		String value = String.valueOf(prng.nextInt(32));
		
		System.out.println(value);
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] data = md.digest(value.getBytes());
		
		String hexString = hexEncode(data);
		System.out.println(hexString);
		
	}
	
	private static String hexEncode(byte[] data) {
		char[] a = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
		StringBuilder result = new StringBuilder();
		for(int i=0;i<data.length;i++)
		{
			byte b = data[i];
			result.append(a[(data[i]&0xF0)>>4]);
			result.append(a[(data[i]&0x0F)]);
		}
		return result.toString();
	}
	
	
	
	
}
