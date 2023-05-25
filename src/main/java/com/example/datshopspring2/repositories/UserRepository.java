package com.example.datshopspring2.repositories;

import com.example.datshopspring2.models.User;
import com.example.datshopspring2.models.enums.AuthenticationProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);


    Optional<User> findUserByEmailAndPassword(String email, String password);

    Optional<User> findUserByUserId(Long sellerId);


    @Query("select u.password from User u where u.email = ?1")
    String findPasswordByEmail(String email);

    @Query("select u from User u where u.email = ?1 and u.authenticationProvider = ?2")
    Optional<User> findUserByEmailAndAuthenticationProvider(String email, AuthenticationProvider auth);
}