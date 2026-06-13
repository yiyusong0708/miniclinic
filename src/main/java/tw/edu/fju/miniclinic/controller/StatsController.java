package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody; // 🎯 記得引入這個，才能回傳純 JSON
import tw.edu.fju.miniclinic.model.AppointmentRepository;
import tw.edu.fju.miniclinic.model.DoctorRepository;   // 🚀 新增引入
import tw.edu.fju.miniclinic.model.PatientRepository;  // 🚀 新增引入

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StatsController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;   // 注入醫生 Repo

    @Autowired
    private PatientRepository patientRepository;  // 注入病人 Repo


    @GetMapping("/stats")
    public String showStats(Model model) {
        List<Object[]> rawStats = appointmentRepository.countAppointmentsByDepartment();
        List<Map<String, Object>> deptStats = new ArrayList<>();
        
        for (Object[] row : rawStats) {
            Map<String, Object> statRow = new HashMap<>();
            statRow.put("department", row[0]);
            statRow.put("count", row[1]);
            deptStats.add(statRow);
        }
        
        model.addAttribute("stats", deptStats);
        return "stats";
    }

    @GetMapping("/api/stats") //  確保路由完全符合 T05 規格
    @ResponseBody             //  加上這行，Spring 才會回傳純 JSON 數據，而不是去找網頁！
    public ResponseEntity<Map<String, Object>> getApiStats() {
        // 1. 取得資料表總筆數
        long totalDoctors = doctorRepository.count();
        long totalPatients = patientRepository.count();
        long totalAppointments = appointmentRepository.count();

        // 2. 取得各狀態掛號數（對接剛才在 Repository 宣告的 countByStatus）
        long bookedCount = appointmentRepository.countByStatus("BOOKED");
        long completedCount = appointmentRepository.countByStatus("COMPLETED");
        long cancelledCount = appointmentRepository.countByStatus("CANCELLED");

        // 3. 使用 LinkedHashMap 組合巢狀 JSON 結構 (符合 T06 規格書提示)
        Map<String, Object> byStatus = new LinkedHashMap<>();
        byStatus.put("BOOKED", (int) bookedCount);
        byStatus.put("COMPLETED", (int) completedCount);
        byStatus.put("CANCELLED", (int) cancelledCount);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalDoctors", (int) totalDoctors);
        result.put("totalPatients", (int) totalPatients);
        result.put("totalAppointments", (int) totalAppointments);
        result.put("byStatus", byStatus);

        return ResponseEntity.ok(result);
    }
}