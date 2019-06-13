package fi.haagahelia.mymcdtools.repo;

import org.springframework.data.repository.CrudRepository;

import fi.haagahelia.mymcdtools.domain.Role;


public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByRole(String role);
	
}