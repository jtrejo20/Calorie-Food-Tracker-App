package Models;

import java.util.ArrayList;
import java.util.HashMap;

public class CalorieFoodTracker {
	//Hash Map with each day of week as a key, and each value is a 
	//  size 2 Object array containing food, calorie count
	private HashMap<String, Object[]> weeklyFoodTrack;
	private String dayOfTheWeek;
	public CalorieFoodTracker() {
		weeklyFoodTrack = new HashMap<String,Object[]>();
		//default value is monday
		dayOfTheWeek = "monday";
	}
	
	public void addFoodToDay(String food,int calories) {
		// Grab food text from field in GUI
		// add food, calorie entry to weeklyFoodTrack
		
	}
	
	public void removeFoodFromDay(String foodName) {
		// Removes the food entry from the day of the week
		
	}
	
	
	
	
}
