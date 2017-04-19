package next.controller.api;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Jbee on 2017. 4. 19..
 */
public class ApiQuestionController extends AbstractController {

    private QuestionDao questionDao = new QuestionDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        List<Question> questionList = questionDao.findAll();
        return jsonView().addObject("questionList", questionList);
    }
}
