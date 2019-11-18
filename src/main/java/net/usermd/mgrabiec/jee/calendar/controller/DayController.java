package net.usermd.mgrabiec.jee.calendar.controller;

import net.usermd.mgrabiec.jee.calendar.model.MyTaskRepo;
import net.usermd.mgrabiec.jee.calendar.model.Task;
import net.usermd.mgrabiec.jee.calendar.model.TimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
        ArrayList<Task> tasks = (ArrayList<Task>) taskRepo.findAllByStartTimeBetween(start, end);
        if(tasks.size()>0) {
            tasks.sort(new TimeComparator());
            model.addAttribute("tasks", tasks);
        }else model.addAttribute("tasks", null);
        model.addAttribute("day", day);
        return "today/index";
    }

    @PostMapping("/adfasds")
    public String setTodayTask(@RequestParam("day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day,Task task,Model model) {
        LocalDateTime dayTime=LocalDateTime.of(day,LocalTime.MIDNIGHT);
        model.addAttribute("day", dayTime);
        return "today/day-add";
    }

    @PostMapping("today/addtask")
    public String addTask(@Valid Task task, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "today/day-add";
        }

        if(task.getStartTime().isBefore(task.getEndTime()))taskRepo.save(task);
        else return "today/day-add";
        List<Task> tasks=taskRepo.findByOrderByStartTime();
        model.addAttribute("tasks", tasks);
        return "today/index";
    }
}
