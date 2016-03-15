package core.di.factory.example;

import core.annotation.Controller;
import core.annotation.Inject;

@Controller
public class QnaController {
	private MyQnaService qnaService;

	@Inject
	public QnaController(MyQnaService qnaService) {
		this.qnaService = qnaService;
	}
	
	public MyQnaService getQnaService() {
		return qnaService;
	}
}
