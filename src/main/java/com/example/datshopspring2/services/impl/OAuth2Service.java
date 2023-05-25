package com.example.datshopspring2.services.impl;

import com.example.datshopspring2.models.User;
import com.example.datshopspring2.models.enums.AuthenticationProvider;
import com.example.datshopspring2.security.details.UserDetailsServiceImpl;
import com.example.datshopspring2.security.oauth.GithubOAuth2User;
import com.example.datshopspring2.security.oauth.GoogleOAuth2User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Service
@SessionAttributes({"auth"})
@RequiredArgsConstructor
public class OAuth2Service extends DefaultOAuth2UserService {

    private final HttpSession httpSession;

    private final UserDetailsServiceImpl userDetailsService;


    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String clientName = userRequest.getClientRegistration().getClientName();

        httpSession.setAttribute("auth", AuthenticationProvider.valueOf(clientName.toUpperCase()));

        OAuth2User oAuth2User = null;

        String role = "";
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (clientName.equals("GitHub")) {
            oAuth2User = new GithubOAuth2User(super.loadUser(userRequest));
            role += checkGitHubUserAndReturnRole((GithubOAuth2User) oAuth2User);

            authorities.add(new SimpleGrantedAuthority(role));

            oAuth2User = new GithubOAuth2User(authorities, super.loadUser(userRequest));
        } else if (clientName.equals("Google")) {
            oAuth2User = new GoogleOAuth2User(super.loadUser(userRequest));
            role += checkGoogleUserAndReturnRole((GoogleOAuth2User) oAuth2User);

            authorities.add(new SimpleGrantedAuthority(role));

            oAuth2User = new GoogleOAuth2User(authorities, super.loadUser(userRequest));
        }

        return oAuth2User;
    }

    private String checkGitHubUserAndReturnRole(GithubOAuth2User githubOAuth2User) {
        String loginName = githubOAuth2User.getLogin();

        User user = userDetailsService.findAccountByEmailAndAndAuthenticationProvider(loginName, AuthenticationProvider.GITHUB);
        if (user == null) {
            userDetailsService.saveNewGitHubOAuth2User(loginName);
            return "user";
        }

        return user.getRole().toString();
    }

    private String checkGoogleUserAndReturnRole(GoogleOAuth2User googleOAuth2User) {
        String email = googleOAuth2User.getEmail();

        User user = userDetailsService.findAccountByEmailAndAndAuthenticationProvider(email, AuthenticationProvider.GOOGLE);

        if (user == null) {
            userDetailsService.saveNewGoogleOAuth2User(email, googleOAuth2User.getUsername());
            return "user";
        }

        return user.getRole().toString();
    }
}
