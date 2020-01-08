package cst8284.asgmt4.scheduler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


public class SchedulerViewer {
	private static final Toolkit tk = Toolkit.getDefaultToolkit();
	private static final Dimension screenSize = tk.getScreenSize();
	private static final JTextArea scrollText = new JTextArea();
	public static JFrame frame;

	public static void reloadJTextArea(String text) { scrollText.setText(text); }
	
	static void loadAndShowWords() {
		frame = new JFrame();
		frame.setTitle("Scheduler Viewer");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int screenX = (int) screenSize.getWidth() / 2;
		int screenY = (int) (7 * screenSize.getHeight() / 8);
		  
	
		
		
			frame.add(getWestPanel(), BorderLayout.WEST);
			frame.add(getCenterPanel(scrollText, screenY), BorderLayout.CENTER);
			frame.setPreferredSize(new Dimension(screenX, screenY));

			frame.pack();
			frame.setVisible(true);
		
	}

	public static JPanel getCenterPanel(JTextArea jta, int height) {
		JScrollPane centerPane = new JScrollPane(jta);
		centerPane.setPreferredSize(new Dimension(400, 7 * height / 8));
		JPanel jp = new JPanel();
		jp.add(centerPane);
		return jp;
	}

	public static JPanel getWestPanel() {
		Scheduler s = new Scheduler();
		JPanel cPanel = new JPanel(new GridLayout(6, 1));
		JPanel westPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.weighty = 1;
		cPanel.add(setWestPanelBtns(" Save Appointment          ", e ->  {AppointmentDialog.showAppointmentDialog();}));
		cPanel.add(setWestPanelBtns("   Display Appointment     ",e -> { AppointmentDialog.showAppointmentDialog();}));
		cPanel.add(setWestPanelBtns("   Display Schedule        ", e -> { AppointmentDialog.showDaySchedule();}));
		cPanel.add(setWestPanelBtns("   Save Appointments to file       ", s.new SaveAppointmentsToFile()));
		cPanel.add(setWestPanelBtns("      Load Appointments from File       ", s.new LoadAppointmentsFromFile()));
		cPanel.add(setWestPanelBtns("      Exit       ", e -> {Scheduler.saveAppointmentsToFile(Scheduler.getAppointments(),  "CurrentAppointments.apts"); 
		frame.dispose(); 
		} ));

		westPanel.add(cPanel, gbc);
		return westPanel;
	}

	private static JButton setWestPanelBtns(String btnLabel, ActionListener act) {
		final Font font = new Font("SansSerif", Font.PLAIN, 20);
		JButton btn = new JButton(btnLabel);
		btn.setFont(font);
		btn.addActionListener(act);
		return btn;
	}
	
	
}
