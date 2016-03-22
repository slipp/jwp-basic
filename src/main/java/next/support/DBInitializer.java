package next.support;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import next.model.User;
import next.repository.UserRepository;

@Component
public class DBInitializer {
	private static final Logger log = LoggerFactory.getLogger(DBInitializer.class);
	
	@Inject
	private UserRepository userRepository;
	
	@PostConstruct
	public void initialize() {
		User user = new User("admin", "test", "name", "javajigi@sample.com");
		userRepository.save(user);
		log.info("DB Initialized Success!!");
	}
}
