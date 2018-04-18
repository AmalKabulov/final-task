package by.epam.processor.parser;

import by.epam.entity.BaseEntity;
import by.epam.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ResultSetParser {

    BaseEntity parse(final ResultSet resultSet/*, final String tableAlias*/) throws SQLException;

    List<? extends BaseEntity> complexParse(final ResultSet resultSet) throws SQLException;
}
