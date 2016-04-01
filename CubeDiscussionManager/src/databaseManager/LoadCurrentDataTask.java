package databaseManager;

import java.util.List;

import dataModel.Cube;
import dataModel.CurrentStateDataModel;

public class LoadCurrentDataTask extends Thread {
	CurrentStateDataModel model;

	public LoadCurrentDataTask(CurrentStateDataModel model) {
		this.model = model;
	}

	public void run() {
		API api = new API();
		List<Cube> cubes = api.getCubes();
		for (Cube c : cubes) {
			model.setCubeState(c.cubeID, c.cubeState);
		}
	}
}
