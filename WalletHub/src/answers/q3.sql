CREATE DEFINER=`root`@`localhost` PROCEDURE `splitColumn`()
BEGIN
	DECLARE id int;
	declare name varchar(50);
	declare tName varchar(1000);
	declare hFns int;
	declare sepparator char;

	DECLARE c CURSOR FOR  SELECT * FROM sometbl ;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET hFns = 1;

	DROP TEMPORARY TABLE IF EXISTS tmpTb;
	CREATE TEMPORARY TABLE IF NOT EXISTS tmpTb  (
		name VARCHAR(1000)
	);

	OPEN c;
	SET hFns = 0;
	set sepparator = '|';
	
    SET tName = "";
    REPEAT
	FETCH c INTO id, name;
    set name = concat(name, sepparator);
	WHILE (LOCATE(sepparator, name) > 0) 
	DO
		IF SUBSTRING_INDEX(name, sepparator, 1) <> ''
        THEN SET tName = concat(tName, "\"", id, ",", SUBSTRING_INDEX(name, sepparator, 1) , "\", ");
        END IF;
		SET name = SUBSTRING(name, LOCATE(sepparator,name) + 1);
	END WHILE; 
	UNTIL hFns 
    END REPEAT;
    SET tName = SUBSTRING(tName, 1, LENGTH(tName) - 2);
	INSERT INTO tmpTb VALUES (tName);
    
	CLOSE c;  
END