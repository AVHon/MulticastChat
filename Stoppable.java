
public abstract class Stoppable extends Thread {
	
	private static boolean stop;
	
	public Stoppable(String name)
	{
		super(name);
		stop = false;
	}
	
	public void stopAllStoppables()
	{
		stop = true;
	}
	
	public boolean shouldStop()
	{
		return stop;
	}
}
