package dataModel;
import java.util.ArrayList;
import java.util.List;

import databaseManager.LoadCurrentDataTask;


public class CurrentStateDataModel
{
	List<Cube> cubes = new ArrayList<>();
	
	public void setCubeState(double cubeNumber, Cube.State state)
	{
		Cube c = getCube(cubeNumber);
		if(c!=null)c.cubeState=state;
		else cubes.add(new Cube(cubeNumber,state,"",""));
	}
	
	private Cube getCube(double cubeNumber)
	{
		for(Cube c:cubes)
		{
			if(c.cubeID==cubeNumber)
			{
				return c;
			}
		}
		return null;
	}
	
	public void beginUpdateInBackground()
	{
		(new LoadCurrentDataTask(this)).run();
	}

}
