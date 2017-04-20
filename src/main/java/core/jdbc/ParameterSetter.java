package core.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ParameterSetter {
    void setParams(PreparedStatement rs) throws SQLException;
}
