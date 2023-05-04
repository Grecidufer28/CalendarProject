
import java.io.*;

public class Event implements java.io.Serializable, Comparable<Event>{
	private String title;
	private int startTime;
	private int endTime;

	private int monthNum;
	private int date;
	private int year;

	/**
	 * Constructor for Event
	 * @param theTitle name of Event
	 * @param theDate date of Event
	 * @param theStartTime time the Event starts
	 * @param theEndTime time the Event ends
	 */
	public Event(String theTitle, String theDate, int theStartTime, int theEndTime) {
		title = theTitle;

		String[] startDateInfo = theDate.split("/");
		monthNum = Integer.parseInt(startDateInfo[0]);
		date = Integer.parseInt(startDateInfo[1]);
		year = Integer.parseInt(startDateInfo[2]);

		startTime = theStartTime;

		endTime = theEndTime;
	}

	/**
	 This function returns title of Event
	 @return title A String data type
	 */
	public String getTitle() {
		return title;
	}

	/**
	 This function returns date of Event
	 @return date A String data type
	 */
	public String getDate() {
		return monthNum+"/"+date+"/"+year;
	}

	/**
	 This function returns starting time of Event
	 @return startTime  A String data type
	 */
	public String getStartTime() {
		return startTime + ":00";
	}

	/**
	 This function returns end time of Event
	 @return endTime A String data type
	 */
	public String getEndTime() {
		return endTime + ":00";
	}

	/**
	 This function checks if this Event equals to other Event or not
	 @param o A variable of Object type
	 @return true if this title of an event equals to the other one`s title of an event. false if they are different
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof Event) {
			Event that = (Event) o;
			return this.compareTo(that) == 0;
		} else {
			return false;
		}
	}

	/**
	 This function compares title of this Event with other one
	 @param that the other Event this needs to be compared to
	 @return 0 if the starting time of an this event and other one`s starting time are equal. < 0 if the starting time of this event is smaller. > 0 if the starting time of this event is greater
	 */
	@Override
	public int compareTo(Event that) {
		if (this.year - that.year == 0) {
			if (this.monthNum - that.monthNum == 0) {
				if (this.date - that.date == 0) {
					return this.startTime - that.startTime;

				}
				return this.date - that.date;

			}
			return this.monthNum - that.monthNum;

		}
		return this.year - that.year;
	}

	/**
	 * Reads Events from a text file
	 * @param br the BufferedReader to read the text file
	 * @return a new Event
	 * @throws IOException
	 */
	public static Event readEvent(BufferedReader br) throws IOException{
		String eventLine = br.readLine();
		if(eventLine == null) return null;
		String[] eventInfo = eventLine.split(";");
		return new Event(eventInfo[0], eventInfo[1]+"/"+eventInfo[2]+"/"+eventInfo[3],
				Integer.parseInt(eventInfo[4]),
				Integer.parseInt(eventInfo[5]));
	}

	/**
	 * Prints Events onto a text file
	 * @param pw the PrintWriter to write on the text file
	 * @param event the Event to record
	 */
	public static void printEvent(PrintWriter pw, Event event){
		pw.println(event);
	}

	/**
	 * Converts printed Event object to String sequence of its data
	 * @return data of Event
	 */
	public String toString(){
		return title+";"+monthNum+";"+date+";"+year+";"+startTime+";"+endTime;
	}
}