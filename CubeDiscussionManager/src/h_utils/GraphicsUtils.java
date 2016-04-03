package h_utils;

import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

public class GraphicsUtils {
	public static double getWidthOfAttributedString(Graphics2D graphics2D, AttributedString attributedString)
	{
	    AttributedCharacterIterator characterIterator = attributedString.getIterator();
	    FontRenderContext fontRenderContext = graphics2D.getFontRenderContext();
	    LineBreakMeasurer lbm = new LineBreakMeasurer(characterIterator, fontRenderContext);
	    TextLayout textLayout = lbm.nextLayout(Integer.MAX_VALUE);
	    return textLayout.getBounds().getWidth();
	}
	public static double getHeightOfAttributedString(Graphics2D graphics2D, AttributedString attributedString)
	{
	    AttributedCharacterIterator characterIterator = attributedString.getIterator();
	    FontRenderContext fontRenderContext = graphics2D.getFontRenderContext();
	    LineBreakMeasurer lbm = new LineBreakMeasurer(characterIterator, fontRenderContext);
	    TextLayout textLayout = lbm.nextLayout(Integer.MAX_VALUE);
	    return textLayout.getBounds().getHeight();
	}

	/**
	 * fits string to size up to size 60 within box
	 * @param graphics2D
	 * @param attributedString
	 * @param width
	 */
	public static void fitAtributedString(Graphics2D graphics2D, AttributedString attributedString, double width, double maxTextSize)
	{
		double min = 1;
		double max = 10000;
		double current = 30;
		while (true)
		{
			current=(min+max)/2;
			attributedString.addAttribute(TextAttribute.SIZE, current);
			double messure = getWidthOfAttributedString(graphics2D,attributedString);
			//System.out.println("min = "+min+"max = "+max+" messure = "+messure + " width = "+width+" current = "+current);
			if(Math.abs(messure-width)<12)
			{
				break;
			}
			else if(messure>width)
			{
				max=current;
			}
			else if(messure<width)
			{
				min=current;
			}
			if(min>max)
			{
				break;
			}
		}
		if(current>maxTextSize)
			attributedString.addAttribute(TextAttribute.SIZE, maxTextSize);
	}

}
