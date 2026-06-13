package tw.edu.fju.miniclinic.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tw.edu.fju.miniclinic.model.Appointment;
import tw.edu.fju.miniclinic.model.Doctor;
import tw.edu.fju.miniclinic.model.AppointmentRepository;
import tw.edu.fju.miniclinic.model.DoctorRepository;

import java.time.LocalDate;
import java.util.List;

@Controller
@SuppressWarnings("null")
public class DashboardController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        String doctorId = (String) session.getAttribute("loggedInDoctorId");
        
        // 1. 依據規格書：以 ID 查詢 DoctorRepository
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        
        // 2. 規格書防禦：若查無醫師，清除 Session 並重導向
        if (doctor == null) {
            session.invalidate();
            return "redirect:/login";
        }
        
        LocalDate today = LocalDate.now();

        // 3. 呼叫規格書指定方法
        List<Appointment> todayAppts = appointmentRepository.findByDoctorAndApptDate(doctor, today);

        // 4. 放入 Model
        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", todayAppts);
        model.addAttribute("today", today);
        
        return "dashboard";
    }
}