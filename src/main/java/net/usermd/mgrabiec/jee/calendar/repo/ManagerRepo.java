package net.usermd.mgrabiec.jee.calendar.repo;

import net.usermd.mgrabiec.jee.calendar.model.Manager;
import net.usermd.mgrabiec.jee.calendar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
public interface ManagerRepo extends JpaRepository<Manager,Long> {

    @Modifying
    @Transactional
    @Query(value="DELETE FROM manager WHERE manager.manager_user_id = :managerId AND manager.worker_user_id= :userId",nativeQuery = true)
    void deleteByWorkerUserIdAndManagerId( @Param("managerId") long managerId, @Param("userId") long userId);

    ArrayList<Manager> findAllByManager(User user);
}
