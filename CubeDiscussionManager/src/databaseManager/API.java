package databaseManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONTokener;

import dataModel.Cube;

/**
 * all calls are long operations, RUN IN THE BACKGROUND!!!!!!!!!!
 * <p>
 * 
 * @author Henry
 * 
 */
public class API {
	private static final String BASE_URL = "http://www.bhswriter.com/api/";

	public Cube getCube(int cubeID) {
		String page = getWebsight(BASE_URL + "cube/" + cubeID);
		JSONTokener parser = new JSONTokener(page);
		try {
			JSONObject json = (JSONObject) parser.nextValue();
			// {"cubeID":2.0,"cubeState":1,"timeStamp":"March, 31 2016 23:59:30","cubeStateID":"myvalue"}
			double cubeNumber = json.getDouble("cubeID");
			if (cubeNumber != cubeID)
				System.out.println("ERROR: cubeNumber!=cubeID");
			int cubeState = json.getInt("cubeState");
			String timeStamp = json.getString("timeStamp");
			String cubeStateID = json.getString("myvalue");
			return new Cube(cubeNumber, Cube.State.fromNumber(cubeState),
					timeStamp, cubeStateID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Cube> getCubes() {
		try {
			String page = downloadUrl(BASE_URL + "cubes", "GET");
			JSONTokener parser = new JSONTokener(page);
			JSONObject base_json = (JSONObject) parser.nextValue();
			System.out.println("base_json = " + base_json);
			List<Cube> cubes = new ArrayList<>();
			for (String key : base_json.keySet()) {
				try {
					JSONObject json = (JSONObject) base_json.get(key);
					System.out.println("json = " + json);
					// {"cubeID":2.0,"cubeState":1,"timeStamp":"March, 31 2016 23:59:30","cubeStateID":"myvalue"}
					int cubeState = json.getInt("cubeState");
					String timeStamp = json.getString("timeStamp");
					int cubeStateID = json.getInt("cubeStateID");
					cubes.add(new Cube(Integer.parseInt(key), Cube.State
							.fromNumber(cubeState), timeStamp, Integer
							.toString(cubeStateID)));
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}

			return cubes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Cube> getCubeHistory(int cubeID, int steps) {
		String page = getWebsight(BASE_URL + "cube/history/" + cubeID + "/"
				+ steps);
		JSONTokener parser = new JSONTokener(page);
		JSONObject json = (JSONObject) parser.nextValue();
		List<Cube> cubes = new ArrayList<>();
		try {
			for (int i = 1; i <= steps; i++) {
				JSONObject cube_json = json.getJSONObject(Integer.toString(i));
				// {"cubeID":2.0,"cubeState":1,"timeStamp":"March, 31 2016 23:59:30","cubeStateID":"myvalue"}
				double cubeNumber = cube_json.getDouble("cubeID");
				int cubeState = cube_json.getInt("cubeState");
				String timeStamp = cube_json.getString("timeStamp");
				String cubeStateID = cube_json.getString("myvalue");
				cubes.add(new Cube(cubeNumber,
						Cube.State.fromNumber(cubeState), timeStamp,
						cubeStateID));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cubes;
	}

	private String getWebsight(String urlString) {
		InputStream is = null;

		try {
			URL url = new URL(urlString);
			System.out.println(url);
			is = url.openStream(); // throws an IOException

			String string = readInputStream(is);
			return string;
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException ioe) {
				// nothing to see here
			}
		}
		return "";
	}

	private String downloadUrl(String myurl, String method) throws IOException {
		InputStream is = null;

		try {
			URL url = new URL(myurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod(method);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			System.out.println("conn.toString(): " + conn.toString());
			// Starts the query
			conn.connect();
			int response = conn.getResponseCode();
			System.out.println("The response is: " + response);
			is = conn.getInputStream();

			// Convert the InputStream into a string
			String contentAsString = readInputStream(is);
			return contentAsString;

			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	private String readInputStream(InputStream is) throws IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(is));
		StringBuilder total = new StringBuilder();
		String line;
		while ((line = r.readLine()) != null) {
			total.append(line).append("\n");
		}
		return total.toString();
	}
}
