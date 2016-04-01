package dataModel;

/**
 * represents a cube at a given time in history
 * @author henry
 *
 */
public class Cube
{
	public enum State
	{
		Top		(1),
		Bottom	(2),
		Left	(3),
		Right	(4),
		Front	(5),
		Back	(6);
		
		final int number;
		State(int number)
		{
			this.number=number;
		}
		
		public static State fromNumber(int number)
		{
			for(State s:values())
			{
				if(s.number==number)return s;
			}
			return null;
		}
	}
	
	public double cubeID;
	public State cubeState;
	public String timeStamp;
	public String cubeStateID;
	//cubeID":2.0,"cubeState":1,"timeStamp":"March, 31 2016 23:59:30","cubeStateID":"myvalue"
	public Cube(double cubeID, State cubeState, String timeStamp, String cubeStateID)
	{
		super();
		this.cubeID = cubeID;
		this.cubeState = cubeState;
		this.timeStamp = timeStamp;
		this.cubeStateID = cubeStateID;
	}
	
}
