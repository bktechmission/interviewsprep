package programcreek;

import java.util.Arrays;

public class Hash {
    public static void main(String[] args)
    {
        String txt = "Hello World";
        System.out.println(Hash.md5(txt).length());	//b10a8db164e0754105b7a99be72e3fe5
        System.out.println(Hash.sha1(txt).length());	//0a4d55a8d778e5022fab701977c5d840bbc486d0
    }
	/**
     * 
     * @param txt, text in plain format
     * @param hashType MD5 OR SHA1
     * @return hash in hashType 
     */
    public static String getHash(String txt, String hashType) {
        try {
	            java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
	            System.out.println("array is "+Arrays.toString(txt.getBytes()));
	            byte[] array = md.digest(txt.getBytes());
	            StringBuffer sb = new StringBuffer();
	            System.out.println("array is "+Arrays.toString(array));
	            for (int i = 0; i < array.length; ++i) {
	            		System.out.println(array[i] & 0xFF);
	                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	            }
                    return sb.toString();
            } catch (java.security.NoSuchAlgorithmException e) {
                //error action
            }
            return null;
    }

    public static String md5(String txt) {
        return Hash.getHash(txt, "MD5");
    }

    public static String sha1(String txt) {
        return Hash.getHash(txt, "SHA1");
    }
}