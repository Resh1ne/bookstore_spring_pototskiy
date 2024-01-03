package com.belhard.bookstore.controller;

import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest req);
}
