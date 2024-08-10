package com.stock.control.app.security;

import com.stock.control.app.configuration.BasicSecurityConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BasicSecurityConfiguration.class)
@WebAppConfiguration
public class CsrfSecurityTest {
    @Value("spring.security.user.name")
    private String username;
    @Value("spring.security.user.password")
    private String password;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext applicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void loginWhenValidCsrfTokenAndInvalidCredentialsThenLoginError() throws Exception {
        this.mockMvc.perform(post("/login").with(csrf().asHeader()).accept(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", this.username)
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(HttpHeaders.LOCATION, "/login?error"));
    }

    @Test
    public void loginWhenInvalidCsrfTokenThenForbidden() throws Exception {
        this.mockMvc.perform(post("/login").with(csrf().useInvalidToken())
                        .accept(MediaType.TEXT_HTML)
                        .param("username", this.username)
                        .param("password", this.password))
                .andExpect(status().isForbidden());
    }

    @Test
    public void loginWhenMissingCsrfTokenThenForbidden() throws Exception {
        this.mockMvc.perform(post("/login")
                        .accept(MediaType.TEXT_HTML)
                        .param("username", this.username)
                        .param("password", this.password))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void logoutWhenValidCsrfTokenThenSuccess() throws Exception {
        this.mockMvc.perform(post("/logout").with(csrf())
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(HttpHeaders.LOCATION, "/login?logout"));
    }
}
