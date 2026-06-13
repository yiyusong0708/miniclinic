package tw.edu.fju.miniclinic.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import java.sql.Types;
import java.time.LocalDate;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appt_id")
    private Long apptId;

    @ManyToOne
    @JoinColumn(name = "chart_no", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    
    @JdbcTypeCode(Types.VARCHAR)
    @Convert(converter = LocalDateConverter.class)
    @Column(name = "appt_date", columnDefinition = "TEXT", nullable = false)
    private LocalDate apptDate;

    @Column(name = "time_slot", length = 20, nullable = false)
    private String timeSlot;

    @Column(name = "status", length = 20, nullable = false)
    private String status = "BOOKED";

    public Appointment() {}

    public Appointment(Patient patient, Doctor doctor, LocalDate apptDate, String timeSlot, String status) {
        this.patient = patient;
        this.doctor = doctor;
        this.apptDate = apptDate;
        this.timeSlot = timeSlot;
        this.status = status;
    }

    // Getters & Setters
    public Long getId() { return apptId; }
    public void setId(Long apptId) { this.apptId = apptId; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public LocalDate getApptDate() { return apptDate; }
    public void setApptDate(LocalDate apptDate) { this.apptDate = apptDate; }
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}