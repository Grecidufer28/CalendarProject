
import java.util.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Model <Event> extends TreeSet<Event> implements java.io.Serializable{
	private ArrayList<ChangeListener> listeners;
	private String date;
	private String curMonth;
	private String curDay;
	
	/**
	 Constructor for Model
	 @param none
	 */
	public Model() {
		listeners = new ArrayList<>();
		
	}

	/**
	 This function attaches ChangeListener
	 @param l ChangeListener variable type
	 */
	public void attach(ChangeListener l) {
		listeners.add(l);
	}
	
	/**
	 This function adds collection of events
	 @param events Collection<Event> type of variable
	 */
	public void addAllEvent(Collection<Event> events) {
		addAll(events);
	}
	/**
	 This function adds an Event to its lists
	 @param event Event type variable
	 */
	public boolean addEvent(Event event) { return add(event); }
	
	/**
	 This function returns all lists of events
	 @param none
	 @return this ArrayList<Event> type
	 */
	public TreeSet<Event> getAllEvents() {
		return this;
	}

	/**
	 This function returns current date of month
	 @param none
	 @return curDay A String data type 
	 */
	public String getCurDay() {
		return curDay;
	}
	
	/**
	 This function returns current date
	 @param none
	 @return date A String data type 
	 */
	public String getCurDate() {
		return date;
	}
	/**
	 This function returns current month
	 @param none
	 @return curMonth A String data type 
	 */
	public String getCurMonth() {
		return curMonth;
	}
	/**
	 This function notifies its changes of state to attached views 
	 @param d A String data type 
	 @param m A String data type 
	 @param c A String data type 
	 */
	public void notifyView(String d, String m, String c) {
		curDay=c;
		date = d;
		curMonth = m;
		for(ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
}
