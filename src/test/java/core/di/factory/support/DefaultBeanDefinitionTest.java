package core.di.factory.support;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.di.factory.config.InjectType;
import core.di.factory.example.JdbcUserRepository;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.MyUserController;

public class DefaultBeanDefinitionTest {
	private static final Logger log = LoggerFactory.getLogger(DefaultBeanDefinitionTest.class);

	@Test
	public void getResolvedAutowireMode() {
		DefaultBeanDefinition dbd = new DefaultBeanDefinition(JdbcUserRepository.class);
		assertEquals(InjectType.INJECT_TYPE, dbd.getResolvedInjectMode());
		
		dbd = new DefaultBeanDefinition(MyQnaService.class);
		assertEquals(InjectType.INJECT_CONSTRUCTOR, dbd.getResolvedInjectMode());
	}
	
	@Test
	public void getInjectProperties() throws Exception {
		DefaultBeanDefinition dbd = new DefaultBeanDefinition(MyUserController.class);
		Set<Class<?>> properties = dbd.getInjectProperties();
		for (Class<?> property : properties) {
			log.debug("inject property : {}", property);
		}
	}
	
	@Test
	public void getConstructor() throws Exception {
		DefaultBeanDefinition dbd = new DefaultBeanDefinition(MyQnaService.class);
		Set<Class<?>> properties = dbd.getInjectProperties();
		assertEquals(0, properties.size());
		Constructor<?> constructor = dbd.getConstructor();
		log.debug("inject constructor : {}", constructor);
	}
}
