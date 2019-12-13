package net.usermd.mgrabiec.jee.calendar.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;
    private String password;
    private String mail;
    private boolean enabled;

    //TODO add regex and e-mail register

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Task> tasks;

    @OneToMany(mappedBy="worker")
    private List<Manager> workers;

    @OneToOne(mappedBy = "manager")
    private Manager manager;

    public User() {
    }

    public User(String username, String password, String mail) {
        this.userName = username;
        this.password = password;
        this.mail = mail;
    }

    public User(String username, String password, String mail, Set<Task> tasks) {
        this.userName = username;
        this.password = password;
        this.mail = mail;
        this.tasks = tasks;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Manager> getWorkers() {
        return this.workers;
    }

    @Override
    public String toString() {
        String result = String.format(
                "User[id=%d, name='%s' mail='%s']%n",
                this.userId, this.userName, this.mail);
        if (this.tasks != null) {
            for (Task task : this.tasks) {
                result += String.format(
                        "Task[id=%d, name='%s']%n",
                        task.getId(), task.getName());
            }
        }

        return result;
    }

    public void setWorkers(List<Manager> workers) {
        this.workers = workers;
    }

    public Manager getManager() {
        return this.manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
