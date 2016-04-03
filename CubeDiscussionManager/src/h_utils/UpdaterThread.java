package h_utils;


public class UpdaterThread extends Thread
{
	HFrame f;
	public UpdaterThread(HFrame f)
	{
		this.f=f;
	}
	
	public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep(20);
				f.updateData();
				f.repaint();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
