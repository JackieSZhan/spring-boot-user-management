package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //     SELECT * FROM User WHERE FullName = ?
    List<User> findByFullName(String fullName);

    //     SELECT * FROM User WHERE phone = ?
    List<User> findByPhone(String phone);

    //     Email should be unique, so we expect at most one result
    //     SELECT * FROM User WHERE email = ?
    Optional<User> findByEmail(String email);

    // --- A custom JPQL query for something method-name derivation can't express cleanly:
    //     "find all User that are active or inactive" (below a given threshold)
    @Query("SELECT u FROM User u WHERE u.active = :threshold")
    List<User> findUserStatus(@Param("threshold") Boolean threshold);


}
