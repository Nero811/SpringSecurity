package com.example.demo.movie_security.filter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserLoginFilter extends OncePerRequestFilter  {

    Logger log = LoggerFactory.getLogger(UserLoginFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.equals("/userLogin")) {
            String userAgent = request.getHeader("User-Agent");
            String user = request.getUserPrincipal().getName();
            Date date = new Date();
            log.info("[LOG] " + date + ", User: " + user + ", login from: " + userAgent);
        }
        filterChain.doFilter(request, response);
    }
}
