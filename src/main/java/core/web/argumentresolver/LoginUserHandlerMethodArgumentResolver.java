package core.web.argumentresolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import next.model.User;
import next.service.SessionComponent;

public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	@Autowired
	private SessionComponent sessionComponent;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		LoginUser loginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class);
		User loginUser = sessionComponent.findUser(webRequest);
		if (loginUserAnnotation.required() && loginUser.isGuestUser()) {
			throw new LoginRequiredException();
		}
		return loginUser;
	}

}
