package com.dst.administrationservice.service;

import com.dst.administrationservice.dto.CreateUserRequest;
import com.dst.administrationservice.dto.UserInfoResponse;
import com.dst.administrationservice.models.User;
import com.dst.administrationservice.models.Role;
import com.dst.administrationservice.models.UserRoles;
import com.dst.administrationservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    public UserInfoResponse saveEmployee(CreateUserRequest employeeDTO) {

        Optional<User> employeeByUsername = this.userRepository.findByUsername(employeeDTO.getUsername());
        Optional<User> employeeByPhoneNumber = this.userRepository.findByPhoneNumber(employeeDTO.getPhoneNumber());
        Optional<Role> role = this.roleService.findByUserRole(UserRoles.valueOf(employeeDTO.getUserRole()));

        if (employeeByUsername.isPresent() || employeeByPhoneNumber.isPresent() || role.isEmpty()) {
            return new UserInfoResponse();
        }

        User user = new User();

        user.setFirstName(employeeDTO.getFirstName());
        user.setLastName(employeeDTO.getLastName());
        user.setUsername(employeeDTO.getUsername());
        user.setPassword(employeeDTO.getPassword());
        user.setPhoneNumber(employeeDTO.getPhoneNumber());
        user.setUserRole(role.get());

        User savedUser = this.userRepository.save(user);

        return new UserInfoResponse(savedUser.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPhoneNumber(),
                user.getUserRole().getRole().name());
    }

    public UserInfoResponse getEmployeeById(Long employeeId) {
        Optional<User> userById = this.userRepository.findById(employeeId);

        if (userById.isEmpty()) {
            return new UserInfoResponse();
        }

        UserInfoResponse userInfoResponse = modelMapper.map(userById.get(), UserInfoResponse.class);
        userInfoResponse.setUserRole(userById.get().getUserRole().getRole().name());

        return userInfoResponse;
    }
}
