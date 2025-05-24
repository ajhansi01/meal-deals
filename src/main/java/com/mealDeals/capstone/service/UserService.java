package com.mealDeals.capstone.service;

import com.mealDeals.capstone.model.Role;
import com.mealDeals.capstone.model.User;
import com.mealDeals.capstone.repository.UserRepository;
import com.mealDeals.capstone.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public boolean isEmailUnique(String email) {
        return !userRepository.findByEmail(email).isPresent();
    }

    public User registerUser(String name, String email, String password, Role role) {
        if (!isEmailUnique(email)) {
            throw new IllegalArgumentException("Email is already in use");
        }

        String hashedPassword = PasswordUtil.hashPassword(password);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.getRoles().add(role);

        return userRepository.save(user);
    }

    public boolean validatePassword(String rawPassword, String hashedPassword) {
        return PasswordUtil.checkPassword(rawPassword, hashedPassword);
    }
}
