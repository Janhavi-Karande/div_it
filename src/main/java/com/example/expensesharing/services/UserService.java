package com.example.expensesharing.services;

import com.example.expensesharing.exceptions.UserNotFoundException;
import com.example.expensesharing.models.Group;
import com.example.expensesharing.models.User;
import com.example.expensesharing.repositories.GroupRepository;
import com.example.expensesharing.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final GroupRepository groupRepository;
    private UserRepository userRepository;

    public UserService(UserRepository userRepository, GroupRepository groupRepository) {

        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public User signup(String name, String email, String password, String phone) {

        // check if user exist
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()) {
            // if exist ask them to login
            try {
                login(email, password);
            }
            catch (UserNotFoundException e) {
                System.out.println("User not found");
            }
        }

        // if, user does not exist encode the password
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // check if phone number is 10 digits
        if(phone.length() != 10) {
            System.out.println("Phone number length should be 10\n Try to signup again.");
            signup(name, email, password, phone);
        }

        // check if password has at least a capital letter, a symbol, and a digit
        if(!isPasswordStrong(password)) {
            System.out.println("Password not strong enough. Try including:");
            System.out.println("- At least 8 characters");
            System.out.println("- At least one uppercase letter");
            System.out.println("- At least one lowercase letter");
            System.out.println("- At least one number");
            System.out.println("- At least one special character");
            signup(name, email, password, phone);
        }

        //check if email is valid
        if(!isEmailValid(email)) {
            System.out.println("Given email is not valid. Enter a valid email address");
            signup(name, email, password, phone);
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhone(phone);

        // save it in database
        return userRepository.save(user);
    }

    public User login(String email, String password) throws UserNotFoundException {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with email " +email+ " does not exist!!");
        }

        User user = optionalUser.get();

        // verify the password
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        throw new RuntimeException("Wrong password");
    }

    @Transactional
    public User updateUserName(String email, String userName) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with " +email+ " does not exists!");
        }
        User user = optionalUser.get();
        user.setName(userName);

        return userRepository.save(user);
    }

    @Transactional
    public User updateUserPassword(String email, String password) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with " +email+ " does not exists!");
        }
        User user = optionalUser.get();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    @Transactional
    public User updateUserPhoneNumber(String email, String phone) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with " +email+ " does not exists!");
        }

        User user = optionalUser.get();
        user.setPhone(phone);

        return userRepository.save(user);
    }

    @Transactional
    public User updateUserEmail(String email) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with " +email+ " does not exists!");
        }

        User user = optionalUser.get();
        user.setEmail(email);

        return userRepository.save(user);
    }

    public boolean isEmailValid(String email) {
        String emailRegex = "^[\\w-\\.]+@[\\w-]+(\\.[\\w-]+)+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isPasswordStrong(String password) {
        if (password.length() < 8)
            return false;

        String upperCasePattern = ".*[A-Z].*";
        String lowerCasePattern = ".*[a-z].*";
        String digitPattern = ".*\\d.*";
        String specialCharPattern = ".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*";

        return password.matches(upperCasePattern) &&
                password.matches(lowerCasePattern) &&
                password.matches(digitPattern) &&
                password.matches(specialCharPattern);
    }

    public User getUser(String createdByUserEmail) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(createdByUserEmail);

        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with " +createdByUserEmail+ " does not exists!");
        }

        User user = optionalUser.get();
        return user;
    }


}
