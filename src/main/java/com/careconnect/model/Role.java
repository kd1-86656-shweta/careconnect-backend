package com.careconnect.model;
import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long roleId;

        @Column(nullable = false, unique = true)
        private String roleName; // DOCTOR, PATIENT, ADMIN
    }
