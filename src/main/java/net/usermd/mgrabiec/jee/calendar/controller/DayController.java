package net.usermd.mgrabiec.jee.calendar.controller;

import net.usermd.mgrabiec.jee.calendar.model.Task;
import net.usermd.mgrabiec.jee.calendar.services.TaskService;
import net.usermd.mgrabiec.jee.calendar.services.TimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class DayController {
    private TaskService taskService;

    @Autowired
    public DayController(TaskService taskService){
        this.taskService = taskService;
    }

    @RequestMapping(value = "/day", method = POST)
    public String getTodayTasks(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day, Model model, HttpServletRequest request, HttpServletResponse response ) {
        //LocalDate.parse( day, DateTimeFormatter.ofPattern("yyyy-MM-dd") );
        LocalDateTime start = LocalDateTime.of(day, LocalTime.MIDNIGHT);
        LocalDateTime end = LocalDateTime.of(day, LocalTime.MIDNIGHT.minusMinutes(1));
        ArrayList<Task> tasks = (ArrayList<Task>) taskService.findBetween(request,start,end);//taskRepo.findAllByStartTimeBetween(start, end);
        if(tasks.size()>0) {
            tasks.sort(new TimeComparator());
            model.addAttribute("tasks", tasks);
        }else model.addAttribute("tasks", null);

        Cookie cookie = new Cookie("date",day.toString());
        response.addCookie(cookie);

        model.addAttribute("day", day);
        return "today/index";
    }

    @GetMapping("/add-task-for-day")
    public String setTodayTask(Task task,Model model,HttpServletRequest request) {
        Cookie[] cookies=request.getCookies();
        LocalDate day=null;
        for (Cookie c: cookies){
            if(c.getName().equals("date"))day=LocalDate.parse(c.getValue());
        }

        if(day!=null){
            LocalDateTime dayTime=LocalDateTime.of(day,LocalTime.MIDNIGHT);
            model.addAttribute("day", dayTime);
        }

        return "today/day-add";
    }

    @PostMapping("today/addtask")
    public String addTask(@Valid Task task, BindingResult result, Model model, @RequestParam("day") String day, HttpServletRequest request) {//TODO fix null if val not changed
        if (result.hasErrors()) {
            return "redirect:/add-task-for-day";
        }
        if(task.getStartTime().isBefore(task.getEndTime()))taskService.saveTask(task,request);
        else return "today/day-add";
        List<Task> tasks=taskService.getAllTasks(request);
        LocalDateTime localdatetime = LocalDateTime.parse(day);
        model.addAttribute("day", localdatetime);
        model.addAttribute("tasks", tasks);
        return "today/index";
    }
}
