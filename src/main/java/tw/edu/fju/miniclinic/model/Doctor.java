package tw.edu.fju.miniclinic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore; 

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @Column(name = "doctor_id", length = 10)
    private String doctorId;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "department", length = 20, nullable = false)
    private String department;

    @Column(name = "specialty", length = 100)
    private String specialty;

   
    @JsonIgnore 
    @Column(name = "password_hash", length = 100)
    private String passwordHash;

    // --- 建構子 ---
    public Doctor() {
    }

    public Doctor(String doctorId, String name, String department, String specialty, String passwordHash) {
        this.doctorId = doctorId;
        this.name = name;
        this.department = department;
        this.specialty = specialty;
        this.passwordHash = passwordHash;
    }

    // --- 原有的 Getters 和 Setters ---
    public String getDoctorId() { return doctorId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    //  Getter 和 Setter
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}