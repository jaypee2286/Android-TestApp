package jcanlas.myprojects.testapp;

public class Team {

	//private variables
	private int id;
	private String location;
	private String name;
	
	// empty constructor
	public Team(){
		
	}
	
	// constructor
	public Team(int id, String location, String name){
		this.id = id;
		this.location = location;
		this.name = name;
	}
	
	// constructor
	public Team(String location, String name){
		this.location = location;
		this.name = name;
	}
	
	// get ID
	public int getID(){
		return this.id;
	}
	// set ID
	public void setID(int id){
		this.id = id;
	}
	
	// get location
	public String getLocation(){
		return this.location;
	}
	// set location
	public void setLocation(String location){
		this.location = location;
	}

	// get name
	public String getName(){
		return this.name;
	}
	// set name
	public void setName(String name){
		this.name = name;
	}
	
}
