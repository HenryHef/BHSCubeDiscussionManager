package h_utils;

import javax.swing.JFrame;

public abstract class HFrame extends JFrame
{
	private static final long serialVersionUID = 2624321725022572892L;
	public HFrame(String string)
	{
		super(string);
	}
	public abstract void updateData();
}
