package fil.rsx.ex3;

import java.io.IOException;

/**
 * A simple UDP multithreadead chat
 * @author dan
 *
 */
public class Testing {
	
	public static void main(String[] args){
		try {
			Thread t = new MulticastReceiver("224.0.0.1", 7654);
			Thread s = new MulticastSender("224.0.0.1", 7654);
			t.start();
			s.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
