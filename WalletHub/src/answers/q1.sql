
SELECT name, votes, @rank := @rank + 1 AS rank
FROM  votes v, (SELECT @rank := 0) r
ORDER BY  votes desc;
