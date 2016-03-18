package di.examples;

import javax.sql.DataSource;

import core.annotation.Inject;
import core.annotation.Repository;

@Repository
public class JdbcUserRepository implements UserRepository {
	@Inject
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}
}
