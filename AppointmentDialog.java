package cst8284.asgmt4.scheduler;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import cst8284.asgmt4.employee.Employee;
import cst8284.asgmt4.scheduler.Scheduler.SaveAppointment;

/* Adapted, with considerable modification, from 
 * http://www.java2s.com/Code/Java/Swing-JFC/TextAcceleratorExample.htm,
 * which is sloppy code and should not be emulated.
 */

public class AppointmentDialog {
	
	private static final GridBagConstraints textConstants = new GridBagConstraints(
	    	0, GridBagConstraints.RELATIVE, 1, 1, 1, 1,  // gridx, gridy, gridwidth, gridheight, weightx, weighty
	    	GridBagConstraints.EAST, 0, new Insets(2, 2, 2, 2), 1, 1); // anchor, fill, insets, ipadx, ipady
		private static final GridBagConstraints labelConstants = new GridBagConstraints(
	    	1, GridBagConstraints.RELATIVE, 1, 1, 1.0, 0, 
	    	GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0);


		private static Container cp;
		private static final int labelWidth = 35;
		private static final Font defaultFont = new Font("SansSerif", Font.PLAIN, 16);
		public static JTextField name;
		public static JTextField phone;
		public static JTextField date;
		public static JTextField time;
		public static JTextField activity;
		public static JComboBox <String>category;
		
		
		public static void showAppointmentDialog(){
			Scheduler s = new Scheduler();
		    JFrame f = new JFrame("Get, set, change or delete an appointment");  
		    cp = f.getContentPane();
		    cp.setLayout(new GridBagLayout());

		    name = setRow("Enter Client Name (as FirstName LastName):", 'n');
		    phone = setRow("Phone Number (e.g. 613-555-1212):", 'p');
		    date = setRow("Appointment Date (entered as DDMMYYYY):", 'd');
		   
		    time = setRow("Appointment Time:", 't');
		    activity = setRow("Activity Description", 'a');
		    category = getComboBox();
		    JLabel l;
		    cp.add(l = new JLabel("Enter a category from the following menu:", SwingConstants.RIGHT), textConstants);
		    l.setFont(defaultFont);
		    cp.add(category, labelConstants);
		   
		    cp.add(setSouthPanelBtns(" Save          ", s.new SaveAppointment()),textConstants);
		    cp.add(setSouthPanelBtns(" Find          ", s.new DisplayAppointment()),textConstants);
		    cp.add(setSouthPanelBtns(" Change          ", s.new ChangeAppointment()),textConstants);
		  
		    cp.add(setSouthPanelBtns(" Delete          ", s.new DeleteAppointment()),textConstants);

		    cp.add(setSouthPanelBtns(" Exit          ", new ActionListener() {@Override
				public void actionPerformed(ActionEvent e) {f.dispose();}}),textConstants);
		       	    	
		    f.pack();
		    f.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent evt) {
		    	f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		      }
		    });
		    f.setVisible(true);
		  }
		
		public static void showDaySchedule() {
			Scheduler s = new Scheduler();
			 JFrame f = new JFrame("Display Day Schedule");  
			  cp = f.getContentPane();
			  cp.setLayout(new GridBagLayout());
			  date = setRow("Appointment Date (entered as DDMMYYYY):", 'd');
			  cp.add(setSouthPanelBtns(" View          ", s.new DisplaySchedule()),textConstants);
			    f.pack();
			    f.addWindowListener(new WindowAdapter() {
			    public void windowClosing(WindowEvent evt) {
			    	f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			      }
			    });
			    f.setVisible(true);
		}
		
		private static JTextField setRow(String label, char keyboardShortcut) {
			JLabel l; JTextField t;
			cp.add(l = new JLabel(label, SwingConstants.RIGHT), textConstants);
			l.setFont(defaultFont);
			l.setDisplayedMnemonic(keyboardShortcut);
		    cp.add(t = new JTextField(labelWidth), labelConstants);
		    t.setFocusAccelerator(keyboardShortcut);
		    return t;
		}
		

		private static JButton setSouthPanelBtns(String btnLabel, ActionListener act) {
			JPanel bottom = new JPanel();
			final Font font = new Font("Dialog", Font.PLAIN, 10);
			JButton btn = new JButton(btnLabel);
			btn.setFont(font);
			btn.addActionListener(act);
			return btn;
		}
		
		public static JComboBox<String> getComboBox() {
			ArrayList<String> activityType = new ArrayList <>();
			activityType = Scheduler.getEmployee().getActivityType();
			String [] something = new String[activityType.size()];
			String []convert = activityType.toArray(something);
			
			 JComboBox<String> categories = new JComboBox<String>(convert);

			 return categories;
	}
	
	  
}
