package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.Question;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Jbee on 2017. 4. 19..
 */
public class UpdateQuestionFormController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UpdateQuestionFormController.class);

    private UserDao userDao = UserDao.getUserDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        User user = userDao.findByUserName(req.getParameter("writer"));//글쓴이
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 글을 수정할 수 없습니다.");
        }
        logger.info("countOfComment: {}", req.getParameter("countOfComment"));
        Question question = new Question(
                Long.parseLong(req.getParameter("questionId")),
                req.getParameter("writer"),
                req.getParameter("title"),
                req.getParameter("contents"),
                Integer.parseInt(req.getParameter("countOfComment")));
        logger.info("question: {}", question);
        ModelAndView mav = jspView("/qna/update.jsp");
        mav.addObject("question", question);
        return mav;
    }
}
