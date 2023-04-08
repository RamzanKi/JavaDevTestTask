CREATE TABLE IF NOT EXISTS purchase (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);
INSERT INTO purchase (name)
SELECT 'Телевизор' AS name
    WHERE NOT EXISTS (
  SELECT 1 FROM purchase WHERE name = 'Телевизор'
)
UNION ALL
SELECT 'Смартфон' AS name
    WHERE NOT EXISTS (
  SELECT 1 FROM purchase WHERE name = 'Смартфон'
)
UNION ALL
SELECT 'Соковыжималка' AS name
    WHERE NOT EXISTS (
  SELECT 1 FROM purchase WHERE name = 'Соковыжималка'
)
UNION ALL
SELECT 'Наушники' AS name
    WHERE NOT EXISTS (
  SELECT 1 FROM purchase WHERE name = 'Наушники'
)
UNION ALL
SELECT 'Клавиатура' AS name
    WHERE NOT EXISTS (
  SELECT 1 FROM purchase WHERE name = 'Клавиатура'
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


-- INSERT INTO purchase(name)
-- VALUES ('Телевизор')
--      , ('Смартфон')
--      , ('Соковыжималка')
--      , ('Наушники')
--      , ('Клавиатура');
