package com.example.datshopspring2.security.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;


public class GithubOAuth2User extends DefaultOAuth2User {
    public GithubOAuth2User(OAuth2User oAuth2User) {
        super(oAuth2User.getAuthorities(), oAuth2User.getAttributes(), "login");
    }

    public GithubOAuth2User(OAuth2User oAuth2User, Map<String, Object> map) {
        super(oAuth2User.getAuthorities(), map, "login");
    }

    public GithubOAuth2User(List<GrantedAuthority> authorities, OAuth2User oAuth2User) {
        super(authorities, oAuth2User.getAttributes(), "login");
    }

    public String getLogin() {
        return getAttribute("login");
    }

    public String getAvatar() {
        return getAttribute("avatar_url");
    }

}
