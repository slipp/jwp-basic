package next.controller.qna;

import core.mvc.ModelAndView;
import next.controller.AbstractController;
import next.dao.AnswerDao;
import next.model.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddAnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) {
        // TODO: Answer에 Builder 패턴 적용, 아래에서 사용
        Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId")));
        log.debug("answer : {}", answer);

        AnswerDao answerDao = new AnswerDao();
        Answer savedAnswer = answerDao.insert(answer);
        return jsonView()
                .addObject("answer", savedAnswer);
    }
}
