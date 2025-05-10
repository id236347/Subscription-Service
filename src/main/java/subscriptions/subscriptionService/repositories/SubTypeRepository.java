package subscriptions.subscriptionService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import subscriptions.subscriptionService.models.SubType;

import java.util.Optional;

@Repository
public interface SubTypeRepository extends JpaRepository<SubType, Integer> {
    Optional<SubType> findByName(String name);
}
