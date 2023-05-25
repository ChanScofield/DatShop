package com.example.datshopspring2.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.io.IOException;

//@Component
@SessionAttributes({"account", "seller", "admin"})
public class SessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String url = httpServletRequest.getServletPath();
        if (session.getAttribute("account") == null) {
            if (url.startsWith("/profile") || url.startsWith("/purchase-history") || url.startsWith("/manage-employees") || url.startsWith("/manage-product")) {
                httpServletResponse.sendRedirect("/home");
                return;
            }

        } else {
            if (url.equals("/sign-in") || url.equals("/sign-up")) {
                httpServletResponse.sendRedirect("/home");
                return;
            }

            if (session.getAttribute("admin") == null || session.getAttribute("admin").equals(false)) {
                if (url.startsWith("/manage-employees") || url.equals("/purchase-history/global-history")) {
                    httpServletResponse.sendRedirect("/home");
                    return;
                }
                if (session.getAttribute("seller") == null || session.getAttribute("seller").equals(false)) {
                    if (url.startsWith("/manage-product")) {
                        httpServletResponse.sendRedirect("/home");
                        return;
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
