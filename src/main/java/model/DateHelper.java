package model;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {
	
	public static String dateToString( Date date){
	
		if (date == null){
			return null;
		}
		
		Calendar formatCalendar = Calendar.getInstance();
		
		formatCalendar.setTime(date);
		
		int year = Integer.valueOf(formatCalendar.get(Calendar.YEAR));
		int month = Integer.valueOf(formatCalendar.get(Calendar.MONTH));
		// los meses comienzan en 0
		month++;		
		int day = Integer.valueOf(formatCalendar.get(Calendar.DAY_OF_MONTH));
		
		String dateAsText=   year+ "-" + month + "-" +day;
								
		return dateAsText;
	}
	
	public static Date StringToDate( String date) throws Exception{
		
		if (date == null){
			return null;
		}
		
		Calendar formatCalendar = Calendar.getInstance();
		formatCalendar.clear();
		formatCalendar.setLenient(false);
		
		String[] dateArray = date.split("-");
		
		if (dateArray.length!= 3){
			throw new Exception("Invalid Format");
		}
		
		try{ 
		
			int year = Integer.valueOf(dateArray[0]);
			int month = Integer.valueOf(dateArray[1]);
			// los meses comienzan en 0
			month--;		
			int day = Integer.valueOf(dateArray[2]);
		
			formatCalendar.set(year, month, day);
		}catch (Exception e){
			throw new Exception("Invalid Format");
		}
		
		return formatCalendar.getTime();
	}
	

}
