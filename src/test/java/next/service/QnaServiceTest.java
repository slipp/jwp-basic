package next.service;

import static next.model.AnswerTest.newAnswer;
import static next.model.QuestionTest.newQuestion;
import static next.model.UserTest.newUser;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import next.CannotDeleteException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTest {
    @Mock
    private QuestionDao questionDao;
    @Mock
    private AnswerDao answerDao;
    
	private QnaService qnaService;
    
    @Before
    public void setup() {
        qnaService = new QnaService(questionDao, answerDao);
    }
    
    @Test(expected = CannotDeleteException.class)
    public void deleteQuestion_없는_질문() throws Exception {
    	when(questionDao.findById(1L)).thenReturn(null);
    	
        qnaService.deleteQuestion(1L, newUser("userId"));
    }
    
    @Test(expected = CannotDeleteException.class)
    public void deleteQuestion_다른_사용자() throws Exception {
        Question question = newQuestion(1L, "javajigi");
        when(questionDao.findById(1L)).thenReturn(question);
        
        qnaService.deleteQuestion(1L, newUser("userId"));
    }
    
    @Test
    public void deleteQuestion_같은_사용자_답변없음() throws Exception {
        Question question = newQuestion(1L, "javajigi");
        when(questionDao.findById(1L)).thenReturn(question);
        when(answerDao.findAllByQuestionId(1L)).thenReturn(Lists.newArrayList());
        
        qnaService.deleteQuestion(1L, newUser("javajigi"));
    }
    
    @Test
    public void deleteQuestion_같은_사용자_답변_글쓴이_같음() throws Exception {
        Question question = newQuestion(1L, "javajigi");
        when(questionDao.findById(1L)).thenReturn(question);
        List<Answer> answers = Lists.newArrayList(newAnswer("javajigi"));
        when(answerDao.findAllByQuestionId(1L)).thenReturn(answers);
        
        qnaService.deleteQuestion(1L, newUser("javajigi"));
    }
    
    @Test(expected = CannotDeleteException.class)
    public void deleteQuestion_같은_사용자_답변_글쓴이_다름() throws Exception {
        Question question = newQuestion(1L, "javajigi");
        when(questionDao.findById(1L)).thenReturn(question);
        List<Answer> answers = Lists.newArrayList(newAnswer("javajigi"), newAnswer("sanjigi"));
        when(answerDao.findAllByQuestionId(1L)).thenReturn(answers);
        
        qnaService.deleteQuestion(1L, newUser("javajigi"));
    }
}
