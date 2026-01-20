package com.careconnect.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
    @Table(name = "profile_attachments")
    public class ProfileAttachment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long attachmentId;

        @ManyToOne
        @JoinColumn(name = "profile_id")
        private UserProfile userProfile;

        private String fileName;

        private String fileType;

        private String fileUrl;

        private LocalDateTime uploadedAt;
    }

