package by.academy.it.task07individual.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static by.academy.it.task07individual.AppTest.PROPERTY_FILE_FOR_TESTS;
import static by.academy.it.task07individual.AppTest.A_CLASS;

class EntityDaoProviderTest extends Assertions {

    @Test
    void getNewInstanceExceptionOnaClassEqualsNull() {
        assertThrows(EntityDaoException.class,
                () -> {
                    EntityDaoProvider.getNewInstance(null, PROPERTY_FILE_FOR_TESTS);
                });
    }

    @Test
    void getNewInstanceExceptionOnBundleFileEqualsNull() {
        assertThrows(EntityDaoException.class,
                () -> {
                    EntityDaoProvider.getNewInstance(EntityDaoProviderTest.class, null);
                });
    }

    @Test
    void getNewInstanceExceptionOnWrongPropertyFile() {
        assertThrows(EntityDaoException.class,
                () -> {
                    EntityDaoProvider.getNewInstance(EntityDaoProviderTest.class, "");

                });
    }

    @Test
    void getNewInstanceGood() {
        try {
            EntityDao dao =
                    EntityDaoProvider.getNewInstance(A_CLASS, PROPERTY_FILE_FOR_TESTS);
            assertNotNull(dao);
        } catch (EntityDaoException e) {
            fail(e);
        }
    }

}