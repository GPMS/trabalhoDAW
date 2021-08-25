package net.ufjnet.calendar;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import net.ufjnet.calendar.models.Category;
import net.ufjnet.calendar.models.Event;
import net.ufjnet.calendar.models.User;
import net.ufjnet.calendar.repositories.CategoryDAO;
import net.ufjnet.calendar.repositories.EventDAO;
import net.ufjnet.calendar.repositories.UserDAO;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class BckendCalApplication implements CommandLineRunner {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private EventDAO eventDAO;
	
	public static void main(String[] args) {
		SpringApplication.run(BckendCalApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		User u1 = new User(1, "Gabriel", "gabriel@cal");
		User u2 = new User(2, "Gabrieli", "gabrieli@cal");
		User u3 = new User(3, "Roberto", "roberto@cal");
		userDAO.saveAll(Arrays.asList(u1, u2, u3));
		
		Category c1 = new Category(1, "Study", "#ff0000", u1);
		Category c2 = new Category(2, "Workout", "#00ff00", u1);
		Category c3 = new Category(3, "Freetime", "#0000ff", u2);
		categoryDAO.saveAll(Arrays.asList(c1, c2, c3));
		
		Event e1 = new Event(1, "Test", LocalDateTime.of(2020, Month.DECEMBER, 1,0, 0), LocalDateTime.of(2020, Month.DECEMBER, 1, 23, 59), u1, c1);
		Event e2 = new Event(2, "Test2", LocalDateTime.of(2020, Month.DECEMBER, 1,1, 0), LocalDateTime.of(2020, Month.DECEMBER, 1, 23, 59), u1);
		eventDAO.saveAll(Arrays.asList(e1, e2));
	}
}
