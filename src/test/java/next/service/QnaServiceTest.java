package next.service;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTest {
    // @Mock
    // private QuestionDao questionDao;
    // @Mock
    // private AnswerDao answerDao;
    //
    // private QnaService qnaService;
    //
    // @Before
    // public void setup() {
    // qnaService = new QnaService(questionDao, answerDao);
    // }
    //
    // @Test(expected = CannotDeleteException.class)
    // public void deleteQuestion_없는_질문() throws Exception {
    // when(questionDao.findById(1L)).thenReturn(null);
    //
    // qnaService.deleteQuestion(1L, newUser("userId"));
    // }
    //
    // @Test
    // public void deleteQuestion_삭제할수_있음() throws Exception {
    // User user = newUser("userId");
    // Question question = new Question(1L, user.getUserId(), "title",
    // "contents", new Date(), 0) {
    // public boolean canDelete(User user, List<Answer> answers) throws
    // CannotDeleteException {
    // return true;
    // };
    // };
    // when(questionDao.findById(1L)).thenReturn(question);
    //
    // qnaService.deleteQuestion(1L, newUser("userId"));
    // verify(questionDao).delete(question.getQuestionId());
    // }
    //
    // @Test(expected = CannotDeleteException.class)
    // public void deleteQuestion_삭제할수_없음() throws Exception {
    // User user = newUser("userId");
    // Question question = new Question(1L, user.getUserId(), "title",
    // "contents", new Date(), 0) {
    // public boolean canDelete(User user, List<Answer> answers) throws
    // CannotDeleteException {
    // throw new CannotDeleteException("삭제할 수 없음");
    // };
    // };
    // when(questionDao.findById(1L)).thenReturn(question);
    //
    // qnaService.deleteQuestion(1L, newUser("userId"));
    // }
}
