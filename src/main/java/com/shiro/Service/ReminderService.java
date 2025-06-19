// src/main/java/com/shiro/Service/ReminderService.java
package com.shiro.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.shiro.Entity.Task;
import com.shiro.Entity.User;
import com.shiro.Repo.TaskRepo;

@Service
public class ReminderService {

    @Autowired private TaskRepo taskRepo;
    @Autowired private EmailService emailService;

    /**
     * Dijadwalkan tiap hari jam 07:00 server‑time.
     * Cron: detik menit jam dom bulan dow
     */
    @Scheduled(cron = "0 0 7 * * *")
    public void sendDailyReminders() {

        // ambil deadline hari ini sampai besok
        LocalDate today  = LocalDate.now();
        LocalDate besok  = today.plusDays(1);

       List<Task> tasks = taskRepo.findByDeadlineBetween(
        Date.valueOf(today),
        Date.valueOf(besok)
        );
        for (Task t : tasks) {
            User u = t.getAssignee();          // relasi ManyToOne sudah ada
            if (u != null) {
                // anggap username = email.
                // Jika Anda punya kolom email di User, ganti getUsername() → getEmail()
                String emailTo = u.getUsername();

                String subject = "Reminder: \"" + t.getName()
                        + "\" deadline " + t.getDeadline();
                String body = "Halo "
                        + u.getName()
                        + ",\n\nTugas \"" + t.getName()
                        + "\" akan jatuh tempo pada " + t.getDeadline()
                        + ".\nSegera selesaikan agar tidak terlambat.\n\n— TaskHub";

                emailService.send(emailTo, subject, body);
            }
        }
    }
}
