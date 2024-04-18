package x5.school.springsecurity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column
    private String name;

    public String getName() {
        return name;
    }
}
