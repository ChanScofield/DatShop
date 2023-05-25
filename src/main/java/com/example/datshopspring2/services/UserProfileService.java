package com.example.datshopspring2.services;

import com.example.datshopspring2.dto.ChangeProfileDto;
import com.example.datshopspring2.models.UserProfile;
import com.example.datshopspring2.models.enums.AuthenticationProvider;

import java.util.List;

public interface UserProfileService {
    UserProfile findProfileByUserId(Long accountId);

    String updateProfile(UserProfile userProfile, ChangeProfileDto changeProfileDTO);

    String updateBalance(UserProfile userProfile, Integer money);

    List<UserProfile> findAllConfirmedUserProfile();

    List<UserProfile> findAllConfirmedEmployeesProfile();

    void deleteUserById(Long eid);

    void buy(Integer total, UserProfile userProfile);

    UserProfile findProfileByEmailAndAuth(String username, AuthenticationProvider auth);

    boolean findProfileByUsername(String username);
}
