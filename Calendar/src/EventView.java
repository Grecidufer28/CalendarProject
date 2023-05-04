
import java.awt.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EventView extends JPanel implements ChangeListener{
	private TreeSet<Event> events;
	private JTextArea area;
	private Model<Event> model;
	private JLabel label;
	private JPanel panel;
	private JPanel qPanel;
	private ArrayList<Event> l;
	private int mode;
	private Calendar c;
	private Date date;
	private String fDate;
	private String tDate;
	private JButton dBtn;
	private JButton wBtn;
	private JButton mBtn;
	private JButton aBtn;
	private JPanel btnPanel;
	private JScrollPane vertical;
	private Date now;
	private JButton enter;
	private SimpleDateFormat dateFormatter;
	/**
	 Constructor for EventView
	 @param none
	 */
	public EventView(Model<Event> m) {
		model=m;
		c=new GregorianCalendar();
		mode=0;
		dateFormatter = new SimpleDateFormat("M/dd/yyyy");
		now = new Date();
		createBtns();
		addToPanels();
	}
	
	/**
	 This function add all components to panels
	 @param none
	 */
	public void addToPanels() {
		btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		btnPanel.add(dBtn);
		btnPanel.add(wBtn);
		btnPanel.add(mBtn);
		btnPanel.add(aBtn);
		btnPanel.add(new JLabel("    "));
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(430,200));
		label = new JLabel(dateFormatter.format(now));
		events = model.getAllEvents();
		int month = c.get(Calendar.MONTH)+1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int year = c.get(Calendar.YEAR);
		area = new JTextArea("");
		for(Event event: events){
			String dateLine = event.getDate();
			String firstDate = dateLine.split("-")[0];
			String[] firstDateInfo = firstDate.split("/");
			if(year == Integer.parseInt(firstDateInfo[2])){
				if(month == Integer.parseInt(firstDateInfo[0])){
					if(day == Integer.parseInt(firstDateInfo[1])){
						if(!event.getStartTime().equals(event.getEndTime())) {
							area.append(event.getTitle() + " " + event.getDate() + ": " + event.getStartTime()
									+ " - " + event.getEndTime() + "\n");
						} else {
							area.append(event.getTitle() + " " + event.getDate() + ": " + event.getStartTime()+"\n");
						}

					}
				}
			}
		}
		if(area.getText().length() == 0) {
			area.setText("NO EVENTS");
		}
		
		vertical = new JScrollPane(area);
		vertical.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		qPanel = new JPanel();
		qPanel.setLayout(new BorderLayout());
		qPanel.add(label,BorderLayout.CENTER);
		qPanel.add(btnPanel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout());
		panel.add(qPanel,BorderLayout.NORTH);
		panel.add(vertical,BorderLayout.CENTER);
		setLayout(new BorderLayout());
		add(panel);
	}
	
	/**
	 This function creates buttons and adds ActionListeners to each buttons
	 @param none
	 */
	public void createBtns() {
		dBtn = new JButton("Day");
		wBtn = new JButton("Week");
		mBtn = new JButton("Month");
		aBtn = new JButton("Agenda");
		
		dBtn.setBackground(Color.GRAY);
		
		
		dBtn.addActionListener(e->{
			mode=0;
			dBtn.setBackground(Color.GRAY);
			wBtn.setBackground(Color.WHITE);
			mBtn.setBackground(Color.WHITE);
			aBtn.setBackground(Color.WHITE);
			if(model!=null)
				model.notifyView(model.getCurDate(), model.getCurMonth(), model.getCurDay());
		});
		
		wBtn.addActionListener(e->{
			mode=1;
			wBtn.setBackground(Color.GRAY);
			dBtn.setBackground(Color.WHITE);
			mBtn.setBackground(Color.WHITE);
			aBtn.setBackground(Color.WHITE);
			if(model!=null)
				model.notifyView(model.getCurDate(), model.getCurMonth(), model.getCurDay());
		});
		
		mBtn.addActionListener(e->{
			mode=2;
			mBtn.setBackground(Color.GRAY);
			wBtn.setBackground(Color.WHITE);
			dBtn.setBackground(Color.WHITE);
			aBtn.setBackground(Color.WHITE);
			if(model!=null)
				model.notifyView(model.getCurDate(), model.getCurMonth(), model.getCurDay());
		});
		
		aBtn.addActionListener(e->{
			mode=3;
			aBtn.setBackground(Color.GRAY);
			wBtn.setBackground(Color.WHITE);
			mBtn.setBackground(Color.WHITE);
			dBtn.setBackground(Color.WHITE);
			
			if(model!=null) {
				setBackground(Color.GRAY);
				JPanel agendaPanel = new JPanel();
				JTextField from = new JTextField();
				JTextField to = new JTextField();
				from.setPreferredSize(new Dimension(100,20));
				to.setPreferredSize(new Dimension(100,20));
				agendaPanel.setLayout(new BoxLayout(agendaPanel, BoxLayout.X_AXIS));
				agendaPanel.add(new JLabel("Date from  "));
				agendaPanel.add(from);
				agendaPanel.add(new JLabel(" to  "));
				agendaPanel.add(to);
				enter = new JButton("Enter");
				enter.addActionListener(e1->{
					fDate = from.getText();
					tDate = to.getText();
					if(!model.getAllEvents().isEmpty())
						model.notifyView(model.getCurDate(), model.getCurMonth(), model.getCurDay());
				
				});
				agendaPanel.add(enter);
				JFrame agendaFrame = new JFrame();
				agendaFrame.add(agendaPanel);
				agendaFrame.setVisible(true);
				agendaFrame.pack();
			}	
		});
	}
	
	/**
	 This function updates text area view part from any changes made in Model class. 
	 @param e A ChangeEvent data type
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		panel.removeAll();

		model = (Model<Event>) e.getSource();
		if(mode==0) {	
			vertical.repaint();
			ArrayList<Event> cl = getCurdayEvent(model.getCurDate());
	
			label.setText(model.getCurDate());
			panel.add(qPanel,BorderLayout.NORTH);
			

			if(!cl.isEmpty()) {
				area.setText("");
				for(Event event : cl) {
					if(!event.getStartTime().equals(event.getEndTime())) {
						area.append(event.getTitle() + " " + event.getDate() + ": " + event.getStartTime()
								+ " - " + event.getEndTime() + "\n");
					} else {
						area.append(event.getTitle() + " " + event.getDate() + ": " + event.getStartTime()+"\n");
					}

				}
				panel.add(vertical,BorderLayout.CENTER);
			}
			else {
				area.setText("NO EVENTS");
				panel.add(vertical,BorderLayout.CENTER);
			}
			panel.repaint();
		}
		else if(mode==1) {
			ArrayList<Event> wl = getWeekEvent();
			
			label.setText(model.getCurDate());
			panel.add(qPanel,BorderLayout.NORTH);
			if(!wl.isEmpty()) {
				area.setText("");
				for(Event event:wl) {
					if(!event.getStartTime().equals(event.getEndTime())) {
						area.append(event.getTitle() + " " + event.getDate() + ": " + event.getStartTime()
								+ " - " + event.getEndTime() + "\n");
					} else {
						area.append(event.getTitle() + " " + event.getDate() + ": " + event.getStartTime()+"\n");
					}
				}
				panel.add(vertical,BorderLayout.CENTER);
			}
			else {
				area.setText("NO EVENTS");
				panel.add(vertical,BorderLayout.CENTER);
			}
			panel.repaint();
			
		}
		else if(mode==2) {
			ArrayList<Event> ml = getMonthEvent(model.getCurMonth(), c.get(Calendar.YEAR)+"");
			label.setText(model.getCurDate());
			panel.add(qPanel,BorderLayout.NORTH);
			
			if(!ml.isEmpty()) {
				area.setText("");
				for(Event event:ml) {
					if(!event.getStartTime().equals(event.getEndTime())) {
						area.append(event.getTitle() + " " + event.getDate() + ": " + event.getStartTime()
								+ " - " + event.getEndTime() + "\n");
					} else {
						area.append(event.getTitle() + " " + event.getDate() + ": " + event.getStartTime()+"\n");
					}
				}
				//panel.add(area,BorderLayout.CENTER);
				panel.add(vertical,BorderLayout.CENTER);
			}
			else {
				area.setText("NO EVENTS");
				panel.add(vertical,BorderLayout.CENTER);
			}
			panel.repaint();
		}
		else if(mode==3){
			ArrayList<Event> al;
			try {
				al = getAgendaEvent(fDate,tDate);
				label.setText(model.getCurDate());
				panel.add(qPanel,BorderLayout.NORTH);
				
				if(!al.isEmpty()) {
					area.setText("");
					for(Event event:al) {
						if(!event.getStartTime().equals(event.getEndTime())) {
							area.append(event.getTitle() + " " + event.getDate() + ": " + event.getStartTime()
									+ " - " + event.getEndTime() + "\n");
						} else {
							area.append(event.getTitle() + " " + event.getDate() + ": " + event.getStartTime()+"\n");
						}
					}
					panel.add(vertical,BorderLayout.CENTER);
				}
				else {
					area.setText("NO EVENTS");
					
					panel.add(vertical,BorderLayout.CENTER);
				}
				panel.repaint();
				
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 This function gathers all current day events
	 @param curDay A variable of String type
	 @return ArrayList<Event> that contains events with current date
	 */
	public ArrayList<Event> getCurdayEvent(String curDay) {
		l = new ArrayList<>();
		events = model.getAllEvents();
		for(Event e : events) {
			if(e.getDate().equals(curDay)) {
				l.add(e);
			}
		}
		return l;
		
	}
	/**
	 This function gathers all events that are in the same week of current day
	 @param none
	 @return ArrayList<Event> that contains events with the same week of current day
	 */
	public ArrayList<Event> getWeekEvent(){
		l = new ArrayList<>();
		ArrayList<String> sl = new ArrayList<>();
		events=model.getAllEvents();

		c.setFirstDayOfWeek(Calendar.SUNDAY);
		c.set(2018, Integer.parseInt(model.getCurMonth()) -1 , Integer.parseInt(model.getCurDay()));
		int to = (7-c.get(Calendar.DAY_OF_WEEK));
		int from = (c.get(Calendar.DAY_OF_WEEK) -1);
		
		for(int i = 0; i<= to; i++) {
			sl.add((c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DATE)+"/"+c.get(Calendar.YEAR));
			c.add(Calendar.DATE, 1);
		}
		c.set(2018, Integer.parseInt(model.getCurMonth()) -1 , Integer.parseInt(model.getCurDay()));
		for(int i = 1 ;i<=from ;i++) {
			c.add(Calendar.DATE, -1);
			sl.add((c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DATE)+"/"+c.get(Calendar.YEAR));

		}
		for(Event e: events) {
			for(String s: sl) {
				if(e.getDate().equals(s)) {
					l.add(e);
				}
			}
		}
		return l;
	}
	
	/**
	 This function gathers all events that are in current month
	 @param date A String variable type
	 @return ArrayList<Event> that contains events in current month
	 */
	public ArrayList<Event> getMonthEvent(String monthNum, String year){
		l = new ArrayList<>();
		events=model.getAllEvents();
		for(Event e: events) {
			String[] eventDateInfo = e.getDate().split("/");
			if(eventDateInfo[0].equals(monthNum) && eventDateInfo[2].equals(year)) {
				l.add(e);
			}
//			if(e.getDate().subSequence(0, 1).equals(monthNum) || e.getDate().substring(0, 2).equals(monthNum)) {
//				l.add(e);
//			}
		}
		return l;
	}
	/**
	 This function gathers all events that are in between specific dates
	 @param from A String variable type
	 @param to A String variable type
	 @return ArrayList<Event> that contains events in between specific dates
	 */
	public ArrayList<Event> getAgendaEvent(String from, String to) throws ParseException{
		l = new ArrayList<>();
		events = model.getAllEvents();
		
		Date fromDate = new SimpleDateFormat("MM/dd/yyyy").parse(from);
		Date toDate = new SimpleDateFormat("MM/dd/yyyy").parse(to);
			
		for(Event e: events) {
			date =new SimpleDateFormat("MM/dd/yyyy").parse(e.getDate());
			if(fromDate.compareTo(date)<=0 && toDate.compareTo(date)>=0) {
				l.add(e);
			}
		}
			
		return l;
	}
}
