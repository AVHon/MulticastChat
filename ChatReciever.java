/**
 * 
 */

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

/**
 * @author alex von hoene
 * @version 21 nov 2012
 */
public class ChatReciever extends Stoppable {
	
	protected DatagramSocket socket;
	protected DatagramPacket packet;
	protected String recieved;

	public ChatReciever(DatagramSocket theSocket) {
		super("ChatReciever");
		socket = theSocket;
	}
	
	public void run()
	{
		while(! shouldStop())
		{
			try
			{
				//Create a byte[] for the packet's data to go into.
				byte[] buf = new byte[1024];
				for(int i = 0; i < buf.length; i++)
					buf[i] = 0;
				
				//Assign that byte[] to be the place the packet's data is stored.
				packet = new DatagramPacket(buf, buf.length);
				
				//Wait for a packet to be recieved.
				socket.receive(packet);
				
				//Take the packet's data, remove the zeroes from the end.
				buf = removeTrailingZeroes(packet.getData());
				
				//Convert the data into a string.
				recieved = new String(buf);
				
				//Print the message.
				System.out.println(new Date().toString() + ": " + recieved);
				
				//Give other threads time to execute.
				sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public byte[] removeTrailingZeroes(byte[] buf)
	{
		int i = buf.length - 1;
		while(buf[i] == 0)
		{
			i--;
		}
		
		//At this point, "i" is the index of the last non-zero element.
		//Create a new array, copy everything from index 0 through index i
		//into the new array.
		byte[] newBuf = new byte[i + 1];
		for(int j = 0; j <= i; j++)
			newBuf[j] = buf[j];
		
		return newBuf;
	}

}
