//This code was thrown together in about 35 minutes don't judge it too hard
//Added in 2 variations to the time finder because I noticed Joseph either says "we'll be back in 30 minutes" or "we'll start again at 12:00"

import java.time.*;
import java.util.Scanner;
import java.io.FileWriter;  
import java.io.IOException; 

public class BreakTime {
	public static void main(String[] args) {
		System.out.println("Press 1 to enter the amount of minutes for the break\nPress 2 to enter the end time of the break");
		
		Scanner s = new Scanner(System.in);
		int sw = s.nextInt();
		if(sw == 1) {
		runMinutes();
	}else if(sw == 2) {
		runTillHour();
	}else {
		System.out.println("Command not recognized run the program again to continue");
	}
		s.close();
	}
	
//Method to set the end time and autoformat from that **Work in Progress**, only works compared to centraltime
public static void runTillHour() {
	String PTmin, CTmin, MTmin, ETmin, ETampm, MTampm, CTampm, PTampm;
	int PThour ,CThour, MThour, EThour, inhour, inmin;
	

	System.out.println("Enter the time you want break to end at with the format XX:XX in military time:");
	Scanner s = new Scanner(System.in);
	String str = s.next();
	String[] tok = str.split(":");
	
	inhour = Integer.valueOf(tok[0]);
	inmin = Integer.valueOf(tok[1]);

	ZoneId ET = ZoneId.of("US/Eastern");
	ZoneId CT = ZoneId.of("US/Central");
	ZoneId MT = ZoneId.of("US/Mountain");
	ZoneId PT = ZoneId.of("US/Pacific");

	
    // create an ZonedDateTime object using now(zoneId)
	ZonedDateTime ETnow = ZonedDateTime.now(ET);
    ZonedDateTime CTnow = ZonedDateTime.now(CT);
    ZonedDateTime MTnow = ZonedDateTime.now(MT);
    ZonedDateTime PTnow = ZonedDateTime.now(PT);

	inhour = inhour - CTnow.getHour();
	inmin = inmin - CTnow.getMinute();
	int breaktime = 60*inhour + inmin;
	
	System.out.println(breaktime);
    
    //Adjust time to account for break time
	if(breaktime < 60) {
		 PTnow = PTnow.plusMinutes(breaktime);
		 ETnow = ETnow.plusMinutes(breaktime);
		 CTnow = CTnow.plusMinutes(breaktime);
		 MTnow = MTnow.plusMinutes(breaktime);
	}else if(breaktime == 60) {
		PTnow = PTnow.plusHours(1);
		ETnow = ETnow.plusHours(1);
		CTnow = CTnow.plusHours(1);
		MTnow = MTnow.plusHours(1);
	}else if(breaktime > 60) {
		int hours = breaktime / 60;
		int minutes = breaktime % 60;
		PTnow = PTnow.plusMinutes(minutes);
		PTnow = PTnow.plusHours(hours);
		ETnow = ETnow.plusMinutes(minutes);
		ETnow = ETnow.plusHours(hours);
		CTnow = CTnow.plusMinutes(minutes);
		CTnow = CTnow.plusHours(hours);
		MTnow = MTnow.plusMinutes(minutes);
		MTnow = MTnow.plusHours(hours);
	}
	
	// Formatting AM/PM info
	if(ETnow.getHour() >= 12) {
		ETampm = "PM";
	}else {
		ETampm = "AM";
	}
	if(CTnow.getHour() >= 12) {
		CTampm = "PM";
	}else {
		CTampm = "AM";
	}
	if(PTnow.getHour() >= 12) {
		PTampm = "PM";
	}else {
		PTampm = "AM";
	}
	if(MTnow.getHour() >= 12) {
		MTampm = "PM";
	}else {
		MTampm = "AM";
	}
	
	//formatting minutes from mil time to standard
	if(ETnow.getHour() >= 13) {
		 EThour = ETnow.getHour() % 12;
	}else {
		 EThour = ETnow.getHour();
	}
	if(CTnow.getHour() >= 13) {
		 CThour = CTnow.getHour() % 12;
	}else {
		 CThour = CTnow.getHour();
	}
	if(MTnow.getHour() >= 13) {
		 MThour = MTnow.getHour() % 12;
	}else {
		 MThour = MTnow.getHour();
	}
	if(PTnow.getHour() >= 13) {
		 PThour = PTnow.getHour() % 12;
	}else {
		 PThour = PTnow.getHour();
	}
	
	//formatting minutes due to :09 => :9
	if(ETnow.getMinute() < 10) {
		 ETmin = ("0" + Integer.toString((ETnow.getMinute() % 60)));
	}else {
		 ETmin = Integer.toString((ETnow.getMinute()));
	}
	
	if(CTnow.getMinute() < 10) {
		 CTmin = ("0" + Integer.toString((CTnow.getMinute() % 60)));
	}else {
		 CTmin = Integer.toString((CTnow.getMinute()));
	}
	
	if(MTnow.getMinute() < 10) {
		 MTmin = ("0" + Integer.toString((MTnow.getMinute() % 60)));
	}else {
		 MTmin = Integer.toString((MTnow.getMinute()));
	}
	
	if(PTnow.getMinute() < 10) {
		 PTmin = ("0" + Integer.toString((PTnow.getMinute() % 60)));
	}else {
		 PTmin = Integer.toString((PTnow.getMinute()));
	}
	
	//Console print incase you want that
	System.out.println("ET - break will be over at " + EThour + ":" + ETmin + " " + ETampm);
	System.out.println("CT - break will be over at " + CThour + ":" + CTmin + " " + CTampm);
	System.out.println("MT - break will be over at " + MThour + ":" + MTmin + " " + MTampm);
	System.out.println("PT - break will be over at " + PThour + ":" + PTmin + " " + PTampm);
	
	
	//Write console print to file "breaktime.txt"
	 try {
	      FileWriter myWriter = new FileWriter("breaktime.txt");
	      myWriter.write("ET - break will be over at " + EThour + ":" + ETmin + " " + ETampm + "\n");
	      myWriter.write("CT - break will be over at " + CThour + ":" + CTmin + " " + CTampm + "\n");
	      myWriter.write("MT - break will be over at " + MThour + ":" + MTmin + " " + MTampm + "\n");
	      myWriter.write("PT - break will be over at " + PThour + ":" + PTmin + " " + PTampm + "\n");
	      myWriter.close();
	      System.out.println("Successfully wrote to the file: breaktime.txt");
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	
	s.close();
}

	
//Method to find the hour that break is over by setting the length of break
public static void runMinutes() {
	String PTmin, CTmin, MTmin, ETmin, ETampm, MTampm, CTampm, PTampm;
	int PThour ,CThour, MThour, EThour;
	

	System.out.println("Enter the amount of time in minutes for the break with the format XX:");
	Scanner s = new Scanner(System.in);
	int breaktime = s.nextInt();
	ZoneId ET = ZoneId.of("US/Eastern");
	ZoneId CT = ZoneId.of("US/Central");
	ZoneId MT = ZoneId.of("US/Mountain");
	ZoneId PT = ZoneId.of("US/Pacific");
	
    // create an ZonedDateTime object using now(zoneId)
	ZonedDateTime ETnow = ZonedDateTime.now(ET);
    ZonedDateTime CTnow = ZonedDateTime.now(CT);
    ZonedDateTime MTnow = ZonedDateTime.now(MT);
    ZonedDateTime PTnow = ZonedDateTime.now(PT);
  
    //Adjust time to account for break time
	if(breaktime < 60) {
		 PTnow = PTnow.plusMinutes(breaktime);
		 ETnow = ETnow.plusMinutes(breaktime);
		 CTnow = CTnow.plusMinutes(breaktime);
		 MTnow = MTnow.plusMinutes(breaktime);
	}else if(breaktime == 60) {
		PTnow = PTnow.plusHours(1);
		ETnow = ETnow.plusHours(1);
		CTnow = CTnow.plusHours(1);
		MTnow = MTnow.plusHours(1);
	}else if(breaktime > 60) {
		int hours = breaktime / 60;
		int minutes = breaktime % 60;
		PTnow = PTnow.plusMinutes(minutes);
		PTnow = PTnow.plusHours(hours);
		ETnow = ETnow.plusMinutes(minutes);
		ETnow = ETnow.plusHours(hours);
		CTnow = CTnow.plusMinutes(minutes);
		CTnow = CTnow.plusHours(hours);
		MTnow = MTnow.plusMinutes(minutes);
		MTnow = MTnow.plusHours(hours);
	}
	
	// Formatting AM/PM info
	if(ETnow.getHour() >= 12) {
		ETampm = "PM";
	}else {
		ETampm = "AM";
	}
	if(CTnow.getHour() >= 12) {
		CTampm = "PM";
	}else {
		CTampm = "AM";
	}
	if(PTnow.getHour() >= 12) {
		PTampm = "PM";
	}else {
		PTampm = "AM";
	}
	if(MTnow.getHour() >= 12) {
		MTampm = "PM";
	}else {
		MTampm = "AM";
	}
	
	//formatting minutes from mil time to standard
	if(ETnow.getHour() >= 13) {
		 EThour = ETnow.getHour() % 12;
	}else {
		 EThour = ETnow.getHour();
	}
	if(CTnow.getHour() >= 13) {
		 CThour = CTnow.getHour() % 12;
	}else {
		 CThour = CTnow.getHour();
	}
	if(MTnow.getHour() >= 13) {
		 MThour = MTnow.getHour() % 12;
	}else {
		 MThour = MTnow.getHour();
	}
	if(PTnow.getHour() >= 13) {
		 PThour = PTnow.getHour() % 12;
	}else {
		 PThour = PTnow.getHour();
	}
	
	//formatting minutes due to :09 => :9
	if(ETnow.getMinute() < 10) {
		 ETmin = ("0" + Integer.toString((ETnow.getMinute() % 60)));
	}else {
		 ETmin = Integer.toString((ETnow.getMinute()));
	}
	
	if(CTnow.getMinute() < 10) {
		 CTmin = ("0" + Integer.toString((CTnow.getMinute() % 60)));
	}else {
		 CTmin = Integer.toString((CTnow.getMinute()));
	}
	
	if(MTnow.getMinute() < 10) {
		 MTmin = ("0" + Integer.toString((MTnow.getMinute() % 60)));
	}else {
		 MTmin = Integer.toString((MTnow.getMinute()));
	}
	
	if(PTnow.getMinute() < 10) {
		 PTmin = ("0" + Integer.toString((PTnow.getMinute() % 60)));
	}else {
		 PTmin = Integer.toString((PTnow.getMinute()));
	}
	
	//Console print incase you want that
	System.out.println("ET - break will be over at " + EThour + ":" + ETmin + " " + ETampm);
	System.out.println("CT - break will be over at " + CThour + ":" + CTmin + " " + CTampm);
	System.out.println("MT - break will be over at " + MThour + ":" + MTmin + " " + MTampm);
	System.out.println("PT - break will be over at " + PThour + ":" + PTmin + " " + PTampm);
	
	
	//Write console print to file "breaktime.txt"
	 try {
	      FileWriter myWriter = new FileWriter("breaktime.txt");
	      myWriter.write("ET - break will be over at " + EThour + ":" + ETmin + " " + ETampm + "\n");
	      myWriter.write("CT - break will be over at " + CThour + ":" + CTmin + " " + CTampm + "\n");
	      myWriter.write("MT - break will be over at " + MThour + ":" + MTmin + " " + MTampm + "\n");
	      myWriter.write("PT - break will be over at " + PThour + ":" + PTmin + " " + PTampm + "\n");
	      myWriter.close();
	      System.out.println("Successfully wrote to the file: breaktime.txt");
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	
	s.close();
}

}
