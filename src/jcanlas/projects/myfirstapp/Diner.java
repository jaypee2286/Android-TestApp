package jcanlas.projects.myfirstapp;

import java.util.ArrayList;
import java.util.List;

public class Diner extends MainPage {

	private String name;
	private List<Integer> itemCosts = new ArrayList<Integer>();
	private List<String> itemNames = new ArrayList<String>();
	
	public Diner(){
		initialize();
	}
	
	public void initialize(){
		setName("Guest");
	}
	
	public void setName(String nameIn){
		name = nameIn;
	}
	
	public String getName(){
		return name;
	}
	
	public String getItemName(int itemNameIndex){
		return itemNames.get(itemNameIndex);
	}
	public int getItemCost(int itemCostIndex){
		return itemCosts.get(itemCostIndex);
	}
	
	public void setItem(String itemName, int itemCost){
		itemNames.add(itemName);
		itemCosts.add(itemCost);
	}
	
	public void removeItem(int index){
		itemCosts.remove(index);
		itemNames.remove(index);
	}
	
}
