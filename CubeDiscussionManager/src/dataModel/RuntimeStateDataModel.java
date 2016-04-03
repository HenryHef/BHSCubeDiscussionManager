package dataModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import databaseManager.LoadCurrentDataTask;
/**
 * this holds all the states of cubes scince the program started running, it does not hold old cube states
 * @author henry
 *
 */
public class RuntimeStateDataModel {
	List<Cube> cubes = new ArrayList<>();

	public void setCubeState(double cubeNumber, Cube.State state) {
		Cube c = getCube(cubeNumber);
		if (c != null)
			c.addState(state);
		else
		{
			Cube newCube = new Cube(cubeNumber);
			newCube.addState(state);
			cubes.add(newCube);
		}
	}

	private Cube getCube(double cubeNumber) {
		for (Cube c : cubes) {
			if (c.cubeID == cubeNumber) {
				return c;
			}
		}
		return null;
	}
	/**
	 * editting returned set does not edit data structure
	 * @return
	 */
	public Set<Cube> cubeSet()
	{
		Set<Cube> re = new HashSet<>();
		re.addAll(cubes);
		return re;
	}

	public void beginSingleUpdateInBackground() {
		(new LoadCurrentDataTask(this,1,1)).start();
	}
	LoadCurrentDataTask backgroundLoopUpdate;
	public void beginRepeatedUpdateInBackground()
	{
		if(backgroundLoopUpdate!=null)backgroundLoopUpdate.shouldStop=true;
		backgroundLoopUpdate=new LoadCurrentDataTask(this,0,2000);
		backgroundLoopUpdate.start();
	}

	public String toString()
	{
		return cubes.toString();
	}
}
