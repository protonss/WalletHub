CREATE PROCEDURE `splitColumn`()
BEGIN
	DECLARE id int;
	DECLARE name varchar(50);
	DECLARE hFns int;
	DECLARE sepparator char;

	DECLARE c CURSOR FOR  SELECT * FROM sometbl ;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET hFns = 1;

	DROP TEMPORARY TABLE IF EXISTS tmpTb;
	CREATE TEMPORARY TABLE IF NOT EXISTS tmpTb  (
		id int, name varchar(50)
	);

	OPEN c;
	SET hFns = 0;
	SET sepparator = '|';
	
    REPEAT
	FETCH c INTO id, name;
    SET name = concat(name, sepparator);
	WHILE (LOCATE(sepparator, name) > 0) 
	DO
	  INSERT INTO tmpTb VALUES (id, SUBSTRING_INDEX(name, sepparator, 1 ) );
	  SET name = SUBSTRING(name, LOCATE(sepparator,name) + 1);
	END WHILE; 
	UNTIL hFns 
    END REPEAT;
    
	CLOSE c;  
END

/* Testing */
call splitColumn();
select * from tmpTb;
  
