package core.di.context.annotation;

import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.junit.Test;

import core.di.beans.factory.support.BeanDefinitionReader;
import core.di.beans.factory.support.DefaultBeanFactory;
import di.examples.ExampleConfig;
import di.examples.IntegrationConfig;
import di.examples.JdbcUserRepository;
import di.examples.MyJdbcTemplate;

public class AnnotatedBeanDefinitionReaderTest {
	@Test
	public void register_simple() {
		DefaultBeanFactory beanFactory = new DefaultBeanFactory();
		BeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
		abdr.loadBeanDefinitions(ExampleConfig.class);
		beanFactory.preInstantiateSinglonetons();
		
		assertNotNull(beanFactory.getBean(DataSource.class));
	}
	
	@Test
	public void register_ClasspathBeanDefinitionScanner_통합() {
		DefaultBeanFactory beanFactory = new DefaultBeanFactory();
		BeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
		abdr.loadBeanDefinitions(IntegrationConfig.class);
		
		ClasspathBeanDefinitionScanner cbds = new ClasspathBeanDefinitionScanner(beanFactory);
		cbds.doScan("di.examples");
		
		beanFactory.preInstantiateSinglonetons();
		
		assertNotNull(beanFactory.getBean(DataSource.class));
		
		JdbcUserRepository userRepository = beanFactory.getBean(JdbcUserRepository.class);
		assertNotNull(userRepository);
		assertNotNull(userRepository.getDataSource());
		
		MyJdbcTemplate jdbcTemplate = beanFactory.getBean(MyJdbcTemplate.class);
		assertNotNull(jdbcTemplate);
		assertNotNull(jdbcTemplate.getDataSource());		
	}
}
