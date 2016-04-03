package pollManager;

import h_utils.HFrame;
import h_utils.UpdaterThread;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import dataModel.RuntimeStateDataModel;

public class PollOrQuizFrame extends HFrame {
	RuntimeStateDataModel dataModel;
	Poll poll = Poll.GET_TEST_POLL_DO_NOT_USE();
	/*
	List<Cube> wantingToMakePoint = new ArrayList<>();
	List<Cube> wantingToRespondToCurentPoint = new ArrayList<>();
	List<Cube> notWantingToSpeak = new ArrayList<>();
	 */

	public static void main(String[] args) {
		new PollOrQuizFrame();
	}
	
	private static final long serialVersionUID = 1250832990785228611L;

	public PollOrQuizFrame() {
		super("");
		setSize(700, 700);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dataModel = new RuntimeStateDataModel();
		dataModel.beginRepeatedUpdateInBackground();
		(new UpdaterThread(this)).start();
	}

	public void paint(Graphics graphics) {
		int width = getWidth();
		int height = getHeight();

		Image i = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) i.getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, width, height/6);
		
		poll.drawInRect(g,0,height/6,width,height*5/6);
		
		graphics.drawImage(i, 0, 0,null);
	}
	
	@Override
	public void updateData()
	{
		poll.checkAdvanceQuestion();
		if(poll.done&&poll.dataAnylisis==null)poll.dataAnylisis= new DataAnylisis(poll,this.dataModel);
		//System.out.println("\n\n\n\n\n"+this.dataModel);
	}
}
