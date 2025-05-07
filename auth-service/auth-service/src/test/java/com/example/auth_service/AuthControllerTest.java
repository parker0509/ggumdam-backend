package com.example.auth_service;

import com.example.auth_service.controller.AuthController;
import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.LoginVerificationRequest;
import com.example.auth_service.dto.UserResponse;
import com.example.auth_service.feign.UserClient;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuthControllerTest {

    @Mock
    private UserClient userClient;

    @InjectMocks
    private AuthController authController;

    @Test
    void testVerifyLogin_Success() {
        // given
        String email = "qwer@example.com";
        String password = "qwer1234";
        LoginVerificationRequest request = new LoginVerificationRequest(email, password);

        // when
        UserResponse expectedResponse = new UserResponse("test@example.com", "Test User");
        Mockito.when(userClient.verifyLogin(request)).thenReturn(expectedResponse);

        System.out.println("expectedResponse = " + expectedResponse);

        // then
        ResponseEntity<UserResponse> response = authController.login();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testVerifyLogin_Failure() {
        // given
        String email = "test@example.com";
        String password = "wrongpassword";
        LoginVerificationRequest request = new LoginVerificationRequest(email, password);

        // when
        Mockito.when(userClient.verifyLogin(request)).thenThrow(new RuntimeException("Invalid credentials"));

        // then
        ResponseEntity<UserResponse> response = authController.verifyLogin(request);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
