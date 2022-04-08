package by.academy.it.task07individual.dao;

import java.util.Map;
import java.util.Optional;

public interface EntityDao {
    Optional<Object> findById(Long id) throws EntityDaoException;

    Map<Long, Object> findAll() throws EntityDaoException;

    void insert(Object entity) throws EntityDaoException;

    void update(Long id, Object entity) throws EntityDaoException;

    void delete(Long id) throws EntityDaoException;
}
