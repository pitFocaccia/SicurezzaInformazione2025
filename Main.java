
import java.math.BigInteger;
import java.util.Base64;

public class Main {
	
	//Andiamo a simulare uno scambio Diffie-Hellman tra due entità Alice e Bob, 
	//una volta concordata una chiave la utilizzeranno per avviare una comunicazione
	//criptata.
	
	
public static void main(String[] args) throws Exception {

		CanaleComunicazione c = new CanaleComunicazione(); //Simula un eventuale canale di comunicazione tra Alice e Bob.
		 
		BigInteger pA = new BigInteger(
			"FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD1" +
			"29024E088A67CC74020BBEA63B139B22514A08798E3404DD" + 
			"EF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245" +
			"E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7ED" +
			"EE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3D" +
			"C2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F" +
			"83655D23DCA3AD961C62F356208552BB9ED529077096966D" +
			"670C354E4ABC9804F1746C08CA18217C32905E462E36CE3B" +
			"E39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9" +
			"DE2BCBF6955817183995497CEA956AE515D2261898FA0510" +
			"15728E5A8AACAA68FFFFFFFFFFFFFFFF", 16);

		 System.out.println("Dimensione in bit di P: " + pA.bitLength());
		 
		 BigInteger gA = BigInteger.valueOf(2);
		 
		 //Molto importante che P sia grande per assicurare che il calcolo del logaritmo modulo p sia difficile, 
		 //una dimensione di 2048 bit dovrebbe garantire una sicurezza simile a quella della chiave 
		 //simmetrica di 128 bit. 
		 
		 //P e G sono stati presi da https://www.ietf.org/rfc/rfc3526.txt
		 
		 
		 System.out.println("\n--------------------Inizio Protocollo--------------------\n");
		 
		 
		 c.invio(pA.toString());
		 c.invio(gA.toString());
		 
		 System.out.println("A-->B: pA || gA");
		 System.out.println("pA = " + pA.toString());
		 System.out.println("gA = " + gA.toString());
		 
		 BigInteger pB = new BigInteger(c.ricezione());
		 BigInteger gB = new BigInteger(c.ricezione());
		 
		 System.out.println("\nB ha ricevuto i prametri");
		 System.out.println("pB = " + pB.toString());
		 System.out.println("gB = " + gB.toString());
		 
		 //Alice condivide con Bob i parametri p e g dello scambio.
		 
		 DifHel dA = new DifHel( pA,gA,"SHA1PRNG");
		 DifHel dB = new DifHel( pB,gB,"SHA1PRNG");
		 
		 //Entrambi creano un proprio oggeto DifHel che gli permetterà di portare a termine lo scambio, si usano due
		 //istanze diverse per simulare l'esecuzione su due macchine differenti.

		 System.out.println("\nA e B impostano il proprio X (i numeri sono segreti, vengono stampati per motivi dimostrativi):");
		 System.out.println("xA = "+ dA.setX().toString());
		 System.out.println("xB = "+ dB.setX().toString());
		 
		 //Vengono scelti due numeri casuali
		 
		 BigInteger yA = dA.getY();
		 c.invio(yA.toString());
		 BigInteger yAperB = new BigInteger(c.ricezione());
		 
		 System.out.println("\nA e B calcolano il proprio Y e lo inviano:");
		 System.out.println("\nA-->B: yA = " + yA.toString());
		 
		 //Alice crea il proprio Y e lo invia a Bob, il quale lo memorizza.
		 
		 BigInteger yB = dB.getY();
		 c.invio(yB.toString());
		 BigInteger yBperA = new BigInteger(c.ricezione());
		 
		 System.out.println("B-->A: yB = " + yB.toString());
		 
		 //Bob crea il proprio Y e lo invia a Alice, la quale lo memorizza.
		 
		 
		 BigInteger kA = dA.getK(yBperA);
		 BigInteger kB = dB.getK(yAperB);
		 
		 System.out.println("\nA e B calcolano il segreto k (i numeri sono segreti, vengono stampati per motivi dimostrativi):");
		 System.out.println("kA: "+ kA.toString());
		 System.out.println("kB: "+ kB.toString());
		 
		 //Alice e Bob vanno a calcolare il segreto K.
		 
		 System.out.println("\n--------------------Fine Protocollo--------------------\n");
		 
		 //Fine del protocollo Diffie-Hellman
		 
		 
		 
		 Cifrario cA = new Cifrario(kA);
		 Cifrario cB = new Cifrario(kB);
		 
		 //Anche in questo caso abbiamo due istanze del cifrario per emulare l'esecuzione su due macchine differenti
		 
		 System.out.println("\n--------------------Inzio com. cifrata--------------------\n");
		 
		 String testoCifrato = Base64.getEncoder().encodeToString(cA.encrypt("Sicurezza-2025"));
		 System.out.println("A: Ek(m) = c");
		 c.invio(testoCifrato);
		 System.out.println("A-->B: c = " + testoCifrato);

		 //Alice cifra il testo e lo invia a B, andiamo ad usare Base64 per codificare un array di byte in una stringa
		 //e continuare ad usare la stessa classe CanaleComunicazione.
		 
		 String testoInChiaro = cB.decrypt(Base64.getDecoder().decode(c.ricezione()));
		 System.out.println("B: Dk(c) = m = " + testoInChiaro);
		 
		 //Bob va a decodificare il messaggio.
		 
		 System.out.println("\n--------------------Fine com. cifrata--------------------\n");
		 
	 }
	
}
