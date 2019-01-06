package com.zoniic645.scoreboard.score;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.Level;

import com.zoniic645.scoreboard.ScoreBoard;

public class TaskSchedular extends TimerTask {
	
	static List<Date> dates = new ArrayList<Date>();
	static Calendar calendar = Calendar.getInstance();
	
	private final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
	
	Timer timer;
	int timeIndex;
	
	private TaskSchedular(int timeIndex) {
		timer = new Timer();
		timer.schedule(this, dates.get(timeIndex));
		updateDate(timeIndex, 1);
		System.out.println(this.timeIndex + " " + dates.get(timeIndex));
	}
	
	private TaskSchedular(Timer timer, int timeIndex) {
		this.timer = timer;
		this.timeIndex = timeIndex + 1;
		if(this.timeIndex == dates.size()) this.timeIndex = 0;
		timer.schedule(this, dates.get(this.timeIndex));
		updateDate(this.timeIndex, 1);
		System.out.println(this.timeIndex + " " + dates.get(this.timeIndex));
	}
	
	public static void init() {
		//Make all the time the same date
		calendar.setTime(new Date());
		Calendar tempCal = Calendar.getInstance();
		for(String time : ScoreConfig.times) {
			try {
				tempCal.setTime(TIME_FORMAT.parse(time));
				calendar.set(Calendar.HOUR_OF_DAY, tempCal.get(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MINUTE, tempCal.get(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				
				dates.add(calendar.getTime());
			} catch (ParseException e) {
				ScoreBoard.logger.log(Level.WARN, "Wrong time format: " + time + ". ignoring this.");
			}
		}
		
		//Get the time that is after now
		Date date = new Date();
		int index = 0;
		for(int i = 0; i < dates.size(); i++) {
			if(dates.get(i).after(date)) {
				index = i;
			}
		}
		
		//If previous time is after the next time, set the date of the next time to the next day
		int plusDate = 0;
		date = dates.get(index);
		for(int i = index+1; i < dates.size(); i++) {
			Date tempDate = dates.get(i);
			if(date.after(tempDate)) {
				plusDate++;
			}
			if(plusDate > 0) {
				updateDate(i, plusDate);
			}
			date = tempDate;
		}
		for(int i = 0; i < index; i++) {
			Date tempDate = dates.get(i);
			if(date.after(tempDate)) {
				plusDate++;
			}
			if(plusDate > 0) {
				updateDate(i, plusDate);
			}
			date = tempDate;
		}
		
		for(Date d : dates) {
			System.out.println(d);
		}
		
		new TaskSchedular(index);
	}
	
	private static void updateDate(int index, int amount) {
		calendar.setTime(dates.get(index));
		calendar.add(Calendar.DATE, amount);
		dates.set(index, calendar.getTime());
	}

	@Override
	public void run() {
		new TaskSchedular(timer, timeIndex);
		ScoreUploader.update();
	}
	
}