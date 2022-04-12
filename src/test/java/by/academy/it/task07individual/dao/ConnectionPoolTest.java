package by.academy.it.task07individual.dao;

import org.junit.jupiter.api.Test;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static by.academy.it.task07individual.AppTest.PROPERTY_FILE_FOR_TESTS;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class ConnectionPoolTest {

    @Test
    void testConstructorNegative() {
        ResourceBundle bundle = ResourceBundle.getBundle("badResourceBundleForTests");
        assertThrows(EntityDaoException.class, () -> new ConnectionPool(bundle));
    }

    @Test
    void testConstructorPositive() {
        try {
            ConnectionPool pool = new ConnectionPool(ResourceBundle.getBundle(PROPERTY_FILE_FOR_TESTS));
            assertNotNull(pool.getConnection());
        } catch (EntityDaoException | MissingResourceException e) {
            fail(e);
        }
    }
}