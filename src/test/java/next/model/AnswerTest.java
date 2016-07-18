package next.model;

public class AnswerTest {
    public static Answer newAnswer(String writer) {
        return new Answer(writer, "contents", 1L);
    }
}
