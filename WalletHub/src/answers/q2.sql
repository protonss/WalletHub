
-- Change root and localhost below according to your context.

CREATE DEFINER=`root`@`localhost` FUNCTION `capFirstWords`( phrase varchar(255)) RETURNS varchar(255) CHARSET utf8
BEGIN
	declare ret varchar(255);
	set ret = '';
	set phrase = concat(lower(phrase), ' ');
	WHILE (LOCATE(' ', phrase) > 0) DO
		set ret = concat(ret, ' ', CONCAT(UCASE(LEFT(LEFT(phrase, LOCATE(' ',phrase) - 1), 1)), SUBSTRING(LEFT(phrase, LOCATE(' ',phrase) - 1), 2)) );
		SET phrase = SUBSTRING(phrase, LOCATE(' ',phrase) + 1);
	END WHILE; 
	return ret; 
END