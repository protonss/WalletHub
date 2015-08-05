
set @start_date := '2011-06-20';
set @end_date := '2011-06-22';

select count(*) as 'Number of bugs open' 
from bugs
where (open_date  <= STR_TO_DATE(@start_date, '%Y-%m-%d') and close_date > STR_TO_DATE(@start_date, '%Y-%m-%d'))
   and (open_date  <= STR_TO_DATE(@end_date, '%Y-%m-%d') and close_date > STR_TO_DATE(@end_date, '%Y-%m-%d')) ;

