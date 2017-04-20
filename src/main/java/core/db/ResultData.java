package core.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * ResultSet Wrapper
 * Created by johngrib on 2017. 4. 22..
 */
public class ResultData {

    final private List<String> labels;
    private ResultSet rs = null;

    public ResultData(final ResultSet rs) throws SQLException {
        this.rs = rs;
        this.labels = getColumnLabels(rs);
    }

    /**
     * column labels를 리턴한다.
     * @return
     */
    public List<String> getLabels() {
        return labels;
    }

    /**
     * column lable 을 수집한다.
     * @param rs
     * @return
     */
    private List<String> getColumnLabels(final ResultSet rs) {
        try {
            final int size = rs.getMetaData().getColumnCount();
            final List<String> list = new ArrayList<>(size);
            final ResultSetMetaData meta = rs.getMetaData();

            for (int i = 1; i <= size; i++) {
                String name = meta.getColumnName(i);
                list.add(name);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    public ResultSet getResultSet() {
        return rs;
    }

    public void close() {
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            rs = null;
        }
    }

    public boolean next() {
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Map<String, Object> getDataMap() {
        try {
            final Map<String, Object> data = new HashMap<>();
            for (String label : labels) {
                data.put(label, rs.getObject(label));
            }
            return data;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_MAP;
    }
}
