package core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
