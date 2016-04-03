package databaseManager;

import h_utils.ClockCalabrationManager;

import java.util.List;

import dataModel.Cube;
import dataModel.RuntimeStateDataModel;

public class LoadCurrentDataTask extends Thread implements ClockCalabrationManager{
	RuntimeStateDataModel model;
	int count;
	int maxTimes;
	int delay;
	public boolean shouldStop;
	public int clockCalabrationsCount = 0;
	public long clockCalabrationMillis = 0;
	

	/**
	 * 
	 * @param model
	 * @param times
	 *            is how many times to repeat. <=0 means forever
	 * @param delayMillis
	 *            is delay between post request in Millis
	 */
	public LoadCurrentDataTask(RuntimeStateDataModel model, int times,
			int delayMillis) {
		this.model = model;
		this.maxTimes = times;
		this.delay = delayMillis;
	}

	public void run() {
		try {
			while ((count < maxTimes || maxTimes <= 0) && !shouldStop) {
				API api = new API();
				List<Cube> cubes = api.getCubes();
				this.clockCalabrationMillis=
						((clockCalabrationMillis*clockCalabrationsCount)+
								System.currentTimeMillis()-cubes.get(0).states.get(0).getRawTimeStampDO_NOT_USE_NORMALLY())
								/(clockCalabrationsCount+1);
				clockCalabrationsCount++;
				
				for (Cube c : cubes) {
					c.states.get(0).ccm=this;
					model.setCubeState(c.cubeID, c.states.get(0));
				}
				
				count++;
				Thread.sleep(delay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public long clockMilliDifference()
	{
		return this.clockCalabrationMillis;
	}
}
