package next.controller.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.Result;

public class DeleteAnswerController implements Controller {
	private final static Logger log = LoggerFactory.getLogger(DeleteAnswerController.class);
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Long answerId = Long.parseLong(req.getParameter("answerId"));
		long questionId = Long.parseLong(req.getParameter("questionId"));
		
		QuestionDao questionDao = new QuestionDao();
		AnswerDao answerDao = new AnswerDao();
		Answer answer = answerDao.findById(answerId);
		

		log.debug("answerId : {}", answerId);
		
		
		
				
		if(answer == null)
			return null;
		
		log.debug("answerId : {}", answer.getAnswerId());
		
		answerDao.delete(answerId);
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(mapper.writeValueAsString(Result.Ok()));	
		return null;
	}

}
