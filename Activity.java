package cst8284.asgmt4.scheduler;
import java.io.Serializable;


public class Activity implements Serializable{
	
	private String descriptionOfWork, category;
	

	public Activity() {this("description","category");} // no-arg constructor used in Scheduler findAppointment method

	public Activity(String descriptionOfWork, String category) { setDescription(descriptionOfWork);setCategory(category);}
	
	
	public String getDescription() {return descriptionOfWork;}

	public void setDescription(String s) {this.descriptionOfWork=s;}
	
	public String getCategory() {return category;}
	
	public void setCategory(String s) {this.category = s;}

	public String toString() {return getCategory()+"" +"\n" +getDescription() + "";}
}
