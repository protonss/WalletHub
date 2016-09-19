SET @start_date := '2016-01-08';
SET @end_date := '2016-02-11';


SELECT id, @day := DATE_ADD(@day, INTERVAL 1 DAY) AS 'Day', 
	(SELECT COUNT(*) 
	FROM bugs
	WHERE  STR_TO_DATE(open_date, '%Y-%m-%d')  <= STR_TO_DATE(@day, '%Y-%m-%d') 
		AND ( STR_TO_DATE(close_date, '%Y-%m-%d') >= STR_TO_DATE(@day, '%Y-%m-%d') or close_date IS NULL )) AS 'Open Bugs'
FROM  bugs b, (SELECT @day := DATE_SUB(STR_TO_DATE(@start_date, '%Y-%m-%d'),INTERVAL 1 DAY)   ) d
WHERE STR_TO_DATE(@day, '%Y-%m-%d') < STR_TO_DATE(@end_date, '%Y-%m-%d');