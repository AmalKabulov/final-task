package by.epam.dao;

import by.epam.dao.impl.RoleDao;
import by.epam.entity.Role;
import com.ititon.jdbc_orm.processor.exception.DefaultOrmException;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class RoleDaoTest extends BaseDaoTest{
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private RoleDao roleDao = daoFactory.getDao(RoleDao.class);


    @Test
    public void saveRole() throws DefaultOrmException {
        Role role = new Role();
        role.setRoleName("admin");

        Role savedRole = roleDao.save(role);

        assertThat(role, equalTo(savedRole));
    }
}
