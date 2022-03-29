package id.ac.fksp.spmi.service.impl;

import id.ac.fksp.spmi.entity.Role;
import id.ac.fksp.spmi.entity.User;
import id.ac.fksp.spmi.exception.UserException;
import id.ac.fksp.spmi.payload.request.UserAddRoleRequest;
import id.ac.fksp.spmi.payload.request.UserListRequest;
import id.ac.fksp.spmi.payload.request.UserSaveRequest;
import id.ac.fksp.spmi.payload.request.UserUpdateRequest;
import id.ac.fksp.spmi.payload.response.UserResponse;
import id.ac.fksp.spmi.repository.RoleRepository;
import id.ac.fksp.spmi.repository.UserRepository;
import id.ac.fksp.spmi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserResponse saveUser(UserSaveRequest request) {
        // cek apakah username sudah terdaftar
        if (userRepository.existsByUsername(request.getUsername())){
            throw new UserException("Username tidak tersedia");
        }
        // cek apakah email sudah terdaftar
        if (userRepository.existsByEmail(request.getEmail())){
            throw new UserException("Email sudah terdaftar");
        }

        // membahkan collection role
        Collection<Role> roles = new ArrayList<>();
        request.getRoles().forEach(value -> {
            Optional<Role> role = roleRepository.findByName(value);
            roles.add(role.get());
        });

        // simpan user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRoles(roles);
        userRepository.save(user);

        return new UserResponse(user.getId(), user.getUsername(), user.getPassword(), user.getRoles());
    }

    @Override
    public List<UserResponse> listUser(UserListRequest request) {
        Page<User> page = userRepository.findAll(PageRequest.of(request.getPage(), request.getSize()));
        List<User> users = page.get().collect(Collectors.toList());

        return users.stream().map(user -> {
            return (new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRoles()));
        }).collect(Collectors.toList());
    }

    @Override
    public UserResponse getUser(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            throw  new UserException("User tidak ditemukan");
        }
        User user = optionalUser.get();

        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRoles());
    }

    @Override
    public void addRoleUser(UserAddRoleRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
        Optional<Role> optionalRole = roleRepository.findByName(request.getRoleName());
        if (optionalUser.isEmpty()){
            throw new UserException("User tidak ditemukan");
        }
        if (optionalRole.isEmpty()){
            throw new UserException("Role tidak ditemukan");
        }

        User user = optionalUser.get();
        Role role = optionalRole.get();

        user.getRoles().add(role);


    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request, String userId) {
        return null;
    }
}
