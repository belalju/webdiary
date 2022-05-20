package com.bd.webdiary.service;

import com.bd.webdiary.model.Role;
import com.bd.webdiary.model.RoleName;
import com.bd.webdiary.model.User;
import com.bd.webdiary.repository.RoleRepository;
import com.bd.webdiary.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public User save(User user) {
        user.createPassword(user.getPassword());

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(userRole);


        user.setRoles(roles);
        return this.userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> getUserList() {
        return this.userRepository.findAll();
    }

}
