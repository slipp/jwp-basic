package next.model;

import static next.model.AnswerTest.newAnswer;
import static next.model.UserTest.newUser;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class QuestionTest {
	private static Question newQuestion(String writer) {
		return new Question(writer, "title", "contents");
	}
	
	@Test(expected = IllegalStateException.class)
	public void canDelete_글쓴이_다르다() throws Exception {
		User user = newUser("javajigi");
		Question question = newQuestion("sanjigi");
		question.canDelete(user, new ArrayList<Answer>());
	}
	
	@Test
	public void canDelete_글쓴이_같음_답변_없음() throws Exception {
		User user = newUser("javajigi");
		Question question = newQuestion("javajigi");
		assertTrue(question.canDelete(user, new ArrayList<Answer>()));
	}
	
	@Test
	public void canDelete_같은_사용자_답변() throws Exception {
		String userId = "javajigi";
		User user = newUser(userId);
		Question question = newQuestion(userId);
		List<Answer> answers = Arrays.asList(newAnswer(userId));
		assertTrue(question.canDelete(user, answers));
	}
	
	@Test(expected = IllegalStateException.class)
	public void canDelete_다른_사용자_답변() throws Exception {
		String userId = "javajigi";
		List<Answer> answers = Arrays.asList(newAnswer(userId), newAnswer("sanjigi"));
		newQuestion(userId).canDelete(newUser(userId), answers);
	}
}
