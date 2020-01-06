
package cst8284.asgmt4.scheduler;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import cst8284.asgmt4.employee.Employee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Scheduler {

	private static ArrayList<Appointment> appointments = new ArrayList <>();

	private static Employee employee;
	
    public Scheduler() {}
   	public Scheduler(Employee emp) {setEmployee(emp);}
	
	public void launch() {
		loadAppointmentsFromFile("CurrentAppointments.apts", getAppointments());
		try {
		SchedulerViewer.loadAndShowWords();
		}catch(BadAppointmentDataException ex) {ex.DisplayErrMessage();}
	}
		
	private void setEmployee(Employee emp) {this.employee=emp;}

	public static Employee getEmployee() {return employee;}
	
	public static boolean saveAppointment(Appointment apt) {
		if(apt == null) {return false;}
		if (findAppointment(apt.getAptDate()) == null) { // check to see if appointment already exists
			getAppointments().add(apt);
			Collections.sort(getAppointments(), new SortAppointmentByCalendar());
			JOptionPane.showMessageDialog(null,"Appointment saved.\n");
			return true;
		}else {
			JOptionPane.showMessageDialog(null,"Cannot save; an appointment at that time already exists");
			return false;
		
		}
	}
	
	public  class SaveAppointment implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			saveAppointment(makeAppointmentFromUserInput());
			
		}
		
	}
	
	

	private boolean deleteAppointment(Calendar cal) {
		if(findAppointment(cal) == null) {
			displayAppointment(cal);
			return false;
		}
		displayAppointment(cal); // using cal, display to user the appointment they want to delete, for confirmation
	    String yes =getResponseTo("Enter 'Yes' to delete this appointment ");
          if(yes.equals("yes") || yes.equals("Yes")) { // confirm user response       
        JOptionPane.showMessageDialog(null,"Appointment deleted");
		return getAppointments().remove(findAppointment(cal)); 
          }else {
        	  JOptionPane.showMessageDialog(null,"Invalid input, cannot delete appointment");
        	  return false;
          }
	}
	
	public class DeleteAppointment implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Calendar cal = makeCalendarFromUserInput(false);
        	if(cal != null) {
        		deleteAppointment(cal); 
        	}
			
		}
		
	}
	
	  private static Calendar makeCalendarChange() {
		  try {
	    	Calendar cal = Calendar.getInstance();
	    	int hour = 0;
	    	cal.clear();
			String date = getResponseTo("Enter new date");
			if(!isInputNotNull(date)) {
	    		throw new BadAppointmentDataException("Must enter a value", "Empty or null value entered");
	    	}
			isDateCorrect(date);
			int day = Integer.parseInt(date.substring(0,2));
			int month = Integer.parseInt(date.substring(2,4))-1;  // offset by one to account for zero-based month in Calendar
			int year = Integer.parseInt(date.substring(4,8));				
			String time = getResponseTo("Enter new time");
			hour = processTimeString(time);
			cal.set(year, month, day, hour, 0);
			return (cal);
		  }catch (BadAppointmentDataException ex) {ex.DisplayErrMessage(); return null;}
	    }
	
	private boolean changeAppointment(Calendar cal) {
		if(findAppointment(cal) == null) {
			displayAppointment(cal);
			return false;
		}
		displayAppointment(cal);// using cal, display to user the appointment they want to change, for confirmation
		String yes =getResponseTo("Enter 'Yes' to change the date and time of this appointment ");
		if(yes.equals("yes") || yes.equals("Yes")) { // confirm user response
		Appointment b = findAppointment(cal);
		JOptionPane.showMessageDialog(null,"Enter new date and time");
		cal = makeCalendarChange(); //store the new appointment information to cal
		if(cal == null) {
			JOptionPane.showMessageDialog(null,"Invalid input, cannot change appointment");
	       	  return false;
		}
		b.setAptDate(cal); // use cal to set the new date values for the appointment
		JOptionPane.showMessageDialog(null,"Appointment re-booked");
		return true;
		}else {
			JOptionPane.showMessageDialog(null,"Invalid input, cannot change appointment");
       	  return false;
		}
	}
	
	public class ChangeAppointment implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Calendar cal = makeCalendarFromUserInput(false);
        	if(cal != null) {
        		changeAppointment(cal); 
        	}
			
		}
		
	}
	

	private void displayAppointment(Calendar cal) {
		if (cal== null) 
			new BadAppointmentDataException("Please input date in order to view the schedule", "Error, no date provided!").DisplayErrMessage();
		
		Appointment apt = findAppointment(cal);
		int hr = cal.get(Calendar.HOUR_OF_DAY);
		JOptionPane.showMessageDialog(null,(apt != null) ? "\n"+ apt.toString()+"\n":"No appointment scheduled between "+ hr + ":00 and " + (hr + 1) + ":00");
	}
	
	public class DisplayAppointment implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Calendar cal = makeCalendarFromUserInput(false);
        	if(cal != null) {
        		displayAppointment(cal);
        	}
			
		}
	}
	
	
	private String displayApt(Calendar cal) {
		if (cal== null) 
			new BadAppointmentDataException("Please input date in order to view the schedule", "Error, no date provided!").DisplayErrMessage();
		Appointment apt = findAppointment(cal);
		int hr = cal.get(Calendar.HOUR_OF_DAY);
		String daySchedule = (apt != null) ? "\n"+ apt.toString()+"\n":"No appointment scheduled between "+ hr + ":00 and " + (hr + 1) + ":00";
		return daySchedule;
	}
	private String displayDaySchedule(Calendar cal) {
		String displayDaySchedule = "";
		for (int hrCtr = 8; hrCtr < 17; hrCtr++) {
			cal.set(Calendar.HOUR_OF_DAY, hrCtr);
			displayDaySchedule += displayApt(cal) + "\n";		
		}
		return displayDaySchedule;
	}
	

	static boolean saveAppointmentsToFile(ArrayList <Appointment> apts, String saveFile) {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(saveFile))){
			for(Appointment a : apts)  
				outputStream.writeObject(a); // output the contents of apts from the program to the file
		} catch (FileNotFoundException ex) {JOptionPane.showMessageDialog(null,"File not found, please check the path for file " + saveFile, "Save Appointments to File Error", JOptionPane.ERROR_MESSAGE);}
		catch (IOException ex) {JOptionPane.showMessageDialog(null,"Unexpected error! Please check the path for file " + saveFile, "Save Appointments to File Error", JOptionPane.ERROR_MESSAGE);}
		return true;
		}
	
	public class SaveAppointmentsToFile implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (saveAppointmentsToFile(getAppointments(),"CurrentAppointments.apts"));
			JOptionPane.showMessageDialog(null,"Appointment data saved to\n" +
					"CurrentAppointments.apts");
		}
		
	}
	
	public class DisplaySchedule implements ActionListener {
        @Override
		public void actionPerformed(ActionEvent e) {
        	Calendar cal = makeCalendarFromUserInput(true);
        	if(cal != null) {
        		SchedulerViewer.reloadJTextArea(displayDaySchedule(cal)); 
        	}
        	
		}
		
	}
	
	
	public class LoadAppointmentsFromFile implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			loadAppointmentsFromFile("CurrentAppointments.apts", getAppointments());
				
			
		}
		
	}

	private static boolean loadAppointmentsFromFile(String sourceFile, ArrayList <Appointment> apts){
			try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(sourceFile))){
			apts.clear(); //clear the ArrayList to overwrite it's contents
			for(;;) 
				apts.add((Appointment)inputStream.readObject()); // cast as Appointment & input file contents from the file to the program		
		}catch(EOFException e) {}
		catch (FileNotFoundException e) {}
		catch (IOException e) {	JOptionPane.showMessageDialog(null,"Unexpected error! Please check the path for file " +  sourceFile, "Load Appointments from File Error", JOptionPane.ERROR_MESSAGE);} 
		catch (ClassNotFoundException e) {}	
		return true;
		}
	
	private static String getResponseTo(String s) {	
	    	 String response = JOptionPane.showInputDialog(null, s);
	    	return response;
	    }
	

    private  Appointment makeAppointmentFromUserInput() {
    	try {
    	String fullName = AppointmentDialog.name.getText();
    	if(!isInputNotNull(fullName)) {
    		throw new BadAppointmentDataException("Must enter a value", "Empty or null value entered");
    	}
    	isNameCorrect(fullName); //continue if user provides correct input, otherwise throw BadAppointmentDataException
    		
   
		String phoneNumber = AppointmentDialog.phone.getText();
		if(!isInputNotNull(phoneNumber)) {
    		throw new BadAppointmentDataException("Must enter a value", "Empty or null value entered");
    	}
		isTelephoneNumberCorrect(phoneNumber);
		TelephoneNumber phone = new TelephoneNumber(phoneNumber); // after verifying user input, instantiate a new TelephoneNumber
		
		Calendar cal = makeCalendarFromUserInput(false);
		String activity = AppointmentDialog.activity.getText();      
		String category = AppointmentDialog.category.getSelectedItem().toString();
		
		Activity act = new Activity(activity, category);
		return (new Appointment(cal, fullName, phone, act));
		
    	}catch (BadAppointmentDataException ex) { ex.DisplayErrMessage(); return null; }
    }
 
    
 
    private static Calendar makeCalendarFromUserInput(boolean suppressHour) {
    	try {
    	Calendar cal = Calendar.getInstance();
    	int hour = 0;
    	
    	cal.clear();
		String date = AppointmentDialog.date.getText();
		if(!isInputNotNull(date)) {
    		throw new BadAppointmentDataException("Must enter a value", "Empty or null value entered");
    	}
		isDateCorrect(date);
		int day = Integer.parseInt(date.substring(0,2));
		int month = Integer.parseInt(date.substring(2,4))-1;  // offset by one to account for zero-based month in Calendar
		int year = Integer.parseInt(date.substring(4,8));
		
		if (!suppressHour) {				
		   String time = AppointmentDialog.time.getText();
		   hour = processTimeString(time);
		}

		cal.set(year, month, day, hour, 0);
		return (cal);
    	}catch (BadAppointmentDataException ex) {ex.DisplayErrMessage(); return null;}
    }
    

	private static int processTimeString(String t) {
		int hour = 0;
		t = t.trim();
		if (t.contains(":")) hour = Integer.parseInt(t.split(":")[0]);
		else if (t.contains (" ")) hour = Integer.parseInt(t.split(" ")[0]);
		else hour = Integer.parseInt(t);
		return ((hour < 8) ? hour+12 : hour);
	}

	
	private static void isTelephoneNumberCorrect(String number) { 
		if(!number.matches("([0-9]|-)+")) //throw BadAppointmentDataException if number contains anything other than digits 0-9 and '-' character
			throw new BadAppointmentDataException("Telephone numbers can only contain numbers or the character '-'","Bad character(s) in input string");
		if(!number.matches("^[\\d]{3}?(-)([\\d]{3})(-)([\\d]{4})")) 
			throw new BadAppointmentDataException("Missing digit(s); correct format is AAA-PPP-NNNN, where AAA is the area code and PPP-NNNN is the local number", "Incorrect format");
		if(number.charAt(0) == '1' || number.charAt(0) == '0') throw new BadAppointmentDataException("Area code can't start with a '0' or a '1'", "Invalid number"); 
		}
	

	private static void isNameCorrect (String name) {
		if(name.contains(" ") && name.length() > 30) throw new BadAppointmentDataException("Name cannot exceed 30 characters", "Name exceeds maximum length");
		if(!name.contains(" ") && name.length() > 30) throw new BadAppointmentDataException("Name cannot exceed 30 characters", "Name exceeds maximum length");
		String firstName = name.split(" ")[0];String lastName = name.split(" ")[1];
		if(!firstName.matches("([ .'a-zA-Z]|-)+") || !lastName.matches("([ .'a-zA-Z]|-)+")) 
			throw new BadAppointmentDataException("Name cannot include characters other than alphabetic characters, the dash(-), the period(.), and the apostrophe(')", "Illegal characters in name");
		if (firstName.length() > 30 || lastName.length() > 30) throw new BadAppointmentDataException("Name cannot exceed 30 characters", "Name exceeds maximum length");
		}
	

	private static boolean isInputNotNull(String empty) {
		if (empty.trim().isEmpty() || empty == null){
			return false;
		}
		return true;
		}
	
	private static void isDateCorrect(String date) {
		if (date != null) {
		Calendar cal = Calendar.getInstance();
		try {
			DateFormat dateFormat= new SimpleDateFormat("ddMMyyyy"); //set format for date
			dateFormat.setLenient(false);  
			Date dates = dateFormat.parse(date);
			cal.setTime(dates);
		}catch (RuntimeException e) { throw new BadAppointmentDataException(null,"General exception thrown; source date input");}
		catch (ParseException e) { throw new BadAppointmentDataException("Bad calendar date entered; format is DDMMYYYY", "Bad calendar format");}
		}
	}
		

	private static Appointment findAppointment(Calendar cal) {
	    Appointment a = new Appointment(cal,"","",new TelephoneNumber(),new Activity()); //instantiate new Appointment whose Calendar will be used as the key in the binarySearch method	
		Collections.sort(getAppointments(), new SortAppointmentByCalendar());
		int indexFound = Collections.binarySearch(getAppointments(),a, new SortAppointmentByCalendar());
		return   (indexFound >=0) ?  getAppointments().get(indexFound) :  null;
		}
	

	static ArrayList<Appointment> getAppointments() {return appointments;}
   
}

	
