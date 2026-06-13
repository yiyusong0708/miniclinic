package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tw.edu.fju.miniclinic.model.Doctor;
import tw.edu.fju.miniclinic.model.DoctorRepository;
import java.util.List;

@Controller
public class DoctorPageController {

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/doctors")
    public String listDoctors(@RequestParam(value = "department", required = false) String department, Model model) {
        
        List<Doctor> doctors;
        if (department != null && !department.isEmpty()) {
            doctors = doctorRepository.findByDepartment(department);
        } else {
            doctors = doctorRepository.findAll();
        }

    
        List<String> departments = doctorRepository.findAllDepartments();

        model.addAttribute("doctors", doctors);
        model.addAttribute("departments", departments); 
        model.addAttribute("selectedDept", department);  

        return "doctors"; 
    }
}