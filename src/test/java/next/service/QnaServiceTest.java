package next.service;

import static next.model.QuestionTest.newQuestion;
import static next.model.UserTest.newUser;
import next.CannotDeleteException;
import next.dao.MockAnswerDao;
import next.dao.MockQuestionDao;
import next.model.Question;

import org.junit.Before;
import org.junit.Test;

public class QnaServiceTest {
	private QnaService qnaService;
	
	@Before
	public void setup() {
		MockQuestionDao questionDao = new MockQuestionDao();
		Question question = newQuestion(1L, "javajigi");
		questionDao.insert(question);
		MockAnswerDao answerDao = new MockAnswerDao();
		qnaService = QnaService.getInstance(questionDao, answerDao);
	}
	
	@Test(expected = CannotDeleteException.class)
	public void deleteQuestion_없는_질문() throws Exception {
		qnaService.deleteQuestion(1L, newUser("userId"));
	}
	
	@Test(expected = CannotDeleteException.class)
	public void deleteQuestion_다른_사용자() throws Exception {
		qnaService.deleteQuestion(1L, newUser("userId"));
	}
	
	@Test
	public void deleteQuestion_같은_사용자_답변없음() throws Exception {
		qnaService.deleteQuestion(1L, newUser("javajigi"));
	}	
}
