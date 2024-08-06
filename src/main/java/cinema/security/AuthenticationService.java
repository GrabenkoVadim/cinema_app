package cinema.security;

import cinema.exception.RegistrationException;
import cinema.model.User;
import cinema.exception.AuthenticationException;

public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationException;

    User register(String email, String password) throws RegistrationException;
}
