
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class DifHel {
	
	//La classe permette di gestire un protocollo Diffie-Hellman.
	//Strutturata per avere un istanza per parte.
	
    private final SecureRandom sr;

    private static final int NBIT=256;
    
    private BigInteger p;
    private BigInteger g;
    
    private BigInteger x;
    private BigInteger y;
    private BigInteger k;
    
    //Per rendere il protocollo + realistico andiamo ad utilizzare numeri molto grandi, questo
    //richiede di usare una classe come BigInteger per gestire al meglio numeri interi grandi.
    
    public DifHel( BigInteger p, BigInteger g, String algorithm) throws NoSuchAlgorithmException {

    	this.p = p;
        this.g = g;
        
        sr = SecureRandom.getInstance(algorithm);        
    }

    public BigInteger setX() {
    	
    	do {
            this.x = new BigInteger(NBIT, sr);
        } while (x.signum() <= 0); 
    	
    	return x;
    	//Controlliamo che x sia positivo a causa dell'implementazione di modPow
    	//della classe BigInteger. 
    	
    	//Andiamo a generare un x da 256 bit per garantire un buon livello di sicurezza,
    	//anche su x sono possibili attacchi di brute forcing, infatti y=(g^x)mod p e 
    	//l'attaccante conosce y,g e p.
    
    }
    
    public BigInteger getY() {
    	
    	y = g.modPow(x, p);
    	return y;
    	
    }
    
    public BigInteger getK(BigInteger yb) {

    	k= yb.modPow(x, p);
    	return k;
    }
    
    
}
