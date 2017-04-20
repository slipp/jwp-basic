package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcTemplate {
    private <R> R execute(String query,
                          ParameterSetter parameterSetter,
                          StatementExecutor<R> executor) {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){
            parameterSetter.setParams(stmt);
            return executor.execute(stmt);
        } catch(SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void insert(String query, Object... params) {
        execute(query, stmt -> parameterSetter(stmt, params), PreparedStatement::execute);
    }

    public <T> Optional<T> queryForObject(String query, RowMapper<T> mapper, Object... params)  {
        List<T> rows = findAll(query, stmt -> parameterSetter(stmt, params), mapper);
        if (rows.size() > 0) {
            return Optional.of(rows.get(0));
        }
        return Optional.empty();
    }

    public <T> List<T> findAll(String query, RowMapper<T> mapper, Object... params) {
        return execute(query, stmt -> parameterSetter(stmt, params), stmt -> fetchAllRows(stmt, mapper));
    }

    public <T> List<T> findAll(String query, ParameterSetter parameterSetter, RowMapper<T> mapper) {
        return execute(query, parameterSetter, stmt -> fetchAllRows(stmt, mapper));
    }

    private void parameterSetter(PreparedStatement stmt, Object[] params) throws SQLException {
        int i = 1;
        for(Object param : params) {
            stmt.setObject(i++, param);
        }
    }

    private <T> List<T> fetchAllRows(PreparedStatement stmt, RowMapper<T> mapper) throws SQLException {
        List<T> rows = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                rows.add(mapper.map(rs));
            }
        }
        return rows;
    }
}

class DataAccessException extends RuntimeException {
    public DataAccessException(Throwable cause) {
        super(cause);
    }
}

@FunctionalInterface
interface StatementExecutor<R> {
    R execute(PreparedStatement stmt) throws SQLException;
}