package tw.edu.fju.miniclinic.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // 🎯 規格書第 10 點指定：傳入 Doctor 物件與 LocalDate 日期
    List<Appointment> findByDoctorAndApptDate(Doctor doctor, LocalDate apptDate);

    List<Appointment> findByApptDate(LocalDate apptDate);

    List<Appointment> findByDoctor(Doctor doctor);

    @Query("SELECT a.doctor.department, COUNT(a) FROM Appointment a GROUP BY a.doctor.department")
    List<Object[]> countAppointmentsByDepartment();
}