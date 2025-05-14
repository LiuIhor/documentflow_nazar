package com.company.docflow.controller;

import com.company.docflow.dto.DepartmentDto;
import com.company.docflow.dto.UserDto;
import com.company.docflow.model.Department;
import com.company.docflow.model.Document;
import com.company.docflow.service.DepartmentService;
import com.company.docflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {


    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        List<DepartmentDto> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/search/byCode")
    public  ResponseEntity<DepartmentDto> getDepartmentByCode(@RequestParam String code) {
        DepartmentDto department = departmentService.getDepartmentByCode(code);
        return new ResponseEntity<>(department,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{code}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable String code) {
        departmentService.deleteDepartment(code);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto) {
        DepartmentDto createdDepartment = departmentService.createDepartment(departmentDto);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    @PutMapping("/{code}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable String code,
                                              @RequestBody DepartmentDto departmentDto) {
        DepartmentDto updatedDepartment = departmentService.updateDepartment(code, departmentDto);
        return ResponseEntity.ok(updatedDepartment);
    }
}
