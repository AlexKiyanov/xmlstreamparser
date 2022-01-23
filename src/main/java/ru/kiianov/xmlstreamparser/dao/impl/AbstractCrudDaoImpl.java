package ru.kiianov.xmlstreamparser.dao.impl;

import ru.kiianov.xmlstreamparser.dao.CrudDao;
import ru.kiianov.xmlstreamparser.dao.DBConnector;
import ru.kiianov.xmlstreamparser.dao.exception.DataBaseRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

public abstract class AbstractCrudDaoImpl<E> implements CrudDao<E, Long> {
    private static final BiConsumer<PreparedStatement, String> STRING_CONSUMER =
            (PreparedStatement pr, String s) -> {
                try {
                    pr.setString(1, s);
                } catch (SQLException e) {
                    throw new DataBaseRuntimeException(e);
                }
            };

    private static final BiConsumer<PreparedStatement, Long> LONG_CONSUMER =
            (PreparedStatement pr, Long param) -> {
                try {
                    pr.setLong(1, param);
                } catch (SQLException e) {
                    throw new DataBaseRuntimeException(e);
                }
            };

    private final DBConnector connector;
    private final String saveQuery;
    private final String findByStringParamQuery;
    private final String findByIdQuery;
    private final String findAllQuery;
    private final String updateQuery;
    private final String deleteByIdQuery;

    protected AbstractCrudDaoImpl(DBConnector connector,
                                  String saveQuery,
                                  String findByStringParamQuery,
                                  String findByIdQuery,
                                  String findAllQuery,
                                  String updateQuery,
                                  String deleteByIdQuery) {
        this.connector = connector;
        this.saveQuery = saveQuery;
        this.findByStringParamQuery = findByStringParamQuery;
        this.findByIdQuery = findByIdQuery;
        this.findAllQuery = findAllQuery;
        this.updateQuery = updateQuery;
        this.deleteByIdQuery = deleteByIdQuery;
    }

    @Override
    public void save(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveQuery)) {
            insert(preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Insertion is failed", e);
        }
    }

    public void save(List<E> entities) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveQuery)) {
            connection.setAutoCommit(false);
            for (E entity : entities) {
                insert(preparedStatement, entity);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Insertion is failed", e);
        }
    }

    @Override
    public Optional<E> findById(Long id) {
        return findByLongParam(id, findByIdQuery);
    }

    public Optional<E> findByStringParam(String param) {
        return findByStringParam(param, findByStringParamQuery);
    }

    public List<E> findAll() {
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()) {
            try (final ResultSet resultSet = statement.executeQuery(findAllQuery)) {
                List<E> entities = new ArrayList<>();
                while (resultSet.next()) {
                    entities.add(mapResultSetToEntity(resultSet));
                }
                return entities;
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public void update(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            updateValues(preparedStatement, entity);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public void deleteAllByIds(Set<Long> ids) {
        ids.forEach(this::deleteById);
    }

    protected abstract void insert(PreparedStatement preparedStatement, E entity) throws SQLException;

    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract void updateValues(PreparedStatement preparedStatement, E entity) throws SQLException;

    protected Optional<E> findByLongParam(Long id, String query) {
        return findByParam(id, query, LONG_CONSUMER);
    }

    protected Optional<E> findByStringParam(String param, String query) {
        return findByParam(param, query, STRING_CONSUMER);
    }

    private <P> Optional<E> findByParam(P param, String query, BiConsumer<PreparedStatement, P> consumer) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            consumer.accept(preparedStatement, param);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.ofNullable(mapResultSetToEntity(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }
}
