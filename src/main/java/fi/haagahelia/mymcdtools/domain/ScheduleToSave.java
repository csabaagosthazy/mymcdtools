package fi.haagahelia.mymcdtools.domain;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Component;

@Component
public class ScheduleToSave{
	
	
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Temporal(TemporalType.TIME)
    private Date endTime;
    
    private User userId;

	public ScheduleToSave(Date startDate, Date startTime, Date endTime, User userId) {
		super();
		this.startDate = startDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.userId = userId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}
	
	

}
