package cst8284.asgmt4.scheduler;
import java.util.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * This class deals with every aspect of an appointment. Appointment instances are composed of numerous fields, many of which are instances derived from other classes.
 * Calendar details, client names, {@link TelephoneNumber}, and {@link Activity} are all required in order to instantiate an Appointment. This class is used in 
 * the {@link Scheduler} class to create an Appointment instance from a user's input.
 * This class implements serializable.
 * 
 * @author Jephte Francois
 *@version 3.0
 */
public class Appointment implements Serializable{
	/**
	 * An instance of Calendar used as one of the parameters to instantiate an Appointment.
	 */
	private Calendar aptDate;
	/**
	 * The name of the customer.
	 */
	private String firstName, lastName;
	/**
	 * An instance of TelephoneNumber used as one of the parameters to instantiate an Appointment.
	 */
	private TelephoneNumber phone;
	/**
	 * An instance of Activity used as one of the parameters to instantiate an Appointment.
	 */
	private Activity activity; 
	/**
	 * The version number associated with this serializable class. 
	 */
	public static final long serialVersionUID = 1L;
	
	
	/**
	 * Requires four parameters, a Calendar instance, a fullName String, a TelephoneNumber instance, and an Activity instance.
	 * <p>
	 * With these parameters, the method implements constructor chaining to pass this information to a second constructor. Prior to passing these fields, the split method is implemented
	 * on the fullName. Essentially, the fullName is split each time a space is encountered. It is imperative that during the time of instantiation, the fullName String is implemented with
	 * a space in between, FirstName LastName for example. Any fullName information provided in a different manner will lead to an error. Thus a fullName of FirstName middleInitial LastName will
	 * certainly lead to errors, errors which this class is incapable of handling. The method looks for a single space and then considers everything before the space as the firstName field, and everything after the space as the lastName field. 
	 * As it currently stands this method is not capable of splitting the information in any other manner.
	 *   
	 * @param cal an instance of Calendar that contains all of the Appointment details relating to Calendar
	 * @param fullName a String composed of firstName and lastName with a space in between
	 * @param phone an instance of TelephoneNumber that contains all of the Appointment details relating to contact information
	 * @param activity an instance of Activity, containing Appointment details relating to description of work and category
	 */
	public Appointment(Calendar cal, String fullName, TelephoneNumber phone, Activity activity) {
		this(cal,fullName.split(" ")[0], fullName.split(" ")[1], phone, activity);  
	}
	
	/**
	 * Receives the parameters passed from the first constructor
	 * <p>
	 * This constructor contains an extra parameter in comparison to it's counterpart. This extra parameter is not really an extra parameter. It is the result of the split action that was performed in
	 * the first constructor. After the fullName String is split, this constructor now holds the resulting Strings in firstName and lastName respectively. {@link #setAptDate(Calendar)}, 
	 * {@link #setFirstName(String)}, {@link #setLastName(String)}, {@link #setPhone(TelephoneNumber)}, and {@link #setActivity(Activity)} are implemented in order to properly assign their values to
	 * an Appointment instance. Now an instance of Appointment can be used to access this information whenever it's required. 
	 * @param cal an instance of Calendar that contains all of the Appointment details relating to Calendar
	 * @param firstName String that stores the first half of the split mechanism employed in the first constructor, it is the firstName portion of the fullName String
	 * @param lastName String that stores the remaining half of the split mechanism employed in the first constructor, it is the lastName portion of the fullName String
	 * @param phone an instance of TelephoneNumber that contains all of the Appointment details relating to contact information
	 * @param activity an instance of Activity, containing Appointment details relating to description of work and category
	 */
	public Appointment(Calendar cal, String firstName, String lastName, TelephoneNumber phone, Activity activity) {
		setAptDate(cal);
		setFirstName(firstName);
		setLastName(lastName);
		setPhone(phone);
		setActivity(activity);
	}
	
	/**
	 * Changes the values of an Appointment date, more specifically it changes the values of the Calendar of a particular Appointment.
	 * This method must take a valid Appointment date as an argument, which is an instance of a Calendar.
	 * 
	 * @param aptDate the new value the Appointment Date will be changed to, this must be a valid Calendar object
	 */
	public void setAptDate(Calendar aptDate) {this.aptDate = aptDate;}
	/**
	 * Returns the details of the Calendar instance passed as a parameter to the constructor
	 * This method cannot return anything asides from a Calendar object, thus it must be used to return the details of a particular Calendar object
	 * 
	 * @return aptDate the Calendar instance containing the Appointment details, pertaining to the Day, month, year and time of a particular Appointment
	 */
	public Calendar getAptDate() {return aptDate;}
	
	/**
	 * Changes the values of a FirstName
	 * This method must take a String as a parameter when called, this String is used to change the values of the firstName field.
	 * <p>
	 * When an Appointment is instantiated, it takes a fullName as a parameter. In the format FirstName LastName, this method provides new values for the FirstName portion of the fullName String. 
	 * This method can prove useful, if the firstName is to be accessed from outside the class. As well, it can be used to update  the values of a firstName if need be. It is important to keep
	 * in mind that due to the manner in which the Appointment constructors divide the fullName, it is important that a valid fullName String contains a space. As well it must not include any special characters,
	 * asides from the (-), the period (.) and the apostrophe (').
	 * 
	 * @param firstName the new value the Appointment firstName will be changed to, this must be a String
	 */
	public void setFirstName(String firstName) {this.firstName = firstName;}
	/**
	 * Returns the details of the first half of the fullName passed as a parameter to the constructor 
	 * In the format FirstName LastName, this method would return the FirstName portion of the String 
	 * This method should only be used to return the information contained within the firstName String
	 *  
	 * @return firstName the details of the firstName
	 */
	public String getFirstName() {return firstName;}
	
	/**
	 * Changes the values of a LastName
	 * This method must take a String as a parameter when called, this String is used to change the values of the lastName field.
	 * <p>
	 * When an Appointment is instantiated, it takes a fullName as a parameter. In the format FirstName LastName, this method provides new values for the LastName portion of the fullName String. 
	 * This method can prove useful, if the lastName is to be edited from outside the class. As well, it can be used to update  the values of a lastName if need be. An example use of this method
	 * would be a case where an individual wishes to change their maiden name in the records. It is important to keep in mind that due to the manner in which the Appointment constructors divide the fullName,
	 * it is important that a valid lastName String does not contain a space and does not include any special characters, asides from the (-), the period (.) and the apostrophe (')
	 * 
	 * @param lastName the new value the Appointment lastName will be changed to, this must be a String
	 */
	public void setLastName(String lastName) {this.lastName = lastName;}
	/**
	 * Returns the details of the second half of the fullName passed as a parameter to the constructor.
	 * In the format FirstName LastName, this method would return the LastName portion of the String.
	 * This method should only be used to return the information contained within the lastName String.
	 * 
	 * @return lastName the details of the lastName
	 */
	public String getLastName() {return lastName;}
	
	/**
	 * Changes the values of an Appointment TelephoneNumber, more specifically it changes the values of the TelephoneNumber instance,passed as a parameter to the constructor.
	 * This method must take a valid Appointment phone number as an argument, which is an instance of a TelephoneNumber.
	 * The phone number should be made up of Strings in the proper format as per the TelephoneNumber class.
	 * 
	 * @param phone the new value the Appointment phone number will be changed to, this must be a valid TelephoneNumber object
	 */
	public void setPhone(TelephoneNumber phone) {this.phone=phone;}
	/**
	 * Returns the details of the TelephoneNumber instance passed as a parameter to the constructor.
	 * This method cannot return anything asides from a TelephoneNumber object, thus it must be used to return the details of a particular TelephoneNumber object.
	 * 
	 * @return phone the TelephoneNumber instance containing the Appointment details, pertaining to the areaCode, prefix, and lineNumber for a particular Appointment
	 */
	public TelephoneNumber getPhone() {return phone;}
	
	/**
	 * Changes the values of an Appointment Activity, more specifically it changes the values of the Activity instance,passed as a parameter to the constructor.
	 * This method must take a valid Appointment activity as an argument, which is an instance of a Activity.
	 * 
	 * @param activity  the new value the Appointment activity will be changed to, this must be a valid Activity object
	 */
	public void setActivity(Activity activity) {this.activity = activity;}
	/**
	 * Returns the details of the Activity instance passed as a parameter to the constructor.
	 * This method cannot return anything asides from a Activity object, thus it must be used to return the details of a particular Activity object.
	 * 
	 * @return activity the Activity instance containing the Appointment details, pertaining to the descriptionOfWork, and the category for a particular Appointment
	 */
	public Activity getActivity() {return activity;}
	
	/**
	 *Provides format for all Appointment details to be displayed on the screen, including Calendar day, month, year, time. FullName details, including firstName and 
	 *lastName. TelephoneNumber details, such as areaCode, prefix, and lineNumber. Description of work and category, relating to Activity details. 
	 *<p>
	 *This method changes the default display options of Calendar. Appointment details relating to the date are input as DDMMYYYY, where DD is the day, MM is the month and YYYY is the year.
	 *These same details are then formatted by this method and displayed accordingly: EEE MMM dd yyyy HH:mm, where EEE is the actual day of the week, MMM represents the month, dd represents the day,
	 *yyyy represents the year, HH represents the hour, and mm represents the minute. An example of a correctly formated Calendar using this method would be:
	 *Sun Oct 20 2019 15:00. This method utilizes {@link #getAptDate()}, {@link #getFirstName()}, {@link #getLastName()}, {@link #getPhone()}, and {@link #getActivity()}
	 *as a way of accessing the private fields required in order to output the Appointment details. As it stands this method can only be used to display Appointment details. If there comes a time
	 *where there is a need to change the displayed information e.g. adding more information to be displayed for a TelephoneNumber, then the changes should be applied to the respective
	 *toString() method of that particular class. To make changes in the manner in which an Activity instance is displayed, then the changes should be done in Activity's toString() method, the same
	 *can be said for any other fields an Appointment takes as parameters that do not directly originate from this class. 
	 *
	 *@return the String message displaying all of the Appointment details
	 */
	public String toString() {
		 SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm"); 
		return sdf.format(getAptDate().getTime()) + "\n" + getFirstName() + " " + getLastName() + "\n"+ getPhone() +"\n" + getActivity() +"\n";//Format date & print all appointment details
	}
		
	}


