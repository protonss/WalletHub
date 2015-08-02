select name, sum(votes) as votes,  @rn := @rn -1 as rank
from votes cross join
     (select @rn := (select count(*) from votes)) variable
group by name, votes
order by votes desc;

