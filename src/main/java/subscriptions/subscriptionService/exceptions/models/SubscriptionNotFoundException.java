package subscriptions.subscriptionService.exceptions.models;

import subscriptions.subscriptionService.exceptions.models.core.EntityNotFoundException;

public class SubscriptionNotFoundException extends EntityNotFoundException {


    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
