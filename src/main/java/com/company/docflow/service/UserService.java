package com.company.docflow.service;

import com.company.docflow.dto.DocumentDto;
import com.company.docflow.dto.UserDto;
import com.company.docflow.model.Document;
import com.company.docflow.model.DocumentType;
import com.company.docflow.model.User;
import com.company.docflow.repository.DocumentRepository;
import com.company.docflow.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public UserService(UserRepository userRepository, DocumentRepository documentRepository) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
    }
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UserDto convertToDTO(User user) {
        return new UserDto(user.getUserLogin(), user.getUsername(), user.getDepartmentCode().getDepartmentCode());
    }

    private User convertToEntity(UserDto userDto) {
        Set<Document> documents = documentRepository.findByUserLogin_userLogin(userDto.login());
        User user = new User();
        user.setUserLogin(userDto.login());
        user.setUsername(userDto.username());
        user.setDocuments(documents);
        return user;
    }
}
