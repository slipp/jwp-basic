package di.examples;

import core.annotation.Controller;
import core.annotation.Inject;

@Controller
public class MyUserController {
	@Inject
	private MyQnaService qnaService;
	
	private MyUserService userService;
	
	@Inject
	public void setUserService(MyUserService userService) {
		this.userService = userService;
	}
	
	public MyUserService getUserService() {
		return userService;
	}
}
