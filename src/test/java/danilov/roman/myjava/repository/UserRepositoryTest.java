package danilov.roman.myjava.repository;

import danilov.roman.myjava.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

    private User userTest = new User("Roman", "9051831442", "test@test.ru", "password");

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void saveUser() {
        userRepository.save(userTest);
        User userSaved = userRepository.findById(1).orElse(null);
        Assert.assertNotNull(userSaved);
    }

    @Test
    void getById() {
        userRepository.save(userTest);
        User user = userRepository.findById(1).orElse(null);
        Assert.assertNotNull(user);
    }

    @Test
    void deleteById() {
        userRepository.save(userTest);
        User user = userRepository.findById(1).orElse(null);
        Assert.assertNotNull(user);
        userRepository.deleteById(1);
        User userDeleted = userRepository.findById(1).orElse(null);
        Assert.assertNull(userDeleted);
    }
}