package next.dao;

import next.model.Answer;

import java.util.List;

public interface AnswerDao {
    Answer insert(Answer answer);

    Answer findById(long answerId);

    List<Answer> findAllByQuestionId(long questionId);

    void delete(Long answerId);
}
