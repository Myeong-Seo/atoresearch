CREATE database hosts

create table hoststatus(
	seq int(10) auto_increment primary key,
	name varchar(30) unique not null,
	ip varchar(30) unique not null,
	date_time varchar(20),
	status varchar(20),
	last_alive varchar(20)
)

drop table hoststatus

ALTER TABLE hoststatus ADD status varchar(20) AFTER date_time
ALTER TABLE hoststatus ADD last_alive varchar(20) AFTER status

ALTER TABLE hoststatus drop status
ALTER TABLE hoststatus drop last_alive

show table status from host like 'hoststatus';
alter table hoststatus max_rows=100;

select * from hoststatus

insert into hoststatus values(null,'aaa','1.1.1.1',"");
insert into hoststatus values(null,'bbb','2.2.2.2',"");
insert into hoststatus values(null,'ccc','3.3.3.3',"");
insert into hoststatus values(null,'ddd','4.4.4.4',"");


