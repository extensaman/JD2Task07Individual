package by.academy.it.task07individual;

import by.academy.it.task07individual.dao.EntityDao;
import by.academy.it.task07individual.dao.EntityDaoException;
import by.academy.it.task07individual.dao.EntityDaoProvider;
import by.academy.it.task07individual.entity.Car;

import java.util.Map;

/**
 * There is a Car entity, it has
 * - identifier;
 * - model
 * - color
 * - price
 *
 * <p>The task is to create a project. Adding a table to the database must be
 * done through liquibase,
 * make tests using H2.</p>
 * Cover functionality with tests and make a report using the jacoco plugin.
 * Connect checkstyle to the project.
 * For the Car entity, make a DAO over each field, write your own annotation
 * MyColumn(name - the name of the column) with the name of the column,
 * write the annotation MyTable(name - the name of the table)
 * above the Car class.
 * Implement CRUD operations on Car using jdbc
 * <p>
 * - select
 * - update
 * - delete
 * - insert
 * <p>
 * Moreover, these operations should make a request to the database
 * using the annotations
 * MyColumn and MyTable (through reflection). i.e. if the user of
 * this API creates another entity, then
 * - select
 * - update
 * - delete
 * - insert
 * <p>should work without changing the internal logic</p>
 *
 * @author Yusikau Aliaksandr
 * @version 1.0
 */

public final class App {
    private App() {
    }

    /**
     *  Resource bundle fo production address.
     */
    public static final String DATABASE_PROD = "databaseProd";

    /**
     * @param args
     * @throws EntityDaoException
     */
    public static void main(final String[] args) throws EntityDaoException {
        EntityDao dao =
                EntityDaoProvider.getNewInstance(Car.class, DATABASE_PROD);
        printMap("DB consists of :", dao.findAll());
    }

    /**
     * @param message
     * @param map
     */
    private static void printMap(final String message,
                                 final Map<Long, Object> map) {
        System.out.println(message);
        for (Map.Entry<Long, Object> longObjectEntry : map.entrySet()) {
            System.out.println("ID = " + longObjectEntry.getKey());
            System.out.println(longObjectEntry.getValue());
        }
    }
}
