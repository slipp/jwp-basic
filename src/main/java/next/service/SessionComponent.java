package next.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import next.model.User;

@Service
public class SessionComponent {
	public static final String USER_SESSION_KEY = "sessionUser";
	
	public User findUser(WebRequest webRequest) {
		Object loginUser = webRequest.getAttribute(USER_SESSION_KEY, WebRequest.SCOPE_SESSION);
		if (loginUser == null) {
			return User.GUEST_USER;
		}
		return (User)loginUser;
	}
}
