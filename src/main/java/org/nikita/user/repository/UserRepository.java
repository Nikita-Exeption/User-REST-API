package org.nikita.user.repository;

import org.nikita.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
/**
 * @author Nikita Gladkov
 * @version 1.0
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "Select * from users_details where birthday Between ?1 AND ?2",
    nativeQuery = true)
    List<User> findByBirthdayBetweenFromAndTo(LocalDate from, LocalDate to);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

}
