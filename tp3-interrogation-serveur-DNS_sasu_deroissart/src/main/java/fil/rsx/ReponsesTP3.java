package fil.rsx;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Ici se trouve les réponses au TP3, à savoir :
 * 	- Créer une requête DNS
 *  - L'envoyer à un serveur DNS
 *  - Récupérer la trame
 *  - Afficher l'adresse IP¨
 *  
 * @auteur DEROISSART Maxime | SASU Daniel
 */

public class ReponsesTP3 {
	
	/**
	 * @param name : La forme symbolique d'un nom de domaine (ex : www.lifl.fr)
	 * @return : Un tableau binaire correspondant au Label DNS
	 */
	public static byte[] symbolicNameToDNSName (String name)
	{
		int cpt = 0; /*Permet de compte le nombre de caractère après le point */
		int indexOfDot = 0; /*Position du point, toujours avant la série de cpt caractères */
		int indexOfCharacter = 1; /* Initialisé à 1, puisque 0 correspond au point */
		byte[] result = new byte[name.length()+1];
		for (Character c : name.toCharArray())
		{
			if (c.equals('.'))
			{
				result[indexOfDot] = (byte) cpt;
				indexOfDot = indexOfCharacter;
				cpt = 0;
			}
			else
			{
				result[indexOfCharacter] = (byte)(int)c;
				cpt +=1;
				
			}
			
			indexOfCharacter +=1;
			
		}
		result[indexOfDot] = (byte) cpt;
		return result;
		
	}
	
	/**
	 * Q3
	 * 
	 * Créé une requête DNS
	 * @param name : La forme symbolique d'un nom de domaine (ex : www.lifl.fr)
	 * @return : Un tableau binaire correspondant à la requête DNS
	 */
	public static byte[] createDNSRequest(String name)
	{
		
		byte[] entete = {(byte) 0x08, (byte) 0xbb,  (byte) 0x01, (byte) 0x00, /* a) 12 octets d'entete : identifiant de requete parametres [RFC1035, 4.1.1]*/
				        (byte) 0x00, (byte) 0x01,  (byte) 0x00, (byte) 0x00, 
				        (byte) 0x00, (byte) 0x00,  (byte) 0x00, (byte) 0x00};
		
		byte[] dnsName = ReponsesTP3.symbolicNameToDNSName(name);                    /* b.1) QNAME : "3www4lifl2fr0" [RFC1035, 3.1]*/                                      
		byte[] Qtype = {(byte) 0x00,(byte) 0x00, (byte) 0x01};                /* b.2) QTYPE : A (a host address) [RFC1035, 3.2.3]*/
		byte[] Qclass = {(byte) 0x00, (byte) 0x01};                           /* b.3) QCLASS : IN (the Internet) [RFC1035, 3.2.4]*/
	    
		
		byte[] result = new byte[entete.length + dnsName.length + Qtype.length + Qclass.length]; /*Concaténation des différentes parties */
		System.arraycopy(entete, 0, result, 0, entete.length);
		System.arraycopy(dnsName, 0, result, entete.length, dnsName.length);
		System.arraycopy(Qtype, 0, result, entete.length + dnsName.length, Qtype.length);
		System.arraycopy(Qclass, 0, result, entete.length + dnsName.length + Qtype.length, Qclass.length);
		
		return result;
	}
	
	/**
	 * Q4/Q5
	 * 
	 * Affiche la ou les adresses IP d'un nom de domaine et retourne l'entier correspondant aux 4 octets de l'adresse
	 * @param name : La forme symbolique d'un nom de domaine (ex : www.lifl.fr)
	 * @return : la ou les adresses IP correspondantes (long 64 bits pour valeurs > (2*31)-1)
	 */
	public static long extraitIP(String nomDeDomaine)
	{
		long res = 0;
        byte[] message = ReponsesTP3.createDNSRequest(nomDeDomaine); /*requête DNS*/
		
		/* 1) Get DNS server address ... by DNS ... (??!)  */
		System.err.print(" get inetaddress by name ... (on peut bien entendu mieux faire) ");
		InetAddress destination;
		try {
		    destination = InetAddress.getByName("193.49.225.15"/* ou 8.8.8.8 ou celui dans /etc/resolv.conf ... */);
		} catch (Exception e) {
		    System.err.println("[error] :" +  e.getMessage());
		    return -1;
		}
		System.err.println("[ok]");
		
		/* 2) creation d'un DatagramPacket pour l'envoi de la question DNS */
		/* affichage complet du packet recu (pas tres lisible ...) */
		System.err.println(" preparing  datagrampacket");
		System.out.println("- message lenght : "+message.length + "\n- message : \n" +new String(message, 0, message.length));
		
		/* affichage des bytes */
		for(int i = 0; i < message.length; i++) {
		    System.out.print(","+Integer.toHexString((message[i])&0xff));
		    if ((i+1)%16 == 0)
			System.out.println("");
		}
		System.out.println("");
		
		DatagramPacket dp = new DatagramPacket(message,message.length,destination,53);
		
		/* 3) creation d'un DatragramSocket (port au choix ) */
		System.err.print(" create datagram socket  ... ");
		DatagramSocket ds ;
		try {
		    ds = new DatagramSocket() ;
		} catch (Exception e) {
		    System.err.println("[error] :" +  e.getMessage());
		    return -1;
		}
		System.err.println("[ok]");
		
		/* 4) et envoi du packet */
		System.err.print(" send datagram ... ");
		try {
		    ds.send(dp);
		} catch (Exception e) {
		    System.err.println("[error] :" +  e.getMessage());
		    ds.close();
		    return -1;
		}
		System.err.println("[ok]");

		/* 5) reception du packet */
		dp = new DatagramPacket(new byte[512],512);
		System.err.print(" receive datagram ... ");
		try {
		    ds.receive(dp);
		} catch (Exception e) {
		    System.err.println("[error] :" +  e.getMessage());
		    ds.close();
		    return -1;
		}
		System.err.println("[ok]");
		
		/* affichage complet du packet recu (pas tres lisible ...) */
		byte[] rec = dp.getData();
		System.out.println("- message length : " + dp.getLength());
	       	System.out.println("- message : \n" + new String(rec, 0, dp.getLength()));
		
		/* affichage des bytes */
		for(int i = 0; i < dp.getLength(); i++) {
		    System.out.print(","+Integer.toHexString((rec[i])&0xff));
		    if ((i+1)%16 == 0)
			System.out.println("");
		}
		System.out.println("");
		
		
		/* Partie principale : affichage des adresses IP */
		
		int cpt = 1; /* Sert à numéroter les adresses IP */
		for(int i = 0; i < dp.getLength(); i++) {
			if (rec [i] == (byte) 0xC0 ) /* Si on tombe sur un octet c0, synonyme de début de réponse */
			{
				if (rec[i+3]== (byte) 0x01) /*Si cela correspond à une requête de type A (host address d'après wireshark) */
				{
					System.out.println("Adresse IP trouvée ! \n");
					System.out.println("IP n°" + cpt +" : " + (rec [i+12] & 0xFF)+ "." +  (rec [i+13]& 0xFF) + "." + (rec [i+14]& 0xFF) + "." + (rec [i+15] & 0xFF)); /* affiche l'adresse au format standard */
	                cpt += 1; /* Incrémentation du compteur */
					res += (rec [i+4] & 0xFF) << 24 & 0xFFFFFFFFL; /* Création du résultat, via un décalage pour placer les octets dans l'ordre. 
					                                                  L = format long int (64 bits), car val max en int : (2**31 -1), soit 0x7FFFFFFF et non 2*32 -1, 
					                                                  ce qui peut poser problème si bit de poids fort du premier octet à 1  */
					res += (rec [i+5] & 0xFF) << 16 ;
					res += (rec [i+6] & 0xFF) << 8 ;
					res += (rec [i+7] & 0xFF) << 0 ;
					System.out.println("héxadécimal :" + (Integer.toHexString(rec [i+4] & 0xFF))+ " " +  (Integer.toHexString(rec [i+5] & 0xFF)) + " " + (Integer.toHexString(rec [i+6] & 0xFF)) + " " + (Integer.toHexString(rec [i+7] & 0xFF)));
					System.out.println("décimal :" + res);
					System.out.println("binaire :" + Long.toBinaryString(res));
					
				}
			}
			

	    }
		ds.close();
		return res; /* Dernière IP obtenue */
	}

	public static void main (String[] args)
	{
		
		ReponsesTP3.extraitIP(args[0]);
		
	}
	
	    
}

