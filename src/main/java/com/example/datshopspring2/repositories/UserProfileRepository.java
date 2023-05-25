package com.example.datshopspring2.repositories;

import com.example.datshopspring2.models.UserProfile;
import com.example.datshopspring2.models.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    @Query("select u from UserProfile u order by u.profileId")
    List<UserProfile> findAll();

    @Query(value = "select u from UserProfile u where u.profileId in (select u.userId from User u where u.state = 'CONFIRMED')")
    List<UserProfile> findAllByState(State state);

    @Query(value = "select u from UserProfile u where u.profileId in (select u.userId from User u where u.state = 'CONFIRMED' and u.role != 'ADMIN')")
    List<UserProfile> findAllEmployeesByState(State state);

    Optional<UserProfile> findProfileByProfileId(Long id);

    Optional<UserProfile> findProfileByUserName(String username);

    Optional<UserProfile> findProfileByEmail(String email);
}
