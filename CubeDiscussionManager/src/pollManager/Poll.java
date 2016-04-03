package pollManager;

import h_utils.Range;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Poll
{
	Map<Range<Long>,Question> displayTimeMap = new HashMap<>();
	List<Question> questions = new ArrayList<>();
	int displayTimeMillis = 4000;
	int pauseTimeMillis = 2000;

	boolean isInBetweenQuestions;
	boolean done;
	long lastClockTime;
	int currentIndex;
	
	public Poll(Question...questions)
	{
		for(Question q:questions)
			this.questions.add(q);
		currentIndex = -1;
		startNextQuestion();
	}
	public boolean hasMoreQuestions()
	{
		return(currentIndex+1<questions.size());
	}
	public void startNextQuestion()
	{
		currentIndex++;
		lastClockTime = System.currentTimeMillis();
		isInBetweenQuestions=false;
	}
	public void endQuestion()
	{
		displayTimeMap.put(new Range<Long>(lastClockTime,System.currentTimeMillis()), getQuestion());
		isInBetweenQuestions=true;
		lastClockTime=System.currentTimeMillis();
		if(!hasMoreQuestions())done=true;
	}
	public Question getQuestion()
	{
		return (this.isInBetweenQuestions?null:questions.get(currentIndex));
	}
	
	public static Poll GET_TEST_POLL_DO_NOT_USE()
	{
		return new Poll(
				new Question("what color is the sky",4,"red","oragne","yellow","green","blue","purple"),
				new Question("how many fingers are on one hand",5,"0","1","2","3","4","5"));
	}
	public void checkAdvanceQuestion()
	{
		if(this.isInBetweenQuestions
				&&this.hasMoreQuestions()
				&&System.currentTimeMillis()>lastClockTime+pauseTimeMillis)
		{
			startNextQuestion();
			System.out.println("Strarting Question");
		}
		else if(!this.isInBetweenQuestions
				&&System.currentTimeMillis()>lastClockTime+displayTimeMillis)
		{
			System.out.println("EndingQuestion");
			endQuestion();
		}
	}
	public double getProgressBarPercentage()
	{
		if(done)return 0;
		return (this.isInBetweenQuestions?
				(System.currentTimeMillis()-lastClockTime)/(double)pauseTimeMillis:
					(System.currentTimeMillis()-lastClockTime)/(double)displayTimeMillis);
	}
	public void drawInRect(Graphics2D g, int x, int y, int width, int height)
	{
		if(!done)
		{
			g.setColor(new Color(200,200,255));
			g.fillRect(x, y, width, 15);
			g.setColor(new Color(100,100,255));
			g.fillRect(x,y, (int) (width*getProgressBarPercentage()), 15);
			
			if(getQuestion()!=null)
				getQuestion().drawInRect(g, x,y,width, height-15);
		}
		else
		{
			dataAnylisis.drawInRect(g,x,y,width,height);
		}
	}
	DataAnylisis dataAnylisis;
}
