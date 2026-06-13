package tw.edu.fju.miniclinic.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tw.edu.fju.miniclinic.model.Appointment;
import tw.edu.fju.miniclinic.model.AppointmentForm;
import tw.edu.fju.miniclinic.model.AppointmentRepository;
import tw.edu.fju.miniclinic.model.Doctor;
import tw.edu.fju.miniclinic.model.DoctorRepository;
import tw.edu.fju.miniclinic.model.Patient;
import tw.edu.fju.miniclinic.model.PatientRepository;

import java.time.LocalDate;

@Controller
@SuppressWarnings("null")
public class AppointmentController {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private PatientRepository patientRepo;

    // GET: 顯示掛號表單
    @GetMapping("/appointment/new")
    public String showAppointmentForm(Model model) {
        model.addAttribute("form", new AppointmentForm());
        model.addAttribute("doctors", doctorRepo.findAll());
        return "appointment-new";
    }
    @GetMapping("/appointments")
    public String listAppointments(Model model) {
    // 撈出所有掛號紀錄塞給畫面
        model.addAttribute("appointments", appointmentRepo.findAll());
    // 回傳對應的 HTML 模板名稱 
        return "appointments"; 
    }

    
    @PostMapping("/appointment/new")
    public String submitAppointment(
            @Valid @ModelAttribute("form") AppointmentForm form, 
            BindingResult result,                                
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("form", form);
            model.addAttribute("doctors", doctorRepo.findAll());
            return "appointment-new";
        }

        // ====== 驗證通過，處理真實資料庫存檔邏輯 ======

        // 1. 找出或防呆建立病患資料
        Patient patient = patientRepo.findById(form.getChartNo()).orElse(null);
        if (patient == null) {
            patient = new Patient();
            patient.setChartNo(form.getChartNo());
            patient.setName("現場建檔病患");
            patientRepo.save(patient);
        }

        // 2. 找出對應的醫生
        Doctor doctor = doctorRepo.findById(form.getDoctorId()).orElse(null);

        // 3. 封裝成真實的 Appointment Entity 準備持久化
        Appointment appt = new Appointment();
        appt.setPatient(patient);
        appt.setDoctor(doctor);
        appt.setApptDate(LocalDate.parse(form.getApptDate()));
        appt.setTimeSlot(form.getTimeSlot());
        appt.setStatus("BOOKED"); // 🎯 初始狀態設為 BOOKED，完美對接你的 API

        // 4. 存入資料庫，此時資料庫會自動生成流水號 ID
        Appointment savedAppt = appointmentRepo.save(appt);

        // ====== 使用 FlashAttribute 安全帶走流水號並重導向 ======
        redirectAttributes.addFlashAttribute("successMessage", 
                "掛號成功！您的掛號流水號為：" + savedAppt.getId() + " 號");

        // 重導向回大首頁，避開表單重複送出的問題
        return "redirect:/";
    }
}