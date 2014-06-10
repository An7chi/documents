package ee.risthein.erko.dokumendid.repositories;

import ee.risthein.erko.dokumendid.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Erko Risthein
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

    UserAccount findByUsername(String username);

    @Query("select u from UserAccount u where LOWER(u.employee.person.lastName) LIKE LOWER(CONCAT('%',:lastName,'%'))")
    List<UserAccount> findByLastName(@Param("lastName") String lastName);
}
