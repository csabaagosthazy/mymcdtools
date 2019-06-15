package fi.haagahelia.mymcdtools.repo;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import fi.haagahelia.mymcdtools.domain.Schedule;
import fi.haagahelia.mymcdtools.domain.User;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
	
	Schedule findByUserId (User userId);
	
	Schedule findByStartDate (Date startDate);

}
