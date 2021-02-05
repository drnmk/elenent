

-- :name sql-load-trait :!
insert into trait (id, subject, attribute, uniq, single, vtype)
values (default, :subject, :attribute, :uniq, :single, :vtype)

-- :name sql-count-trait :?
select count(*)
from trait
where subject = :subject
and attribute = :attribute

-- :name sql-find-trait-id :?
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
with tid as (
select id
from trait
where subject = :subject
and attribute = :attribute ), 
vid as (
insert into vtext (id, val)
values (default, :val)
returning id )
insert into atom
(id, entity, trait_id, value_id, ref_id, valid, stamp)
values (:aid, :eid, tid, vid, :aid, true, default);
