package by.academy.it.task07individual.dao.impl;

import by.academy.it.task07individual.dao.EntityDao;
import by.academy.it.task07individual.dao.EntityDaoException;
import by.academy.it.task07individual.dao.EntityDaoProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static by.academy.it.task07individual.AppTest.A_CLASS;
import static by.academy.it.task07individual.AppTest.PROPERTY_FILE_FOR_TESTS;

class EntityDaoImplTest extends Assertions {
    public static final long NOT_EXISTING_ID = 1000L;
    public static final long EXISTING_ID = 3L;
    public static final long FIRST_ID = 1L;
    private static EntityDao dao;

    @BeforeAll
    static void beforeAll() {
        try {
            dao = EntityDaoProvider.getNewInstance(A_CLASS, PROPERTY_FILE_FOR_TESTS);
        } catch (EntityDaoException e) {
            fail(e);
        }
    }

    @Test
    void findByIdPositive() {
        try {
            assertTrue(dao.findById(EXISTING_ID).isPresent());
        } catch (EntityDaoException e) {
            fail(e);
        }
    }

    @Test
    void findByIdNotExistingId() {
        try {
            assertTrue(dao.findById(NOT_EXISTING_ID).isEmpty());
        } catch (EntityDaoException e) {
            fail(e);
        }
    }

    @Test
    void findAll() {
        Map map = null;
        try {
            map = dao.findAll();
        } catch (EntityDaoException e) {
            fail(e);
        }
        assertTrue(map.size() > 0);
    }

    @Test
    void updatePositive() {
        try {
            Object entity01 = dao.findById(EXISTING_ID).get();
            Object saveBox = dao.findById(EXISTING_ID + 1).get();
            dao.update(EXISTING_ID + 1, entity01);
            Object entity02 = dao.findById(EXISTING_ID + 1).get();
            assertEquals(entity01, entity02);
            dao.update(EXISTING_ID + 1, saveBox);
        } catch (EntityDaoException e) {
            fail(e);
        }
    }

    @Test
    void updateNotExistingElement() {
        Object entity01 = null;
        try {
            entity01 = dao.findById(EXISTING_ID).get();
        } catch (EntityDaoException e) {
            fail(e);
        }

        Object finalEntity01 = entity01;
        assertThrows(EntityDaoException.class, () -> dao.update(NOT_EXISTING_ID, finalEntity01));
    }

    @Test
    void deleteNotExistingElement() {
        assertThrows(EntityDaoException.class, () -> dao.delete(NOT_EXISTING_ID));
    }

    @Test
    void deletePositive() {
        Optional<Object> optional = Optional.of(new Object());
        try {
            dao.delete(FIRST_ID);
            optional = dao.findById(FIRST_ID);
        } catch (EntityDaoException e) {
            fail(e);
        }
        assertTrue(optional.isEmpty());
    }

    @Test
    void insertPositive() {
        try {
            Object entity01 = dao.findById(EXISTING_ID).get();
            Map<Long, Object> initialMap = dao.findAll();
            long initialEntityCount = initialMap.values().stream().filter(o -> o.equals(entity01)).count();
            dao.insert(entity01);
            Map<Long, Object> finalMap = dao.findAll();
            long finalEntityCount = finalMap.values().stream().filter(o -> o.equals(entity01)).count();
            assertEquals(initialEntityCount + 1, finalEntityCount);
        } catch (EntityDaoException e) {
            fail(e);
        }
    }

    @Test
    void insertNegative() {
        assertThrows(EntityDaoException.class, () -> dao.insert(new Object()));
    }
}