package cst8284.asgmt4.scheduler;
import cst8284.asgmt4.employee.Dentist;

public class SchedulerLauncher {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater
		(new Runnable() { 
			public void run() {
				new Scheduler(new Dentist("Dr.Andrews")).launch();
			}
		});
	}

}

