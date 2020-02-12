package danilov.roman.myjava.service;

import danilov.roman.myjava.model.User;
import danilov.roman.myjava.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Добавление нового пользователя
     *
     * @param user User
     * @return boolean false - если пользователь уже существует, true - если успешно добавился.
     */
    public boolean addUser(User user) {
        User userFromBd = userRepository.getByEmail(user.getEmail());
        if (userFromBd != null) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    /**
     * Возращает найденного по ID пользователя, иначе null
     *
     * @param id Integer ID пользователя
     * @return User/null
     */
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Возвращает список всех пользователей
     *
     * @return List<User>
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
