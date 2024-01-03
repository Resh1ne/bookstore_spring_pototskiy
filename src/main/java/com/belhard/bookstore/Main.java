package com.belhard.bookstore;

import com.belhard.bookstore.data.dao.BookDao;
import com.belhard.bookstore.data.connection.DataSource;
import com.belhard.bookstore.data.entity.enums.GenresOfTheBook;
import com.belhard.bookstore.data.entity.enums.LanguagesOfTheBook;
import com.belhard.bookstore.data.dao.impl.BookDaoImpl;
import com.belhard.bookstore.data.connection.impl.DataSourceImpl;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.impl.BookServiceImpl;
import com.belhard.bookstore.util.PropertiesManager;
import com.belhard.bookstore.util.impl.PropertiesManagerImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookDao bookDao = getBookDao();
        BookService bookService = new BookServiceImpl(bookDao);

        Pattern pattern = Pattern.compile("\\d+");
        System.out.println(bookDao.countAll());

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

            if (!usingMenu(userInput, id, command, scanner, bookService)) {
                return;
            }
        }
    }

    private static BookDao getBookDao() {
        PropertiesManager propertiesManager = new PropertiesManagerImpl("/app.properties");
        String profile = propertiesManager.getKey("my.app.profile");
        String url = propertiesManager.getKey("my.app.db." + profile + ".url");
        String user = propertiesManager.getKey("my.app.db." + profile + ".user");
        String password = propertiesManager.getKey("my.app.db." + profile + ".password");
        String driver = propertiesManager.getKey("my.app.db." + profile + ".driver");
        DataSource dataSource = new DataSourceImpl(password, user, url, driver);
        return new BookDaoImpl(dataSource);
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

    private static boolean usingMenu(String userInput, Long id, String command, Scanner scanner, BookService bookService) {
        if (id > 0 && "/update{}".equals(command)) {
            BookDto book = updateBook(scanner, bookService, id);
            System.out.println(bookService.update(book).toString());
        } else if (userInput.equals("/all")) {
            List<BookDto> books = bookService.getAll();
            for (BookDto book : books) {
                System.out.println(book.toString());
            }
        } else if (userInput.equals("/exit")) {
            return false;
        } else if (id > 0 && "/get{}".equals(command)) {
            BookDto book = bookService.getById(id);
            System.out.println(book);
        } else if (id > 0 && "/delete{}".equals(command)) {
            bookService.delete(id);
        } else if (userInput.equals("/create")) {
            BookDto book = createBookWithoutID(scanner);
            BookDto createdBook = bookService.create(book);
            System.out.println(createdBook.toString());
        } else {
            System.out.println("Incorrect command!");
        }
        return true;
    }

    private static BookDto updateBook(Scanner scanner, BookService bookService, long id) {
        while (true) {
            if (bookService.getById(id) == null) {
                System.out.println("There is no user with this id! Enter it again!");
                id = scanner.nextLong();
                //Here, the scanner is called to clear the scanner's clipboard of \n that was left after the call scanner.NextLong()
                scanner.nextLine();
                continue;
            }
            break;
        }
        BookDto book = createBookWithoutID(scanner);
        book.setId(id);
        return book;
    }

    private static BookDto createBookWithoutID(Scanner scanner) {
        BookDto book = new BookDto();
        setIsbn(scanner, book);
        setAuthor(scanner, book);
        setTitle(scanner, book);
        setGenre(scanner, book);
        setLanguage(scanner, book);
        setPages(scanner, book);
        setPrice(scanner, book);
        setPublicationYear(scanner, book);
        return book;
    }

    private static void setIsbn(Scanner scanner, BookDto book) {
        System.out.print("Enter the ISBN of the book: ");
        book.setIsbn(scanner.nextLine());
    }

    private static void setAuthor(Scanner scanner, BookDto book) {
        System.out.print("Enter the author of the book: ");
        book.setAuthor(scanner.nextLine());
    }

    private static void setTitle(Scanner scanner, BookDto book) {
        System.out.print("Enter the title of the book: ");
        book.setTitle(scanner.nextLine());
    }

    private static void setGenre(Scanner scanner, BookDto book) {
        System.out.print("Enter the genre of the book: ");
        book.setGenre(GenresOfTheBook.valueOf(scanner.nextLine()));
    }

    private static void setLanguage(Scanner scanner, BookDto book) {
        System.out.print("Enter the language of the book: ");
        book.setLanguage(LanguagesOfTheBook.valueOf(scanner.nextLine()));
    }

    private static void setPages(Scanner scanner, BookDto book) {
        System.out.print("Enter the number of pages of the book: ");
        while (true) {
            int pages = scanner.nextInt();
            if (pages >= 0) {
                book.setPages(pages);
                break;
            }
            System.out.println("Incorrect input! Enter it again!");
        }
        //Here, the scanner is called to clear the scanner's clipboard of \n that was left after the call scanner.nextInt()
        scanner.nextLine();
    }

    private static void setPrice(Scanner scanner, BookDto book) {
        System.out.print("Enter the price of the book: ");
        while (true) {
            BigDecimal price = scanner.nextBigDecimal();
            if (price.compareTo(BigDecimal.ZERO) >= 0) {
                book.setPrice(price);
                break;
            }
            System.out.println("Incorrect input! Enter it again!");
        }
        //Here, the scanner is called to clear the scanner's clipboard of \n that was left after the call scanner.nextInt()
        scanner.nextLine();
    }

    private static void setPublicationYear(Scanner scanner, BookDto book) {
        System.out.print("Enter the publication year of the book: ");
        while (true) {
            int year = scanner.nextInt();
            if (year > 1000 && year < LocalDate.now().getYear()) {
                book.setPublicationYear(year);
                break;
            }
            System.out.println("Incorrect input! Enter it again!");
        }
        //Here, the scanner is called to clear the scanner's clipboard of \n that was left after the call scanner.nextInt()
        scanner.nextLine();
    }
}
