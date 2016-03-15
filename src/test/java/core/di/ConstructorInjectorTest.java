package core.di;

import static org.junit.Assert.assertNotNull;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;

import com.google.common.collect.Sets;

import core.annotation.Inject;
import core.annotation.Repository;
import core.annotation.Service;

public class ConstructorInjectorTest {
	
	private Reflections reflections;
	private ConstructorInjector ci;
	
	@Before
	@SuppressWarnings("unchecked")
	public void setup() {
		reflections = new Reflections("core.di");
		Set<Class<?>> preInstanticateClazz = getTypesAnnotatedWith(Service.class, Repository.class);
		ci = new ConstructorInjector(preInstanticateClazz);
	}

	@Test
	public void di() throws Exception {
		ci.inject();
		
		MyService myService = ci.getBean(MyService.class);
		assertNotNull(myService);
		assertNotNull(myService.getRepository());
		assertNotNull(myService.getYourRepository());
	}
	
	@SuppressWarnings("unchecked")
	Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
		Set<Class<?>> beans = Sets.newHashSet();
		for (Class<? extends Annotation> annotation : annotations) {
			beans.addAll(reflections.getTypesAnnotatedWith(annotation));
		}
		return beans;
	}
}

@Service
class MyService {
	private MyRepository repository;
	private YourRepository yourRepository;

	@Inject
	public MyService(MyRepository repository, YourRepository yourRepository) {
		this.repository = repository;
		this.yourRepository = yourRepository;
	}
	
	public MyRepository getRepository() {
		return repository;
	}
	
	public YourRepository getYourRepository() {
		return yourRepository;
	}
}

@Repository
class MyRepository {
	private YourRepository repository;

	@Inject
	public MyRepository(YourRepository repository) {
		this.repository = repository;
	}
	
	public YourRepository getRepository() {
		return repository;
	}
}

@Repository
class YourRepository {
	
}
