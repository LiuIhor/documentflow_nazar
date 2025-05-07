package com.company.docflow.repository;

import com.company.docflow.model.Department;
import com.company.docflow.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
