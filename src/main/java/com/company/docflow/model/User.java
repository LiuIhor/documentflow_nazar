package com.company.docflow.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "users", schema = "be")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "user_login", nullable = false, unique = true)
    private String userLogin;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @ManyToOne
    @JoinColumn(name = "department_code", referencedColumnName = "department_code", nullable = false)
    @JsonIgnore
    private Department departmentCode;

    @OneToMany(mappedBy = "userLogin")
    @JsonIgnore
    private Set<Document> documents;

}

