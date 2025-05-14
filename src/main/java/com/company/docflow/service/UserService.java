package com.company.docflow.service;

import com.company.docflow.dto.UserDto;
import com.company.docflow.model.Department;
import com.company.docflow.model.Document;
import com.company.docflow.model.User;
import com.company.docflow.repository.DepartmentRepository;
import com.company.docflow.repository.DocumentRepository;
import com.company.docflow.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public UserService(UserRepository userRepository, DocumentRepository documentRepository, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.departmentRepository = departmentRepository;
    }
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public UserDto getUserByLogin(String login) {
        Optional<User> user = userRepository.findByUserLogin(login);
        return convertToDTO(user.get());
    }

    public void deleteUser(String login) {
        if (!userRepository.existsById(login)) {
            throw new EntityNotFoundException("User not found with id: " + login);
        }
        userRepository.deleteById(login);
    }

    public UserDto createUser(UserDto userDto) {
        User savedUser = userRepository.save(convertToEntity(userDto));
        return convertToDTO(savedUser);
    }

    public UserDto updateUser(String login, UserDto updateUser) {
        User user = userRepository.findById(login)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + login));

        if (updateUser.username() != null) {
            user.setUsername(updateUser.username());
        }
        if (updateUser.departmentCode() != null) {
            Department department = departmentRepository.findById(updateUser.departmentCode())
                    .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + updateUser.departmentCode()));

            user.setDepartmentCode(department);
        }

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    private UserDto convertToDTO(User user) {
        return new UserDto(user.getUserLogin(), user.getUsername(), user.getDepartmentCode().getDepartmentCode());
    }

    private User convertToEntity(UserDto userDto) {
        List<Document> documents = documentRepository.findByUserLogin_userLogin(userDto.login());
        User user = new User();
        user.setUserLogin(userDto.login());
        user.setUsername(userDto.username());
        user.setDepartmentCode(getDepartment(userDto.departmentCode()));
        user.setDocuments(documents.stream().collect(Collectors.toSet()));
        return user;
    }

    private Department getDepartment(String code) {
        Department department = departmentRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + code));
        return department;
    }
}
