package danilov.roman.myjava.loader;

import danilov.roman.myjava.model.Address;
import danilov.roman.myjava.model.Privilege;
import danilov.roman.myjava.model.Role;
import danilov.roman.myjava.model.User;
import danilov.roman.myjava.repository.AddressRepository;
import danilov.roman.myjava.repository.PrivilegeRepository;
import danilov.roman.myjava.repository.RoleRepository;
import danilov.roman.myjava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private boolean isLoad = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (isLoad) {
            return;
        }

        if (userRepository.findByEmail("daromnik@yandex.ru") != null) {
            return;
        }

        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", new HashSet<>(Arrays.asList(readPrivilege, writePrivilege)));
        Role userRole = createRoleIfNotFound("ROLE_USER", new HashSet<>(Collections.singletonList(readPrivilege)));

        Address address = new Address("Vodoprovodnay 59, 31");
        addressRepository.save(address);

        User userAdmin = User.builder()
                .username("Danilov Roman")
                .email("daromnik@yandex.ru")
                .phone("9051831442")
                .password(bCryptPasswordEncoder.encode("123456"))
//                .roles(Collections.singletonList(adminRole))
                .roles(new HashSet<>(Collections.singletonList(adminRole)))
                .build();

        User userSimple = User.builder()
                .username("Petrov Nikita")
                .email("nekit@yandex.ru")
                .phone("9051832222")
                .password(bCryptPasswordEncoder.encode("password"))
//                .roles(Collections.singletonList(userRole))
                .roles(new HashSet<>(Collections.singletonList(userRole)))
                .build();

        userAdmin.setCreateDate(LocalDateTime.now());
        userSimple.setCreateDate(LocalDateTime.now());

        userAdmin.setAddress(address);
        userSimple.setAddress(address);

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
    public Role createRoleIfNotFound(String name, Set<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

}
