package next.dao;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Answer;

public class AnswerDaoTest {
	private static final Logger log = LoggerFactory.getLogger(AnswerDaoTest.class);
	
	private AnswerDao answerDao;
	
    @Test
    public void addAnswer() throws Exception {
        long questionId = 1L;
        Answer expected = new Answer("javajigi", "answer contents", questionId);
        Answer answer = answerDao.insert(expected);
        log.debug("Answer : {}", answer);
    }
}
