SELECT *
FROM book
WHERE price = (SELECT MAX(price)
               FROM book);

SELECT *
FROM book
WHERE id = 1 and state in ('NOT_CONFIRMED','DELETED');

SELECT author
FROM book
WHERE quantity_sold = (SELECT MAX(quantity_sold)
                       FROM book);

SELECT *
FROM book
WHERE price > (SELECT AVG(price)
               FROM book);

SELECT DISTINCT author
FROM book
WHERE author IN (SELECT author
                 FROM book
                 WHERE price > 100);

select *
from user_profile
where profile_id in (select user_id
                    from users
                    where state = 'CONFIRMED');


select *
from user_profile
where profile_id in (select user_id
                     from users
                     where state = 'CONFIRMED' and role != 'ADMIN');
