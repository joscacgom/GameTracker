package co.empathy.academy.gametracker.services;

import co.empathy.academy.gametracker.models.User;
import co.empathy.academy.gametracker.repositories.UserRepository;
import co.empathy.academy.gametracker.utils.JWTUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private JWTUtils jwtUtils;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public UserService(UserRepository userRepository, JWTUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Register a user.
     *
     * @param username String
     * @param email String
     * @param password String
     * @return a User registered
     */
    public User registerUser(String username, String email, String password) {
        // Check if a user with the given username or email already exists
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }

        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists.");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Encode the password
        String encodedPassword = passwordEncoder.encode(password);

        // Create a new user object with the encoded password
        User user = new User(username, email, encodedPassword, "USER");

        // Save the user to the repository
        return userRepository.save(user);
    }

    /**
     * Load user by its username
     * @param username String
     * @return the UserDetails object.
     * @throws UsernameNotFoundException
     */
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

    /**
     * User login
     * @param username
     * @param password
     * @return token or null if the authentication fails.
     */
    public String loginUser(String username, String password) {
        // Find the user by username
        User user = userRepository.findByUsername(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Check if the user exists and the provided password matches the stored encoded password
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {

            UserDetails userDetails = loadUserByUsername(username);
            // Generate and return a JWT token
            String token = jwtUtils.generateToken(userDetails);

            return token;
        }

        // Return null if authentication fails
        return null;
    }

    /**
     * Update the user's email and username.
     *
     * @param username The username of the user to update.
     * @param email    The new email to set.
     * @return The updated user.
     * @throws Exception If the user is not found or the update fails.
     */
    public User updateUser(String currentUsername, String username, String email) throws Exception {
        // Find the user by the username
        User user = userRepository.findByUsername(currentUsername);

        if (user == null) {
            throw new Exception("User not found.");
        }

        // Update the email and username
        user.setEmail(email);
        user.setUsername(username);

        // Save the updated user
        User updatedUser = userRepository.save(user);

        return updatedUser;
    }

    /**
     * Delete a user by username.
     *
     * @param currentUsername The username of the user to delete.
     * @throws Exception If the user is not found or the delete fails.
     */
    public void changePassword(String currentUsername, String newPassword) throws Exception {
        User user = userRepository.findByUsername(currentUsername);

        if (user == null) {
            throw new Exception("User not found.");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        // Encode the new password
        String encodedPassword = passwordEncoder.encode(newPassword);

        // Update the user's password
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    /**
     * Get a user by username.
     *
     * @param username The username of the user to get.
     * @throws Exception If the user is not found.
     */
    public String getUserId(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new IllegalArgumentException("User not found.");
        }
        return user.getId();
    }

    /**
     * Get a user by username.
     *
     * @param username The username of the user to get.
     * @throws Exception If the user is not found.
     */
    public String getUserEmail(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new IllegalArgumentException("User not found.");
        }
        return user.getEmail();
    }

    /**
     * Get a user by username.
     *
     * @param username The username of the user to get.
     * @return user found by its username.
     * @throws Exception If the user is not found.
     */
    public User getUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }
        return user;
    }

}
