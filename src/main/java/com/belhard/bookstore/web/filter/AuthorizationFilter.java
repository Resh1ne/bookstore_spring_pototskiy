package com.belhard.bookstore.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class AuthorizationFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (!req.getRequestURI().equals("/login") && !req.getRequestURI().equals("/")
                && !req.getRequestURI().endsWith(".css") && !req.getRequestURI().equals("/users/create")) {
            if (extracted(req, res)) return;
        }
        chain.doFilter(req, res);
    }

    private static boolean extracted(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Object user = req.getSession().getAttribute("user");

        if (user == null) {
            res.sendRedirect("/login");
            return true;
        }

        return false;
    }
}
