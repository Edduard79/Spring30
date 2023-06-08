package com.example.Ex30;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testCreateUser() throws Exception {
        // Arrange
        User user = new User();
        user.setName("Marco");
        user.setSurname("Verdi");

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);

        // Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Marco\",\"surname\":\"Verdi\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Marco"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Verdi"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Arrange
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "Marco", "Rossi"));
        userList.add(new User(2L, "Marta", "Bianchi"));

        Mockito.when(userService.getAllUsers()).thenReturn(userList);

        // Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Marco"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].surname").value("Rossi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Marta"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].surname").value("Bianchi"));
    }

    @Test
    public void testGetUserById() throws Exception {
        // Arrange
        User user = new User(1L, "Marco", "Rossi");

        Mockito.when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        // Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Marco"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Rossi"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Arrange
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName("Marta");
        updatedUser.setSurname("Bianchi");

        User existingUser = new User(userId, "Marco", "Rossi");

        Mockito.when(userService.updateUser(Mockito.any(Long.class), Mockito.any(User.class))).thenReturn(updatedUser);
        Mockito.when(userService.getUserById(userId)).thenReturn(Optional.of(existingUser));

        // Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Marta\",\"surname\":\"Bianchi\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Marta"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Bianchi"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Arrange
        Long userId = 1L;

        // Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
