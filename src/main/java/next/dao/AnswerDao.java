package next.dao;

import java.util.List;

import next.model.Answer;

public interface AnswerDao {

    Answer insert(Answer answer);

    Answer findById(long answerId);

    List<Answer> findAllByQuestionId(long questionId);

    void delete(Long answerId);
}