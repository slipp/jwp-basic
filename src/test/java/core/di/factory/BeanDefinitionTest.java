package core.di.factory;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import di.examples.JdbcQuestionRepository;
import di.examples.MyQnaService;
import di.examples.MyUserController;

public class BeanDefinitionTest {
	private static final Logger log = LoggerFactory.getLogger(BeanDefinitionTest.class);

	@Test
	public void getResolvedAutowireMode() {
		BeanDefinition dbd = new BeanDefinition(JdbcQuestionRepository.class);
		assertEquals(InjectType.INJECT_NO, dbd.getResolvedInjectMode());
		
		dbd = new BeanDefinition(MyUserController.class);
		assertEquals(InjectType.INJECT_FIELD, dbd.getResolvedInjectMode());
		
		dbd = new BeanDefinition(MyQnaService.class);
		assertEquals(InjectType.INJECT_CONSTRUCTOR, dbd.getResolvedInjectMode());
	}
	
	@Test
	public void getInjectProperties() throws Exception {
		BeanDefinition dbd = new BeanDefinition(MyUserController.class);
		Set<Field> injectFields = dbd.getInjectFields();
		for (Field field : injectFields) {
			log.debug("inject field : {}", field);
		}
	}
	
	@Test
	public void getConstructor() throws Exception {
		BeanDefinition dbd = new BeanDefinition(MyQnaService.class);
		Set<Field> injectFields = dbd.getInjectFields();
		assertEquals(0, injectFields.size());
		Constructor<?> constructor = dbd.getInjectConstructor();
		log.debug("inject constructor : {}", constructor);
	}
}
