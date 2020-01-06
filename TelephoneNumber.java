package cst8284.asgmt4.scheduler;
import java.io.Serializable;

/**
 * The TelephoneNumber class deals with every aspect of a phone number. This class contains methods that, organize, set, and display information regarding 
 * Telephone numbers. This class is used in {@link Appointment} to instantiate a new Appointment object. 
 * This class cannot be used to instantiate anything other than a TelephoneNumber. While an instance this class is used as one of the arguments when instantiating an Appointment object, 
 * the same cannot be said on the other hand. Essentially you simply cannot pass an Appointment object as an argument when instantiating a new TelephoneNumber.
 * This class implements serializable.
 * 
 * @author Jephte Francois
 * @version 3.0
 */
public class TelephoneNumber implements Serializable{
	/**
	 * Contain the information required for a valid phone number. These constants are used to parameterize a TelephoneNumber object.
	 */
	private int areaCode,prefix,lineNumber;
	
	/**
	 * Passes a single default String, to it's chained counterpart.
	 * Allows for the instantiation of a TelephoneNumber object, without requiring the need for any arguments.
	 * Used in {@link Scheduler#findAppointment(Calendar)} to aid in instantiating a new Appointment object.
	 */
	public TelephoneNumber() {this("000-000-0000");} // no-arg constructor used in Scheduler findAppointment method
	/**
	 * Receives a parameter passed from the no-arg constructor.
	 * Allows for the instantiation of a TelephoneNumber object, requiring the need for a single parameter passed as a String.
	 * <p>
	 * When instantiating a TelephoneNumber using this constructor you must provide it a valid String. It is this String that is used in {@link #getAreaCode()}, {@link #getLineNumber()},
	 * and {@link #getPrefix()} to return the values of the provided phoneNumber. This constructor splits apart the given String each time it encounters a '-' character. It then proceeds to convert
	 * these values into an int value as opposed to a String. The first index(index 0) contains the areaCode, the second(index 1) contains the prefix, and the third(index 2) contains the 
	 * lineNumber. This method utilizes {@link #setAreaCode(int)}, {@link #setPrefix(int)}, and {@link #setLineNumber(int)} to assign these new integer values to their appropriate fields. 
	 * <p>
	 * In short this constructor takes a TelephoneNumber as a String and converts it to an int. This method must be given a valid Telephone Number String in the format AAA-PPP-NNNN, where AAA is the area code
	 * and PPP-NNNN is the local number. Failure to comply to this request will lead to an error, more specifically the error thrown would be a NumberFormatException. This constructor on its own is
	 * ill equipped to handle any errors associated with the String input as it's parameter.  
	 * 
	 * @param phoneNumber a String containing the TelephoneNumber information
	 * @throws NumberFormatException if user input is incorrect
	 */
	public TelephoneNumber(String phoneNumber) { 
		//split and convert phoneNumber into integer values
		areaCode = Integer.valueOf(phoneNumber.split("-")[0]);
		prefix = Integer.valueOf(phoneNumber.split("-")[1]);
		lineNumber = Integer.valueOf(phoneNumber.split("-")[2]);
		
		//set values for areaCode,prefix,lineNumber
		setAreaCode(areaCode);
		setPrefix(prefix);
		setLineNumber(lineNumber);
	}
	
	/**
	 * Returns an int value of the first three characters in the phoneNumber String passed as a parameter to the constructor.
	 * <p>
	 * This method cannot be used to access the entire phoneNumber String. It's purpose is to provide access to the first three characters in the String,
	 * who themselves are available only within the class.
	 * 
	 * @return the first three characters in the phoneNumber String as a int value
	 */
	public int getAreaCode() {return areaCode;}
	/**
	 * Changes the values of an areaCode.
	 * This method must take a valid areaCde, which is a three digit int value.
	 * Should not be used to change the prefix and lineNumber values.
	 * 
	 * @param areaCode the new value the areaCode will be changed to
	 */
	public void setAreaCode(int areaCode) {this.areaCode=areaCode;}
	
	/**
	 * Returns an int value of the second set of characters in the phoneNumber String passed as a parameter to the constructor.
	 * <p>
	 * This method cannot be used to access the entire phoneNumber String. It's purpose is to provide access to the second set of characters in the String,
	 * who themselves are available only within the class. In the format of AAA-PPP-NNNN, this method returns only the PPP portion as an int value.
	 * 
	 * @return the prefix of the phoneNumber String as an int
	 */
	public int getPrefix() {return prefix;}
	/**
	 * Changes the values of a prefix.
	 * This method must take a valid prefix, which is a three digit int value.
	 * This method should not be used to change the areaCode and lineNumber values.
	 * 
	 * @param prefix the new value the prefix will be changed to
	 */
	public void setPrefix(int prefix) {this.prefix = prefix;}
	
	/**
	 * Returns an int value of the last set of characters in the phoneNumber String passed as a parameter to the constructor.
	 * <p>
	 * This method cannot be used to access the entire phoneNumber String. It's purpose is to provide access to the third set of characters in the String,
	 * who themselves are available only within the class. In the format of AAA-PPP-NNNN, this method returns only the NNNN portion as an int value.
	 * 
	 * @return the lineNumber of the phoneNumber String as an int
	 */
	public int getLineNumber() {return lineNumber;}
	/**
	 * Changes the values of a lineNumber.
	 * This method must take a valid lineNumber, which is a three digit int value.
	 * Should not be used to change the areaCode and prefix values.
	 * 
	 * @param linenumber the new value the lineNumber will be changed to
	 */
	public void setLineNumber(int linenumber) {this.lineNumber = linenumber;}
	
	/**
	 *Provides format for TelephoneNumber details to be displayed on the screen.
	 *This method displays the areaCode first, followed by the prefix, then finally the lineNumber. This method utilizes {@link #getAreaCode()}, {@link #getPrefix()}, and as well as
	 *{@link #getLineNumber()} to display the TelephoneNumber details.
	 *<p>
	 *Generally when  a user inputs a TelephoneNumber containing zeroes the zeroes are omitted from the displayed output. This method is designed to handle this circumstance. 
	 *The TelephoneNumber String is formatted within this method so that should a 0 value be entered, it would still be displayed in the output.
	 *This method  should be used to display only TelephoneNumber details. 
	 *
	 *@return the String message displaying the TelephoneNumber details
	 */
	public String toString() {
		//Format TelephoneNumber details & pad with zeros, in the event that user inputs phone number containing 0's
	  return "(" +String.format("%03d",getAreaCode()) + ")" + " " +String.format("%03d",getPrefix()) + "-" +String.format("%04d",getLineNumber());

	}
}
