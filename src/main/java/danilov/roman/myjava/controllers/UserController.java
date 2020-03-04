package danilov.roman.myjava.controllers;

import danilov.roman.myjava.model.User;
import danilov.roman.myjava.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Log4j2
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String users(Model model) {
        log.info("Method - users");
        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }

    @GetMapping("edit/{id}")
    public String editUser(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        if (user != null) {
            //userService.deleteUser(id);
            model.addAttribute("user", user);
            return "user/edit";
        }
        return "redirect:/users";
    }

    @PostMapping("edit/{id}")
    public String submitEditUser(@PathVariable int id, @Valid User formUser) {
        User user = userService.getUserById(id);
        userService.updateUser(user);
        return "redirect:/users";
    }

}
