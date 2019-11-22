package net.usermd.mgrabiec.jee.calendar;

import net.usermd.mgrabiec.jee.calendar.model.*;
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

    @Autowired
    public Start(MyTaskRepo myTaskRepo,UserRepo userRepo) {
        this.myTaskRepo = myTaskRepo;
        this.userRepo=userRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runExample(){
        User user = new User("Mati","password","example@examle.com");
        Set<Task> tasks = new HashSet<>();

        LocalDateTime czas1= LocalDateTime.of(LocalDate.now(),LocalTime.of(0,0));
        LocalDateTime czas11=LocalDateTime.of(LocalDate.now(),LocalTime.of(1,30));
        LocalDateTime czas2=LocalDateTime.of(LocalDate.now(),LocalTime.of(2,0));
        LocalDateTime czas3=LocalDateTime.of(LocalDate.now(),LocalTime.of(3,0));
        LocalDateTime czas4=LocalDateTime.of(LocalDate.now(),LocalTime.of(4,0));
        LocalDateTime czas5=LocalDateTime.of(LocalDate.now(),LocalTime.of(5,0));
        Task task = new Task(czas1,czas2,"zad1","blablabla" , Status.DONE, Priority.LOW,user);
//        myTaskRepo.save(task);
        Task task1 = new Task(czas2,czas3,"zad2","blablabla" ,Status.FREE,Priority.LOW,user);
//        myTaskRepo.save(task);
        Task task2 = new Task(czas3,czas4,"zad3","blablabla" ,Status.DURING,Priority.LOW,user);
//        myTaskRepo.save(task);
        Task task3 = new Task(czas4,czas5,"zad4","blablabla" ,Status.FREE,Priority.LOW,user);
//        myTaskRepo.save(task);
        tasks.add(task);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        user.setTasks(tasks);

        System.out.println(user.getTasks().size());

        userRepo.save(user);

//                userRepo.findAll().forEach(u -> {
//                   u.getTasks().forEach(System.out::println);
//                });



        for (User u : userRepo.findAll()) {
            System.out.println(u);
        }
    }

}