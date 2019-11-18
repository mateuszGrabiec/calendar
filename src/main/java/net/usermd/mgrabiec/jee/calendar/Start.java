package net.usermd.mgrabiec.jee.calendar;

import net.usermd.mgrabiec.jee.calendar.model.MyTaskRepo;
import net.usermd.mgrabiec.jee.calendar.model.Priority;
import net.usermd.mgrabiec.jee.calendar.model.Status;
import net.usermd.mgrabiec.jee.calendar.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class Start {
    private MyTaskRepo myTaskRepo;

    @Autowired
    public Start(MyTaskRepo myTaskRepo) {
        this.myTaskRepo = myTaskRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runExample(){
        LocalDateTime czas1= LocalDateTime.of(LocalDate.now(),LocalTime.of(0,0));
        LocalDateTime czas11=LocalDateTime.of(LocalDate.now(),LocalTime.of(1,30));
        LocalDateTime czas2=LocalDateTime.of(LocalDate.now(),LocalTime.of(2,0));
        LocalDateTime czas3=LocalDateTime.of(LocalDate.now(),LocalTime.of(3,0));
        LocalDateTime czas4=LocalDateTime.of(LocalDate.now(),LocalTime.of(4,0));
        LocalDateTime czas5=LocalDateTime.of(LocalDate.now(),LocalTime.of(5,0));
        Task task = new Task(czas1,czas2,"zad1","blablabla" ,Status.DONE, Priority.LOW);
        myTaskRepo.save(task);
        task = new Task(czas2,czas3,"zad2","blablabla" ,Status.FREE,Priority.LOW);
        myTaskRepo.save(task);
        task = new Task(czas3,czas4,"zad3","blablabla" ,Status.DURING,Priority.LOW);
        myTaskRepo.save(task);
        task = new Task(czas4,czas5,"zad4","blablabla" ,Status.FREE,Priority.LOW);
        myTaskRepo.save(task);
    }

}