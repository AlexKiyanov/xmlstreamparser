package ru.kiianov.xmlstreamparser.dao.impl;

import ru.kiianov.xmlstreamparser.dao.DBConnector;
import ru.kiianov.xmlstreamparser.dao.entity.CompanyEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyDaoImpl extends AbstractCrudDaoImpl<CompanyEntity> {
    private static final String SAVE_QUERY =
            "INSERT INTO companies (company_id, " +
                    "name, " +
                    "company_domain, " +
                    "yearFounded, " +
                    "industry, " +
                    "sizeRange, " +
                    "locality, " +
                    "country, " +
                    "linkedinUrl, " +
                    "currentEmployeeEstimate, " +
                    "totalEmployeeEstimate) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM companies WHERE company_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM companies";
    private static final String UPDATE_QUERY = "UNSUPPORTED";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM companies WHERE company_id = ?";
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM companies WHERE name = ?";

    public CompanyDaoImpl(DBConnector connector) {
        super(connector,
                SAVE_QUERY,
                FIND_BY_NAME_QUERY,
                FIND_BY_ID_QUERY,
                FIND_ALL_QUERY,
                UPDATE_QUERY,
                DELETE_BY_ID_QUERY);
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, CompanyEntity entity) throws SQLException {
        preparedStatement.setLong(1, entity.getId());
        preparedStatement.setString(2, entity.getName());
        preparedStatement.setString(3, entity.getDomain());
        preparedStatement.setString(4, entity.getYearFounded());
        preparedStatement.setString(5, entity.getIndustry());
        preparedStatement.setString(6, entity.getSizeRange());
        preparedStatement.setString(7, entity.getLocality());
        preparedStatement.setString(8, entity.getCountry());
        preparedStatement.setString(9, entity.getLinkedinUrl());
        preparedStatement.setLong(10, entity.getCurrentEmployeeEstimate());
        preparedStatement.setLong(11, entity.getTotalEmployeeEstimate());
    }

    @Override
    protected CompanyEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return CompanyEntity.builder()
                .withId(resultSet.getLong("company_id"))
                .withName(resultSet.getString("name"))
                .withDomain(resultSet.getString("company_domain"))
                .withYearFounded(resultSet.getString("yearFounded"))
                .withIndustry(resultSet.getString("industry"))
                .withSizeRange(resultSet.getString("sizeRange"))
                .withLocality(resultSet.getString("locality"))
                .withCountry(resultSet.getString("country"))
                .withLinkedinUrl(resultSet.getString("linkedinUrl"))
                .withCurrentEmployeeEstimate(resultSet.getLong("currentEmployeeEstimate"))
                .withTotalEmployeeEstimate(resultSet.getLong("totalEmployeeEstimate"))
                .build();
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, CompanyEntity entity) throws SQLException {
        // TODO update method is unsupported for this app version
    }
}
