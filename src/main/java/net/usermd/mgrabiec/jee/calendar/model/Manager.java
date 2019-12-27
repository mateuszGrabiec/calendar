package net.usermd.mgrabiec.jee.calendar.model;

import javax.persistence.*;

@Entity
@Table
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long managerId;

    @ManyToOne
    private User worker;


    @OneToOne
    private User manager;

    public Manager() {
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }
}
