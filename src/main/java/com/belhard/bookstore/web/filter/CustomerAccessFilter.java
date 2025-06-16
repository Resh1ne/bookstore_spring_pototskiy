package com.belhard.bookstore.web.filter;

import com.belhard.bookstore.data.entity.enums.Role;
import com.belhard.bookstore.service.dto.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(4)
public class CustomerAccessFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        if (requestUrlIsTrue(req)) {
            if (extracted(req, res)) return;
        }
        chain.doFilter(req, res);
    }

    private static boolean requestUrlIsTrue(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        Pattern bookIdPattern = Pattern.compile("^/books/\\d+$");
        Pattern orderAddBookPattern = Pattern.compile("^/orders/add/\\d+$");
        Pattern orderPayPattern = Pattern.compile("^/orders/\\d+/pay$");
        Pattern orderRemoveBookPattern = Pattern.compile("^/orders/remove/\\d+$");

        Matcher bookMatcher = bookIdPattern.matcher(requestURI);
        Matcher orderAddBookMatcher = orderAddBookPattern.matcher(requestURI);
        Matcher orderPayMatcher = orderPayPattern.matcher(requestURI);
        Matcher orderRemoveBookMatcher = orderRemoveBookPattern.matcher(requestURI);

        return !requestURI.equals("/login")
                && !req.getRequestURI().equals("/logout")
                && !requestURI.equals("/")
                && !requestURI.endsWith(".css")
                && !requestURI.endsWith(".jpg")
                && !requestURI.equals("/books")
                && !requestURI.equals("/orders/my")
                && !bookMatcher.matches()
                && !orderAddBookMatcher.matches()
                && !orderPayMatcher.matches()
                && !orderRemoveBookMatcher.matches();
    }

    private static boolean extracted(HttpServletRequest req, HttpServletResponse res) throws IOException {
        UserDto user = (UserDto) req.getSession().getAttribute("user");

        if (user == null || user.getRole().equals(Role.CUSTOMER)) {
            res.sendRedirect("/");
            return true;
        }

        return false;
    }
}