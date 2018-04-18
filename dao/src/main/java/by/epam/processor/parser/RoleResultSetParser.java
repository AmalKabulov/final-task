package by.epam.processor.parser;

import by.epam.entity.BaseEntity;
import by.epam.entity.Role;
import by.epam.entity.User;
import by.epam.processor.CacheProcessor;
import by.epam.processor.meta.EntityMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RoleResultSetParser implements ResultSetParser {
    private final EntityMeta ROLE_META = CacheProcessor.getInstance().getMeta(Role.class);

    private final String idColumn;
    private final String roleColumn;

    public RoleResultSetParser() {
        idColumn = ROLE_META.getIdColumnName();
        roleColumn = ROLE_META.getFieldMetas().get("roleName").getColumnName();
    }

    @Override
    public BaseEntity parse(ResultSet resultSet/*, String tableAlias*/) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getLong(idColumn));
        role.setRoleName(resultSet.getString(roleColumn));

        return role;
    }

    @Override
    public List<? extends BaseEntity> complexParse(ResultSet resultSet) throws SQLException {
        return null;
    }

}
