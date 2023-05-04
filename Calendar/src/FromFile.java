
import java.io.IOException;
import java.util.TreeSet;

import javax.swing.JButton;

public class FromFile extends JButton{
	private Model<Event> model;
	private FromFileFormatter f;
	private TreeSet<Event> events;
	/**
	 Constructor for FromFile and it adds actionListener to FromFile button
	 @param m Mode<Event> type variable
	 */
	public FromFile(Model<Event> m) {
		super("FROM FILE");
		model = m;
		f = new Formatter();
		addActionListener(e->{
			try {
				events=f.formatEvents();
				model.addAllEvent(events);	
				System.out.println("Recurring events have been loaded!\n\n");
				m.notifyView(m.getCurDate(), m.getCurMonth(),m.getCurDay());
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
	}

}
