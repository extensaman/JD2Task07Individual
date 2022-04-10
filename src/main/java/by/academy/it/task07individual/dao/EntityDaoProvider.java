package by.academy.it.task07individual.dao;

import by.academy.it.task07individual.dao.impl.EntityDaoImpl;

import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 */
public final class EntityDaoProvider {

    /**
     *
     */
    private EntityDaoProvider() {

    }

    /**
     * @param aClass
     * @param bundleFile
     * @return -
     * @throws EntityDaoException
     */
    public static EntityDao getNewInstance(final Class aClass,
                                           final String bundleFile)
                                    throws EntityDaoException {
        Optional.ofNullable(aClass).orElseThrow(EntityDaoException::new);
        Optional.ofNullable(bundleFile).orElseThrow(EntityDaoException::new);
        EntityDao entityDao = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(bundleFile);
            ConnectionPool pool = new ConnectionPool(bundle);
            entityDao = new EntityDaoImpl(aClass, pool);
        } catch (EntityDaoException | MissingResourceException e) {
            throw new EntityDaoException(e);
        }
        return entityDao;
    }
}
