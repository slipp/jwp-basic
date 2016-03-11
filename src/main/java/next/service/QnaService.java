package next.service;

import java.util.List;

import next.CannotDeleteException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;

public class QnaService {
	private static QnaService qnaService;

	private QuestionDao questionDao;
	private AnswerDao answerDao;

	
	private QnaService(QuestionDao questionDao, AnswerDao answerDao) {
		this.questionDao = questionDao;
		this.answerDao = answerDao;
	}
	
	public static QnaService getInstance(QuestionDao questionDao, AnswerDao answerDao) {
		if (qnaService == null) {
			qnaService = new QnaService(questionDao, answerDao);
		}
		return qnaService;
	}
	
	public Question findById(long questionId) {
		return questionDao.findById(questionId);
	}
	
	public List<Answer> findAllByQuestionId(long questionId) {
		return answerDao.findAllByQuestionId(questionId);
	}
	
	public void deleteQuestion(long questionId, User user) throws CannotDeleteException {
		Question question = questionDao.findById(questionId);
		if (question == null) {
			throw new CannotDeleteException("존재하지 않는 질문입니다.");
		}
		
		List<Answer> answers = answerDao.findAllByQuestionId(questionId);
		if (question.canDelete(user, answers)) {
			questionDao.delete(questionId);
		}
	}
}
