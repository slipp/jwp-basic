package core.jdbc;

import core.db.Query;
import core.db.ResultData;
import util.DataMethod;
import util.ReflectionUtil;
import util.UpperStringMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by johngrib on 2017. 4. 20..
 */
public class JdbcTemplate {

    /**
     * INSERT, UPDATE 를 수행한다.
     *
     * @param sql
     * @param vo
     * @throws SQLException
     */
    public void update(String sql, Object vo) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(Query.build(sql, vo));
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    /**
     * SELECT 를 수행한다.
     * @param sql
     * @param voClass
     * @return
     * @throws SQLException
     */
    public <T> List<T> select(String sql, Class<T> voClass) throws SQLException {
        return select(sql, voClass, null);
    }

    /**
     * SELECT 를 수행한 결과의 첫 번째 row 를 리턴한다.
     * @param sql
     * @param voClass
     * @param vo
     * @return
     * @throws SQLException
     */
    public <T> T selectOne(String sql, Class<T> voClass, Object vo) throws SQLException {
        final List<T> list = select(sql, voClass, vo);
        return (list.size() > 0) ? list.get(0) : null;
    }

    /**
     * SELECT 를 수행한다.
     *
     * @param sql
     * @param voClass
     * @return
     * @throws SQLException
     */
    public <T> List<T> select(String sql, Class<T> voClass, Object vo) throws SQLException {

        ResultSet rs = null;
        final String query = Query.build(sql, vo);
        try (
                final Connection con = ConnectionManager.getConnection();
                final PreparedStatement pstmt = con.prepareStatement(query);
        ) {
            rs = pstmt.executeQuery();

            final ResultData rd = new ResultData(rs);
            final List<T> list = new ArrayList<>(rs.getFetchSize());
            final Map<String, DataMethod> setters = ReflectionUtil.getSetterMemberMap(voClass, new UpperStringMap());
            final List<String> labels = rd.getLabels();

            while (rd.next()) {

                final T row = ReflectionUtil.newSimpleInstance(voClass);

                for (String label: labels) {
                    DataMethod setter = setters.get(label);
                    setVoFromResultSet(setter, rd.getResultSet(), row);
                }
                list.add(row);
            }
            return list;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    /**
     * ResultSet 의 데이터를 vo 에 매핑한다.
     * @param m
     * @param rs
     * @param vo
     */
    private void setVoFromResultSet(final DataMethod m, final ResultSet rs, Object vo) {
        try {
            if (String.class.equals(m.type)) {
                m.setter(vo, rs.getString(m.fieldName));
                return;
            }
            if (Integer.class.equals(m.type)) {
                m.setter(vo, rs.getInt(m.fieldName));
                return;
            }
            if (Double.class.equals(m.type)) {
                m.setter(vo, rs.getInt(m.fieldName));
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
