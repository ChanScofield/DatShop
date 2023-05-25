package com.example.datshopspring2.services.impl;

import com.example.datshopspring2.dto.ChangeProfileDto;
import com.example.datshopspring2.exceptions.NotFoundException;
import com.example.datshopspring2.exceptions.UserNotFoundException;
import com.example.datshopspring2.models.User;
import com.example.datshopspring2.models.UserProfile;
import com.example.datshopspring2.models.enums.AuthenticationProvider;
import com.example.datshopspring2.models.enums.State;
import com.example.datshopspring2.repositories.UserProfileRepository;
import com.example.datshopspring2.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfile findProfileByUserId(Long accountId) {
        return userProfileRepository.findProfileByProfileId(accountId).orElseThrow(
                () -> new UserNotFoundException("User id: " + accountId + " not found", 404)
        );
    }

    @Override
    public String updateProfile(UserProfile userProfile, ChangeProfileDto changeProfileDTO) {
        String result = "";

        String regex = "((8|\\+7)-?)?\\(?\\d{3}\\)?-?\\d-?\\d-?\\d-?\\d-?\\d-?\\d-?\\d";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(changeProfileDTO.getPhoneNumber());

        userProfile.setUserName(changeProfileDTO.getUsername());
        userProfile.setCity(changeProfileDTO.getCity());
        userProfile.setSex(changeProfileDTO.getSex());
        userProfile.setAge(changeProfileDTO.getAge());

        if (!matcher.matches()) {
            result += "number not correct";
            System.out.println("sai");
        } else {
            userProfile.setPhoneNumber(changeProfileDTO.getPhoneNumber());
            System.out.println("dung");
        }

        userProfileRepository.save(userProfile);

        return result;
    }

    @Override
    public String updateBalance(UserProfile userProfile, Integer money) {
        if (money < 1) {
            return "Must be greater than 0";
        } else {
            userProfile.setBalance(userProfile.getBalance() + money);
            userProfileRepository.save(userProfile);
            return "Done";
        }
    }

    @Override
    public List<UserProfile> findAllConfirmedUserProfile() {
        return userProfileRepository.findAllByState(State.CONFIRMED);
    }

    @Override
    public List<UserProfile> findAllConfirmedEmployeesProfile() {
        return userProfileRepository.findAllEmployeesByState(State.CONFIRMED);
    }

    @Override
    public void deleteUserById(Long eid) {
        userProfileRepository.deleteById(eid);
    }

    @Override
    public void buy(Integer total, UserProfile userProfile) {
        userProfile.setBalance(userProfile.getBalance() - total);
        userProfileRepository.save(userProfile);
    }

    @Override
    public UserProfile findProfileByEmailAndAuth(String email, AuthenticationProvider auth) {
        return userProfileRepository.findProfileByEmail(email).orElseThrow(
                () -> new UserNotFoundException(email + " not found", 404)
        );
    }

    @Override
    public boolean findProfileByUsername(String username) {
        try {
            userProfileRepository.findProfileByUserName(username);
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }
}
