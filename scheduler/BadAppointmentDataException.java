package cst8284.asgmt4.scheduler;

import javax.swing.JOptionPane;


public class BadAppointmentDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String Description;

	public BadAppointmentDataException(String s, String a) {super(s); setDescription(a);}

	public BadAppointmentDataException(){this("Please try again", "Bad data entered");}

	public String getDescription() {return Description;}

	public void setDescription(String description) {this.Description = description;}
	
	public void DisplayErrMessage() {
		JOptionPane.showMessageDialog(null, getMessage(), getDescription(), JOptionPane.ERROR_MESSAGE);
	}
	
	public String toString() {
		return getDescription() + "\n" + getMessage() + "\n";
	}
}



