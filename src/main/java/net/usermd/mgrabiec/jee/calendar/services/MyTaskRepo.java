package net.usermd.mgrabiec.jee.calendar.services;

import net.usermd.mgrabiec.jee.calendar.model.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface MyTaskRepo extends CrudRepository<Task,Long> {

    @Modifying
    @Query("delete from task t where t.id=:id")
    void deleteById(long id);
    List<Task> findAllByStartTimeBetween(LocalDateTime start,LocalDateTime stop);
    List<Task> findByUserUserNameOrderByStartTime(String userName);
}