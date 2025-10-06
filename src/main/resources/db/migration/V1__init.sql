CREATE TABLE chairs (
                          id SERIAL PRIMARY KEY,                       -- Уникальный идентификатор товара
                          name VARCHAR(255) NOT NULL,                  -- Название стула
                          description TEXT,                            -- Подробное описание
                          category VARCHAR(100),                       -- Категория (например: офисный, кухонный и т.д.)
                          price NUMERIC(10,2) NOT NULL CHECK (price >= 0),  -- Цена, с проверкой на неотрицательность
                          stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),  -- Количество на складе
                          material VARCHAR(100),                       -- Материал (например: дерево, металл, пластик)
                          image_url TEXT,                              -- Ссылка на изображение товара
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- Дата добавления
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP     -- Дата последнего обновления
);


INSERT INTO chairs (name, description, category, price, stock, material, image_url)
VALUES
    ('Офисный стул Comfort',
     'Эргономичный офисный стул с регулируемой высотой и мягкой спинкой',
     'Офисные',
     1599.99,
     12,
     'Металл и ткань',
     'https://ir.ozone.ru/s3/rp-photo-12/c200/1c2eb21d-48f0-4133-b137-6af42c7f3278.jpeg'),

    ('Кухонный стул Classic',
     'Простой и прочный деревянный стул для кухни, подходит под любой интерьер',
     'Кухонные',
     849.50,
     25,
     'Дерево',
     'https://ir.ozone.ru/s3/rp-photo-12/c200/1c2eb21d-48f0-4133-b137-6af42c7f3278.jpeg'),

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
     'https://ir.ozone.ru/s3/rp-photo-12/c200/1c2eb21d-48f0-4133-b137-6af42c7f3278.jpeg'),

    ('Складной стул EasyFold',
     'Легкий складной стул, удобен для хранения и транспортировки',
     'Складные',
     499.00,
     40,
     'Пластик и металл',
     'https://ir.ozone.ru/s3/rp-photo-12/c200/1c2eb21d-48f0-4133-b137-6af42c7f3278.jpeg');
