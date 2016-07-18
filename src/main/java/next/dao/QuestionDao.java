package next.dao;

import java.util.List;

import next.model.Question;

public interface QuestionDao {

    Question insert(Question question);

    List<Question> findAll();

    Question findById(long questionId);

    void update(Question question);

    void delete(long questionId);

    void updateCountOfAnswer(long questionId);

}