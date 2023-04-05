
CREATE TABLE IF NOT EXISTS purchase (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS user_purchase (
                               id SERIAL PRIMARY KEY,
                               user_name VARCHAR(255) NOT NULL,
                               user_lastname VARCHAR(255) NOT NULL,
                               user_age INTEGER NOT NULL,
                               purchase_id INTEGER REFERENCES purchase(id),
                               count INTEGER NOT NULL,
                               amount NUMERIC(10,2) NOT NULL,
                               purchase_date DATE NOT NULL
);

INSERT INTO purchase(name)
VALUES ('Телевизор')
     , ('Смартфон')
     , ('Соковыжималка')
     , ('Наушники')
     , ('Клавиатура');
