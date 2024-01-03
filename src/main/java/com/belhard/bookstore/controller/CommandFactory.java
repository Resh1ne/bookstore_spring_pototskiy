package com.belhard.bookstore.controller;

import com.belhard.bookstore.controller.impl.BookCommand;
import com.belhard.bookstore.controller.impl.BooksCommand;
import com.belhard.bookstore.controller.impl.CreateBookCommand;
import com.belhard.bookstore.controller.impl.CreateBookFormCommand;
import com.belhard.bookstore.controller.impl.CreateUserCommand;
import com.belhard.bookstore.controller.impl.CreateUserFormCommand;
import com.belhard.bookstore.controller.impl.EditBookCommand;
import com.belhard.bookstore.controller.impl.EditBookFormCommand;
import com.belhard.bookstore.controller.impl.EditUserCommand;
import com.belhard.bookstore.controller.impl.EditUserFormCommand;
import com.belhard.bookstore.controller.impl.ErrorCommand;
import com.belhard.bookstore.controller.impl.UserCommand;
import com.belhard.bookstore.controller.impl.UsersCommand;
import com.belhard.bookstore.data.connection.DataSource;
import com.belhard.bookstore.data.connection.impl.DataSourceImpl;
import com.belhard.bookstore.data.dao.BookDao;
import com.belhard.bookstore.data.dao.UserDao;
import com.belhard.bookstore.data.dao.impl.BookDaoImpl;
import com.belhard.bookstore.data.dao.impl.UserDaoImpl;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.impl.BookServiceImpl;
import com.belhard.bookstore.service.impl.UserServiceImpl;
import com.belhard.bookstore.util.PropertiesManager;
import com.belhard.bookstore.util.impl.PropertiesManagerImpl;
import lombok.extern.log4j.Log4j2;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class CommandFactory implements Closeable {
    public static final CommandFactory INSTANCE = new CommandFactory();
    private final Map<String, Command> controllers;
    private final List<Closeable> closeables;

    private CommandFactory() {
        DataSource dataSource = getDataSource();
        closeables = new ArrayList<>();
        closeables.add(dataSource);
        UserDao userDao = new UserDaoImpl(dataSource);
        UserService userService = new UserServiceImpl(userDao);

        BookDao bookDao = new BookDaoImpl(dataSource);
        BookService bookService = new BookServiceImpl(bookDao);

        controllers = new HashMap<>();
        controllers.put("user", new UserCommand(userService));
        controllers.put("users", new UsersCommand(userService));
        controllers.put("books", new BooksCommand(bookService));
        controllers.put("book", new BookCommand(bookService));
        controllers.put("error", new ErrorCommand());
        controllers.put("create_user_form", new CreateUserFormCommand());
        controllers.put("create_user", new CreateUserCommand(userService));
        controllers.put("edit_user_form", new EditUserFormCommand(userService));
        controllers.put("edit_user", new EditUserCommand(userService));
        controllers.put("create_book", new CreateBookCommand(bookService));
        controllers.put("create_book_form", new CreateBookFormCommand());
        controllers.put("edit_book", new EditBookCommand(bookService));
        controllers.put("edit_book_form", new EditBookFormCommand(bookService));
    }

    private static DataSource getDataSource() {
        PropertiesManager propertiesManager = new PropertiesManagerImpl("/app.properties");
        String profile = propertiesManager.getKey("my.app.profile");
        String url = propertiesManager.getKey("my.app.db." + profile + ".url");
        String user = propertiesManager.getKey("my.app.db." + profile + ".user");
        String password = propertiesManager.getKey("my.app.db." + profile + ".password");
        String driver = propertiesManager.getKey("my.app.db." + profile + ".driver");
        return new DataSourceImpl(password, user, url, driver);
    }

    public Command get(String command) {
        Command controller = controllers.get(command);
        if (controller == null) {
            return controllers.get("error");
        }
        return controller;
    }

    @Override
    public void close() {
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
