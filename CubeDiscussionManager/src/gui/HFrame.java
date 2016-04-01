package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import dataModel.CurrentStateDataModel;

public class HFrame extends JFrame {
	CurrentStateDataModel dataModel;

	public static void main(String[] args) {
		new HFrame();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1250832990785228611L;

	public HFrame() {
		super("");
		setSize(700, 700);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dataModel = new CurrentStateDataModel();
		dataModel.beginUpdateInBackground();
	}

	public void paint(Graphics graphics) {
		int width = getWidth();
		int height = getHeight();

		Image i = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = (Graphics2D) i.getGraphics();
		g.drawRect(0, 0, width, height);
	}
}
