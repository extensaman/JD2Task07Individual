package by.academy.it.task07individual.dao;

import java.util.Map;
import java.util.Optional;

/**
 *
 */
public interface EntityDao {
    /**
     * @param id
     * @return -
     * @throws EntityDaoException
     */
    Optional<Object> findById(Long id) throws EntityDaoException;

    /**
     * @return -
     * @throws EntityDaoException
     */
    Map<Long, Object> findAll() throws EntityDaoException;

    /**
     * @param entity
     * @throws EntityDaoException
     */
    void insert(Object entity) throws EntityDaoException;

    /**
     * @param id
     * @param entity
     * @throws EntityDaoException
     */
    void update(Long id, Object entity) throws EntityDaoException;

    /**
     * @param id
     * @throws EntityDaoException
     */
    void delete(Long id) throws EntityDaoException;
}
