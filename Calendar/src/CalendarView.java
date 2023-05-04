
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.border.Border;


public class CalendarView extends JPanel implements java.io.Serializable {
	private ArrayList<JLabel> labels;
	private Border border;
	private JLabel label;
	private int[] days = {
            31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        };
	private MONTHS[] arrayOfMonths;
	private DAYS[] arrayOfDays;
	private Calendar c;
	private GregorianCalendar temp;
	private String curDay;
	private JButton prevBtn;
	private JButton nextBtn;
	private JButton cBtn;
	private JPanel panel;
	private JPanel calPanel;
	private int emptyDays = 0;
	private Model<Event> m;
	private String curMonth;
	private JButton tBtn;
	private JButton pBtn;
	private JButton nBtn;
	private JButton qBtn;
	private JLabel date;
	private JPanel dayPanel;
	private JPanel qPanel;
	private JPanel tPanel;
	private JPanel mPanel;
	private JPanel p;
	
	/**
	 Constructor for CalendarView
	 @param model A variable of Model<Event> type 
	 */
	public CalendarView(Model<Event> model) {
		setLayout(new BorderLayout());
		m=model;
		calPanel = new JPanel();
		labels = new ArrayList<>();
		border = BorderFactory.createLineBorder(Color.BLUE, 5);
		calPanel.setLayout(new GridLayout(6,7));
		arrayOfMonths = MONTHS.values();
		arrayOfDays = DAYS.values();
		c=new GregorianCalendar();
		temp=new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
		curMonth=(c.get(Calendar.MONTH)+1)+"";
		dayPanel = new JPanel();
		dayPanel.setLayout(new GridLayout(1,1));
		
		
		createCalendar();
		createBtns();
		addToPanels();
	}
	/**
	 This function creates initial calendar
	 @param none
	 */
	public void createCalendar() {
		for(int i = 0 ; i<7;i++) {
			label = new JLabel();
			label.setText(arrayOfDays[i].toString());
			dayPanel.add(label);
		}
	
		emptyDays=0;
		if(temp.get(Calendar.DAY_OF_WEEK)!=1) {
			
			int prevDays = days[c.get(Calendar.MONTH)-1] - temp.get(Calendar.DAY_OF_WEEK)+2;
			
			for (int i = 1; i < temp.get(Calendar.DAY_OF_WEEK); i++) {
				emptyDays+=1;
				label=new JLabel();
				label.setText((prevDays++)+"");
				label.setForeground(Color.gray);
				calPanel.add(label);
			}
		}
		int eDay=1;

		for(int i = 1 ; i <= (42-emptyDays);i++) {
			label = new JLabel();
			
			label.setText(i+"");
			if(i==c.get(Calendar.DATE)) {
				label.setBorder(border);
				curDay=label.getText();
			}
		
			
			if(i<=days[c.get(Calendar.MONTH)]) {
				label.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						JLabel b = (JLabel) e.getSource();
						for(JLabel check : labels) {
							if(check!=b) {
								check.setBorder(null);
							}
						}
						curDay=b.getText();
						
						b.setBorder(border);
						m.notifyView((c.get(Calendar.MONTH)+1)+"/"+curDay+"/"+c.get(Calendar.YEAR),curMonth,curDay);
					}
				});
				m.notifyView((c.get(Calendar.MONTH)+1)+"/"+curDay+"/"+c.get(Calendar.YEAR),curMonth,curDay);
				calPanel.add(label);
				labels.add(label);
			}
			else {
				label=new JLabel((eDay++)+"");
				label.setForeground(Color.gray);
				calPanel.add(label);
			}
			
		}
	}
	/**
	 This function add all components to panels
	 @param none
	 */
	public void addToPanels() {
		date = new JLabel((arrayOfMonths[c.get(Calendar.MONTH)].toString()+"    "+c.get(Calendar.YEAR) +"      "));
		
		qPanel = new JPanel();
		qPanel.setLayout(new BoxLayout(qPanel,BoxLayout.X_AXIS));
		qPanel.add(tBtn);
		qPanel.add(qBtn);
		
		tPanel = new JPanel();
		tPanel.setLayout(new BoxLayout(tPanel,BoxLayout.Y_AXIS));
		tPanel.add(qPanel);
		
		panel.add(cBtn);
		panel.add(prevBtn);
		panel.add(nextBtn);
		
		tPanel.add(panel);
		
		p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(dayPanel,BorderLayout.NORTH);
		p.add(calPanel,BorderLayout.CENTER);
		
		mPanel = new JPanel();
		mPanel.setLayout(new BoxLayout(mPanel, BoxLayout.X_AXIS));
		mPanel.add(date);
		mPanel.add(pBtn);
		mPanel.add(nBtn);
		
		add(tPanel, BorderLayout.NORTH);
		add(mPanel, BorderLayout.CENTER);
		add(p, BorderLayout.SOUTH);
	}
	/**
	 This function creates buttons and adds ActionListeners to each buttons
	 @param none
	 */
	public void createBtns() {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		qBtn = new JButton("QUIT");
		cBtn = new JButton("CREATE");
		prevBtn = new JButton("<");
		nextBtn = new JButton(">");
		tBtn = new JButton("TODAY");
		pBtn = new JButton("<");
		nBtn = new JButton(">");

		cBtn.setBackground(Color.RED);
		cBtn.setForeground(Color.WHITE);
		cBtn.setFont(new Font("anything",Font.BOLD,10));
		
		pBtn.setOpaque(false);
		pBtn.setContentAreaFilled(false);
		pBtn.setBorderPainted(false);
		pBtn.setForeground(Color.GRAY);
		nBtn.setOpaque(false);
		nBtn.setContentAreaFilled(false);
		nBtn.setBorderPainted(false);
		nBtn.setForeground(Color.GRAY);
		
		qBtn.addActionListener(e->{
			try{
				FileWriter fw = new FileWriter(new File("events.bin"));
				PrintWriter pw = new PrintWriter(fw);
				pw.print("");
				for(Event event: m){
					Event.printEvent(pw, event);
				}
				pw.close();
				fw.close();
				JFrame f = new JFrame();
				f.add(new JLabel("EVENT(S) HAS BEEN SAVED!"));
				f.setVisible(true);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.pack();
			} catch(IOException x) {
				x.printStackTrace();
			}
		});
		
		pBtn.addActionListener(e->{
			c.add(Calendar.MONTH, -1);
			curMonth=(c.get(Calendar.MONTH)+1)+"";
			date.setText((arrayOfMonths[c.get(Calendar.MONTH)].toString()+"    "+c.get(Calendar.YEAR) +"      "));
			for(JLabel l : labels) {
				if(l.getText().equals(curDay)) {
					l.setBorder(null);
				}
			}
			setCalendar(1);
			curDay="1";
			for(JLabel l : labels) {
				if(l.getText().equals(curDay)) {
					
					l.setBorder(border);
				}
			}
			m.notifyView((c.get(Calendar.MONTH)+1)+"/"+curDay+"/"+c.get(Calendar.YEAR), curMonth, curDay);
		});
		cBtn.addActionListener(e->{
			JFrame createWindow = new JFrame("Create an Event");
			createWindow.setSize(750, 100);
			JPanel createEvent = new JPanel();
			createEvent.setLayout(new BorderLayout());
			JTextField title = new JTextField("Untitled Event");
			createEvent.add(title, BorderLayout.NORTH);
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			JTextField startDate = new JTextField(m.getCurDate());
			panel.add(startDate);
			JTextField startTime = new JTextField("Starting Hour (0-23)");
			panel.add(startTime);
			JTextField endTime = new JTextField("Ending Hour (0-23)");
			panel.add(endTime);
			JButton saveButton = new JButton("Save");
			saveButton.addActionListener((new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Event event = new Event(title.getText(), startDate.getText(), Integer.parseInt(startTime.getText()),
							Integer.parseInt(endTime.getText()));
					boolean originalEvent = m.addEvent(event);
					if(originalEvent) {
						m.notifyView(m.getCurDate(), m.getCurMonth(), m.getCurDay());
						createWindow.dispose();
					} else {
						JOptionPane optionPane = new JOptionPane("Time Conflict", JOptionPane.ERROR_MESSAGE);
						JDialog dialog = optionPane.createDialog("Time Conflict Error");
						dialog.setVisible(true);
						dialog.setAlwaysOnTop(true);
					}
				}
			}));
			panel.add(saveButton);
			createEvent.add(panel);
			createWindow.add(createEvent);
			createWindow.setVisible(true);
		});
		nBtn.addActionListener(e->{
			c.add(Calendar.MONTH, 1);
			curMonth=(c.get(Calendar.MONTH)+1)+"";
		
			date.setText((arrayOfMonths[c.get(Calendar.MONTH)].toString()+"    "+c.get(Calendar.YEAR) +"      "));
			for(JLabel l : labels) {
				if(l.getText().equals(curDay)) {
					l.setBorder(null);
				}
			}
			setCalendar(0);
			curDay="1";
			for(JLabel l : labels) {
				if(l.getText().equals(curDay)) {
					l.setBorder(border);
				}
			}
			m.notifyView((c.get(Calendar.MONTH)+1)+"/"+curDay+"/"+c.get(Calendar.YEAR), curMonth, curDay);
		});
		tBtn.addActionListener(e->{
			c=new GregorianCalendar();
			
			for(JLabel check:labels) {
				if(check.getText().equals(curDay)) {
					check.setBorder(null);

					setCalendar(2);
					date.setText((arrayOfMonths[c.get(Calendar.MONTH)].toString()+"    "+c.get(Calendar.YEAR)));
					date.repaint();
				}
			}
			for(JLabel check : labels) {
				if(check.getText().equals(curDay)) {
					check.setBorder(border);
					break;
				}
			}
			curMonth=(c.get(Calendar.MONTH)+1)+"";
			m.notifyView((c.get(Calendar.MONTH)+1)+"/"+curDay+"/"+c.get(Calendar.YEAR), curMonth, curDay);
		});
		
		prevBtn.addActionListener(e->{
			for(JLabel check: labels) {
				if(check.getText().equals(curDay)) {
					if(1>(Integer.parseInt(check.getText())-1)) {
						c.add(Calendar.MONTH, -1);
						check.setBorder(null);

						setCalendar(1);
						date.setText((arrayOfMonths[c.get(Calendar.MONTH)].toString()+"    "+c.get(Calendar.YEAR)));
						date.repaint();
						break;
					}
					check.setBorder(null);
					curDay = ((Integer.parseInt(check.getText())-1)+"");
					break;
				}
				
			}
			for(JLabel check : labels) {
				if(check.getText().equals(curDay)) {
					check.setBorder(border);
					break;
				}
			}
			curMonth=(c.get(Calendar.MONTH)+1)+"";
			m.notifyView((c.get(Calendar.MONTH)+1)+"/"+curDay+"/"+c.get(Calendar.YEAR),curMonth,curDay);
		});
		
		nextBtn.addActionListener(e->{
			for(JLabel check: labels) {
				if(check.getText().equals(curDay)) {
					if(days[c.get(Calendar.MONTH)]<(Integer.parseInt(check.getText())+1)) {
						c.add(Calendar.MONTH, 1);
						date.setText((arrayOfMonths[c.get(Calendar.MONTH)].toString()+"    "+c.get(Calendar.YEAR)));
						date.repaint();
						check.setBorder(null);
	
						setCalendar(0);
						break;
					}
					check.setBorder(null);
					curDay = ((Integer.parseInt(check.getText())+1)+"");
					break;
				}
			}
			
			for(JLabel check : labels) {
				if(check.getText().equals(curDay)) {
					check.setBorder(border);
					break;
				}
			}
			
			curMonth=(c.get(Calendar.MONTH)+1)+"";
			m.notifyView((c.get(Calendar.MONTH)+1)+"/"+curDay+"/"+c.get(Calendar.YEAR), curMonth, curDay);
		});
	}
	/**
	 This function repaints calendar view
	 @param mode A variable of Integer type 
	 */
	public void setCalendar(int mode) {
		calPanel.removeAll();
		temp=new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
	
		emptyDays=0;

		if(temp.get(Calendar.DAY_OF_WEEK)!=1) {
			int prevDays = days[c.get(Calendar.MONTH)] - temp.get(Calendar.DAY_OF_WEEK)+2;
	
			for (int i = 1; i < temp.get(Calendar.DAY_OF_WEEK); i++) {
				emptyDays+=1;
				label=new JLabel();
				label.setText((prevDays)+"");
				prevDays++;
				label.setForeground(Color.gray);
				calPanel.add(label);
			}
		}
		
		int eDay=1;


		for(JLabel l : labels) {
			if(Integer.parseInt(l.getText())<=days[c.get(Calendar.MONTH)]) {
				if(Integer.parseInt(l.getText())==c.get(Calendar.DATE)) {
					curDay=l.getText();
				}

				calPanel.add(l);
			}
		}
		
		

		for(int i = 1 ; i <= (42-emptyDays-days[c.get(Calendar.MONTH)]);i++) {
			label=new JLabel((eDay++)+"");
			label.setForeground(Color.gray);
			calPanel.add(label);
		}
		if(mode==0)
			curDay="1";
		else if(mode==1)
			curDay=days[c.get(Calendar.MONTH)]+"";
		else {
			curDay=c.get(Calendar.DATE)+"";
		}
			
		
		calPanel.repaint();
	}
}
