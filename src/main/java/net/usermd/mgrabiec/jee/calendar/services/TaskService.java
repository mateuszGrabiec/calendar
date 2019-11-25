package net.usermd.mgrabiec.jee.calendar.services;

import net.usermd.mgrabiec.jee.calendar.model.Task;
import net.usermd.mgrabiec.jee.calendar.model.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private UserRepo userRepo;
    private MyTaskRepo taskRepo;

    public TaskService(UserRepo userRepo, MyTaskRepo taskRepo){
        this.taskRepo=taskRepo;
        this.userRepo=userRepo;
    }

    public List<Task> getAllTasks(HttpServletRequest request){
        List<Task> taskList=new ArrayList<>();
        User user= createUser(request);
        if(user!=null){
            taskList.addAll(
                    taskRepo.findByUserUserNameOrderByStartTime(user.getUserName()));
        }
        return taskList;
    }

    public void saveTask(Task task,HttpServletRequest request){
        User user=createUser(request);
        if (user!=null){
            task.setUser(user);
            taskRepo.save(task);
        }
    }

    public void delete(long taskId,HttpServletRequest request){
        User user=createUser(request);
        Task task;
        Optional<Task> taskOptional=taskRepo.findById(taskId);
        task = taskOptional.orElse(null);
        if (user!=null && task!=null) {
            if (task.getUser().equals(user)) {
                taskRepo.deleteById(taskId);
            }
        }
    }

    public Task findById(long id, HttpServletRequest request){
        Optional<Task> foundedTask=taskRepo.findById(id);
        User user = createUser(request);
        if(foundedTask.isPresent() && user!=null){
            Task task=foundedTask.get();
            if(task.getUser().equals(user)) return task;
            else return null;
        }
        else return null;
    }


    private User createUser(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String userName=principal.getName();
        Optional<User> foundedUser=userRepo.findByUserName(userName);
        return foundedUser.orElse(null);
    }

    public List<Task> findBetween(HttpServletRequest request, LocalDateTime start, LocalDateTime end) {
        User user = createUser(request);
        List<Task> taskList=taskRepo.findAllByStartTimeBetween(start,end);
        taskList.forEach(task -> {
            if(! task.getUser().equals(user)) taskList.remove(task);
        });
        return taskList;
    }
}