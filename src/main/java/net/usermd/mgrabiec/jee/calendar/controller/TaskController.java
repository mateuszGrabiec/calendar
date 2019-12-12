package net.usermd.mgrabiec.jee.calendar.controller;

import net.usermd.mgrabiec.jee.calendar.model.Task;
import net.usermd.mgrabiec.jee.calendar.model.User;
import net.usermd.mgrabiec.jee.calendar.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping("/task")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/add")
    public String showSignUpForm(Task task, Model model){
        //model.addAttribute("localDateTime", LocalDateTime.now());
        return "add-task";
    }

    @PostMapping("/addtask")
    public String addTask(@Valid Task task, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "add-task";
        }

        if(task.getStartTime().isBefore(task.getEndTime()))taskService.saveTask(task,request);
        else return "add-task";
        List<Task> tasks=taskService.getAllTasks(request);
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping
    public String index(Model model, HttpServletRequest request) {
        if(taskService.getAllTasks(request).size()>0)
            model.addAttribute("tasks",taskService.getAllTasks(request));
        else
            model.addAttribute("tasks",null);
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model,HttpServletRequest request) {
        try {
            Task task = taskService.findById(id, request);
            if(task!=null)model.addAttribute("task", task);
        }catch(java.lang.IllegalArgumentException e){
            return "/error";
        }
        return "update-task";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Task task, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            task.setId(id);
            return "update-task";
        }
        if(task.getStartTime().isBefore(task.getEndTime()))taskService.saveTask(task,request);
        else return "update-task";
        ArrayList<Task> tasks=(ArrayList<Task>)taskService.getAllTasks(request);
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model, HttpServletRequest request) {
        try {
            taskService.delete(id,request);
        }catch (java.lang.IllegalArgumentException e) {
        return "/error";
        }
        model.addAttribute("tasks", taskService.getAllTasks(request));
        return "index";
    }

    @GetMapping("/manager")
    public String addT(Model model, HttpServletRequest request) {
        ArrayList<User> team= (ArrayList<User>) taskService.getAllUsers(request);
        if(team.get(0).getTasks().size()>0)
            model.addAttribute("tasks",team.get(0).getTasks());
        else
            model.addAttribute("tasks",null);
        return "index";
    }

    @GetMapping("/error")
    public String getErrorPath(){
        return "index";
    }
}
