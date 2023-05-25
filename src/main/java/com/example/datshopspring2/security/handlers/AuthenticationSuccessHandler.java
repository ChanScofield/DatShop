package com.example.datshopspring2.security.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.io.IOException;

@Component
@SessionAttributes({"logged"})
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        HttpSession session = request.getSession();
        System.out.println(authentication);
        String role = authentication.getAuthorities().stream().findFirst().orElseThrow().getAuthority();
        session.setAttribute("logged", "_" + role.toLowerCase());

        System.out.println(session.getAttribute("logged"));

        response.sendRedirect("/home");

    }
}
