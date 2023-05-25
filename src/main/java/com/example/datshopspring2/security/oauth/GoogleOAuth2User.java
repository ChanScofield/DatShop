package com.example.datshopspring2.security.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleOAuth2User extends DefaultOAuth2User {
    public GoogleOAuth2User(OAuth2User oAuth2User) {
        super(oAuth2User.getAuthorities(), oAuth2User.getAttributes(), "email");
    }

    public GoogleOAuth2User(OAuth2User oAuth2User, Map<String, Object> map) {
        super(oAuth2User.getAuthorities(), map, "email");
    }

    public GoogleOAuth2User(List<GrantedAuthority> authorities, OAuth2User oAuth2User) {
        super(authorities, oAuth2User.getAttributes(), "email");
    }

    public String getEmail() {
        return getAttribute("email");
    }

    public String getPicture() {
        return getAttribute("picture");
    }

    public String getUsername() {
        return getAttribute("name");
    }


}
