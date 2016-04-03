package pollManager;

import h_utils.GraphicsUtils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class Question
{
	String question;
	String[] answers;
	int correct;
	public Question(String question, int correct, String...answers) {
		super();
		this.question = question;
		this.answers = answers;
		this.correct = correct;
	}
	public String getQuestion() {
		return question;
	}
	public String[] getAnswers() {
		return answers;
	}
	public int getCorrect() {
		return correct;
	}
	
	public void drawInRect(Graphics2D g, int x, int y, int width, int height)
	{
		int answerYSpace = 35;
		int answerXSpace = 35;
		int answerHeight = (height-answerYSpace*4)/3;
		int answerWidth = (width-answerXSpace*3)/2;
		
		for(int a = 1; a <= 3; a++)//y
		{
			for(int b = 1; b <= 2; b++)//x
			{
				g.setColor(Color.DARK_GRAY);
				g.fillRect(x+answerXSpace*b+(b-1)*answerWidth, y+a*answerYSpace+(a-1)*answerHeight,
						answerWidth, (height-answerYSpace*4)/3);
				
				g.setColor(Color.WHITE);
				AttributedString text = new AttributedString(getAnswers()[(a-1)*2+(b-1)]);
				GraphicsUtils.fitAtributedString(g,text,answerWidth-30,60);
				//System.out.println("getHeightOfAttributedString(g,text)="+getHeightOfAttributedString(g,text));
				g.drawString(
						text.getIterator(),
						(int) (x+answerXSpace*b+(b-1)*answerWidth+(answerWidth-GraphicsUtils.getWidthOfAttributedString(g,text))/2),
						(int) (y+a*answerYSpace+(a-1)*answerHeight+GraphicsUtils.getHeightOfAttributedString(g,text)/2)+answerHeight/2);
			}
		}
		
		g.setColor(Color.WHITE);
		AttributedString title = new AttributedString(getQuestion());
		title.addAttribute(TextAttribute.SIZE, 64);
		GraphicsUtils.fitAtributedString(g,title,width-30,60);
		g.drawString(title.getIterator(), (int) ((width-GraphicsUtils.getWidthOfAttributedString(g,title))/2), 90);
	}
	
}
