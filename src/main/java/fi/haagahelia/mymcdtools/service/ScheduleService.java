package fi.haagahelia.mymcdtools.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.haagahelia.mymcdtools.domain.Schedule;
import fi.haagahelia.mymcdtools.domain.ScheduleToSave;
import fi.haagahelia.mymcdtools.repo.ScheduleRepository;

@Service
public class ScheduleService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ScheduleRepository sRepo;
	@Autowired
	private Schedule schedule;
	@Autowired
	private ScheduleToSave scheduleToSave;

	@Autowired
	public ScheduleService(ScheduleRepository sRepo, Schedule schedule, ScheduleToSave scheduleToSave) {
		this.sRepo = sRepo;
		this.schedule = schedule;
		this.scheduleToSave = scheduleToSave;
	}
	//find schedule by date for listing
	@SuppressWarnings("unchecked")
	public List<Schedule> findByStartDate (Date startDate) {
		
		log.debug("Listing schedules");
		List<Schedule> scheduleList = new ArrayList<Schedule>();
		
		
		scheduleList = (List<Schedule>) sRepo.findByStartDate(startDate);
		
		return scheduleList;
		
	}
	//save schedule to database by transforming data from website
	public boolean saveScheduleFromScheduleToSave (ArrayList<ScheduleToSave> toSaveList) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar c = Calendar.getInstance();
		
		String myDate ="";
		Schedule schedule = new Schedule();
		Date startDateTime = new Date();
		Date endDateTime = new Date();
		
		for (ScheduleToSave toSave : toSaveList) {
			
			if(toSave.getStartTime() != null && toSave.getEndTime() != null) {
				log.debug("Saving schedules from list");
				
				schedule.setStartDate(toSave.getStartDate());
			
				myDate = toSave.getStartDate()+ " "+ toSave.getStartTime();
				log.debug("Date string to parse: "+myDate);
				
				try {
					startDateTime = sdf.parse(myDate);
					schedule.setStartDateTime(startDateTime);
				} catch (ParseException e) {
					log.debug("Date format: "+myDate+" cannot be converted to date_time: "+e);
					return false;
				}
				
				//Check if the start hour is larger than end hour (after midnight) set end date +1
				if(toSave.getStartTime().compareTo(toSave.getEndTime()) > 0) {
					c.setTime(toSave.getStartDate());
					c.add(Calendar.DATE, 1);
					myDate = c.getTime()+ " "+toSave.getEndTime();
					try {
						endDateTime = sdf.parse(myDate);
						schedule.setEndDateTime(endDateTime);
					} catch (ParseException e) {
						log.debug("Date format: "+myDate+" cannot be converted to date_time: "+e);
						return false;
					}
					
					
				}else {
					myDate = toSave.getStartDate()+ " "+toSave.getEndTime();
					try {
						endDateTime = sdf.parse(myDate);
						schedule.setEndDateTime(endDateTime);
					} catch (ParseException e) {
						log.debug("Date format: "+myDate+" cannot be converted to date_time: "+e);
						return false;
					}
				}
				
				schedule.setUserId(toSave.getUserId());
				
				sRepo.save(schedule);
				
			}else log.debug("There is nothing to save : empty hours");
			
			
			
		}
		
		
		
		return true;
	}

}
