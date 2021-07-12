package net.ufjnet.calendar;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import net.ufjnet.calendar.models.User;
import net.ufjnet.calendar.repositories.UserDAO;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class BckendCalApplication implements CommandLineRunner {
	
	@Autowired
	private UserDAO userDAO;
	
	public static void main(String[] args) {
		SpringApplication.run(BckendCalApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		User u1 = new User(1, "Gabriel", "gabriel@cal");
		User u2 = new User(2, "Gabrieli", "gabrieli@cal");
		User u3 = new User(3, "Roberto", "roberto@cal");
		
		userDAO.saveAll(Arrays.asList(u1, u2, u3));
	}
}
