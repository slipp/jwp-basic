package core.mvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
	private final View view;
	private final Map<String, Object> model = new HashMap<>();

	public ModelAndView(final View view) {
		this.view = view;
	}

	public ModelAndView addObject(final String attributeName, final Object attributeValue) {
		model.put(attributeName, attributeValue);
		return this;
	}

	public View getView() {
		return view;
	}

	public Map<String, Object> getModel() {
		return Collections.unmodifiableMap(model);
	}
}
