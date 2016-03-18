package core.di.factory;

import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.junit.Test;

import di.examples.JdbcUserRepository;
import di.examples.MyConfig;

public class AnnotatedBeanDefinitionReaderTest {

	@Test
	public void register() {
		BeanFactory beanFactory = new BeanFactory();
		AnnotatedBeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
		abdr.register(MyConfig.class);
		
		ClasspathBeanDefinitionScanner cbds = new ClasspathBeanDefinitionScanner(beanFactory);
		cbds.doScan("di.examples");
		
		beanFactory.initialize();
		
		assertNotNull(beanFactory.getBean(DataSource.class));
		
		JdbcUserRepository userRepository = beanFactory.getBean(JdbcUserRepository.class);
		assertNotNull(userRepository);
		assertNotNull(userRepository.getDataSource());
	}
}
