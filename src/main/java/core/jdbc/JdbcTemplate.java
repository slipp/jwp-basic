package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
    private JdbcTemplate() {}

    private static class SingletonHolder {
        public static final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    }

    public static JdbcTemplate getJdbcTemplate() {
        return SingletonHolder.jdbcTemplate;
    }

    public void update(String sql, PreparedStatementSetter pss) throws DataAccessException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pss.setParameters(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void update(String sql, Object... parameters) {
        update(sql, createPreparedStatementSetter(parameters));
    }

    public void update(PreparedStatementCreator psc, KeyHolder holder) {
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement ps = psc.createPreparedStatement(conn);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                holder.setId(rs.getLong(1));
            }
            rs.close();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public <T> T queryForObject(String sql, RowMapper<T> rm, PreparedStatementSetter pss) {
        List<T> list = query(sql, rm, pss);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public <T> T queryForObject(String sql, RowMapper<T> rm, Object... parameters) {
        return queryForObject(sql, rm, createPreparedStatementSetter(parameters));
    }

    public <T> List<T> query(String sql, RowMapper<T> rm, PreparedStatementSetter pss) throws DataAccessException {
        ResultSet rs = null;
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pss.setParameters(pstmt);
            rs = pstmt.executeQuery();

            List<T> list = new ArrayList<T>();
            while (rs.next()) {
                list.add(rm.mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        }
    }

    public <T> List<T> query(String sql, RowMapper<T> rm, Object... parameters) {
        return query(sql, rm, createPreparedStatementSetter(parameters));
    }

    private PreparedStatementSetter createPreparedStatementSetter(Object... parameters) {
        return new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                for (int i = 0; i < parameters.length; i++) {
                    pstmt.setObject(i + 1, parameters[i]);
                }
            }
        };
    }
}
