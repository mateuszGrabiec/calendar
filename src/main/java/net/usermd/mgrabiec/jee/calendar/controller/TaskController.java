package net.usermd.mgrabiec.jee.calendar.controller;

import net.usermd.mgrabiec.jee.calendar.model.Task;
import net.usermd.mgrabiec.jee.calendar.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/add")
    public String addTask(Task task, Model model){
        //model.addAttribute("localDateTime", LocalDateTime.now());
        return "tasks/add-task";
    }

    @PostMapping("/addtask")
    public String addTask(@Valid Task task, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "tasks/add-task";
        }

        if(task.getStartTime().isBefore(task.getEndTime()))taskService.saveTask(task,request);
        else return "/tasks/add-task";
        return "redirect:/tasks";
    }

    @GetMapping
    public String index(Model model, HttpServletRequest request) {
        if(taskService.getAllTasks(request).size()>0)
            model.addAttribute("tasks",taskService.getAllTasks(request));
        else
            model.addAttribute("tasks",null);
        return "tasks/index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model,HttpServletRequest request) {
        try {
            Task task = taskService.findById(id, request);
            if(task!=null)model.addAttribute("task", task);
        }catch(java.lang.IllegalArgumentException e){
            return "redirect:/error";
        }
        return "tasks/update-task";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Task task, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            task.setId(id);
            return "/tasks/update-task";
        }
        if(task.getStartTime().isBefore(task.getEndTime()))taskService.saveTask(task,request);
        else return "/tasks/update-task";
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model, HttpServletRequest request) {
        try {
            taskService.delete(id,request);
        }catch (java.lang.IllegalArgumentException e) {
        return "/error";
        }
        return "redirect:/tasks";
    }
}
