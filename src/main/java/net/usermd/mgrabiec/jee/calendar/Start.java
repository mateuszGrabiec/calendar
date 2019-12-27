package net.usermd.mgrabiec.jee.calendar;

import net.usermd.mgrabiec.jee.calendar.model.*;
import net.usermd.mgrabiec.jee.calendar.repo.ManagerRepo;
import net.usermd.mgrabiec.jee.calendar.repo.MyTaskRepo;
import net.usermd.mgrabiec.jee.calendar.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class Start {
    private MyTaskRepo myTaskRepo;
    private UserRepo userRepo;
    private ManagerRepo managerRepo;

    @Autowired
    public Start(MyTaskRepo myTaskRepo,UserRepo userRepo,ManagerRepo managerRepo) {
        this.myTaskRepo = myTaskRepo;
        this.userRepo=userRepo;
        this.managerRepo=managerRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runExample() {
        User user = new User("admin", "admin", "example@examle.com");

//        user.setEnabled(true);
        Set<Task> tasks = new HashSet<>();

        LocalDateTime czas1 = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
        LocalDateTime czas11 = LocalDateTime.of(LocalDate.now(), LocalTime.of(1, 30));
        LocalDateTime czas2 = LocalDateTime.of(LocalDate.now(), LocalTime.of(2, 0));
        LocalDateTime czas3 = LocalDateTime.of(LocalDate.now(), LocalTime.of(3, 0));
        LocalDateTime czas4 = LocalDateTime.of(LocalDate.now(), LocalTime.of(4, 0));
        LocalDateTime czas5 = LocalDateTime.of(LocalDate.now(), LocalTime.of(5, 0));
        Task task = new Task(czas1, czas2, "zad1", "blablabla", Status.DONE, Priority.LOW, user);
//        myTaskRepo.save(task);
        Task task1 = new Task(czas2, czas3, "zad2", "blablabla", Status.FREE, Priority.LOW, user);
//        myTaskRepo.save(task);
        Task task2 = new Task(czas3, czas4, "zad3", "blablabla", Status.DURING, Priority.LOW, user);
//        myTaskRepo.save(task);
        Task task3 = new Task(czas4, czas5, "zad4", "blablabla", Status.FREE, Priority.LOW, user);
//        myTaskRepo.save(task);
        tasks.add(task);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        user.setTasks(tasks);

        userRepo.save(user);


        //myTaskRepo.findByUserUserNameOrderByStartTime(user.getUserName()).forEach(System.out::println);

        User user1 = new User("user", "user", "example1@examle.com");
//        user.setEnabled(true);
        Set<Task> tasks1 = new HashSet<>();
        tasks1.add(new Task(czas11, czas5, "zad5", "sdadsa", Status.FREE, Priority.LOW, user1));

        user1.setTasks(tasks1);
        userRepo.save(user1);


        Manager manager = new Manager();
        manager.setWorker(user1);
        manager.setManager(user);
        managerRepo.save(manager);
        user1.setManager(manager);
        userRepo.save(user1);
    }

}