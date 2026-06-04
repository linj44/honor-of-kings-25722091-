package service;

import model.Admin;
import model.Person;
import model.Player;
import model.Role;

import java.util.Optional;

public class AuthenticationService {
    private Person currentUser;

    public Optional<Person> login(GameDataManager manager, String username, String password) {
        Optional<Person> userOptional = manager.findUser(username);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        Person user = userOptional.get();
        if (!user.authenticate(password)) {
            return Optional.empty();
        }
        currentUser = user;
        return Optional.of(user);
    }

    public void logout() {
        currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public Person getCurrentUser() {
        return currentUser;
    }

    public boolean isAdmin() {
        return currentUser instanceof Admin || (currentUser != null && currentUser.getRole() == Role.ADMIN);
    }

    public boolean isPlayer() {
        return currentUser instanceof Player;
    }

    public Player getCurrentPlayer() {
        return currentUser instanceof Player ? (Player) currentUser : null;
    }
}
