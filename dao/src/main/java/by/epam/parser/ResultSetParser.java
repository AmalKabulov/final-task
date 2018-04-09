package by.epam.parser;

import by.epam.entity.BaseEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetParser {

    BaseEntity parse(final ResultSet resultSet) throws SQLException;
}
