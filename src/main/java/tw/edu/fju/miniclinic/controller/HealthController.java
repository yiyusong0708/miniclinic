package tw.edu.fju.miniclinic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HealthController {


    @GetMapping("/api/health")
    public Map<String, String> getHealth() {
        return Map.of(
                "status", "ok",
                "service", "miniclinic"
        );
    }

    @GetMapping("/api/about")
    public Map<String, String> getAbout() {
        return Map.of(
                "student_id", "414570409",
                "student_name", "宋炘庭",
                "project", "MiniClinic",
                "version", "1.0.0",   
                "chapter", "Database-Integration" 
        );
    }
}