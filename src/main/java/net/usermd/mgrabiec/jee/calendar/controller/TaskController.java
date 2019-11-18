package net.usermd.mgrabiec.jee.calendar.controller;

import net.usermd.mgrabiec.jee.calendar.model.MyTaskRepo;
import net.usermd.mgrabiec.jee.calendar.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping("/task")
public class TaskController {
    private MyTaskRepo taskRepo;

    @Autowired
    public TaskController(MyTaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @GetMapping("/add")
    public String showSignUpForm(Task task, Model model){
        //model.addAttribute("localDateTime", LocalDateTime.now());
        return "add-task";
    }

    @PostMapping("/addtask")
    public String addTask(@Valid Task task, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-task";
        }

        if(task.getStartTime().isBefore(task.getEndTime()))taskRepo.save(task);
        else return "add-task";
        List<Task> tasks=taskRepo.findByOrderByStartTime();
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("tasks", taskRepo.findByOrderByStartTime());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        try {
            Task task = taskRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
            model.addAttribute("task", task);
        }catch(java.lang.IllegalArgumentException e){
            return "/error";
        }
        return "update-task";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Task task, BindingResult result, Model model) {
        if (result.hasErrors()) {
            task.setId(id);
            return "update-task";
        }
        if(task.getStartTime().isBefore(task.getEndTime()))taskRepo.save(task);
        else return "update-task";
        ArrayList<Task> tasks=(ArrayList<Task>)taskRepo.findByOrderByStartTime();
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        try {
            Task task = taskRepo.findById(id).orElseThrow(() -> {
                throw new IllegalArgumentException("Invalid task Id:" + id);
            });
            taskRepo.delete(task);
        }catch (java.lang.IllegalArgumentException e) {
        return "/error";
        }
        model.addAttribute("tasks", taskRepo.findByOrderByStartTime());
        return "index";
    }

    @GetMapping("/error")
    public String getErrorPath(){
        return "index";
    }
}
