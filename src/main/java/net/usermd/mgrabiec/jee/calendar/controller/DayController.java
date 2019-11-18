package net.usermd.mgrabiec.jee.calendar.controller;

import net.usermd.mgrabiec.jee.calendar.model.MyTaskRepo;
import net.usermd.mgrabiec.jee.calendar.model.Task;
import net.usermd.mgrabiec.jee.calendar.model.TimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class DayController {
    private MyTaskRepo taskRepo;

    @Autowired
    public DayController(MyTaskRepo taskRepo){
        this.taskRepo = taskRepo;
    }

    @RequestMapping(value = "/day", method = POST)
    public String getTodayTasks(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day, Model model) {
        //LocalDate.parse( day, DateTimeFormatter.ofPattern("yyyy-MM-dd") );
        LocalDateTime start = LocalDateTime.of(day, LocalTime.MIDNIGHT);
        LocalDateTime end = LocalDateTime.of(day, LocalTime.MIDNIGHT.minusMinutes(1));
        ArrayList<Task> tasks=(ArrayList<Task>)taskRepo.findAllByStartTimeBetween(start,end);
        tasks.sort(new TimeComparator());
        model.addAttribute("tasks",tasks);
        return "today/index";
    }
}
