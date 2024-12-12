package utility;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = JDBCUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IOException("Unable to find application properties");
            }
            properties.load(input);
        } catch (IOException e) {
            LoggerUtil.logError("Unable to load application properties", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Driver hsqlDriver = new org.hsqldb.jdbcDriver();
        DriverManager.registerDriver(hsqlDriver);
        String dbURL = properties.getProperty("db.url");
        String dbUser = properties.getProperty("db.user");
        String dbPassword = properties.getProperty("db.password");
        Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
        conn.setAutoCommit(false);
        return conn;
    }

    public static void commit(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                } else {
                    LoggerUtil.logWarning("Autocommit is enabled. No commit executed.");
                }
            } else {
                LoggerUtil.logWarning("Connection is null or closed. Commit not possible.");
            }
        } catch (SQLException e) {
            LoggerUtil.logError("Error committing transaction", e);
        }
    }

    public static void rollback(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            LoggerUtil.logError("Error rolling back", e);
        }
    }
}