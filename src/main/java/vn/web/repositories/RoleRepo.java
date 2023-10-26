package vn.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.web.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
