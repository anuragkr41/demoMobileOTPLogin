package com.sms.demo.repository;

import com.sms.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

//    @Query(value = "SELECT * FROM pictu_users WHERE username = :username", nativeQuery = true)
//    Optional<PictuUser> findByUsername(@Param("username") String username);

    Optional<User> findByUsername(String username);

}
