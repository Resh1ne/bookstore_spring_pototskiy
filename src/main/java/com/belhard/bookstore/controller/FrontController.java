package com.belhard.bookstore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
@WebServlet("/controller")
public class FrontController extends HttpServlet {
    public static final String REDIRECT = "redirect:";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page;
        try {
            String command = req.getParameter("command");
            Command controller = AppListener.getContext().getBean(command, Command.class);
            page = controller.execute(req);
            if (page.startsWith(REDIRECT)) {
                resp.sendRedirect(req.getContextPath() + "/" + page.substring(REDIRECT.length()));
                return;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            page = AppListener.getContext().getBean("error", Command.class).execute(req);
        }
        req.getRequestDispatcher(page).forward(req, resp);
    }
}
