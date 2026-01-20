package com.careconnect.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    private String contactNumber;

    private LocalDate birthDate;

    private String gender;

    private String idProofType;

    private String idProofNumber;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private List<ProfileAttachment> attachments;
}
