package com.example.user_service.service;

import com.example.user_service.domain.User;
import com.example.user_service.dto.ChangePasswordRequest;
import com.example.user_service.dto.RegisterRequest;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

/*
   전체 읽기 기능 >> 불필요
   //Read ( 읽기 )
    public List<User> readUser(User userId) {

        return userRepository.findAll();
    }

    */

    //Update ( 갱신 )
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

    public UserResponseDto verifyUserCredentials(String email, String password) {
        // 이메일로 사용자 찾기
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        // 사용자 정보를 응답 객체로 반환
        return new UserResponseDto(user.getEmail(), user.getUsername());
    }

}

