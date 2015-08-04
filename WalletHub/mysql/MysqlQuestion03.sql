
DROP PROCEDURE IF EXISTS SplitColumn $$

CREATE PROCEDURE SplitColumn()
BEGIN
  DECLARE id int;
  declare name varchar(50);
  declare bDone int;
  declare sep char;

  DECLARE curs CURSOR FOR  SELECT * FROM sometbl ;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET bDone = 1;

  DROP TEMPORARY TABLE IF EXISTS ResultTable;
  CREATE TEMPORARY TABLE IF NOT EXISTS ResultTable  (
    id int, name varchar(50)
  );

  OPEN curs;
  SET bDone = 0;
  set sep = '|';
  REPEAT
    FETCH curs INTO id, name;
    set name = concat(name,sep);
    WHILE (LOCATE(sep, name) > 0)
    DO
      INSERT INTO ResultTable VALUES (id, CONCAT(UCASE(LEFT(LEFT(name, LOCATE(sep,name) - 1), 1)), SUBSTRING(LEFT(name, LOCATE(sep,name) - 1), 2)));
      SET name = SUBSTRING(name, LOCATE(sep,name) + 1);
    END WHILE; 
  UNTIL bDone END REPEAT;
  CLOSE curs;  
END $$

delimiter ;

call wallethub.SplitColumn();

/* Testing */
select * from ResultTable;  
  
