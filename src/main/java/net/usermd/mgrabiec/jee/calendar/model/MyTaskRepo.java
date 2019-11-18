package net.usermd.mgrabiec.jee.calendar.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface MyTaskRepo extends CrudRepository<Task,Long> {

    Task removeTaskById(long id);
    List<Task> findAllByStartTimeBetween(LocalDateTime start,LocalDateTime stop);
}