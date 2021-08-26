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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.ufjnet.calendar.models.Category;
import net.ufjnet.calendar.models.Event;
import net.ufjnet.calendar.models.Permission;
import net.ufjnet.calendar.models.User;
import net.ufjnet.calendar.repositories.CategoryDAO;
import net.ufjnet.calendar.repositories.EventDAO;
import net.ufjnet.calendar.repositories.PermissionDAO;
import net.ufjnet.calendar.repositories.UserDAO;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class BckendCalApplication implements CommandLineRunner {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PermissionDAO permissionDAO;
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private EventDAO eventDAO;
	
	private BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
	
	public static void main(String[] args) {
		SpringApplication.run(BckendCalApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		Permission normal = new Permission();
		normal.setDescription("NORMAL");
		
		User u1 = new User();
		u1.setName("Gabriel");
		u1.setEmail("gab.portela@hotmail.com");
		u1.setPassword(bCrypt.encode("123"));
		u1.setAccountNonExpired(true);
		u1.setAccountNonLocked(true);
		u1.setCredentialsNonExpired(true);
		u1.setEnabled(true);
		u1.getPermissions().addAll(Arrays.asList(normal));
		
		User u2 = new User();
		u2.setName("Gabrieli");
		u2.setEmail("gabrieli@cal");
		u2.setPassword(bCrypt.encode("456"));
		u2.setAccountNonExpired(true);
		u2.setAccountNonLocked(true);
		u2.setCredentialsNonExpired(true);
		u2.setEnabled(true);
		u2.getPermissions().addAll(Arrays.asList(normal));
		
		Category c1 = new Category(1, "Study", "#ff0000", u1);
		Category c2 = new Category(2, "Workout", "#00ff00", u1);
		Category c3 = new Category(3, "Freetime", "#0000ff", u2);
		
		Event e1 = new Event(1, "Test", LocalDateTime.of(2020, Month.DECEMBER, 1,0, 0), LocalDateTime.of(2020, Month.DECEMBER, 1, 23, 59), u1, c1);
		Event e2 = new Event(2, "Test2", LocalDateTime.of(2020, Month.DECEMBER, 1,1, 0), LocalDateTime.of(2020, Month.DECEMBER, 1, 23, 59), u1);
		Event e3 = new Event(3, "Test3", LocalDateTime.of(2020, Month.DECEMBER, 2,1, 0), LocalDateTime.of(2020, Month.DECEMBER, 2, 23, 59), u2);
		
		permissionDAO.saveAll(Arrays.asList(normal));
		userDAO.saveAll(Arrays.asList(u1, u2));
		categoryDAO.saveAll(Arrays.asList(c1, c2, c3));
		eventDAO.saveAll(Arrays.asList(e1, e2, e3));
	}
}
