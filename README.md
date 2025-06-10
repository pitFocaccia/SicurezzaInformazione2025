# SicurezzaInformazione2025
Progetto pratico per l'esame di sicurezza dell'informazione M. L'obbiettivo è realizzare uno scambio Diffie-Hellman, che permetta a due entità di concordare una chiave simmetrica.

# File presenti

- **DifHel.java**: implementa il protocollo vero e proprio;

- **CanaleComunicazione.java**: va a simulare un eventuale canale di comunicazione tra le due entità;

- **Cifrario.java**: una volta stabilita la chiave permette di comunicare in maniera cifrata;

- **Main.Java**;

## DifHel

Per rendere il protocollo più realistico andiamo ad utilizzare numeri molto grandi, questo richiede di usare una classe come BigInteger per gestire al meglio numeri interi grandi.

Andiamo a generare un x da 256 bit per garantire un buon livello di sicurezza, anche su x sono possibili attacchi di brute forcing, infatti y=(g^x)mod p e l'attaccante conosce y,g e p. Considerando il protocollo DH associato ad una cifratura simmetrica con chiave a 128 bit è importante che la complessità computazionale sia simile. Infatti avere una chiave di cifratura a 128 bit sarebbe superfluo se la complessità richiesta per eseguire un attacco di bruteforcing sul protocollo DH fosse minore. Per le stesse notivazioni anche i parametri di ingresso del protocollo devono garantire una sicurezza paragonabile.


