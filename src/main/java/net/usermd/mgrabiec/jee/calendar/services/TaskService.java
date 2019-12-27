package net.usermd.mgrabiec.jee.calendar.services;

import net.usermd.mgrabiec.jee.calendar.model.Manager;
import net.usermd.mgrabiec.jee.calendar.model.Task;
import net.usermd.mgrabiec.jee.calendar.model.User;
import net.usermd.mgrabiec.jee.calendar.repo.ManagerRepo;
import net.usermd.mgrabiec.jee.calendar.repo.MyTaskRepo;
import net.usermd.mgrabiec.jee.calendar.repo.UserRepo;
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
    private ManagerRepo managerRepo;

    public TaskService(UserRepo userRepo, MyTaskRepo taskRepo,ManagerRepo managerRepo){
        this.taskRepo=taskRepo;
        this.userRepo=userRepo;
        this.managerRepo=managerRepo;
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
            if (task.getUser().equals(user)){
                taskRepo.deleteById(taskId);
            }
            else if(isMyManager(user,task.getUser().getUserId()))taskRepo.deleteById(taskId);
        }
    }

    public Task findById(long id, HttpServletRequest request){
        Optional<Task> foundedTask=taskRepo.findById(id);
        User user = createUser(request);
        if(foundedTask.isPresent() && user!=null){
            Task task=foundedTask.get();
            if(task.getUser().equals(user)) return task;
            else if(isMyManager(user,task.getUser().getUserId())){
                    return task;
                }
        }
        return null;
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
        taskList.removeIf(task -> !task.getUser().equals(user));
        return taskList;
    }

    public List<User> getAllUsers(HttpServletRequest request) {
        User user = createUser(request);
        ArrayList<User> team= new ArrayList<>();
        ArrayList<Manager> managers= managerRepo.findAllByManager(user);
        managers.forEach(manager -> {
            team.add(manager.getWorker());
        });
        return team;
    }

    public boolean deleteFromTeam(HttpServletRequest request, long id) {
        User user = createUser(request);
        managerRepo.deleteByWorkerUserIdAndManagerId(user.getUserId(), id);
        return true;
    }

    public List<Task> getUserTasks(HttpServletRequest request, long id){
        User menago=createUser(request);
        User user=null;
        List<Task> tasks=null;
        if(userRepo.findById(id).isPresent()) user=userRepo.findById(id).get();
        if(user!=null) {
            if (isMyManager(menago,user.getUserId())) tasks = taskRepo.findByUserUserNameOrderByStartTime(user.getUserName());
        }
        return tasks;
    }

    public void saveTask(Task task, HttpServletRequest request, long id) {
        User menago = createUser(request);
        User worker = null;
        if (userRepo.findById(id).isPresent()) worker = userRepo.findById(id).get();
        if (worker!=null) {
            if(isMyManager(menago,id)){
                task.setUser(worker);
                taskRepo.save(task);
            }
        }
    }

    private boolean isMyManager(User manager, long userId){
        List<Manager> managerList = managerRepo.findAllByManager(manager);
        for (Manager managment : managerList) {
            if (managment.getWorker().getUserId() == userId) return true;
        }
        return false;
    }

    public List<User> getListOfUser(HttpServletRequest request) {
        User menago = createUser(request);
        List<User> newUsers = new ArrayList<>();
        for (User u: userRepo.findAll()) {
            if(! isMyManager(menago,u.getUserId()) && u.getUserId()!=menago.getUserId()) newUsers.add(u);
        }
        if(newUsers.size()>0) return newUsers;
        else return null;
    }

    public void addToTeam(HttpServletRequest request, long id) {
        User menago=createUser(request);
        User worker=null;
        if(userRepo.findById(id).isPresent())worker=userRepo.findById(id).get();
        if(worker!=null) {
            if (!isMyManager(menago, id)) {
                Manager manager = new Manager();
                manager.setWorker(worker);
                manager.setManager(menago);
                managerRepo.save(manager);
                worker.setManager(manager);
                userRepo.save(worker);
            }
        }
    }
}
