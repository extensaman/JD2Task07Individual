package by.academy.it.task07individual.dao;

/**
 *
 */
public class EntityDaoException extends Exception {
    /**
     *
     */
    public EntityDaoException() {
    }

    /**
     * @param message
     */
    public EntityDaoException(final String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public EntityDaoException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public EntityDaoException(final Throwable cause) {
        super(cause);
    }
}
