package fi.haagahelia.mymcdtools.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table( name = "schedules" )
public class Schedule{
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long scheduleId;
	@Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDateTime;
    @ManyToOne
    @JoinColumn(name ="userId")
    private User userId;
    
    
    
    public Schedule() {}

	public Schedule(Date startDate, Date startDateTime, Date endDateTime, User userId) {
		super();
		this.startDate = startDate;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.userId = userId;
	}

	public long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}

	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

}
