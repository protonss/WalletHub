CREATE  FUNCTION `capFirstWords`( phrase VARCJAR(255)) RETURNS VARCHAR(255) CHARSET utf8
BEGIN
	DECLARE ret varchar(255);
	SET ret = '';
	SET phrase = concat(lower(phrase), ' ');
	WHILE (LOCATE(' ', phrase) > 0) DO
		SET ret = concat(ret, ' ', CONCAT(UCASE(LEFT(LEFT(phrase, LOCATE(' ',phrase) - 1), 1)), SUBSTRING(LEFT(phrase, LOCATE(' ',phrase) - 1), 2)) );
		SET phrase = SUBSTRING(phrase, LOCATE(' ',phrase) + 1);
	END WHILE; 
	RETURN ret;
ENDS



/* Testing */
SELECT capFirstWords("UNITED  states   Of   AmERIca");


