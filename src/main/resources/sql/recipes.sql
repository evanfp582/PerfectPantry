INSERT INTO public.ingredient
VALUES
(DEFAULT, 'water', 'Liquid for life', null ),
(DEFAULT, 'ice', 'Frozen liquid for life', null ),
(DEFAULT, 'bread', 'The best thing', null ),
(DEFAULT, 'cheese', 'Savory milk', null )
;

INSERT INTO public.recipe
VALUES
(DEFAULT, 'Iced Water', 'Best drink', '1 min', 1, 'Evan Fisher-Perez', null, '{"steps": ["Add ice to water"]}'),
(DEFAULT, 'Grilled Cheese', 'Yummy sandwich served best with tomato soup', '4 min', 1, 'Evan Fisher-Perez', null, '{"steps": ["Take bread and toast it","Put cheese in between bread"]}')
;

INSERT INTO public.recipe_ingredient
VALUES
(1,1,8,'ounce'),
(2,1,3,'cube'),
(3,2,2,'slice'),
(4,2,1,'slice')
;

INSERT INTO public.usr
VALUES
(DEFAULT, 'Judytm2001'),
(DEFAULT, 'spike8pizza'),
(DEFAULT, 'ethanfp582')
;

INSERT INTO public.usr_recipe
VALUES
(2,1,'Judys favorite Grilled Cheese recipe!')
;