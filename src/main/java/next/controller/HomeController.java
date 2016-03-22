package next.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import next.dao.QuestionDao;

@Controller
public class HomeController {
	@Inject
	private QuestionDao questionDao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String execute(Model model) throws Exception {
		model.addAttribute("questions", questionDao.findAll());
		return "index";
	}
}
