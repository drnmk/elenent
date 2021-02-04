

-- :name insert-into-trait :!
insert into trait (id, subject, attribute, uniq, single, vtype)
values (default, :subject, :attribute, :uniq, :single, :vtype)

-- :name select-count-existing-trait :?
select count(*)
from trait
where subject = :subject
and attribute = :attribute

-- :name select-find-trait-id :?
select id 
from trait
where subject = :subject
and attribute = :attribute

-- :name insert-into-entity :!
insert into entity (id)
values (default)
returning id;

-- :name insert-into-vtext :!
insert into vtext (id, val)
values (default, :val)
returning id;

-- :name select-get-available-atom-id :?
select max(a.id) + 1 as id
from atom as a;

-- :name insert-into-atom :!
insert into atom (id, entity_id, trait_id, value_id, ref_id, valid, stamp)
values (:id, :entity_id, :trait_id, :value_id, :ref_id, :valid, default)

-- :name select-show-full-atom-by-id :?
select a.id as id, t.*, vt.val
from atom as a
left join vtext as vt on a.value_id = vt.id
left join trait as t on a.trait_id = t.id
where a.id = :id


























-- :name create-log-get-id :!
insert into log (id, by, at)
values (default, :by, default)
returning id ;

-- :name create-client-get-id :!
insert into client (id, branch)
values (default, :branch)
returning id ;

-- :name add-client-info :!
insert into client_info (id, client_id, name, email, log_id)
values (default, :client_id, :name, :email, :log_id) ;

-- :name get-client-by-id :?
select cnf.client_id
, cnf.name
, cnf.email
, l.at
from client_info as cnf 
left join log as l on cnf.log_id = l.id
where cnf.id =
( select max(cnf.id)
from client_info as cnf
where client_id = :cid ) ;

-- :name get-clients :?







/* 

-- :name read-logs :?
select *
from log

-- :name get-max-log-id :?
select max(l.id)
from log as l;

-- :name create-log :!
insert into log (id, tb, rf, op, by, at)
values (:id, :tb, :rf, true, :by, default);

-- :name retract-log :!
insert into log (id, tb, rf, op, by, at)
values (:id, :tb, :rf, false, :by, default);

-- :name create-client :!
insert into client (id) values (:id);

-- :name create-client-info :!
insert into client_info (id, client_id, name, email)
values (:id, :client_id, :name, :email);

-- :name read-log-status :?
select sum(case when op then 1 else -1 end)
from log
where rf = :rf;

-- :name get-recent-log :?
select *
from log
where id =
( select max(id)
from log
where rf = :rf );

-- :name read-client :? 
select c.id as client_id
, ci.name
, ci.email 
from client as c
left join
( select l.op 
from client_info as ci 
left join log as l on ci.id = l.id
where client_id = :cid

) as ci on c.id = ci.client_id
where c.id = :cid ;



select * from log as l where l.rf in (select cnf.id from client_info as cnf where cnf.client_id = 1);


select sum(case when t.op then 1 else -1 end) from (select * from log as l where l.rf in (select cnf.id from client_info as cnf where cnf.client_id = 1)) as t;



select t.rf
from
( select *
from log as l
where l.rf in
( select cnf.id
from client_info as cnf
where cnf.client_id = 1)
) as t
group by t.rf
having sum(case when t.op then 1 else -1 end) > 0;
