package org.eu.cn.apache.utils;

import com.alibaba.fastjson2.JSONObject;
import com.sinoeyes.sync.enums.DatabaseType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DataX工具类
 *
 * @author adonis lau
 * @email adonis.liu@pharmeyes.com
 * @date 2024-12-04
 */
@Slf4j
public class DataXUtils {

    /**
     * 读取 DataX 的 json 文件
     *
     * @param jobPath DataX 的 job 文件路径
     * @return json
     */
    public static JSONObject readDataXJson(String jobPath) {
        // 读取目录下的json文件
        try {
            return JSONObject.parseObject(FileUtils.readFileToString(ResourceUtils.getFile(jobPath), Charset.defaultCharset()));
        } catch (IOException e) {
            log.error("读取 DataX 的 json 文件失败", e);
        }
        return JSONObject.parseObject("{}");
    }

    /**
     * 获取 writer 对应表数据的最大更新时间
     *
     * @param jobPath DataX 的 job 文件路径
     * @return 表数据的最大更新时间
     */
    public static String getWriterMaxUpdateTime(String jobPath) throws SQLException, ClassNotFoundException {
        JSONObject job = readDataXJson(jobPath);
        JSONObject writer = job.getJSONObject("job").getList("content", JSONObject.class).getFirst().getJSONObject("writer");
        String writerName = writer.getString("name");
        String username = writer.getJSONObject("parameter").getString("username");
        String password = writer.getJSONObject("parameter").getString("password");
        JSONObject connection = writer.getJSONObject("parameter").getList("connection", JSONObject.class).getFirst();
        String jdbcUrl = connection.getString("jdbcUrl");
        String table = connection.getList("table", String.class).getFirst();
        log.debug("job: {}, writerName: {}, username: {}, password: {}, jdbcUrl: {}, table: {}", job, writerName, username, password, jdbcUrl, table);

        // 连接数据库
        Connection conn = null;
        ResultSet resultSet = null;
        try {
            String driver = DatabaseType.of(writerName).getDriver();
            conn = DatabaseUtils.getConnection(jdbcUrl, username, password, driver);
            String sql = String.format("select max(paas_update_time) from %s", table);
            resultSet = DatabaseUtils.executeQuery(conn, sql);
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } finally {
            DatabaseUtils.close(conn, resultSet);
        }
        return null;
    }

    /**
     * 获取 reader 对应表数据的最小更新时间
     *
     * @param jobPath DataX 的 job 文件路径
     * @return 表数据的最小更新时间
     */
    public static String getReaderMinUpdateTime(String jobPath) throws SQLException, ClassNotFoundException {
        JSONObject job = readDataXJson(jobPath);
        JSONObject reader = job.getJSONObject("job").getList("content", JSONObject.class).getFirst().getJSONObject("reader");
        String readerName = reader.getString("name");
        String username = reader.getJSONObject("parameter").getString("username");
        String password = reader.getJSONObject("parameter").getString("password");
        JSONObject connection = reader.getJSONObject("parameter").getList("connection", JSONObject.class).getFirst();
        String jdbcUrl = connection.getList("jdbcUrl", String.class).getFirst();
        String table = connection.getList("table", String.class).getFirst();
        log.debug("job:{}, readerName: {}, username: {}, password: {}, jdbcUrl: {}, table: {}", job, readerName, username, password, jdbcUrl, table);

        // 连接数据库
        Connection conn = null;
        ResultSet resultSet = null;
        try {
            String driver = DatabaseType.of(readerName).getDriver();
            conn = DatabaseUtils.getConnection(jdbcUrl, username, password, driver);
            String sql = String.format("select min(paas_update_time) from %s", table);
            resultSet = DatabaseUtils.executeQuery(conn, sql);
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } finally {
            DatabaseUtils.close(conn, resultSet);
        }
        return null;
    }
}
