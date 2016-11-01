package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//특별한 로직이 없는 단순 페이지뷰를 위한 컨트롤
public class ForwardController implements Controller {
	private String forwardUrl;
	
	public ForwardController(String url){
		this.forwardUrl = url;
		if(forwardUrl == null){
			throw new NullPointerException("forwardUrl is Null. 이동할 URL을 입력하세요.");
		}
	}
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return forwardUrl;
	}

}
