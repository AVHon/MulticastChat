/**
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author alex von hoene
 * @version 21 nov 2012
 */
public class ChatSender extends Stoppable {
	
	protected DatagramSocket socket;
	protected BufferedReader keyboard;
	protected int port;
	
	public ChatSender(DatagramSocket theSocket, int thePort)
	{
		super("ChatSender");
		socket = theSocket;
		port = thePort;
		keyboard = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void run()
	{
		String input = "New client joined.";
		
		while(! input.equals("quit"))
		{
			sendString(input);
			try {
				sleep(100);
				input = keyboard.readLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//set the boolean "stop" variable for the ChatReciever to "true"
		stopAllStoppables();
		//The ChatReciever waits until it recieves a message before
		//it checks the "stop" variable. TO ensure this happens in a
		//timely manner, send a "quit" message.
		sendString("Client quit.");
		try{keyboard.close();}catch(Exception e){e.printStackTrace();}
	}
	
	public void sendString(String in)
	{
		try {
			byte[] buf = in.getBytes();
			
			InetAddress group;
			group = InetAddress.getByName("230.0.0.1");
			DatagramPacket packet = new DatagramPacket(buf, buf.length, group, port);
        	socket.send(packet);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
