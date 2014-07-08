package DatabaseConnection;

//import java.net.URLDecoder;
//import java.net.URLEncoder;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/*
 * Reference :
 *  http://www.code2learn.com/2011/06/encryption-and-decryption-of-data-using.html
 */

public class AESCrypto {
	private static final String ALGORITHM = "AES";
	private static final String sharedKey = "AakashClickerApplication";
	private static final byte[] privateKey =  sharedKey.getBytes();

	public static String encrypt(String msg) throws Exception {
		Key mykey = keyGenerator();
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, mykey);
		byte[] encMsg = cipher.doFinal(msg.getBytes());
		String encryptedMsg = new BASE64Encoder().encode(encMsg);
		return encryptedMsg;
	}
	
    public static String decrypt(String cipherMsg) throws Exception {
        Key mykey = keyGenerator();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, mykey);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(cipherMsg);
        byte[] decMsg = cipher.doFinal(decordedValue);
        String decryptedMsg = new String(decMsg);
        return decryptedMsg;
    }
    
    private static Key keyGenerator() throws Exception {
        Key myKey = new SecretKeySpec(privateKey, ALGORITHM);
        return myKey;
    }
    
    /*public static void main(String[] args) throws Exception {
        String password = "No MACADDRESS";
        String cipher = AESCrypto.encrypt(password);
        String msg = AESCrypto.decrypt(cipher);
	    System.out.println("Plain Text : " + password);
        System.out.println("Encrypted Text : " + cipher);
        System.out.println(URLEncoder.encode(cipher,"ISO-8859-1"));
        System.out.println(URLDecoder.decode(URLEncoder.encode(cipher,"ISO-8859-1"), "ISO-8859-1"));
        System.out.println("Decrypted Text : " + msg);
    }*/
}