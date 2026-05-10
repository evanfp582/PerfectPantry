CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE TYPE measurements AS ENUM (
    'tablespoon',
    'teaspoon',
    'cup',
    'pint',
    'quart',
    'gallon',
    'pound',
    'ounce',
    'milliliter',
    'liter',
    'gram',
    'dash',
    'sprinkle',
    'pinch',
    'drop',
    'slice',
    'cube'
);

CREATE TABLE recipe (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR,
    "time" VARCHAR,
    yield DOUBLE PRECISION,
    source VARCHAR,
    url VARCHAR,
    instructions JSONB DEFAULT '{}'::jsonb
);

CREATE TABLE ingredient (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR,
    description VARCHAR,
    cost NUMERIC
);

CREATE TABLE tag (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE recipe_ingredient (
    ingredient_id INTEGER NOT NULL,
    recipe_id INTEGER NOT NULL,
    quantity DOUBLE PRECISION,
    unit measurements,

    CONSTRAINT recipe_ingredient_pkey
        PRIMARY KEY (ingredient_id, recipe_id),

    CONSTRAINT fk_recipe_ingredient_ingredient
        FOREIGN KEY (ingredient_id)
        REFERENCES ingredient(id),

    CONSTRAINT fk_recipe_ingredient_recipe
        FOREIGN KEY (recipe_id)
        REFERENCES recipe(id)
);

CREATE TABLE recipe_tag (
    recipe_id INTEGER NOT NULL,
    tag_id INTEGER NOT NULL,

    CONSTRAINT recipe_tag_pkey
        PRIMARY KEY (recipe_id, tag_id),

    CONSTRAINT fk_recipe_tag_recipe
        FOREIGN KEY (recipe_id)
        REFERENCES recipe(id),

    CONSTRAINT fk_recipe_tag_tag
        FOREIGN KEY (tag_id)
        REFERENCES tag(id)
);