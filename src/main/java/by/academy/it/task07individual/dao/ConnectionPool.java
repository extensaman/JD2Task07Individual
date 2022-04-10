package by.academy.it.task07individual.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 */
public class ConnectionPool {
    /**
     *
     */
    private static final String URL_ALIAS = "url";
    /**
     *
     */
    private static final String USER_ALIAS = "username";
    /**
     *
     */
    private static final String PASSWORD_ALIAS = "password";
    /**
     *
     */
    private final HikariDataSource dataSource;


    /**
     * @param bundle
     * @throws EntityDaoException
     */
    public ConnectionPool(final ResourceBundle bundle)
                                throws EntityDaoException {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(bundle.getString(URL_ALIAS));
            config.setUsername(bundle.getString(USER_ALIAS));
            config.setPassword(bundle.getString(PASSWORD_ALIAS));
            dataSource = new HikariDataSource(config);
        } catch (MissingResourceException e) {
            throw new EntityDaoException(e);
        }
    }

    /**
     * @return -
     * @throws EntityDaoException
     */
    public Connection getConnection() throws EntityDaoException {
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new EntityDaoException(e);
        }
        return connection;
    }
}
