CREATE TABLE genres (
	id BIGSERIAL PRIMARY KEY,
	genre VARCHAR(50)
);
INSERT INTO genres (genre)
VALUES
	('REALISM'),
	('SCIENCE_FICTION'),
	('THRILLER'),
	('ROMANCE'),
	('ADVENTURE'),
	('HORROR'),
	('HISTORICAL_FICTION'),
	('DRAMA'),
	('COMEDY'),
	('FANTASY'),
	('NON_FICTION');


CREATE TABLE languages (
	id BIGSERIAL PRIMARY KEY,
	"language" VARCHAR(50)
);
INSERT INTO languages ("language")
VALUES
	('ENGLISH'),
	('CHINESE'),
	('SPANISH'),
	('ARABIC'),
	('FRENCH'),
	('RUSSIAN'),
	('PORTUGUESE'),
	('JAPANESE'),
	('GERMAN'),
	('KOREAN'),
	('TURKISH'),
	('ITALIAN');


CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY,
    author VARCHAR(255),
    isbn VARCHAR(20),
    title VARCHAR(255),
    pages INTEGER,
    publication_year INTEGER,
    genre_id INT2 REFERENCES genres,
    language_id INT2 REFERENCES languages,
    price DECIMAL(10, 2)
);
INSERT INTO books (author, isbn, title, pages, publication_year, genre_id, language_id, price)
VALUES
    ('Author1', 'ISBN111111', 'Title1', 300, 2000, 1, 1, 25.99),
	('Author2', 'ISBN222222', 'Title2', 250, 2013, 2, 2, 19.99),
	('Author3', 'ISBN333333', 'Title3', 400, 1999, 3, 3, 29.99),
	('Author4', 'ISBN444444', 'Title4', 350, 1823, 4, 1, 35.00),
	('Author5', 'ISBN555555', 'Title5', 320, 2005, 5, 4, 27.50),
	('Author6', 'ISBN666666', 'Title6', 280, 2013, 6, 2, 22.50),
	('Author7', 'ISBN777777', 'Title7', 310, 1953, 7, 5, 31.50),
	('Author8', 'ISBN888888', 'Title8', 260, 2000, 8, 3, 18.75),
	('Author9', 'ISBN999999', 'Title9', 380, 2013, 9, 12, 24.99),
	('Author10', 'ISBN101010', 'Title10', 290, 2022, 10, 8, 28.50),
	('Author11', 'ISBN111111', 'Title11', 330, 2011, 11, 8, 21.99),
	('Author12', 'ISBN121212', 'Title12', 270, 1987, 6, 9, 30.00),
	('Author13', 'ISBN131313', 'Title13', 400, 1845, 1, 6, 26.50),
	('Author14', 'ISBN141414', 'Title14', 320, 1867, 2, 7, 33.25),
	('Author15', 'ISBN151515', 'Title15', 350, 1999, 3, 11, 19.99),
	('Author16', 'ISBN161616', 'Title16', 310, 1987, 4, 3, 29.99),
	('Author17', 'ISBN171717', 'Title17', 280, 1999, 5, 10, 25.00),
	('Author18', 'ISBN181818', 'Title18', 330, 1982, 6, 10, 22.99),
	('Author19', 'ISBN191919', 'Title19', 370, 2018, 7, 8, 27.50),
	('Author20', 'ISBN202020', 'Title20', 300, 2016, 8, 9, 31.99);

CREATE TABLE roles (
	id BIGSERIAL PRIMARY KEY,
	role VARCHAR(50)
);
INSERT INTO roles (role)
VALUES
	('ADMIN'),
	('MANAGER'),
	('CUSTOMER');


CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    "password" VARCHAR(255),
    age INTEGER,
	role_id INT2 REFERENCES roles
);
INSERT INTO users (first_name, last_name, role_id, email, "password", age)
VALUES
    ('John', 'Doe', '2', 'john.doe@email.com', 'password123', 25),
    ('Jane', 'Smith', '3', 'jane.smith@email.com', 'adminPass456', 30),
    ('Bob', 'Johnson', '3', 'bob.johnson@email.com', 'userPass789', 22),
    ('Alice', 'Williams', '3', 'alice.williams@email.com', 'password456', 28),
    ('Charlie', 'Brown', '1', 'charlie.brown@email.com', 'adminPass789', 35),
    ('Eva', 'Davis', '2', 'eva.davis@email.com', 'userPass123', 26),
    ('Frank', 'Miller', '3', 'frank.miller@email.com', 'password789', 32),
    ('Grace', 'Wilson', '1', 'grace.wilson@email.com', 'adminPass123', 29),
    ('Henry', 'Jones', '2', 'henry.jones@email.com', 'userPass456', 31),
    ('Isabel', 'Taylor', '3', 'isabel.taylor@email.com', 'password789', 27),
    ('Jack', 'White', '1', 'jack.white@email.com', 'adminPass456', 34),
    ('Karen', 'Moore', '2', 'karen.moore@email.com', 'userPass789', 23),
    ('Leo', 'Clark', '3', 'leo.clark@email.com', 'password123', 33),
    ('Mia', 'Thomas', '1', 'mia.thomas@email.com', 'adminPass789', 28),
    ('Nathan', 'Lee', '3', 'nathan.lee@email.com', 'userPass123', 30),
    ('Olivia', 'Hall', '3', 'olivia.hall@email.com', 'password456', 25),
    ('Paul', 'Smith', '2', 'paul.smith@email.com', 'adminPass123', 29),
    ('Quincy', 'Adams', '2', 'quincy.adams@email.com', 'userPass789', 27),
    ('Rachel', 'Moore', '3', 'rachel.moore@email.com', 'password123', 31),
    ('Samuel', 'Jones', '3', 'samuel.jones@email.com', 'adminPass456', 32);


CREATE TABLE statuses(
        id BIGSERIAL PRIMARY KEY,
        status_type VARCHAR(255)
    );
INSERT INTO statuses (id, status_type) VALUES
    (1, 'PENDING'),
    (2, 'PAID'),
    (3, 'DELIVERED'),
    (4, 'CANCELED');
CREATE TABLE orders(
        id BIGSERIAL PRIMARY KEY,
        user_id SERIAL REFERENCES users,
        total_cost DECIMAL(10, 2),
        status_type_id SERIAL REFERENCES statuses
    );
CREATE TABLE order_items(
        id BIGSERIAL PRIMARY KEY,
        order_id INT2 REFERENCES orders,
        book_id INT2 REFERENCES books,
        quantity INT2,
        price DECIMAL(10, 2)
    );
INSERT INTO orders (user_id, total_cost, status_type_id) VALUES
    (1, 50.00, 1),
    (2, 75.50, 2),
    (3, 100.25, 3),
    (4, 120.75, 1),
    (5, 90.50, 2),
    (6, 60.25, 3),
    (7, 80.00, 1),
    (8, 55.50, 2),
    (9, 95.25, 3),
    (10, 110.75, 1),
    (11, 70.50, 2),
    (12, 45.25, 3),
    (13, 85.00, 1),
    (14, 62.50, 2),
    (15, 78.25, 3),
    (16, 105.75, 1),
    (17, 88.50, 2),
    (18, 42.25, 3),
    (19, 115.00, 1),
    (20, 67.50, 2);
INSERT INTO order_items (order_id, book_id, quantity, price) VALUES
    (1, 1, 2, 25.00),
    (1, 3, 1, 12.50),
    (2, 5, 3, 10.00),
    (2, 2, 1, 15.00),
    (3, 4, 2, 20.00),
    (3, 6, 1, 30.00),
    (4, 8, 4, 7.50),
    (4, 10, 2, 18.75),
    (5, 12, 3, 9.00),
    (5, 14, 1, 22.50),
    (6, 16, 2, 15.00),
    (6, 18, 1, 25.00),
    (7, 20, 3, 12.00),
    (7, 7, 1, 17.50),
    (8, 11, 2, 22.50),
    (8, 13, 1, 11.25),
    (9, 15, 4, 6.25),
    (9, 17, 2, 16.50),
    (10, 19, 3, 14.00),
    (10, 9, 1, 20.00);