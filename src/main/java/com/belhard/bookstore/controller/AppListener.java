package com.belhard.bookstore.controller;

import com.belhard.bookstore.AppContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebListener
@Log4j2
public class AppListener implements ServletContextListener {
    @Getter
    private static AnnotationConfigApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Context init event");
        context = new AnnotationConfigApplicationContext(AppContext.class);
        log.info("Created");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Context destroy event");
        if(context != null) {
            context.close();
        }
    }
}
