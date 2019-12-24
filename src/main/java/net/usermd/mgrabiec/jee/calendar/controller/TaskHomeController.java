package net.usermd.mgrabiec.jee.calendar.controller;


import net.usermd.mgrabiec.jee.calendar.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TaskHomeController {
    private TaskService taskService;

    @Autowired
    public TaskHomeController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request){
        model.addAttribute(request);
        return "index";
    }
}
