CREATE TABLE chairs (
                        id SERIAL PRIMARY KEY,                       -- Уникальный идентификатор товара
                        name VARCHAR(255) NOT NULL UNIQUE,                   -- Название стула
                        description TEXT,                            -- Подробное описание
                        category VARCHAR(100),                       -- Категория

                        price NUMERIC(10,2) NOT NULL CHECK (price >= 0),  -- Цена, с проверкой на неотрицательность
                        stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),  -- Количество на складе
                        material VARCHAR(100),                       -- Материал
                        image_url TEXT,                              -- Ссылка на изображение товара
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- Дата добавления
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,     -- Дата последнего обновления
                        deleted BOOLEAN NOT NULL DEFAULT FALSE
);

--------------------------------------------------------------------

CREATE TABLE users(
                      id BIGSERIAL PRIMARY KEY ,
                      username VARCHAR(30) NOT NULL UNIQUE ,
                      password VARCHAR(80) NOT NULL,
                      email VARCHAR(50) NOT NULL UNIQUE ,
                      created TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- дата добавления
                      updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- дата добавления
                      registration_status VARCHAR(30) NOT NULL ,
                      last_login TIMESTAMP,
                      deleted BOOLEAN NOT NULL DEFAULT FALSE
);

--------------------------------------------------------------------

CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL,
                       user_system_role VARCHAR(50) NOT NULL,
                       active BOOLEAN NOT NULL DEFAULT true,
                       created_by VARCHAR(50) NOT NULL

);
--------------------------------------------------------------------

CREATE TABLE user_roles(
                           user_id BIGINT NOT NULL,
                           role_id INT NOT NULL,
                           PRIMARY KEY (user_id, role_id),
                           FOREIGN KEY (user_id) REFERENCES users(id),
                           FOREIGN KEY (role_id) REFERENCES roles(id)

);


--------------------------------------------------------------------

-- Каждому пользователю — одна корзина.
-- При удалении пользователя корзина удаляется автоматически.
CREATE TABLE shopping_cart (
                               id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT UNIQUE NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                               created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



--------------------------------------------------------------------



-- Связь многие ко многим между корзиной и велосипедами.
-- Можно хранить количество каждого велосипеда (quantity).
CREATE TABLE cart_items (
                            id BIGSERIAL PRIMARY KEY,
                            cart_id BIGINT NOT NULL REFERENCES shopping_cart(id) ON DELETE CASCADE,
                            chair_id BIGINT NOT NULL REFERENCES chairs(id) ON DELETE CASCADE,
                            quantity INT NOT NULL DEFAULT 1,
                            UNIQUE (cart_id, chair_id)
);





--------------------------------------------------------------------

CREATE TABLE refresh_tokens(
                               id SERIAL PRIMARY KEY,
                               token VARCHAR(128) NOT NULL,
                               created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               user_id BIGINT NOT NULL,
                               CONSTRAINT FK_refresh_token_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                               CONSTRAINT refresh_token_UNIQUE UNIQUE (user_id, id)
);




INSERT INTO chairs (name, description, category, price, stock, material, image_url)
VALUES

    ('Барный стул Loft',
     'Высокий стул в стиле лофт с металлическими ножками и деревянным сиденьем',
     'Барные',
     1299.00,
     8,
     'Металл и дерево',
     'https://ir.ozone.ru/s3/rp-photo-12/c200/1c2eb21d-48f0-4133-b137-6af42c7f3278.jpeg'),

    ('Игровой стул Racer',
     'Удобное кресло для геймеров с регулировкой наклона и подлокотников',
     'Игровые',
     2499.99,
     5,
     'Кожзам и пластик',
     'https://img-webcalypt.ru/storage/memes/327/20253/V0rpFQ0Czs6dvn3VAMII8Ek1CMCvWH9vv0AXlKltSJnAq00Fl4yegBDdlA5TqED75pnfCTq0xNwOxJtWl3oeVB9tjOIuPYs4u8dLTh4KMoxOWAZTSUXb4ukKPQOXj8SH.jpeg'),

    ('Складной стул EasyFold',
     'Легкий складной стул, удобен для хранения и транспортировки',
     'Складные',
     499.00,
     40,
     'Пластик и металл',
     'https://cs15.pikabu.ru/post_img/2024/10/22/11/172962549913092764.jpg'),

('Кресло-качалка Relax',
    'Уютное кресло-качалка для отдыха после тяжелого дня, идеально для гостиной',
    'Гостиные',
    3299.00,
    6,
    'Массив дерева',
    'https://i.pinimg.com/236x/15/8c/c7/158cc797b3a1378b6f872af4b79992ad.jpg'),

('Детский стул Rainbow',
    'Яркий и безопасный стул для детей с закругленными краями и устойчивой конструкцией',
    'Детские',
    699.99,
    18,
    'Пластик',
    'https://external-preview.redd.it/DYZZBDT-zM7UEOBKfGKWuIVERSu9aXuf0rreL2DMA80.jpg?width=640&crop=smart&auto=webp&s=52963cc6a1a2027b7aae32f82d739445852b4a61'),

('Стул для руководителя Premium',
    'Роскошное кресло для руководителя с высокой спинкой и кожаной отделкой',
    'Офисные',
    5499.50,
    3,
    'Натуральная кожа, металл',
    'https://ic.pics.livejournal.com/chemodur/69177696/1736589/1736589_900.jpg'),

('Садовый стул Garden',
    'Водостойкий стул для террасы и сада, не боится влаги и перепадов температур',
    'Садовые',
    1199.00,
    15,
    'Техноротанг',
    'https://img2.safereactor.cc/pics/post/full/%D0%B6%D0%B8%D0%B2%D0%BD%D0%BE%D1%81%D1%82%D1%8C-%D0%BA%D0%BE%D1%82%D1%8D-%D1%81%D1%82%D1%83%D0%BB-9089475.jpeg'),

('Венский стул Retro',
    'Классический венский стул с гнутыми элементами в винтажном стиле',
    'Винтажные',
    1899.00,
    0,
    'Гнутая фанера, бук',
    'https://img2.safereactor.cc/pics/post/%D1%81%D1%82%D1%83%D0%BB-%D0%A0%D0%B0%D0%B9%D0%B0%D0%BD-%D0%93%D0%BE%D1%81%D0%BB%D0%B8%D0%BD%D0%B3-%D0%90%D0%BA%D1%82%D0%B5%D1%80%D1%8B-%D0%B8-%D0%90%D0%BA%D1%82%D1%80%D0%B8%D1%81%D1%8B-8339365.jpeg');

--------------------------------------------------------------------




INSERT INTO users(username, password, email, created, updated, registration_status, last_login, deleted)
VALUES
    ('Super_Admin', '$2a$10$Ixan2nE1CBnpYQE97wmjUuoCAeO2aQ/6WaB3rIcPWG.puEVn0E1RO', 'SuperAdmin@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP, false),
    ('Admin', '$2a$10$Ixan2nE1CBnpYQE97wmjUuoCAeO2aQ/6WaB3rIcPWG.puEVn0E1RO', 'Admin@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP, false),
    ('User', '$2a$10$Ixan2nE1CBnpYQE97wmjUuoCAeO2aQ/6WaB3rIcPWG.puEVn0E1RO', 'User@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP, false);



--------------------------------------------------------------------




INSERT INTO shopping_cart (user_id, created, updated)
VALUES
    (1, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), -- корзина для alex
    (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- корзина для maria
    (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



--------------------------------------------------------------------




INSERT INTO cart_items (cart_id, chair_id, quantity)
VALUES
    (1, 1, 1),
    (2, 2, 1),
    (3, 3, 1);


--------------------------------------------------------------------

INSERT INTO roles (name, user_system_role, created_by)
VALUES
    ('SUPER_ADMIN','SUPER_ADMIN','SUPER_ADMIN' ),
    ('ADMIN', 'ADMIN', 'SUPER_ADMIN'),
    ('USER', 'USER', 'SUPER_ADMIN');

INSERT INTO user_roles (user_id, role_id)
VALUES
    (1,1),
    (2,2),
    (3,3);

--------------------------------------------------------------------