package by.epam.entity;

import by.epam.processor.annotation.Column;
import by.epam.processor.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Role implements BaseEntity {

    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "role_name")
    private String roleName;


    private List<User> users = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(roleName, role.roleName) &&
                Objects.equals(users, role.users);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, roleName, users);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", users=" + users +
                '}';
    }
}
