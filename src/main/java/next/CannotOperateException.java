package next;

public class CannotOperateException extends Exception {
    private static final long serialVersionUID = 1L;

    public CannotOperateException(String message) {
        super(message);
    }
}