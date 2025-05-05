package com.example.auth_service;

import com.example.auth_service.dto.UserResponse;
import com.example.auth_service.feign.UserClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserClientIntegrationTest  {

    @Autowired
    private UserClient userClient; // Feign Test

    @Test
    public void testUserClient(){

        String testEmail  = "qwer@test.com";
        UserResponse response  = userClient.getUserByEmail(testEmail);

        // then
        assertNotNull(response);
        assertEquals(testEmail, response.getEmail());
        System.out.println("response = " + response);
        System.out.println("testEmail = " + testEmail);

    }


}
