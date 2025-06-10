
import java.math.BigInteger;
import java.util.Arrays;

import java.security.MessageDigest;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Cifrario {
	
	private static final String ALGO= "AES/ECB/PKCS5Padding";
	
    private final Cipher cifrario;
    private final SecretKey k;
    
    public Cifrario(BigInteger segreto) throws Exception {
    	
    	this.cifrario = Cipher.getInstance(ALGO);
    	
    	byte[] bytes = MessageDigest.getInstance("SHA-256").digest(segreto.toByteArray());
    	this.k = new SecretKeySpec(Arrays.copyOf(bytes, 16), "AES");
    	
    	//Andiamo a generare un hash del segreto, cosi da poterlo usare come chiave della cifratura simmetrica    	
    	
    }
    
    public byte[] encrypt(String plaintext) throws Exception {

    	cifrario.init(Cipher.ENCRYPT_MODE, k);
    	return cifrario.doFinal(plaintext.getBytes());
    	
    }
    
    public String decrypt(byte[] ciphertext) throws Exception {

    	cifrario.init(Cipher.DECRYPT_MODE, k);
    	byte [] decripted = cifrario.doFinal(ciphertext);
    	return new String(decripted);
    	
    }
	
}
