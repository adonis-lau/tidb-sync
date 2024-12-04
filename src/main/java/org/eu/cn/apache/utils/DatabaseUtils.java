package org.eu.cn.apache.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库工具类
 *
 * @author adonis lau
 * @email adonis.liu@pharmeyes.com
 * @date 2024-12-04
 */
@Slf4j
public class DatabaseUtils {

    /**
     * 获取数据库连接
     *
     * @param url      a database url of the form jdbc:subprotocol:subname
     * @param user     the database user on whose behalf the connection is being made
     * @param password the user's password
     * @param driver   the fully qualified name of the implementation class
     * @return a connection to the URL
     * @throws SQLException           if a database access error occurs or the url is null
     * @throws ClassNotFoundException when the driver has determined that the timeout value specified by the setLoginTimeout method has been exceeded and has at least tried to cancel the current database connection attempt
     */
    public static Connection getConnection(String url, String user, String password, String driver) throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 关闭数据库连接
     *
     * @param conn 数据库连接
     * @param rs   结果集
     */
    public static void close(Connection conn, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            log.error(e.getSQLState(), e);
        }
    }

    /**
     * 关闭数据库连接
     *
     * @param conn  数据库连接
     * @param pstmt 预处理语句
     * @param rs    结果集
     */
    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            close(conn, rs);
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            log.error(e.getSQLState(), e);
        }
    }

    /**
     * 执行查询操作
     *
     * @param sql    查询语句
     * @param params 参数
     * @return 结果集
     * @throws SQLException 抛出异常
     */
    public static ResultSet executeQuery(Connection conn, String sql, Object... params) throws SQLException {
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setQueryTimeout(300);
            // 设置参数
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            log.error(e.getSQLState(), e);
            throw e;
        }
    }
}
