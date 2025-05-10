package subscriptions.subscriptionService.exceptions.models;

import subscriptions.subscriptionService.exceptions.models.core.EntityNotFoundException;

public class SubTypeNotFoundException extends EntityNotFoundException {
    public SubTypeNotFoundException(String message) {
        super(message);
    }
}
