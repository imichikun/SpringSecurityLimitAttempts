package loginAttempts.repository;

import loginAttempts.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByUsername(String username);

    @Query("UPDATE Person p SET p.loginAttempt = ?1 WHERE p.username = ?2")
    @Modifying
    void updatePersonLoginAttempts(int attempt, String username);
}