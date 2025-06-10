CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY,
    author VARCHAR(255),
    isbn VARCHAR(20),
    title VARCHAR(255),
    pages INTEGER,
    publication_year INTEGER,
    genre VARCHAR(50),
    language VARCHAR(50),
    price DECIMAL(10, 2)
);

INSERT INTO books (author, isbn, title, pages, publication_year, genre, language, price)
VALUES
    ('Author1', 'ISBN111111', 'Title1', 300, 2000, 'REALISM', 'ENGLISH', 25.99),
    ('Author2', 'ISBN222222', 'Title2', 250, 2013, 'SCIENCE_FICTION', 'CHINESE', 19.99),
    ('Author3', 'ISBN333333', 'Title3', 400, 1999, 'THRILLER', 'SPANISH', 29.99),
    ('Author4', 'ISBN444444', 'Title4', 350, 1823, 'ROMANCE', 'ENGLISH', 35.00),
    ('Author5', 'ISBN555555', 'Title5', 320, 2005, 'ADVENTURE', 'ARABIC', 27.50),
    ('Author6', 'ISBN666666', 'Title6', 280, 2013, 'HORROR', 'CHINESE', 22.50),
    ('Author7', 'ISBN777777', 'Title7', 310, 1953, 'HISTORICAL_FICTION', 'FRENCH', 31.50),
    ('Author8', 'ISBN888888', 'Title8', 260, 2000, 'DRAMA', 'SPANISH', 18.75),
    ('Author9', 'ISBN999999', 'Title9', 380, 2013, 'COMEDY', 'ITALIAN', 24.99),
    ('Author10', 'ISBN101010', 'Title10', 290, 2022, 'FANTASY', 'JAPANESE', 28.50),
    ('Author11', 'ISBN111111', 'Title11', 330, 2011, 'NON_FICTION', 'JAPANESE', 21.99),
    ('Author12', 'ISBN121212', 'Title12', 270, 1987, 'HORROR', 'GERMAN', 30.00),
    ('Author13', 'ISBN131313', 'Title13', 400, 1845, 'REALISM', 'RUSSIAN', 26.50),
    ('Author14', 'ISBN141414', 'Title14', 320, 1867, 'SCIENCE_FICTION', 'PORTUGUESE', 33.25),
    ('Author15', 'ISBN151515', 'Title15', 350, 1999, 'THRILLER', 'TURKISH', 19.99),
    ('Author16', 'ISBN161616', 'Title16', 310, 1987, 'ROMANCE', 'SPANISH', 29.99),
    ('Author17', 'ISBN171717', 'Title17', 280, 1999, 'ADVENTURE', 'KOREAN', 25.00),
    ('Author18', 'ISBN181818', 'Title18', 330, 1982, 'HORROR', 'KOREAN', 22.99),
    ('Author19', 'ISBN191919', 'Title19', 370, 2018, 'HISTORICAL_FICTION', 'JAPANESE', 27.50),
    ('Author20', 'ISBN202020', 'Title20', 300, 2016, 'DRAMA', 'GERMAN', 31.99);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    age INTEGER,
    role VARCHAR(20)
);

INSERT INTO users (first_name, last_name, role, email, password, age)
VALUES
    ('John', 'Doe', 'MANAGER', 'john.doe@email.com', 'password123', 25),
    ('Jane', 'Smith', 'CUSTOMER', 'jane.smith@email.com', 'adminPass456', 30),
    ('Bob', 'Johnson', 'CUSTOMER', 'bob.johnson@email.com', 'userPass789', 22),
    ('Alice', 'Williams', 'CUSTOMER', 'alice.williams@email.com', 'password456', 28),
    ('Charlie', 'Brown', 'ADMIN', 'charlie.brown@email.com', 'adminPass789', 35),
    ('Eva', 'Davis', 'MANAGER', 'eva.davis@email.com', 'userPass123', 26),
    ('Frank', 'Miller', 'CUSTOMER', 'frank.miller@email.com', 'password789', 32),
    ('Grace', 'Wilson', 'ADMIN', 'grace.wilson@email.com', 'adminPass123', 29),
    ('Henry', 'Jones', 'MANAGER', 'henry.jones@email.com', 'userPass456', 31),
    ('Isabel', 'Taylor', 'CUSTOMER', 'isabel.taylor@email.com', 'password789', 27),
    ('Jack', 'White', 'ADMIN', 'jack.white@email.com', 'adminPass456', 34),
    ('Karen', 'Moore', 'MANAGER', 'karen.moore@email.com', 'userPass789', 23),
    ('Leo', 'Clark', 'CUSTOMER', 'leo.clark@email.com', 'password123', 33),
    ('Mia', 'Thomas', 'ADMIN', 'mia.thomas@email.com', 'adminPass789', 28),
    ('Nathan', 'Lee', 'CUSTOMER', 'nathan.lee@email.com', 'userPass123', 30),
    ('Olivia', 'Hall', 'CUSTOMER', 'olivia.hall@email.com', 'password456', 25),
    ('Paul', 'Smith', 'MANAGER', 'paul.smith@email.com', 'adminPass123', 29),
    ('Quincy', 'Adams', 'MANAGER', 'quincy.adams@email.com', 'userPass789', 27),
    ('Rachel', 'Moore', 'CUSTOMER', 'rachel.moore@email.com', 'password123', 31),
    ('Samuel', 'Jones', 'CUSTOMER', 'samuel.jones@email.com', 'adminPass456', 32);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    total_cost DECIMAL(10, 2),
    status VARCHAR(20)
);

INSERT INTO orders (user_id, total_cost, status) VALUES
    (1, 50.00, 'PENDING'),
    (2, 75.50, 'PAID'),
    (3, 100.25, 'DELIVERED'),
    (4, 120.75, 'PENDING'),
    (5, 90.50, 'PAID'),
    (6, 60.25, 'DELIVERED'),
    (7, 80.00, 'PENDING'),
    (8, 55.50, 'PAID'),
    (9, 95.25, 'DELIVERED'),
    (10, 110.75, 'PENDING'),
    (11, 70.50, 'PAID'),
    (12, 45.25, 'DELIVERED'),
    (13, 85.00, 'PENDING'),
    (14, 62.50, 'PAID'),
    (15, 78.25, 'DELIVERED'),
    (16, 105.75, 'PENDING'),
    (17, 88.50, 'PAID'),
    (18, 42.25, 'DELIVERED'),
    (19, 115.00, 'PENDING'),
    (20, 67.50, 'PAID');

CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders(id) ON DELETE CASCADE,
    book_id BIGINT REFERENCES books(id) ON DELETE CASCADE,
    quantity INTEGER,
    price DECIMAL(10, 2)
);

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


    -- 1. Для связи order_items -> orders (удаление позиций при удалении заказа)
    ALTER TABLE order_items
    DROP CONSTRAINT order_items_order_id_fkey,
    ADD CONSTRAINT order_items_order_id_fkey
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE;

    -- 2. Для связи order_items -> books (удаление позиций при удалении книги)
    ALTER TABLE order_items
    DROP CONSTRAINT order_items_book_id_fkey,
    ADD CONSTRAINT order_items_book_id_fkey
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE;

    -- 3. Для связи orders -> users (удаление заказов и их позиций при удалении пользователя)
    ALTER TABLE orders
    DROP CONSTRAINT orders_user_id_fkey,
    ADD CONSTRAINT orders_user_id_fkey
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;