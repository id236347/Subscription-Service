package subscriptions.subscriptionService.exceptions.models;

import subscriptions.subscriptionService.exceptions.models.core.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
