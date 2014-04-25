// Created By: John Paul Canlas
// April 25, 2014
// This app was created for an assessment for a job interview.
//
// This app is consumes an ESPN web service to grab a list of
//	NBA basketball teams and inputs them into an SQLiteDatabase.

package jcanlas.myprojects.testapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.os.AsyncTask;
import org.json.JSONObject;
import org.apache.http.StatusLine;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

	private static DatabaseHandler db;
	private static List<String> teamListArray = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button submitButton = (Button) this.findViewById(R.id.submit_btn);

		db = new DatabaseHandler(this);
		// Reading all contacts

		Log.d("Reading: ", "Reading all contacts..");

		submitButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// API URL
				String url = "http://api.espn.com/v1/sports/basketball/nba/teams/?apikey=v92dkvje8keh77vt4d5gkpdb";
				new readTeamsJSONFeedTask().execute(url);
			}
		});
	}

	// initialize
	public String readJSONFeed(String URL) {
		StringBuilder stringBuilder = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream inputStream = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
				String line;
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
				}
				inputStream.close();
			} else {
				Log.d("readJSONFeed", "Failed to download file");
			}
		} catch (Exception e) {
			Log.d("readJSONFeed", e.getLocalizedMessage());
		}
		return stringBuilder.toString();
	}

	private class readTeamsJSONFeedTask extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... urls) {
			return readJSONFeed(urls[0]);
		}

		protected void onPostExecute(String result) {
			String teamList = "List of teams: \n";
			teamListArray = new ArrayList<String>();
			try {
				// Create master object
				JSONObject sports = new JSONObject(result);

				// Create nested objects
				JSONArray sportsArray = new JSONArray(
						sports.getString("sports")); // "sports" array
				JSONObject sportsObject = sportsArray.getJSONObject(0); // "sports" objects
				JSONArray leaguesArray = new JSONArray(
						sportsObject.getString("leagues")); // "leagues" array
				JSONObject leaguesObject = leaguesArray.getJSONObject(0); // "leagues" objects
				JSONArray teamsArray = new JSONArray(
						leaguesObject.getString("teams")); // "teams" array
				JSONObject teamsObject = teamsArray.getJSONObject(0); // "teams" objects

				// Display teams and add them to database
				for (int i = 0; i < teamsArray.length(); i++) {
					// get team and insert into SQLiteDatabase
					Log.d("Insert: ", "Inserting ...");
					teamsObject = teamsArray.getJSONObject(i);
					db.addTeam(new Team(teamsObject.getString("location"),
							teamsObject.getString("name")));

					// for display in android app textview
					teamListArray.add(teamsObject.getString("location") + " "
							+ teamsObject.getString("name"));
					teamList += teamListArray.get(i) + "\n";
				}

				// Show database data in logcat
				List<Team> teams = db.getAllTeams();
				for (int j = 0; j < teams.size(); j++) {
					Team team = teams.get(j);
					String log = "Id: " + team.getID() + " ,Location: "
							+ team.getLocation() + " ,Name: " + team.getName();
					Log.d("Name: ", log);
				}

			} catch (Exception e) {
				Log.d("readTeamsJSONFeedTask", e.getLocalizedMessage());
				teamList += "FAIL \n";
			}

			TextView resp = (TextView) findViewById(R.id.response);
			if (teamList.trim().length() > 0)
				resp.setText(teamList);
			else
				resp.setText("Sorry no match found");
		}
	}
}
