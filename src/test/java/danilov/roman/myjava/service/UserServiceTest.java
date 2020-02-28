package danilov.roman.myjava.service;

import danilov.roman.myjava.model.User;
import danilov.roman.myjava.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void addUserSuccessfully() {
        when(userRepository.findByEmail(any())).thenReturn(null);
        boolean isSave = userService.addUser(new User());
        assertTrue(isSave);
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(userRepository, times(1)).findByEmail(argument.capture());
        System.out.println(argument.getValue());
    }

    @Test
    void addUserWhenUserExists() {
        when(userRepository.findByEmail(any())).thenReturn(new User());
        boolean isSave = userService.addUser(new User());
        assertFalse(isSave);
    }

    @Test
    void getUserByIdWhenUserExist() {
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));
        User user = userService.getUserById(1);
        assertNotNull(user);
    }

    @Test
    void getUserByIdWhenUserNotExist() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        User user = userService.getUserById(1);
        assertNull(user);
    }

    @Test
    void getAllUsersWhenUserExist() {
        List<User> usersFromBd = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(usersFromBd);
        List<User> allUsers = userService.getAllUsers();
        assertEquals(usersFromBd.size(), allUsers.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getAllUsersWhenUsersNotExist() {
        List<User> usersFromBd = Collections.emptyList();
        when(userRepository.findAll()).thenReturn(usersFromBd);
        List<User> allUsers = userService.getAllUsers();
        assertNotNull(allUsers);
        assertEquals(usersFromBd.size(), allUsers.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserByUsernameWhenUserExist() {
        when(userRepository.findByUsername("test")).thenReturn(new User());
        UserDetails user = userService.loadUserByUsername("test");
        assertNotNull(user);
    }

    @Test
    void getUserByUsernameWhenUserNotExist() {
        assertThrows(UsernameNotFoundException.class, () -> {
            when(userRepository.findByUsername("test")).thenReturn(null);
            userService.loadUserByUsername("test");
        });
    }

}