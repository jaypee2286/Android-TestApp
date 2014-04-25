package jcanlas.myprojects.testapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 7;
	// database name
	private static final String DATABASE_NAME = "teamsManager";

	// Teams table name
	private static final String TABLE_TEAMS = "Teams";

	// Teams Table Column Names
	private static final String KEY_ID = "id";
	private static final String KEY_LOCATION = "location";
	private static final String KEY_NAME = "name";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Create Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TEAMS_TABLE = "CREATE TABLE " + TABLE_TEAMS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_LOCATION + " TEXT,"
				+ KEY_NAME + " TEXT" + ")";
		db.execSQL(CREATE_TEAMS_TABLE);
	}

	// Upgrading DB
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
		// Recreate tables
		onCreate(db);
	}

	// Adding new team
	public void addTeam(Team team) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_LOCATION, team.getLocation()); // team location
		values.put(KEY_NAME, team.getName()); // team name;

		// Insert row
		db.insert(TABLE_TEAMS, null, values);
		db.close(); // close db connection
	}

	// Getting single team
	public Team getTeam(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_TEAMS, new String[] { KEY_ID,
				KEY_LOCATION, KEY_NAME }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Team team = new Team(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));

		// Return team
		return team;
	}

	// Getting all contacts
	public List<Team> getAllTeams() {
		List<Team> teamList = new ArrayList<Team>();

		// Select all query
		String selectQuery = "SELECT * FROM " + TABLE_TEAMS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// Go through all the rows and add to list
		if (cursor.moveToFirst()) {
			do {
				Team team = new Team();
				team.setID(Integer.parseInt(cursor.getString(0)));
				team.setLocation(cursor.getString(1));
				team.setName(cursor.getString(2));
				// Add team to list
				teamList.add(team);
			} while (cursor.moveToNext());
		}

		// Return team list
		return teamList;
	}

	// Updating single team
	public int updateTeam(Team team) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_LOCATION, team.getLocation());
		values.put(KEY_NAME, team.getName());

		// updating row
		return db.update(TABLE_TEAMS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(team.getID()) });
	}

	// Deleting single team
	public void deleteTeam(Team team) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TEAMS, KEY_ID + " = ?",
				new String[] { String.valueOf(team.getID()) });
		db.close();
	}
}
