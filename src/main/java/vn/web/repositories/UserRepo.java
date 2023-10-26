package vn.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.web.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {
}
