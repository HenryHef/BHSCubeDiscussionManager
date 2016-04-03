package dataModel;

import h_utils.ClockCalabrationManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * represents a cube at a given time in history
 * 
 * @author henry
 * 
 */
public class Cube {
	public double cubeID;
	public List<State> states = new ArrayList<>();
	public static class State implements Comparable<State>
	{
		public ClockCalabrationManager ccm;
		public Face cubeFace;
		public String timeStamp;
		/**
		 * within 1000 millis of my computers system clock time. TODO add computer clock calibration
		 */
		private long milliTimeStamp;
		public String cubeStateID;
		
		public long getCalabratedMilliTimeStamp()
		{
			return (ccm==null?milliTimeStamp:milliTimeStamp+ccm.clockMilliDifference());
		}
		
		public State(Face cubeState, String timeStamp, String cubeStateID) {
			this.cubeFace = cubeState;
			this.timeStamp = timeStamp;
			if(timeStamp!=null&&!timeStamp.equals(""))
			{
				//easier then checking for the correct format
				try
				{
					String[] parts = timeStamp.split("(,?\\ )|(:)");//Month,day,year,hour,minute,second
					parts[0] = mapMonthString(parts[0]);
					int[] ips =
							new int[]{Integer.parseInt(parts[2]),Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),
							Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5])};
					GregorianCalendar timeS = (new GregorianCalendar(ips[0],ips[1],ips[2],ips[3],ips[4],ips[5]));
					this.milliTimeStamp = timeS.getTimeInMillis()-60*1000*60*4+18*1000;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			this.cubeStateID = cubeStateID;
		}
		private static String mapMonthString(String month)
		{
			String m = month.toLowerCase().trim();
			int re = 0;
			if(m.equals("january"))re = GregorianCalendar.JANUARY;
			else if(m.equals("february"))re = GregorianCalendar.FEBRUARY;
			else if(m.equals("march"))re = GregorianCalendar.MARCH;
			else if(m.equals("april"))re = GregorianCalendar.APRIL;
			else if(m.equals("may"))re = GregorianCalendar.MAY;
			else if(m.equals("june"))re = GregorianCalendar.JUNE;
			else if(m.equals("july"))re = GregorianCalendar.JULY;
			else if(m.equals("august"))re = GregorianCalendar.AUGUST;
			else if(m.equals("september"))re = GregorianCalendar.SEPTEMBER;
			else if(m.equals("october"))re = GregorianCalendar.OCTOBER;
			else if(m.equals("november"))re = GregorianCalendar.NOVEMBER;
			else if(m.equals("december"))re = GregorianCalendar.DECEMBER;
			return Integer.toString(re);
		}
		@Override
		public int compareTo(State o) {
			return (int)(o.milliTimeStamp-milliTimeStamp);
		}
		public String toString()
		{
			return "[mts="+milliTimeStamp+" ts="+timeStamp+" face="+cubeFace.toString()+"]";
		}

		public long getRawTimeStampDO_NOT_USE_NORMALLY()
		{
			return milliTimeStamp;
		}
	}
	
	public enum Face {
		Top(1), Bottom(2), Left(3), Right(4), Front(5), Back(6);

		public final int number;

		Face(int number) {
			this.number = number;
		}

		public static Face fromNumber(int number) {
			for (Face s : values()) {
				if (s.number == number)
					return s;
			}
			return null;
		}
	}
	
	public Cube(double cubeID)
	{
		super();
		this.cubeID = cubeID;
	}

	public void addState(Face cubeState, String timeStamp, String cubeStateID)
	{
		this.addState(new Cube.State(cubeState,timeStamp,cubeStateID));
	}
	public void addState(State state)
	{
		states.add(state);
		Collections.sort(states);
	}
	
	public String toString()
	{
		return "cube={id="+this.cubeID+" states = "+states.toString()+"}\n";
	}
}
