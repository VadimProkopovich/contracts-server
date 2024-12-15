package tests.lyceum.apitests.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tests.lyceum.apitests.models.User;

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);
}
