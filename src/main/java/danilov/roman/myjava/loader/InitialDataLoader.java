package danilov.roman.myjava.loader;

import danilov.roman.myjava.model.Privilege;
import danilov.roman.myjava.model.Role;
import danilov.roman.myjava.model.User;
import danilov.roman.myjava.repository.PrivilegeRepository;
import danilov.roman.myjava.repository.RoleRepository;
import danilov.roman.myjava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;


@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PrivilegeRepository privilegeRepository;

    private boolean isLoad = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (isLoad) {
            return;
        }
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        Role adminRole = createRoleIfNotFound("ADMIN", Arrays.asList(readPrivilege, writePrivilege));
        Role userRole = createRoleIfNotFound("USER", Collections.singletonList(readPrivilege));

        User userAdmin = User.builder()
                .name("Danilov Roman")
                .email("daromnik@yandex.ru")
                .phone("9051831442")
                .password("123456")
                .createDate(LocalDateTime.now())
                .roles(Collections.singletonList(adminRole))
                .build();

        User userSimple = User.builder()
                .name("Petrov Nikita")
                .email("nekit@yandex.ru")
                .phone("9051832222")
                .password("password")
                .createDate(LocalDateTime.now())
                .roles(Collections.singletonList(userRole))
                .build();

        userRepository.save(userAdmin);
        userRepository.save(userSimple);

        isLoad = true;
    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    public Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

}
