package com.example.datshopspring2.security.details;

import com.example.datshopspring2.dto.ChangePasswordDto;
import com.example.datshopspring2.dto.SignUpDto;
import com.example.datshopspring2.exceptions.UserNotFoundException;
import com.example.datshopspring2.models.User;
import com.example.datshopspring2.models.UserProfile;
import com.example.datshopspring2.models.enums.AuthenticationProvider;
import com.example.datshopspring2.models.enums.Role;
import com.example.datshopspring2.models.enums.State;
import com.example.datshopspring2.repositories.UserProfileRepository;
import com.example.datshopspring2.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@SessionAttributes("auth")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final HttpSession httpSession;

    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmailAndAuthenticationProvider(email, AuthenticationProvider.LOCAL).orElseThrow(
                () -> new UserNotFoundException(email + " not found", 404)
        );
        httpSession.setAttribute("auth", AuthenticationProvider.LOCAL);

        return new UserDetailsImpl(user);
    }


    public User findAccountByAccountId(Long sellerId) {
        return userRepository.findUserByUserId(sellerId).orElseThrow(
                () -> new UserNotFoundException("User not found", 404)
        );
    }


    public User findUserByEmailAndAuthenticationProvider(String email, AuthenticationProvider auth) {
        return userRepository.findUserByEmailAndAuthenticationProvider(email, auth).orElseThrow(
                () -> new UserNotFoundException(email + auth + " not found", 404)
        );
    }


    public Map<String, String> updatePassword(UserProfile userProfile, ChangePasswordDto changePasswordDto) {
        String email = userProfile.getEmail();
        String rootPassword = userRepository.findPasswordByEmail(email);
        String oldPassword = changePasswordDto.getOldPassword();
        String newPassword = changePasswordDto.getNewPassword();

        Map<String, String> map = new HashMap<>();

        if (!passwordEncoder.matches(oldPassword, rootPassword)) {
            map.put("oldPassword", "Password not correct");
            return map;
        }

        if (oldPassword.equals(newPassword)) {
            map.put("newPassword", "Same old password");
            return map;
        }

        User user = userRepository.findUserByUserId(userProfile.getProfileId()).orElseThrow(
                () -> new UserNotFoundException("User not found", 404)
        );
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return map;

    }


    public void updateSeller(Long eid, String isSeller) {
        User user = userRepository.findUserByUserId(eid).orElseThrow(
                () -> new UserNotFoundException("User not found", 404)
        );
        if (isSeller != null) {
            user.setRole(Role.SELLER);
        } else {
            user.setRole(Role.USER);
        }
        userRepository.save(user);
    }


    public void saveAccount(SignUpDto accountToSave) {
        User user = User.builder()
                .email(accountToSave.getEmail())
                .password(passwordEncoder.encode(accountToSave.getPassword()))
                .role(Role.USER)
                .state(State.CONFIRMED)
                .authenticationProvider(AuthenticationProvider.LOCAL)
                .build();

        userRepository.save(user);

        User userSaved = userRepository.findUserByEmailAndAuthenticationProvider(user.getEmail(), AuthenticationProvider.LOCAL).orElseThrow();

        saveNewUserProfile(accountToSave.getUsername(), userSaved);
    }

    public void saveNewGitHubOAuth2User(String loginName) {
        User user = User.builder()
                .email(loginName)
                .authenticationProvider(AuthenticationProvider.GITHUB)
                .state(State.CONFIRMED)
                .role(Role.USER)
                .build();

        userRepository.save(user);

        saveNewUserProfile(loginName, user, AuthenticationProvider.GITHUB);
    }

    public void saveNewGoogleOAuth2User(String email, String name) {
        User user = User.builder()
                .email(email)
                .authenticationProvider(AuthenticationProvider.GOOGLE)
                .state(State.CONFIRMED)
                .role(Role.USER)
                .build();

        userRepository.save(user);

        saveNewUserProfile(name, user, AuthenticationProvider.GOOGLE);
    }

    public User findAccountByEmailAndAndAuthenticationProvider(String email, AuthenticationProvider auth) {
        try {
            return userRepository.findUserByEmailAndAuthenticationProvider(email, auth).orElseThrow(
                    () -> new UsernameNotFoundException("Email <" + email + "> not found")
            );
        } catch (UsernameNotFoundException e) {
            return null;
        }
    }

    private void saveNewUserProfile(String username, User userSaved) {
        saveNewUserProfile(username, userSaved, AuthenticationProvider.LOCAL);
    }

    private void saveNewUserProfile(String username, User user, AuthenticationProvider auth) {
        UserProfile userProfile = UserProfile.builder()
                .profileId(userRepository.findUserByEmailAndAuthenticationProvider(user.getEmail(), auth).orElseThrow().getUserId())
                .email(user.getEmail())
                .userName(username)
                .age(0)
                .city("Moscow")
                .phoneNumber("0")
                .sex("Male")
                .balance(0)
                .build();

        userProfileRepository.save(userProfile);
    }

    public void updateGitHubOAuth2User(String loginName) {

    }

    public void deleteUserById(Long eid) {
        User user = userRepository.findUserByUserId(eid).orElseThrow(
                () -> new UserNotFoundException("User id: " + eid + " not found", 404)
        );

        user.setState(State.DELETED);

        userRepository.save(user);
    }
}
