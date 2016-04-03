package pollManager;

import h_utils.Range;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import dataModel.Cube;
import dataModel.RuntimeStateDataModel;

public class DataAnylisis
{
	Map<Double,Integer> cubeIDScoreMap = new HashMap<>();
	Map<Integer,Map<Integer,Integer>> questionIndex_answerIndexNumberMap = new HashMap<>();
	
	public DataAnylisis(Poll poll, RuntimeStateDataModel data)
	{
		buildCubeIDScoreMap(poll,data);
		buildQuestionIndex_answerIndexNumberMap(poll,data);
	}

	private void buildCubeIDScoreMap(Poll poll, RuntimeStateDataModel data) {
		for(Cube cube:data.cubeSet())
		{
			cubeIDScoreMap.put(cube.cubeID, scoreCube(poll,cube));
		}
	}
	private int scoreCube(Poll poll,Cube c) {
		Map<Range<Long>,Cube.State> map = getCubeRangeLastStateMap(poll, c);
		int score = 0;
		for(Range<Long> range:map.keySet())
		{
			if(poll.displayTimeMap.get(range).correct==map.get(range).cubeFace.number)
				score++;
		}
		return score;
	}

	private void buildQuestionIndex_answerIndexNumberMap(Poll poll, RuntimeStateDataModel data)
	{
		for(int a = 0; a < poll.questions.size(); a++)
		{
			this.questionIndex_answerIndexNumberMap.put(a, buildAnswerIndexNumberMap(poll,data,a));
		}
	}
	private Map<Integer,Integer> buildAnswerIndexNumberMap(Poll poll, RuntimeStateDataModel data, int questionNumber)
	{
		Map<Integer,Integer> map = new HashMap<>();
		for(int a = 1; a <= 6; a++)map.put(a, 0);

		System.out.println();
		for(Cube c:data.cubeSet())
		{
			Map<Range<Long>,Cube.State> finalStateMap = getCubeRangeLastStateMap(poll,c);
			Range<Long> range = getFirstKeyForValue(poll.displayTimeMap,poll.questions.get(questionNumber));
			System.out.println(range);
			Cube.State finalState = finalStateMap.get(range);
			System.out.println(finalState);
			
			map.put(finalState.cubeFace.number, map.get(finalState.cubeFace.number)+1);	
		}
		return map;
	}
	
	private Map<Range<Long>,Cube.State> getCubeRangeLastStateMap(Poll poll, Cube c)
	{
		Map<Range<Long>,Cube.State> map = new HashMap<>();
		for(Cube.State state:c.states)
		{
			for(Range<Long> range: poll.displayTimeMap.keySet())
			{
				if(range.contains(state.getCalabratedMilliTimeStamp())
						&&(!map.containsKey(range)||map.get(range).getCalabratedMilliTimeStamp()<state.getCalabratedMilliTimeStamp()))
					map.put(range, state);
			}
		}
		return map;
	}
	
	private static <T, E> T getFirstKeyForValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            return entry.getKey();
	        }
	    }
		return null;
	}

	public void drawInRect(Graphics2D g, int x, int y, int width, int height)
	{
		g.setColor(Color.BLACK);
		int count = 0;

		g.drawString("cube scores",x+100,y+80);
		for(Double d:cubeIDScoreMap.keySet())
		{
			g.drawString(count+" : "+cubeIDScoreMap.get(d),x+100,y+100+19*(count++));
		}
		
		
		System.out.println(questionIndex_answerIndexNumberMap);
		count=0;
		g.drawString("cube scores",x+100,y+80);
		for(Integer i:questionIndex_answerIndexNumberMap.keySet())
		{
			String st = count+"=[";
			for(Integer j:questionIndex_answerIndexNumberMap.get(i).keySet())
			{
				st=st+j+":"+questionIndex_answerIndexNumberMap.get(i).get(j)+"  ";
			}
			g.drawString(st,x+400,y+100+19*(count++));
		}
	}
}
