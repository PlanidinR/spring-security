package x5.school.springsecurity.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(
            name = "role_permission",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
    private List<Permission> permissions;

    public String getName() {
        return name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }
}
