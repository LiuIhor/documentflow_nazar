package com.company.docflow.service;

import com.company.docflow.dto.DepartmentDto;
import com.company.docflow.dto.DocumentDto;
import com.company.docflow.model.Department;
import com.company.docflow.model.Document;
import com.company.docflow.model.DocumentType;
import com.company.docflow.model.User;
import com.company.docflow.repository.DepartmentRepository;
import com.company.docflow.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public DepartmentDto getDepartmentByCode(String code) {
        Optional<Department> department = departmentRepository.findById(code);
        return convertToDTO(department.get());
    }

    public void deleteDepartment(String code) {
        if (!departmentRepository.existsById(code)) {
            throw new EntityNotFoundException("Department not found with id: " + code);
        }
        departmentRepository.deleteById(code);
    }

    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department savedDocument = departmentRepository.save(convertToEntity(departmentDto));
        return convertToDTO(savedDocument);
    }

    public DepartmentDto updateDepartment(String code, DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + code));

        if (departmentDto.departmentName() != null) {
            department.setDepartmentName(departmentDto.departmentName());
        }

        Department savedDepartment = departmentRepository.save(department);
        return convertToDTO(savedDepartment);
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
