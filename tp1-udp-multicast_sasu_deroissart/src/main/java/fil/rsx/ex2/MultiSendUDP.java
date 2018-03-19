package fil.rsx.ex2;

import java.net.*;

public class MultiSendUDP {
	
	public static void main (String[] args) throws Exception
	{
		DatagramPacket p;
		DatagramSocket s;
		int port = 7654;
		String message = args[0];
		InetAddress dst = InetAddress.getByName("224.0.0.1");
		p = new DatagramPacket (message.getBytes(),message.length() , dst, port);
		s = new DatagramSocket();
		s.send(p);
		s.close();
	}

}
