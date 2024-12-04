package com.sinoeyes.sync.enums;

import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用平台类型
 *
 * @author adonis lau
 * @email adonis.liu@pharmeyes.com
 * @date 2023/11/19
 */
@Getter
public enum DatabaseType {

    MYSQL("mysql", "com.mysql.jdbc.Driver"),
    ORACLE("oracle", "oracle.jdbc.driver.OracleDriver"),
    POSTGRESQL("postgresql", "org.postgresql.Driver"),
    SQLSERVER("sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");

    private static final Map<String, DatabaseType> CODE_MAP = new HashMap<>();

    static {
        for (DatabaseType DatabaseType : DatabaseType.values()) {
            CODE_MAP.put(DatabaseType.getDesc(), DatabaseType);
        }
    }

    /**
     * Get <code>DatabaseType</code> by code, if the code is invalidated will throw {@link IllegalArgumentException}.
     */
    public static @NonNull DatabaseType of(String desc) {
        DatabaseType DatabaseType = CODE_MAP.get(desc.toLowerCase().replace("reader", "").replace("writer", ""));
        if (DatabaseType == null) {
            throw new IllegalArgumentException(String.format("The platform type execution status code: %s is invalidated",
                    desc));
        }
        return DatabaseType;
    }

    private final String desc;
    private final String driver;

    DatabaseType(String desc, String driver) {
        this.desc = desc;
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "DatabaseType{" + "desc='" + desc + '\'' + "driver='" + driver + '\'' + '}';
    }
}
