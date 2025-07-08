package com.example.user_service.service;

import com.example.user_service.domain.SubscriptionLevel;
import com.example.user_service.domain.User;
import com.example.user_service.dto.ChangePasswordRequest;
import com.example.user_service.dto.RegisterRequest;
import com.example.user_service.dto.UserInfoDto;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPhone(registerRequest.getPhone());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setSubscriptionLevel(SubscriptionLevel.LEVEL_1);

        return userRepository.save(newUser);
    }
    // 이메일로 조회
    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    // 유저 Username으로 조회
    public Optional<User> getUserByname(String username) {
        return userRepository.findByUsername(username);
    }

    // 비밀번호 변경 기능
    //( ID 재확인, 비밀번호 재확인)
    @Transactional
    public void changePassword(String token, ChangePasswordRequest request) {

        String userEmail = request.getEmail();
        System.out.println("요청된 이메일: " + userEmail);

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        if(passwordEncoder.matches(request.getNewPassword(), user.getPassword())){
            throw new IllegalArgumentException("현재 비밀번호와 같습니다.");
        }
        String encodeNewPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(encodeNewPassword);

        userRepository.save(user);
    }


    public void updateUser(Long id, User userDetails) {

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPhone(userDetails.getPhone());
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));

            userRepository.save(user); // 실제 저장 누락된 경우 대비
        }

    }

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

    public UserResponseDto verifyUserCredentials(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        return new UserResponseDto(user.getEmail(), user.getUsername());
    }

    public Optional<User> getUserById(Long id) {

        return userRepository.findById(id);

    }

    public void createOrUpdateFromOAuth(UserResponseDto dto) {
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(dto.getUsername());
            userRepository.save(user);
        } else {
            User newUser = User.builder()
                    .email(dto.getEmail())
                    .username(dto.getUsername())
                    .password("OAUTH2_USER")
                    .subscriptionLevel(SubscriptionLevel.LEVEL_1)
                    .build();
            userRepository.save(newUser);
        }
    }

/*    // UserService.java
    public void createOrUpdateFromOAuth(UserInfoDto dto) {
        System.out.println(">>> createOrUpdateFromOAuth() 진입");
        System.out.println(">>> 이메일 = " + dto.getEmail());

        // Kafka 메시지가 중복 수신되는 문제 방지용: 이메일 기준 중복 체크
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
        System.out.println(">>> 사용자 존재 여부: " + optionalUser.isPresent());

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setUsername(dto.getNickname());
            System.out.println("기존에 있던 정보 existingUser = " + existingUser);
            userRepository.save(existingUser);
        } else {
            User newUser = User.builder()
                    .email(dto.getEmail())
                    .username(dto.getNickname())
                    .password("OAUTH2_USER") // OAuth 유저는 비밀번호를 사용하지 않기 때문에 고정된 더미 값 저장
                    .subscriptionLevel(SubscriptionLevel.LEVEL_1)
                    .build();
            System.out.println("새로운 회원이 가입 됐습니다. = " + newUser);
            userRepository.save(newUser);
        }
    }*/

}

