package fi.haagahelia.mymcdtools;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fi.haagahelia.mymcdtools.domain.Role;
import fi.haagahelia.mymcdtools.domain.User;
import fi.haagahelia.mymcdtools.repo.RoleRepository;
import fi.haagahelia.mymcdtools.repo.UserRepository;


//@ComponentScan({"java.util.Date"})
@SpringBootApplication
public class MymcdtoolsApplication {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	public static void main(String[] args) {
		SpringApplication.run(MymcdtoolsApplication.class, args);
	}
	
	@Bean
    public Date date (){
        return new Date();
    }
	@Profile("dev")
	@Bean
	public CommandLineRunner userAddDev(UserRepository uRepo, RoleRepository rRepo ) {
		return (args) -> {
			
			//create new roles
			log.info("Create init roles");
			
			Role role1 = new Role("USER");
			Role role2 = new Role("ADMIN");
			
			rRepo.save(role1);
			rRepo.save(role2);
		
			log.info("Save init users");

			
			// Create users: admin/admin user/user
			log.debug("Save init user: username: user, email: agocsa18@freemail.hu, password: user, User:enabled, Forum: enabled, role: USER");
			User user1 = new User("user", "agocsa18@freemail.hu", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6","", true, true);
			
			uRepo.save(user1);
			
			log.debug("Save init user: username: admin, email: csaba.agosthazy@myy.haaga-helia.fi, password: admin, User:enabled, Forum: enabled, role: USER, ADMIN");
			User user2 = new User("admin", "csaba.agosthazy@myy.haaga-helia.fi", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "", true, true);
			
			uRepo.save(user2);
			
			role1 = rRepo.findByRole("USER");
			role2 = rRepo.findByRole("ADMIN");
			
			if (role1 != null) {
				user1.getRoles().add(role1);
			} else {
				user1.addRoles("USER");
			}
			uRepo.save(user1);
			
			role1 = rRepo.findByRole("USER");
			if (role1 != null) {
				user2.getRoles().add(role1);
			} else {
				user2.addRoles("USER");
			}
			
			if (role2 != null) {
				user2.getRoles().add(role2);
			} else {
				user2.addRoles("ADMIN");
			}


			uRepo.save(user2);


		};
	}
	
	

}
