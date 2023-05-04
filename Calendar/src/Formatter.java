
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Formatter implements FromFileFormatter{
	private BufferedReader br;
	private TreeSet<Event> events;
	/**
	 Constructor for Formatter
	 */
	public Formatter() {
		events = new TreeSet<>();
		try {
			br = new BufferedReader(new FileReader("input.txt"));
		} catch (FileNotFoundException e) {

		}
	}

	/**
	 This function creates all recurring events from input.txt file
	 @return ArrayList<Event> that contains all recurring events
	 */
	@Override
	public TreeSet<Event> formatEvents() throws IOException {
		String r;
		
		String str[];
			do {
				r=br.readLine();
				if(r==null) {
					break;
				}
				str = r.split(";");
				String title = str[0];
				int monthNum = Integer.parseInt(str[1]);
				int date = Integer.parseInt(str[2]);
				int year = Integer.parseInt(str[3]);
				int startTime = Integer.parseInt(str[4]);
				int endTime = Integer.parseInt(str[5]);
				for(int i=0; i<2000; i++) {
					year+=i;
					events.add(new Event(title, monthNum + "/" + date + "/" + year, startTime, endTime));
				}
			}while(r!=null);
			
			return events;
		
	}

}
