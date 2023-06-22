package co.empathy.academy.gametracker.services;

import co.empathy.academy.gametracker.models.User;
import co.empathy.academy.gametracker.repositories.UserRepository;
import co.empathy.academy.gametracker.utils.JWTUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public User registerUser(String username, String email, String password) {
        // Check if a user with the given username or email already exists
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }

        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists.");
        }

        // Create a new user object
        User user = new User(username, email, password, "USER");

        // Save the user to the repository
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public String loginUser(String username, String password) {
        // Find the user by username
        User user = userRepository.findByUsername(username);

        // Check if the user exists and the provided password matches the stored encoded password
        if (user != null && password.equals(user.getPassword())) {

            UserDetails userDetails = loadUserByUsername(username);
            // Generate and return a JWT token
            String token = jwtUtils.generateToken(userDetails);
            
            return token;
        }

        // Return null if authentication fails
        return null;
    }
}
