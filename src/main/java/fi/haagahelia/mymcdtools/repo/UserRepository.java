package fi.haagahelia.mymcdtools.repo;
import org.springframework.data.repository.CrudRepository;

import fi.haagahelia.mymcdtools.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
	
	User findByEmail(String email);

	User findByActivation(String code);
	
}