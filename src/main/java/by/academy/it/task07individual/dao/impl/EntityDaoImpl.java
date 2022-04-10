package by.academy.it.task07individual.dao.impl;

import by.academy.it.task07individual.dao.ConnectionPool;
import by.academy.it.task07individual.dao.EntityDao;
import by.academy.it.task07individual.dao.EntityDaoException;
import by.academy.it.task07individual.entity.MyColumn;
import by.academy.it.task07individual.entity.MyTable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
public class EntityDaoImpl implements EntityDao {
    /**
     *
     */
    private static final String SELECT_STATEMENT = "SELECT * FROM %s";
    /**
     *
     */
    private static final String SELECT_BY_ID_STATEMENT =
                            "SELECT * FROM %s WHERE id = %d";
    /**
     *
     */
    private static final String DELETE_STATEMENT =
                            "DELETE FROM %s WHERE id = %d";
    /**
     *
     */
    private static final String INSERT_STATEMENT =
                            "INSERT INTO %s (%s) VALUES (%s)";
    /**
     *
     */
    private static final String UPDATE_STATEMENT =
                            "UPDATE %s SET %s WHERE id = %d";
    /**
     *
     */
    private static final String SINGLE_QUOTE_SIGN = "'";
    /**
     *
     */
    private static final String COMMA_SIGN = ",";
    /**
     *
     */
    private static final String EQUAL_SIGN = "=";
    /**
     *
     */
    public static final int SHIFT_TO_COLUMN = 2;
    /**
     *
     */
    public static final int ID_COLUMN = 1;
    /**
     *
     */
    private final String tableName;
    /**
     *
     */
    private final String[] tableColumnNames;
    /**
     *
     */
    private final String[] classFieldNames;
    /**
     *
     */
    private final Constructor constructor;
    /**
     *
     */
    private final ConnectionPool connectionPool;

    /**
     * @param aClass
     * @param pool
     * @throws EntityDaoException
     */
    public EntityDaoImpl(final Class aClass,
                         final ConnectionPool pool)
                                    throws EntityDaoException {

        Optional.ofNullable(aClass).orElseThrow(EntityDaoException::new);
        Optional.ofNullable(pool).orElseThrow(EntityDaoException::new);

        this.connectionPool = pool;

        // check class for ability to persistence
        if (aClass.isAnnotationPresent(MyTable.class)) {
            tableName = ((MyTable) aClass.getAnnotation(MyTable.class)).name();
        } else {
            throw new EntityDaoException("No 'MyTable' annotation");
        }

        // looking for annotated fields
        Field[] fields = aClass.getDeclaredFields();
        int fieldCount = 0;
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(MyColumn.class)) {
                fieldCount++;
            }
            field.setAccessible(false);
        }

        tableColumnNames = new String[fieldCount];
        classFieldNames = new String[fieldCount];
        Class[] classFieldTypes = new Class[fieldCount];
        int index = 0;
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(MyColumn.class)) {
                tableColumnNames[index] =
                        ((MyColumn) field.getAnnotation(MyColumn.class))
                                .name();
                classFieldTypes[index] = field.getType();
                classFieldNames[index++] = field.getName();
            }
            field.setAccessible(false);
        }

        // get constructor for ability of creation new instances
        // of persistent class
        try {
            constructor = aClass.getConstructor(classFieldTypes);
        } catch (NoSuchMethodException e) {
            throw new EntityDaoException(e);
        }
    }

    /**
     * Create new instance of persistent class.
     *
     * @param params - values of class fields
     * @return new instance of persistent class
     * @throws EntityDaoException
     */
    private Object createEntity(final Object[] params)
                                throws EntityDaoException {
        Object entity = null;
        try {
            entity = constructor.newInstance(params);
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException e) {
            throw new EntityDaoException(e);
        }
        return entity;
    }

    /**
     * @param id
     * @return -
     * @throws EntityDaoException
     */
    @Override
    public Optional<Object> findById(final Long id) throws EntityDaoException {
        Optional.ofNullable(id).orElseThrow(EntityDaoException::new);
        Optional<Object> result = Optional.empty();
        try (
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = // SELECT * FROM %s WHERE id = %d
                        statement.executeQuery(
                                String.format(SELECT_BY_ID_STATEMENT,
                                                tableName,
                                                id));
        ) {
            Object[] paramsForCreation = new Object[classFieldNames.length];
            if (resultSet.next()) {
                for (int i = 0; i < tableColumnNames.length; i++) {
                    paramsForCreation[i] =
                            resultSet.getObject(i + SHIFT_TO_COLUMN);
                }
                result = Optional.ofNullable(createEntity(paramsForCreation));
            }
        } catch (SQLException e) {
            throw new EntityDaoException(e);
        }
        return result;
    }

    /**
     * @return -
     * @throws EntityDaoException
     */
    @Override
    public Map<Long, Object> findAll() throws EntityDaoException {
        Map<Long, Object> map = new HashMap<>();
        try (
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet =
                        statement.executeQuery(// SELECT * FROM %s
                                String.format(SELECT_STATEMENT, tableName));
        ) {
            Object[] paramsForCreation = new Object[classFieldNames.length];
            while (resultSet.next()) {
                for (int i = 0; i < tableColumnNames.length; i++) {
                    paramsForCreation[i] =
                            resultSet.getObject(i + SHIFT_TO_COLUMN);
                }
                map.put(resultSet.getLong(ID_COLUMN),
                        createEntity(paramsForCreation));
            }
        } catch (SQLException e) {
            throw new EntityDaoException(e);
        }
        return map;
    }

    /**
     * @param id
     * @param entity
     * @throws EntityDaoException
     */
    @Override
    public void update(final Long id,
                       final Object entity)
                            throws EntityDaoException {
        Optional.ofNullable(id).orElseThrow(EntityDaoException::new);
        Optional.ofNullable(entity).orElseThrow(EntityDaoException::new);

        int updatedCount = 0;
        String[] params = getObjectParam(entity);

        try (
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
        ) {
            updatedCount =
                    statement.executeUpdate(// UPDATE %s SET %s WHERE id = %d
                            String.format(UPDATE_STATEMENT,
                                    tableName,
                                    IntStream.range(0, tableColumnNames.length)
                                            .mapToObj(i -> tableColumnNames[i]
                                                    .concat(EQUAL_SIGN)
                                                    .concat(params[i]))
                                            .collect(Collectors
                                                    .joining(COMMA_SIGN)),
                                    id
                            ));
        } catch (SQLException e) {
            throw new EntityDaoException(e);
        }

        if (updatedCount != 1) {
            throw new EntityDaoException(
                    "Updating transaction failed (updatedCount != 1)");
        }
    }

    /**
     * @param id
     * @throws EntityDaoException
     */
    @Override
    public void delete(final Long id) throws EntityDaoException {
        Optional.ofNullable(id).orElseThrow(EntityDaoException::new);
        int deletedCount = 0;
        try (
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
        ) {
            deletedCount =
                    statement.executeUpdate(// DELETE FROM %s WHERE id = %d
                            String.format(DELETE_STATEMENT,
                                    tableName,
                                    id));
        } catch (SQLException e) {
            throw new EntityDaoException(e);
        }
        if (deletedCount != 1) {
            throw new EntityDaoException(
                    "Deletion transaction failed (deletedCount != 1)");
        }
    }

    /**
     * @param entity
     * @throws EntityDaoException
     */
    @Override
    public void insert(final Object entity) throws EntityDaoException {
        Optional.ofNullable(entity).orElseThrow(EntityDaoException::new);
        int insertedCount = 0;
        String[] params = getObjectParam(entity);

        try (
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
        ) {
            insertedCount =
                    statement.executeUpdate(//INSERT INTO %s (%s) VALUES (%s)
                            String.format(INSERT_STATEMENT,
                                    tableName,
                                    String.join(COMMA_SIGN, tableColumnNames),
                                    String.join(COMMA_SIGN, params)
                            ));

        } catch (SQLException e) {
            throw new EntityDaoException(e);
        }
        if (insertedCount != 1) {
            throw new EntityDaoException(
                    "Insertion transaction failed (insertedCount != 1)");
        }
    }

    /**
     * @param entity
     * @return -
     * @throws EntityDaoException
     */
    private String[] getObjectParam(final Object entity)
                                throws EntityDaoException {
        String[] param = new String[classFieldNames.length];
        try {
            for (int i = 0; i < classFieldNames.length; i++) {
                Field field = entity.getClass().
                            getDeclaredField(classFieldNames[i]);
                field.setAccessible(true);
                Object fieldValue = field.get(entity);
                if (fieldValue instanceof CharSequence) {
                    fieldValue = SINGLE_QUOTE_SIGN
                            .concat(((CharSequence) fieldValue)
                                    .toString())
                            .concat(SINGLE_QUOTE_SIGN);
                }
                param[i] = fieldValue.toString();
                field.setAccessible(false);
            }
        } catch (NoSuchFieldException
                    | IllegalAccessException e) {
            throw new EntityDaoException(e);
        }
        return param;
    }
}

