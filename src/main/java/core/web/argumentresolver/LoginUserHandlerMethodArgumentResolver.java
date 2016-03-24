package core.web.argumentresolver;

import next.model.User;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		LoginUser loginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class);
		User loginUser = (User)webRequest.getAttribute(loginUserAnnotation.value(), WebRequest.SCOPE_SESSION);
		if (loginUserAnnotation.required() && loginUser == null) {
			throw new LoginRequiredException();
		}
		return loginUser;
	}

}
