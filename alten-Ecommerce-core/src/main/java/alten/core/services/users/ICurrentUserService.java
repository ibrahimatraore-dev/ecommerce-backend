package alten.core.services.users;

import alten.core.dtos.user.UserDTO;

/**
 * Interface of user service.
 */
public interface ICurrentUserService {

    /**
     * Get current user
     *
     * @return current user.
     */
    UserDTO getCurrentUser();


}
