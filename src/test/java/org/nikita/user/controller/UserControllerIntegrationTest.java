package org.nikita.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql("/sql/users.sql")
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void updateUserByIdTest() throws Exception {
        String content = "{\"firstName\": \"testUser\", \"email\": \"test@gmail.com\", " +
                "\"lastName\" : \"surname\"}";

        mockMvc.perform(patch("/v1/users/%d".formatted(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@gmail.com"));
    }

    @Test
    void deleteUserByIdTest() throws Exception {
        mockMvc.perform(delete("/v1/users/%d".formatted(1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}








