package next.controller.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Answer;

public class AddAnswerController implements Controller {
	private final static Logger log = LoggerFactory.getLogger(AddAnswerController.class);

	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Long questionId = Long.parseLong(req.getParameter("questionId"));
		log.debug("questionId : {}",questionId);
		
		Answer answer = new Answer(req.getParameter("writer"),req.getParameter("contents"),questionId);
		log.debug("Answer : {}", answer);
		AnswerDao answerDao = new AnswerDao();
		Answer savedAnswer = answerDao.insert(answer);
		log.debug("SavedAnswer : {}", savedAnswer);
		
		ObjectMapper mapper = new ObjectMapper();
		
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(mapper.writeValueAsString(savedAnswer));	
		
		return null;
	}

}
