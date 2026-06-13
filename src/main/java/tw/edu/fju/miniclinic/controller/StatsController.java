package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tw.edu.fju.miniclinic.model.AppointmentRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StatsController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/stats")
    public String showStats(Model model) {
        // 🎯 呼叫我們寫好的 Repository 統計 JPQL 語法
        List<Object[]> rawStats = appointmentRepository.countAppointmentsByDepartment();
        
        // 將 List<Object[]> 整理成前端好讀的 Map 或 List<Map>
        List<Map<String, Object>> deptStats = new ArrayList<>();
        
        for (Object[] row : rawStats) {
            Map<String, Object> statRow = new HashMap<>();
            statRow.put("department", row[0]); // 科別名稱 (String)
            statRow.put("count", row[1]);      // 掛號人數 (Long)
            deptStats.add(statRow);
        }
        
        // 🎯 傳給 Thymeleaf 的變數名稱叫做 "stats"
        model.addAttribute("stats", deptStats);
        return "stats"; // 對應 templates/stats.html
    }
}