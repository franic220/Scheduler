package cst8284.asgmt4.employee;

import java.util.ArrayList;

public class Dentist extends Employee{
	private static ArrayList<String> activityTypes = new ArrayList <>();
	
	public Dentist(String fullName) {
		super(fullName); // load dentist full name using the Employee super class constructor
	}
	
@Override
	public  ArrayList<String>  getActivityType() {
	activityTypes.add("Assessment");
	activityTypes.add("Filling");
	activityTypes.add("Crown");
	activityTypes.add("Cosmetic Repair");
	return activityTypes;
	
	}
		

}
