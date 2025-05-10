package subscriptions.subscriptionService.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import subscriptions.subscriptionService.models.SubType;
import subscriptions.subscriptionService.models.Subscription;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    @Query("SELECT st FROM SubType st LEFT JOIN Subscription s ON s.type.id = st.id " +
            "GROUP BY st.id ORDER BY COUNT(s.id) DESC")
    List<SubType> findTopSubTypes(Pageable pageable);

}

