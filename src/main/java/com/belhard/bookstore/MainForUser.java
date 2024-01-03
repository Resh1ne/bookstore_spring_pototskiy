package com.belhard.bookstore;

import com.belhard.bookstore.data.connection.DataSource;
import com.belhard.bookstore.data.dao.UserDao;
import com.belhard.bookstore.data.entity.enums.Role;
import com.belhard.bookstore.data.connection.impl.DataSourceImpl;
import com.belhard.bookstore.data.dao.impl.UserDaoImpl;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.service.impl.UserServiceImpl;
import com.belhard.bookstore.util.PropertiesManager;
import com.belhard.bookstore.util.impl.PropertiesManagerImpl;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainForUser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDao userDao = getUserDao();
        UserService userService = new UserServiceImpl(userDao);

        Pattern pattern = Pattern.compile("\\d+");
        System.out.println(userDao.countAll());

        while (true) {
            printMenu();

            String userInput = scanner.nextLine();
            Matcher matcher = pattern.matcher(userInput);
            String command = "";
            long id = 0;
            if (matcher.find()) {
                command = matcher.group();
                id = Long.parseLong(command);
                command = matcher.replaceAll("");
            }

            if (!usingMenu(userInput, id, command, scanner, userService)) {
                return;
            }
        }
    }

    private static UserDao getUserDao() {
        PropertiesManager propertiesManager = new PropertiesManagerImpl("/app.properties");
        String profile = propertiesManager.getKey("my.app.profile");
        String url = propertiesManager.getKey("my.app.db." + profile + ".url");
        String user = propertiesManager.getKey("my.app.db." + profile + ".user");
        String password = propertiesManager.getKey("my.app.db." + profile + ".password");
        String driver = propertiesManager.getKey("my.app.db." + profile + ".driver");
        DataSource dataSource = new DataSourceImpl(password, user, url, driver);
        return new UserDaoImpl(dataSource);
    }

    private static void printMenu() {
        String commandAll = "\u001B[35m" + "/all" + "\u001B[0m\n";
        String commandGet = "\u001B[35m" + "/get{id}" + "\u001B[0m\n";
        String commandDelete = "\u001B[35m" + "/delete{id}" + "\u001B[0m\n";
        String commandExit = "\u001B[35m" + "/exit" + "\u001B[0m\n";
        String commandCreate = "\u001B[35m" + "/create" + "\u001B[0m\n";
        String commandUpdate = "\u001B[35m" + "/update{id}" + "\u001B[0m\n";
        System.out.print("-------Menu-------\n" +
                "~To view all the books, enter: " + commandAll +
                "~To update the book, enter: " + commandUpdate +
                "~To display detailed information about the book, enter: " + commandGet +
                "~To delete a book, enter: " + commandDelete +
                "~To create a book, enter: " + commandCreate +
                "~To exit, enter: " + commandExit);
    }

    private static boolean usingMenu(String userInput, Long id, String command, Scanner scanner, UserService userService) {
        if (id > 0 && "/update{}".equals(command)) {
            UserDto user = updateUser(scanner, userService, id);
            System.out.println(userService.update(user).toString());
        } else if (userInput.equals("/all")) {
            List<UserDto> users = userService.getAll();
            for (UserDto user : users) {
                System.out.println(user.toString());
            }
        } else if (userInput.equals("/exit")) {
            return false;
        } else if (id > 0 && "/get{}".equals(command)) {
            UserDto user = userService.getById(id);
            System.out.println(user);
        } else if (id > 0 && "/delete{}".equals(command)) {
            userService.delete(id);
        } else if (userInput.equals("/create")) {
            UserDto user = createUserWithoutID(scanner);
            UserDto createdUser = userService.create(user);
            System.out.println(createdUser.toString());
        } else {
            System.out.println("Incorrect command!");
        }
        return true;
    }

    private static UserDto updateUser(Scanner scanner, UserService userService, long id) {
        while (true) {
            if (userService.getById(id) == null) {
                System.out.println("There is no user with this id! Enter it again!");
                id = scanner.nextLong();
                //Here, the scanner is called to clear the scanner's clipboard of \n that was left after the call scanner.NextLong()
                scanner.nextLine();
                continue;
            }
            break;
        }
        UserDto user = createUserWithoutID(scanner);
        user.setId(id);
        return user;
    }

    private static UserDto createUserWithoutID(Scanner scanner) {
        UserDto user = new UserDto();
        setFirstName(scanner, user);
        setLastName(scanner, user);
        setRole(scanner, user);
        setAge(scanner, user);
        setEmail(scanner, user);
        setPassword(scanner, user);
        return user;
    }

    private static void setFirstName(Scanner scanner, UserDto user) {
        System.out.print("Enter first name of the user: ");
        user.setFirstName(scanner.nextLine());
    }
    private static void setLastName(Scanner scanner, UserDto user) {
        System.out.print("Enter last name of the user: ");
        user.setLastName(scanner.nextLine());
    }

    private static void setEmail(Scanner scanner, UserDto user) {
        System.out.print("Enter email of the user: ");
        user.setEmail(scanner.nextLine());
    }
    private static void setPassword(Scanner scanner, UserDto user) {
        System.out.print("Enter password of the user: ");
        user.setPassword(scanner.nextLine());
    }

    private static void setRole(Scanner scanner, UserDto user) {
        System.out.print("Enter the role of the user: ");
        user.setRole(Role.valueOf(scanner.nextLine()));
    }

    private static void setAge(Scanner scanner, UserDto user) {
        System.out.print("Enter the age of the user: ");
        while (true) {
            int age = scanner.nextInt();
            if (age >= 0) {
                user.setAge(age);
                break;
            }
            System.out.println("Incorrect input! Enter it again!");
        }
        //Here, the scanner is called to clear the scanner's clipboard of \n that was left after the call scanner.NextInt()
        scanner.nextLine();
    }
}
