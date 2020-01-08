package cst8284.asgmt4.scheduler;
import java.util.Comparator;

/**
 * Provides a blueprint for organizing Appointment objects as requested. 
 * This class must be used with type Appointment only. 
 * 
 * @author Jephte Francois
 * @version 1.0
 */
public class SortAppointmentByCalendar implements Comparator<Appointment>{
	/**
	 *Compares two appointments
	 *<p>
	 *This method must take two objects of type Appointment. It cannot be used with any other types. Pass the two Appointment objects as arguments to the method. 
	 *The two Appointment objects are then compared, and an integer value is returned. The method utilizes the built in compareTo method for Calendars. If the two Appointments are equal,
	 *(meaning their dates are identical) the method returns an integer value representing this result. An integer value of 0 suggests that the two Appointments are equal.
	 *The method returns an integer value of -1 if the the first Appointment passed as a parameter to this method has an earlier date than the second Appointment. An integer value of 1 is returned in cases where the first Appointment 
	 *passed as a parameter to this method has a later date than the second Appointment. 
	 *
	 *@param  a the first Appointment to be compared
	 *@param  b the second Appointment to be compared
	 *@return an integer value of 0, -1, or 1, representing the difference between the two Appointments, it is this integer that is used to organize the Appointments
	 */
	public int compare(Appointment a, Appointment b) {
	 return a.getAptDate().compareTo(b.getAptDate()); //use Calendar's compareTo method to compare the Calendar's of two Appointments
	}

}
