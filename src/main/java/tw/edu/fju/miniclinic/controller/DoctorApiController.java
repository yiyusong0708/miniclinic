package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.edu.fju.miniclinic.model.Doctor;
import tw.edu.fju.miniclinic.model.DoctorRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctors") 
@SuppressWarnings("null")
public class DoctorApiController {

    @Autowired
    private DoctorRepository doctorRepo;

    // 取得所有醫師資料
    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    @GetMapping("/{doctorId}")  
    public ResponseEntity<Doctor> getDoctorById(@PathVariable String doctorId) {
        return doctorRepo.findById(doctorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorRepo.save(doctor);
        return ResponseEntity.status(201).body(savedDoctor);
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable String doctorId, @RequestBody Doctor doctorDetails) {
        Optional<Doctor> doctorOpt = doctorRepo.findById(doctorId);
        if (doctorOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Doctor doctor = doctorOpt.get();
        doctor.setName(doctorDetails.getName());
        doctor.setDepartment(doctorDetails.getDepartment());
        doctor.setSpecialty(doctorDetails.getSpecialty());
        
        Doctor updatedDoctor = doctorRepo.save(doctor);
        return ResponseEntity.ok(updatedDoctor);
    }

    // 🎯 修正點：補上關鍵的 @DeleteMapping 註解，讓刪除醫師功能生效
    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable String doctorId) {
        if (!doctorRepo.existsById(doctorId)) {
            return ResponseEntity.notFound().build();
        }
        doctorRepo.deleteById(doctorId);
        return ResponseEntity.noContent().build(); 
    }
}