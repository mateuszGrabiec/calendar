package net.usermd.mgrabiec.jee.calendar.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime  endTime;
    private String content;
    private String name;
    private Status status;
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    public Task() {
    }

    //getters
    public LocalDateTime getStartTime() { return this.startTime; }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public String getContent() { return content; }

    public long getId() { return this.id; }

    public Priority getPriority() {
        return this.priority;
    }

    public Status getStatus() {
        return this.status;
    }

    public String getName() {
        return this.name;
    }

    public User getUser() {
        return user;
    }

    //setters

    public boolean setStartTime(LocalDateTime startTime) {
        boolean isSet = false;
        try {
            this.startTime = startTime;
            isSet = true;
        } catch (IllegalArgumentException exception) {//
        }
        return isSet;
    }

    public boolean setEndTime(LocalDateTime endTime) {
        try {
            this.endTime = endTime;
        } catch (IllegalArgumentException exception) {//
            return false;
        }
        return true;
    }

    public boolean setPriority(Priority priority) {
        try {
            this.priority = priority;
        } catch (IllegalArgumentException exception) {//
            return false;
        }
        return true;
    }

    public boolean setStatus(Status status) {
        try {
            this.status = status;
        } catch (IllegalArgumentException exception) {//
            return false;
        }
        return true;
    }

    public boolean setId(long id) {
        try {
            this.id = id;
        } catch (IllegalArgumentException exception) {//
            return false;
        }
        return true;
    }

    public boolean setContent(String content) {
        try {
            this.content = content;
        } catch (IllegalArgumentException exception) {//
            return false;
        }
        return true;
    }

    public boolean setName(String name) {
        try {
            this.name = name;
        } catch (IllegalArgumentException exception) {//
            return false;
        }
        return true;
    }

    public boolean setUser(User user) {
        try {
            this.user = user;
        } catch (IllegalArgumentException exception) {//
            return false;
        }
        return true;
    }



    public Task(long id, LocalDateTime startTime, LocalDateTime endTime, String name, String content, Status status, Priority priority) {
        this.id = id;
        this.endTime = endTime;
        this.startTime = startTime;
        this.name = name;
        this.content = content;
        this.status = status;
        this.priority = priority;
    }

    public Task(LocalDateTime startTime, LocalDateTime endTime, String name, String content, Status status, Priority priority) {
        this.endTime = endTime;
        this.startTime = startTime;
        this.name = name;
        this.content = content;
        this.status = status;
        this.priority = priority;
    }

    public Task(LocalDateTime startTime, LocalDateTime endTime, String name, String content , Status status, Priority priority, User user) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.content = content;
        this.name = name;
        this.status = status;
        this.priority = priority;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                '}';
    }
}
