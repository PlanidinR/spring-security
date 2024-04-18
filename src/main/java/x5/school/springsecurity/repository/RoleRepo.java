package x5.school.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import x5.school.springsecurity.entity.Role;

import java.util.UUID;

@Repository
public interface RoleRepo extends JpaRepository<Role, UUID> {
}
