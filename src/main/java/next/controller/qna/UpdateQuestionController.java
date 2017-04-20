package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Question;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateQuestionController extends AbstractController {

    private QuestionDao questionDao = QuestionDao.getQuestionDao();
    private UserDao userDao = UserDao.getUserDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        User user = userDao.findByUserName(req.getParameter("writer"));
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 글을 수정할 수 없습니다.");
        }
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
