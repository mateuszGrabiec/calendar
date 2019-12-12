package net.usermd.mgrabiec.jee.calendar.services;

import net.usermd.mgrabiec.jee.calendar.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User,Long> {

    Optional<User> findByUserName(String userName);
    User findByManager(User user);
}
