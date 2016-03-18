package next.dao;

import next.config.MyConfiguration;
import next.model.Answer;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.di.factory.ApplicationContext;
import core.jdbc.ConnectionManager;

public class AnswerDaoTest {
	private static final Logger log = LoggerFactory.getLogger(AnswerDaoTest.class);
	
	private AnswerDao answerDao;
	
    @Before
    public void setup() {
    	ApplicationContext ac = new ApplicationContext(MyConfiguration.class);
    	answerDao = ac.getBean(AnswerDao.class);
    	
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }
    
    @Test
    public void addAnswer() throws Exception {
        long questionId = 1L;
        Answer expected = new Answer("javajigi", "answer contents", questionId);
        Answer answer = answerDao.insert(expected);
        log.debug("Answer : {}", answer);
    }
}
