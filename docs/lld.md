# Low Level Design (LLD)

## Core Entities
- User
- Role
- PatientProfile
- DoctorProfile
- MedicalRecord
- Appointment
- Hospital
- AmbulanceService

## Relationships
- User → PatientProfile (1:1)
- User → DoctorProfile (1:1)
- PatientProfile → MedicalRecord (1:N)
- Doctor → Appointment (1:N)
- Patient → Appointment (1:N)
