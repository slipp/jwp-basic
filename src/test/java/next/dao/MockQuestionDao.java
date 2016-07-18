package next.dao;

import java.util.List;
import java.util.Map;

import next.model.Question;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MockQuestionDao implements QuestionDao {
    private Map<Long, Question> questions = Maps.newHashMap();

    @Override
    public Question insert(Question question) {
        return questions.put(question.getQuestionId(), question);
    }

    @Override
    public List<Question> findAll() {
        return Lists.newArrayList(questions.values());
    }

    @Override
    public Question findById(long questionId) {
        return questions.get(questionId);
    }

    @Override
    public void update(Question question) {
        findById(question.getQuestionId()).update(question);
    }

    @Override
    public void delete(long questionId) {
        questions.remove(questionId);
    }

    @Override
    public void updateCountOfAnswer(long questionId) {

    }
}
