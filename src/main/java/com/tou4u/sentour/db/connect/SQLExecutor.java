package com.tou4u.sentour.db.connect;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SQLExecutor {

    // JDBC 드라이버
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    // MySql URL / DB 이름
    private static final String CONNECT_URL = "jdbc:mysql://localhost:52559/tourdb?useUnicode=true&characterEncoding=utf8";
//	private static final String CONNECT_URL = "jdbc:mysql://192.168.0.20:3306/trip";

    // mysql user id
    private static final String id = "azure";
//	private static final String id = "root";

    // mysql user password
    private static final String pwd = "6#vWHD_$";
//	private static final String pwd = "root";

    private PreparedStatement pstmt = null;
    private CallableStatement ctsmt = null;
    private ResultSet rs = null;
    private Connection conn = null;

    private void initSimpleDataSource() {
        SimpleDataSource.init(JDBC_DRIVER, CONNECT_URL, id, pwd);
    }

    /**
     * PreparedStatement에 파라미터 셋팅
     *
     * @param pstmt
     * @param data
     * @return
     * @throws SQLException
     */
    private PreparedStatement prepareSql(PreparedStatement pstmt, List<Object> data) throws SQLException {
        try {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i) instanceof String) {
                    pstmt.setString(i + 1, (String) data.get(i));
                } else if (data.get(i) instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) data.get(i));
                } else if (data.get(i) instanceof Date) {
                    pstmt.setDate(i + 1, (Date) data.get(i));
                }
            }
        } catch (SQLException ex) {
            throw ex;
        }

        return pstmt;
    }

    private String getSql(String sql, List<String> columnName) {
        for (int i = 0; i < columnName.size(); i++) {
            if (columnName.get(i) instanceof String) {
                sql = sql.replaceFirst("\\!", columnName.get(i));
            }
        }
        return sql;
    }

    /**
     * CallableStatement 파라미터 셋팅
     *
     * @param ctsmt
     * @param inParameter
     * @return
     * @throws SQLException
     */
    private CallableStatement prepareCall(CallableStatement ctsmt, List<Object> inParameter, int... outparamsType)
            throws SQLException {

        try {
            for (int i = 0; i < inParameter.size(); i++) {
                if (inParameter.get(i) instanceof String) {
                    ctsmt.setString(i + 1, (String) inParameter.get(i));
                } else if (inParameter.get(i) instanceof Integer) {
                    ctsmt.setInt(i + 1, (Integer) inParameter.get(i));
                } else if (inParameter.get(i) instanceof Date) {
                    ctsmt.setDate(i + 1, (Date) inParameter.get(i));
                }
            }
            for (int i = inParameter.size(), index = 0; index < outparamsType.length; i++, index++) {
                ctsmt.registerOutParameter(i + 1, outparamsType[index]);
            }
        } catch (SQLException ex) {
            throw ex;
        }

        return ctsmt;
    }

    public ResultSet callQuery(String procedure_name, List<Object> inParameter, List<Object> outParameter,
                               int... outparamsType) throws IOException, ClassNotFoundException {

        initSimpleDataSource();
        int intCnt = 0;

        try {
            conn = SimpleDataSource.getConnection();
            String sql = "{call " + procedure_name;

            int paramSize = inParameter.size() + outparamsType.length;

            if (paramSize != 0) {
                sql += " (";
            } else {
                sql += "}";
            }
            for (int idx = 0; idx < paramSize; idx++) {
                sql += ((idx < (paramSize + -1)) ? "?, " : "?)}");
            }

            ctsmt = prepareCall(conn.prepareCall(sql), inParameter, outparamsType);

            if (isVIEW(sql)) {
                rs = ctsmt.executeQuery();
            } else {
                intCnt = ctsmt.executeUpdate();
            }

            for (int index = inParameter.size(), i = 0; i < outparamsType.length; index++, i++) {
                outParameter.add(ctsmt.getObject(index + 1));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rs;
    }

    public ResultSet callQuery(String procedure_name, List<Object> inParameter)
            throws IOException, ClassNotFoundException {

        initSimpleDataSource();
        int intCnt = 0;

        try {
            conn = SimpleDataSource.getConnection();
            String sql = "{call " + procedure_name;

            int inSize = inParameter.size();

            if (inSize != 0) {
                sql += " (";
            } else {
                sql += "}";
            }
            for (int idx = 0; idx < inSize; idx++) {
                sql += ((idx < (inSize + -1)) ? "?, " : "?)}");
            }

            ctsmt = prepareCall(conn.prepareCall(sql), inParameter);

            if (isVIEW(sql)) {
                rs = ctsmt.executeQuery();
            } else {
                intCnt = ctsmt.executeUpdate();
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rs;
    }

    /**
     * 바인드 변수가 있는 쿼리 처리
     *
     * @param sql
     * @param data
     * @return
     * @throws Exception
     */
    public ResultSet runQuery(String sql, List<String> columnName, List<Object> data) throws Exception {

        initSimpleDataSource();

        int intCnt = 0;

        try {
            conn = SimpleDataSource.getConnection();

            sql = getSql(sql, columnName);

            pstmt = prepareSql(conn.prepareStatement(sql), data);

            if (isSelectQuery(sql)) {
                rs = pstmt.executeQuery();

                // ResultSetMetaData rsmd = rs.getMetaData();
            } else {
                intCnt = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    /**
     * 바인드 변수가 있는 쿼리 처리
     *
     * @param sql
     * @param data
     * @return
     * @throws Exception
     */
    public ResultSet runQuery(String sql, List<Object> data) throws Exception {

        initSimpleDataSource();
        int intCnt = 0;

        try {
            conn = SimpleDataSource.getConnection();

            pstmt = prepareSql(conn.prepareStatement(sql), data);

            if (isSelectQuery(sql)) {
                rs = pstmt.executeQuery();

                // ResultSetMetaData rsmd = rs.getMetaData();
            } else {
                intCnt = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {

        }

        return rs;
    }

    /**
     * 단순 Statement 쿼리 처리, 바인드 변수 없는 쿼리 처리
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public ResultSet runQuery(String sql) throws Exception {

        initSimpleDataSource();
        int intCnt = 0;

        try {
            conn = SimpleDataSource.getConnection();
            pstmt = conn.prepareStatement(sql);

            if (isSelectQuery(sql)) {
                rs = pstmt.executeQuery();

                // ResultSetMetaData rsmd = rs.getMetaData();
            } else {
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            throw ex;
        }

        return rs;
    }

    /**
     * 문자열이 Select 쿼리인지 점검
     *
     * @param procedure_name
     * @return
     */
    private boolean isVIEW(String procedure_name) {
        StringBuffer sb = new StringBuffer(procedure_name.trim());
        String s = sb.substring(6, 12);
        return s.equalsIgnoreCase("select");
    }

    /**
     * 문자열이 Select 쿼리인지 점검
     *
     * @param sql
     * @return
     */
    private boolean isSelectQuery(String sql) {
        StringBuffer sb = new StringBuffer(sql.trim());
        String s = sb.substring(0, 6);

        return s.equalsIgnoreCase("SELECT");
    }

    /*
     * private boolean isPreparedStatement(String sql) { StringBuffer sb = new
     * StringBuffer(sql.trim()); boolean b = true;
     *
     * if(sb.indexOf("?") <= 0) { b = false; }
     *
     * return b; }
     */

    /**
     * 사용한 자원 반환
     */
    public void closeQuery() {
        try {
            if (ctsmt != null) {
                ctsmt.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.commit();
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
