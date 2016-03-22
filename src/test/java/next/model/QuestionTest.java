package next.model;

public class QuestionTest {
//	public static Question newQuestion(String writer) {
//		return new Question(1L, writer, "title", "contents", new Date(), 0);
//	}
//	
//	public static Question newQuestion(long questionId, String writer) {
//		return new Question(questionId, writer, "title", "contents", new Date(), 0);
//	}
//	
//	@Test(expected = CannotDeleteException.class)
//	public void canDelete_글쓴이_다르다() throws Exception {
//		User user = newUser("javajigi");
//		Question question = newQuestion("sanjigi");
//		question.canDelete(user, new ArrayList<Answer>());
//	}
//	
//	@Test
//	public void canDelete_글쓴이_같음_답변_없음() throws Exception {
//		User user = newUser("javajigi");
//		Question question = newQuestion("javajigi");
//		assertTrue(question.canDelete(user, new ArrayList<Answer>()));
//	}
//	
//	@Test
//	public void canDelete_같은_사용자_답변() throws Exception {
//		String userId = "javajigi";
//		User user = newUser(userId);
//		Question question = newQuestion(userId);
//		List<Answer> answers = Arrays.asList(newAnswer(userId));
//		assertTrue(question.canDelete(user, answers));
//	}
//	
//	@Test(expected = CannotDeleteException.class)
//	public void canDelete_다른_사용자_답변() throws Exception {
//		String userId = "javajigi";
//		List<Answer> answers = Arrays.asList(newAnswer(userId), newAnswer("sanjigi"));
//		newQuestion(userId).canDelete(newUser(userId), answers);
//	}
}
