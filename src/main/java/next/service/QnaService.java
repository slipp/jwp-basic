package next.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import next.CannotDeleteException;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import next.repository.AnswerRepository;
import next.repository.QuestionRepository;

@Service
public class QnaService {
	private QuestionRepository questionRepository;
	private AnswerRepository answerRepository;

	@Inject
	public QnaService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
		this.questionRepository = questionRepository;
		this.answerRepository = answerRepository;
	}
	
	public List<Question> findQuestions() {
		return questionRepository.findAll();
	}

	public Question findById(Long questionId) {
		return questionRepository.findOne(questionId);
	}

	public List<Answer> findAllByQuestionId(Long questionId) {
		return answerRepository.findByQuestion(findById(questionId));
	}

	public void deleteQuestion(Long questionId, User user) throws CannotDeleteException {
		Question question = findById(questionId);
		if (question == null) {
			throw new CannotDeleteException("존재하지 않는 질문입니다.");
		}

		List<Answer> answers = findAllByQuestionId(questionId);
		if (question.canDelete(user, answers)) {
			questionRepository.delete(question);
		}
	}
}
