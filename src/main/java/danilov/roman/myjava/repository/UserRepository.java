package danilov.roman.myjava.repository;

import danilov.roman.myjava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    User findByUsername(String username);

    @Modifying
    @Query("update User u set u.del = true where u.id = :userId")
    int markUserDelete(@Param("userId") int id);

}
