package fil.rsx.ex2;

import java.net.*;
import java.io.*;

public class MultiReceiveUDP {
	
	public static void main (String[] args) throws Exception
	{
		int i;
		MulticastSocket s;
		DatagramPacket p;
		s = new MulticastSocket(7654);
		s.joinGroup(InetAddress.getByName("224.0.0.1"));
		p = new DatagramPacket(new byte[512],512);
		while(true)
		{
		s.receive(p);
		System.out.println("Nouveau paquet reçu ! \n\nAdresse : "+ p.getAddress()+
		"\nPort    : "+            p.getPort()+
		"\nTaille  : " +          p.getLength());
		byte array[] = p.getData();
		String message = new String (array);
		System.out.println("Message : " + message);
		
		}
	}

}
