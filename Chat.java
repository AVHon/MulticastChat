/**
 * 
 */

import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author alex von hoene
 * @version 21 nov 2012
 */
public class Chat extends Stoppable{
	
	protected final int PORT = 8224;

	protected MulticastSocket socket;
	protected ChatReciever in;
	protected ChatSender out;
	
	public static void main(String[] args) {
		System.out.println("Starting...");
		System.out.println("To stop this program, type 'quit' and press ENTER.");
		new Chat();
	}
	
	public Chat()
	{
		super("Chat");
		
		try
		{
			socket = new MulticastSocket(PORT);
			
			//Multicast-specific network code
	        InetAddress address = InetAddress.getByName("230.0.0.1");
	        socket.joinGroup(address);
			
			
			in = new ChatReciever(socket);
			out = new ChatSender(socket, PORT);
			System.out.println("Ready.");
			in.start();
			out.start();
			
			//Let the ChatSender and ChatReciever do their magic.
			//Because this thread created those threads, this thread
			//needs to wait until ChatReciever and ChatSender are done.
			//Until then, do nothing.
			while(!shouldStop())
			{
				try{sleep(500);}catch(Exception e){e.printStackTrace();}
			}
			
			//Now that the program should close, wait for the ChatSender
			//and ChatReciever to close, up to 5 seconds.
			for(int i = 0; i < 5 && (in.isAlive() || out.isAlive()); i++)
				try{sleep(1000);}catch(Exception e){e.printStackTrace();}
			
			//Then, neatly close everything.
			socket.close();
			System.exit(0);
		}
		catch(java.io.IOException e)
		{
			//This code is executed only if the client fails to connect.
			//If this happens, the client will not work if allowed to continue.
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	

}
