MERGE INTO genres AS g
USING (VALUES
           ('Комедия'),
           ('Драма'),
           ('Мультфильм'),
           ('Триллер'),
           ('Документальный'),
           ('Боевик')
)AS source(genre_name)
    ON g.name = source.genre_name
    WHEN NOT MATCHED THEN
INSERT (name) VALUES (source.genre_name);


MERGE INTO rating AS r
USING (VALUES
           ('G', 'у фильма нет возрастных ограничений'),
           ('PG', 'детям рекомендуется смотреть фильм с родителями'),
           ('PG-13', 'детям до 13 лет просмотр не желателен'),
           ('R', 'лицам до 17 лет просматривать фильм можно только в присутствии взрослого'),
           ('NC-17', 'лицам до 18 лет просмотр запрещён')
)AS source(rating_name, description)
    ON r.name = source.rating_name
    WHEN NOT MATCHED THEN
INSERT (name, description) VALUES (source.rating_name, source.description);