package com.example.Ex30;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepository) {
        this.userRepo = userRepository;
    }

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User updateUser(Long id, User updatedUser) {
        updatedUser.setId(id);
        return userRepo.save(updatedUser);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
