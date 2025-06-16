package com.belhard.bookstore.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
@Log4j2
public class AuthorizationFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        if (requestUrlIsTrue(req)) {
            if (extracted(req, res)) return;
        }
        chain.doFilter(req, res);
    }

    private static boolean requestUrlIsTrue(HttpServletRequest req) {
        return !req.getRequestURI().endsWith(".css") && !req.getRequestURI().endsWith(".png")
                && !req.getRequestURI().equals("/login")
                && !req.getRequestURI().equals("/logout")
                && !req.getRequestURI().equals("/")
                && !req.getRequestURI().equals("/users/create");
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
