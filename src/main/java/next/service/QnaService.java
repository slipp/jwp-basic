package next.service;

import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QnaService {

    private QuestionDao questionDao = QuestionDao.getQuestionDao();
    private AnswerDao answerDao = AnswerDao.getAnswerDao();

    public void delete(long questionId, HttpSession session) {
        Question question = questionDao.findById(questionId);
        if (question == null) {
            throw new IllegalStateException("존재하지 않는 질문입니다.");
        }

        if (!question.isSameUser(UserSessionUtils.getUserFromSession(session))) {
            throw new IllegalStateException("다른 사용자가 쓴 글을 삭제할 수 없습니다.");
        }

        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        if (!answers.isEmpty()) {
            throw new IllegalStateException("다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다.");
        }

        boolean canDelete = true;
        for (Answer answer : answers) {
            String writer = question.getWriter();
            if (!writer.equals(answer.getWriter())) {
                canDelete = false;
                break;
            }
        }

        if (!canDelete) {
            throw new IllegalStateException("다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다.");
        }

        questionDao.delete(questionId);
    }

    public Map<String, Object> ready(long questionId) {
        Map<String, Object> map = new HashMap<>();
        map.put("question", questionDao.findById(questionId));
        map.put("answers", answerDao.findAllByQuestionId(questionId));
        return map;
    }
}
