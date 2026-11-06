INSERT INTO public.ingredient
VALUES
(DEFAULT, 'water', 'Liquid for life', null ),
(DEFAULT, 'ice', 'Frozen liquid for life', null )
;

INSERT INTO public.recipe
VALUES
(DEFAULT, 'Iced Water', 'Best drink', '1 min', 1, 'Evan Fisher-Perez', null, '{"steps": ["Add ice to water"]}')
;

INSERT INTO public.recipe_ingredient
VALUES
(1,1,8,'ounce'),
(2,1,3,'cube')
;