package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Jbee on 2017. 4. 19..
 */
public class UpdateQuestionController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UpdateQuestionController.class);

    private QuestionDao questionDao = QuestionDao.getQuestionDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        logger.info("countOfComment: {}", req.getParameter("countOfComment"));

        Question question = new Question(
                Long.parseLong(req.getParameter("questionId")),
                req.getParameter("writer"),
                req.getParameter("title"),
                req.getParameter("contents"),
                Integer.parseInt(req.getParameter("countOfComment")));
        questionDao.update(question);
        return jspView("redirect:/");
    }
}
