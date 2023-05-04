
import java.awt.BorderLayout;
import java.io.*;

import javax.swing.JFrame;


enum DAYS
{
	Sun, Mon, Tue, Wed, Thu, Fri, Sat ;
}
enum MONTHS
{
	Jan, Feb, March, Apr, May, June, July, Aug, Sep, Oct, Nov, Dec;
}

public class CalendarTester{
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		Model model = new Model();
		File eventsFile = new File("events.bin");
		if(eventsFile.exists()){
			try{
				FileReader fr = new FileReader(eventsFile);
				BufferedReader br = new BufferedReader(fr);
				boolean done = false;
				while(!done){
					Event event = Event.readEvent(br);
					if(event == null) { done = true; }
					else { model.addEvent(event); }
				}
			} catch(IOException x){

			}
		}
		
		EventView ev = new EventView(model);
		CalendarView v = new CalendarView(model);
		model.attach(ev);
		
		FromFile ff = new FromFile(model);

		frame.setSize(1500,1500);
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(v,BorderLayout.SOUTH);
		frame.add(ev,BorderLayout.CENTER);
		frame.add(ff, BorderLayout.EAST);
		frame.pack();
	}
}