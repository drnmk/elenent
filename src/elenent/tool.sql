
-- :name sql-get-atoms :?
select a.id, a.ent, t.key, t.uni, t.cardi, t.vtype, a.val, a.stamp
from atom as a
left join trait as t
on a.tkey = t.key
and a.rid not in 
( select rid from atom group by rid 
having sum (case valid when true then 1 else -1 end ) = 0 ) ;

-- :name sql-get-atoms-by-ent :?
select a.id, a.ent, t.key, t.uni, t.cardi, t.vtype, a.val, a.stamp
from atom as a
left join trait as t
on a.tkey = t.key
where a.ent = :ent
and a.rid not in 
( select rid from atom group by rid 
having sum (case valid when true then 1 else -1 end ) = 0 ) ;
