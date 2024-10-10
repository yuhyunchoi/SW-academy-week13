package com.nhnacademy.daily.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RedisTemplate<String, UserDetails> redisTemplate;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAdminAccess() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MEMBER")
    public void testMemberAccessToPrivateProject() throws Exception {
        mockMvc.perform(get("/private-project"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "GOOGLE_USER")
    public void testGoogleUserAccessToPrivateProject() throws Exception {
        mockMvc.perform(get("/private-project"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUnauthorizedAccessToAdmin() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testPublicAccess() throws Exception {
        mockMvc.perform(get("/public-project"))
                .andExpect(status().isOk());
    }

    @Test
    public void testOAuth2LoginSuccess() throws Exception {
        // Mocking RedisTemplate interaction
        UserDetails mockUser = org.springframework.security.core.userdetails.User
                .withUsername("user@example.com")
                .password("")
                .authorities(Collections.singletonList(() -> "ROLE_GOOGLE_USER"))
                .build();

        Mockito.when(redisTemplate.opsForValue().get("user@example.com")).thenReturn(mockUser);

        mockMvc.perform(get("/private-project")
                        .with(SecurityMockMvcRequestPostProcessors.user(mockUser)))
                .andExpect(status().isOk());
    }
}
