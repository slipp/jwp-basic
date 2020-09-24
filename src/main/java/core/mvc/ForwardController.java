package core.mvc;

import next.controller.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController extends AbstractController {
    private final String forwardUrl;

    public ForwardController(String forwardUrl) {
        this.forwardUrl = forwardUrl;
        // TODO: null 체크를 생성자 첫 줄에서 하는 건 어떨까? + Objects.requireNonNull()
        if (forwardUrl == null) {
            throw new NullPointerException("forwardUrl is null. 이동할 URL을 입력하세요.");
        }
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) {
        return jspView(forwardUrl);
    }
}
