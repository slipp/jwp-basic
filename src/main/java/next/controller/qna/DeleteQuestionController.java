package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.service.QnaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class DeleteQuestionController extends AbstractController {

    private QnaService qnaService;

    public DeleteQuestionController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        Map<String, Object> map = qnaService.ready(questionId);
        try {
            qnaService.delete(questionId, req.getSession());
            return jspView("redirect:/");
        } catch(Exception e) {
            return jspView("show.jsp")
                    .addObject("question", map.get("question"))
                    .addObject("answers", map.get("answers"))
                    .addObject("errorMessage", e.getMessage());
        }
    }
}