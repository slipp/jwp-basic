package core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {
    private String forwardUrl;

    public ForwardController(String forwardUrl) { // 아니 이렇게 되면 매 요청마다 컨트롤로 객체가 생성되는거 아냐?
        this.forwardUrl = forwardUrl;
        if (forwardUrl == null) {
            throw new NullPointerException("forward is null. Enter URL to move.");
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forwardUrl;
    }
}