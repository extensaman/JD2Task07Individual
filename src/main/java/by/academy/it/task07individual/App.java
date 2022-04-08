package by.academy.it.task07individual;

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
 *
 * - select
 * - update
 * - delete
 * - insert
 *
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

public class App
{
    public static void main( String[] args )
    {

    }
}
