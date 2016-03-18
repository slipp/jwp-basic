package next.dao;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import core.jdbc.JdbcTemplate;
import next.model.Answer;

public class AnswerDaoTest {
	private static final Logger log = LoggerFactory.getLogger(AnswerDaoTest.class);
	
	private JdbcTemplate jdbcTemplate;
	
    @Before
    public void setup() {
    	jdbcTemplate = new JdbcTemplate();
    	
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }
    
    @Test
    public void addAnswer() throws Exception {
        long questionId = 1L;
        Answer expected = new Answer("javajigi", "answer contents", questionId);
    	AnswerDao dut = new JdbcAnswerDao(jdbcTemplate);
        Answer answer = dut.insert(expected);
        log.debug("Answer : {}", answer);
    }
}
