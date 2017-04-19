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
public class AddQuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    private QuestionDao questionDao = new QuestionDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        Question question = new Question(req.getParameter("writer"), req.getParameter("title"), req.getParameter("contents"));
        log.debug("question : {}", question);

        questionDao.insert(question);
        return jspView("redirect:/");
    }
}
