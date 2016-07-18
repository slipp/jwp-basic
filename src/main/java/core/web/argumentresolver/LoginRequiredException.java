package core.web.argumentresolver;

import org.springframework.core.ErrorCoded;

public class LoginRequiredException extends Exception implements ErrorCoded {
    private static final long serialVersionUID = 588798050611082750L;
    private static final String ERROR_CODE = "exception.login.required";

    @Override
    public String getErrorCode() {
        return ERROR_CODE;
    }
}
