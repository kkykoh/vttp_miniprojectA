package vttp.ssf.projectA.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.ssf.projectA.Model.User;
import vttp.ssf.projectA.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // public void registerUser(String username, String password) {
    //     if (userRepository.findByUsername(username) != null) {
    //         throw new IllegalArgumentException("Username already exists");
    //     }

    //     User user = new User();
    //     user.setUsername(username);
    //     user.setPassword(password);

    //     userRepository.save(user);
    // }

    // public boolean authenticateUser(String username, String password) {
    //     User user = userRepository.findByUsername(username);
    //     if (user == null) {
    //         return false; // User not found
    //     }
    //     return user.getPassword().equals(password); // Plain text comparison
    // }
    
    public void registerUser(String username, String password) {
        if (userRepository.findPasswordByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        userRepository.save(username, password);
    }

    public boolean authenticateUser(String username, String password) {
        String storedPassword = userRepository.findPasswordByUsername(username);

        if (storedPassword == null) {
            return false;
        }

        return storedPassword.equals(password);
    }
}
