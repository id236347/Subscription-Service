package subscriptions.subscriptionService.exceptions.models;

import subscriptions.subscriptionService.exceptions.models.core.EntityNotFoundException;

public class RoleNotFoundException extends EntityNotFoundException {

    public RoleNotFoundException(String message) {
        super(message);
    }
}
