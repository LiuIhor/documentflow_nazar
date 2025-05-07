package com.company.docflow.service;

import com.company.docflow.dto.DepartmentDto;
import com.company.docflow.dto.DocumentDto;
import com.company.docflow.model.Department;
import com.company.docflow.model.Document;
import com.company.docflow.model.DocumentType;
import com.company.docflow.model.User;
import com.company.docflow.repository.DepartmentRepository;
import com.company.docflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DepartmentDto convertToDTO(Department department) {
        return new DepartmentDto(department.getDepartmentCode(), department.getDepartmentName());
    }

    private Department convertToEntity(DepartmentDto departmentDto) {

        Department department = new Department();
        department.setDepartmentName(departmentDto.departmentName());
        department.setDepartmentCode(departmentDto.departmentCode());
        return department;
    }
}
