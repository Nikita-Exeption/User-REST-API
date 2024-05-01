package org.nikita.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllUsersWithoutDateParametersTest() throws Exception {
        mockMvc.perform(get("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUsersWithDateParametersTest() throws Exception {
        LocalDate fromDate = LocalDate.of(2002, 1, 1);
        LocalDate toDate = LocalDate.of(2002, 1, 31);

        mockMvc.perform(get("/v1/users")
                        .param("from", fromDate.toString())
                        .param("to", toDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createNewUserTest() throws Exception {
        String newUserJson = "{\"firstName\": \"testUser\", \"email\": \"test@example.com\", " +
                "\"lastName\" : \"surname\", \"birthday\" : \"2002-02-02\"}";

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.firstName").value("testUser"));
    }
}