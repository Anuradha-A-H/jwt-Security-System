package com.example.security_last_try;

import com.example.security_last_try.entity.Role;
import com.example.security_last_try.entity.User;
import com.example.security_last_try.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SecurityLastTryApplication implements CommandLineRunner {

	private final UserRepository userRepository;

	public SecurityLastTryApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(SecurityLastTryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User adminAccount = userRepository.findByRole(Role.ADMIN);
		if(null == adminAccount)
		{
			User user = new User();

			user.setEmail("admin@gmail.com");
			user.setFirstName("Admin");
			user.setSecondName(" A H");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
	}
}
