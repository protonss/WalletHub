
DELIMITER  $$

drop function if exists capFirstWords $$
create function capFirstWords( phrase varchar(255)) returns varchar(255)
deterministic
begin
  declare ret varchar(255);
  set ret = '';
    set phrase = concat(lower(phrase), ' ');
  WHILE (LOCATE(' ', phrase) > 0)
  DO
    set ret = concat(ret, ' ', CONCAT(UCASE(LEFT(LEFT(phrase, LOCATE(' ',phrase) - 1), 1)), SUBSTRING(LEFT(phrase, LOCATE(' ',phrase) - 1), 2)) );
        SET phrase = SUBSTRING(phrase, LOCATE(' ',phrase) + 1);
  END WHILE;
    return ret;
end

$$
DELIMITER ;


/* Testing */
SELECT capFirstWords("UNITED  states   Of   AmERIca");


