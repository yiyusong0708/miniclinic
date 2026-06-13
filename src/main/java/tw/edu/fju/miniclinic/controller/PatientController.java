package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.edu.fju.miniclinic.model.*;
import java.util.List;

@Controller
@SuppressWarnings("null")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // 1. 網頁端點：顯示所有病患清單頁面 
    @GetMapping("/patients")
    public String listPatients(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        return "patients"; 
    }

    // 2. API 端點：取得所有病患的 JSON 
    @GetMapping("/api/patients")
    @ResponseBody // 確保回傳純 JSON 資料
    public List<Patient> getPatientsApi() {
        return patientRepository.findAll();
    }

    @GetMapping("/api/patients/{chartNo}")
    @ResponseBody
    public ResponseEntity<Patient> getPatientByChartNo(@PathVariable String chartNo) {
        return patientRepository.findById(chartNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); // 找不到就回傳 404
    }
}