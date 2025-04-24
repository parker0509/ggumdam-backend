package com.example.user_service.service;

import com.example.user_service.domain.User;
import com.example.user_service.dto.RegisterRequest;
import com.example.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    //create ( 만들기 )
    //@Transactional  createUser Illegal 롤백
    @Transactional
    public User createUser(RegisterRequest registerRequest) {

        userRepository.findByEmail(registerRequest.getEmail())
                .ifPresent(user->{
                    throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
                });

        User newUser = new User();

        newUser.setEmail(registerRequest.getEmail());
        newUser.setUsername(registerRequest.getName());
        newUser.setPhone(registerRequest.getPhone());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        return userRepository.save(newUser);
    }

    //Read ( 읽기 )
    public List<User> readUser(User userId) {

        return userRepository.findAll();
    }

    //Update ( 갱신 )
    public void updateUser(Long id, User userDetails) {

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPhone(userDetails.getPhone());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

    }

    //Delete (삭제)
    public void deleteUser(Long id) {

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {

            User user = userOpt.get();
            userRepository.delete(user);

        } else {

            //유저가 없을 경우 예외 처리
            throw new RuntimeException("User not found with id : " + id);

        }
    }

}

