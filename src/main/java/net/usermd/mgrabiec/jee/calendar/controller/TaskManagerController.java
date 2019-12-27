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
public class TaskManagerController {
    private TaskService taskService;

    @Autowired
    public TaskManagerController(TaskService taskService) {
        this.taskService=taskService;
    }

    @GetMapping("/manager")
    public String myWorkers(Model model, HttpServletRequest request){

        ArrayList<User> team= (ArrayList<User>) taskService.getAllUsers(request);
        if(team.size()>0)
            model.addAttribute("users",team);
        else
            model.addAttribute("users",null);

        return "manager/manager-users";
    }

    @GetMapping("/manager/delete/{id}")
    public String deleteFromTeam(@PathVariable("id") long id, HttpServletRequest request){
        if(taskService.deleteFromTeam(request, id))
            return "redirect:/manager";
            else return "redirect:/error";
    }

    @GetMapping("/manager/user/{id}")
    public String showFromTeam(@PathVariable("id") long id, HttpServletRequest request, Model model){
        model.addAttribute("tasks",taskService.getUserTasks(request,id));
        return "/tasks/index";
    }

    @GetMapping("/manager/add-task/{id}")
    public String addTaskToUser(@PathVariable("id") long id,Model model, Task task){
        model.addAttribute("id",id);
        return "/manager/add-task-to-user";
    }

    @PostMapping("/manager/add-task-to-user/{id}")
    public String addTask(@Valid Task task,@PathVariable("id") long id, BindingResult result, Model model, HttpServletRequest request) {
        model.addAttribute("id",id);
        if (result.hasErrors() || task==null) {
            return "/manager/add-task-to-user";
        }
        if(task.getStartTime().isBefore(task.getEndTime())) taskService.saveTask(task,request,id);
        else return "/manager/add-task-to-user";
        return "redirect:/manager";
    }

    @GetMapping("/manager/add-user")
    public String addUserToTeamForm(Model model, HttpServletRequest request){
        List<User> listNewUsers=taskService.getListOfUser(request);
        model.addAttribute("users",listNewUsers);
        //else model.addAttribute("users",null);
        return "/manager/add-user";
    }

    @GetMapping("manager/add-to-team/{id}")
    public String addUser(HttpServletRequest request,@PathVariable("id") long id){
        taskService.addToTeam(request,id);
        return "redirect:/manager";
    }
}
