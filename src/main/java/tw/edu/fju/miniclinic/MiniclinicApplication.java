package tw.edu.fju.miniclinic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tw.edu.fju.miniclinic.model.*;
import java.time.LocalDate;

@SpringBootApplication
public class MiniclinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniclinicApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(
            DoctorRepository doctorRepo, 
            PatientRepository patientRepo, 
            AppointmentRepository appointmentRepo) { 
        return args -> {
            
            // 每次啟動時先清空舊資料
            appointmentRepo.deleteAll();
            doctorRepo.deleteAll();
            patientRepo.deleteAll();

            //  1. 醫生名單：將原本的 null 替換成 pass1234 對應的 BCrypt 雜湊值
            Doctor d1 = new Doctor("D001", "陳志明醫師", "家醫科", "一般內科、慢性病管理", "$2a$10$XhyEgd4qh5TXJa7NkMg3gOqsJxATykAyJERH7ZqTD7eEPVlcmgewm");
            Doctor d2 = new Doctor("D002", "林佩君醫師", "內科", "心臟血管、高血壓", "$2a$10$/x/fVm66HZJWeeYZRUbPp..gS9Czgs3a27RjYQPs75obpRoUWU9ZC");
            Doctor d3 = new Doctor("D003", "王建華醫師", "復健科", "運動傷害、脊椎復健", "$2a$10$4fZBPZq1NJmqW5MUgOUsqukV6OiTJutAKR/WbiFiQ6PRTjFbNsMFy");
            Doctor d4 = new Doctor("D004", "李美玲醫師", "小兒科", "兒童感冒、疫苗接種", "$2a$10$ZlsUgEo2MOm0RYxwcP55qukrjipEXYNKyyRfdIKkOEv7RpuXEPhxK");
            Doctor d5 = new Doctor("D005", "張雅筑醫師", "身心科", "焦慮、失眠、情緒調節", "$2a$10$XsgY9Cmk7PqJ2pve2k4xwuTnV/hakC6LOGJqicQyjH.wDiM7PQhWa");
            
            doctorRepo.save(d1);
            doctorRepo.save(d2);
            doctorRepo.save(d3);
            doctorRepo.save(d4);
            doctorRepo.save(d5);
            
            // 2. 病患名單
            Patient p1 = new Patient("TEST00001", "王小明", "男", "0912-345678", LocalDate.of(1995, 5, 20));
            Patient p2 = new Patient("TEST00002", "李大同", "男", "0923-456789", LocalDate.of(1988, 11, 12));
            Patient p3 = new Patient("TEST00003", "林心怡", "女", "0934-567890", LocalDate.of(2000, 3, 15));
            
            patientRepo.save(p1);
            patientRepo.save(p2);
            patientRepo.save(p3);
            
            // 3. 掛號資料
            appointmentRepo.save(new Appointment(p1, d1, LocalDate.now(), "上午診", "BOOKED")); 
            appointmentRepo.save(new Appointment(p2, d1, LocalDate.now(), "下午診", "BOOKED")); 
            appointmentRepo.save(new Appointment(p3, d2, LocalDate.now().plusDays(1), "夜間診", "BOOKED")); 
            
            System.out.println("🏥 [系統通知] 醫生、老師指定病患（TEST00001-3）以及初始掛號資料已全數安全寫入 SQLite 資料庫！");
        };
    }
}