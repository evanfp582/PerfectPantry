CREATE TABLE IF NOT EXISTS person (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    age INT
);

--INSERT INTO person(first_name, last_name, age) VALUES ('Evan', 'Fisher-Perez', 23);

select * from person