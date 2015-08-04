/*
Depending on how you want to rank, there are two different approaches for this question.
*/

SELECT name, votes, @rank := @rank + 1 AS rank
FROM  votes v, (SELECT @rank := 0) r
ORDER BY  votes desc;

SELECT 
  name, 
  votes,
  CASE 
    WHEN @pRank = votes THEN @rank 
    WHEN @pRank := votes THEN @rank := @rank + 1
  END AS rank
FROM votes v,
(SELECT @rank :=0, @pRank := NULL) r
ORDER BY votes desc