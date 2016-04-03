package disscussionManager;

import h_utils.HFrame;
import h_utils.UpdaterThread;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import javax.swing.JFrame;

import pollManager.Poll;
import dataModel.RuntimeStateDataModel;

public class DisscussionFrame extends HFrame {
	RuntimeStateDataModel dataModel;
	Poll poll = Poll.GET_TEST_POLL_DO_NOT_USE();
	/*
	List<Cube> wantingToMakePoint = new ArrayList<>();
	List<Cube> wantingToRespondToCurentPoint = new ArrayList<>();
	List<Cube> notWantingToSpeak = new ArrayList<>();
	 */

	public static void main(String[] args) {
		new DisscussionFrame();
	}
	
	private static final long serialVersionUID = 1250832990785228611L;

	public DisscussionFrame() {
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
		g.drawRect(0, 0, width, height);
		g.setColor(Color.DARK_GRAY);
		g.drawRect(0, 0, width/4, height);
		g.setColor(Color.WHITE);
		AttributedString a = new AttributedString(poll.getQuestion().getQuestion());
		g.drawString(a.getIterator(), 100, 100);
	}

	@Override
	public void updateData()
	{
		
	}
}
