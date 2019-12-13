package net.usermd.mgrabiec.jee.calendar.services;

import net.usermd.mgrabiec.jee.calendar.model.Manager;
import net.usermd.mgrabiec.jee.calendar.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ManagerRepo extends CrudRepository<Manager,Long> {


    ArrayList<Manager> findAllByManager(User user);
}
