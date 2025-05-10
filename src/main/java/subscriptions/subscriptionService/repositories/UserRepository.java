package subscriptions.subscriptionService.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import subscriptions.subscriptionService.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("Select u from User u left join fetch u.roles where u.login=:login")
    Optional<User> findByLogin(@Param("login") String username);

}
