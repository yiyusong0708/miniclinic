package tw.edu.fju.miniclinic.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tw.edu.fju.miniclinic.model.Doctor;
import tw.edu.fju.miniclinic.model.DoctorRepository;

@Controller
public class PasswordController {

    @Autowired
    private DoctorRepository doctorRepo;

    @GetMapping("/password")
    public String passwordForm() {
        return "password";
    }

    @PostMapping("/password")
    public String changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            HttpSession session,
            Model model) {

        String doctorId = (String) session.getAttribute("loggedInDoctorId");
        
        // 🎯 加上這段防禦性檢查：如果 doctorId 為 null，直接踢回登入頁
        if (doctorId == null) {
            return "redirect:/login";
        }

        // 當程式碼走到這裡，IDE 就100%確定 doctorId 絕對不會是 null 了，警告自然消失！
        Doctor doctor = doctorRepo.findById(doctorId).orElse(null);

        // 1. 驗證舊密碼是否正確
        if (doctor == null || !BCrypt.checkpw(oldPassword, doctor.getPasswordHash())) {
            model.addAttribute("error", "舊密碼錯誤");
            return "password";
        }

        // 2. 驗證新密碼長度（至少 8 碼）
        if (newPassword.length() < 8) {
            model.addAttribute("error", "新密碼至少需要 8 碼");
            return "password";
        }

        // 3. 驗證新密碼與確認密碼是否一致
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "新密碼與確認密碼不一致");
            return "password";
        }

        // 4. 成功：雜湊後更新至資料庫
        String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        doctor.setPasswordHash(hashed);
        doctorRepo.save(doctor);

        model.addAttribute("success", "密碼修改成功！");
        return "password";
    }
}