-- thanks to patrick lightbody for submitting this...
--
-- in your quartz properties file, you'll need to set 
-- org.quartz.jobstore.driverdelegateclass = org.quartz.impl.jdbcjobstore.postgresqldelegate

drop table qrtz_job_listeners;
drop table qrtz_trigger_listeners;
drop table qrtz_fired_triggers;
drop table qrtz_paused_trigger_grps;
drop table qrtz_scheduler_state;
drop table qrtz_locks;
drop table qrtz_simple_triggers;
drop table qrtz_cron_triggers;
drop table qrtz_blob_triggers;
drop table qrtz_triggers;
drop table qrtz_job_details;
drop table qrtz_calendars;

create table qrtz_job_details
  (
    job_name  varchar(200) not null,
    job_group varchar(200) not null,
    description varchar(250) null,
    job_class_name   varchar(250) not null, 
    is_durable bool not null,
    is_volatile bool not null,
    is_stateful bool not null,
    requests_recovery bool not null,
    job_data bytea null,
    primary key (job_name,job_group)
);

create table qrtz_job_listeners
  (
    job_name  varchar(200) not null, 
    job_group varchar(200) not null,
    job_listener varchar(200) not null,
    primary key (job_name,job_group,job_listener),
    foreign key (job_name,job_group) 
	references qrtz_job_details(job_name,job_group) 
);

create table qrtz_triggers
  (
    trigger_name varchar(200) not null,
    trigger_group varchar(200) not null,
    job_name  varchar(200) not null, 
    job_group varchar(200) not null,
    is_volatile bool not null,
    description varchar(250) null,
    next_fire_time bigint null,
    prev_fire_time bigint null,
    priority integer null,
    trigger_state varchar(16) not null,
    trigger_type varchar(8) not null,
    start_time bigint not null,
    end_time bigint null,
    calendar_name varchar(200) null,
    misfire_instr smallint null,
    job_data bytea null,
    primary key (trigger_name,trigger_group),
    foreign key (job_name,job_group) 
	references qrtz_job_details(job_name,job_group) 
);

create table qrtz_simple_triggers
  (
    trigger_name varchar(200) not null,
    trigger_group varchar(200) not null,
    repeat_count bigint not null,
    repeat_interval bigint not null,
    times_triggered bigint not null,
    primary key (trigger_name,trigger_group),
    foreign key (trigger_name,trigger_group) 
	references qrtz_triggers(trigger_name,trigger_group)
);

create table qrtz_cron_triggers
  (
    trigger_name varchar(200) not null,
    trigger_group varchar(200) not null,
    cron_expression varchar(120) not null,
    time_zone_id varchar(80),
    primary key (trigger_name,trigger_group),
    foreign key (trigger_name,trigger_group) 
	references qrtz_triggers(trigger_name,trigger_group)
);

create table qrtz_blob_triggers
  (
    trigger_name varchar(200) not null,
    trigger_group varchar(200) not null,
    blob_data bytea null,
    primary key (trigger_name,trigger_group),
    foreign key (trigger_name,trigger_group) 
        references qrtz_triggers(trigger_name,trigger_group)
);

create table qrtz_trigger_listeners
  (
    trigger_name  varchar(200) not null, 
    trigger_group varchar(200) not null,
    trigger_listener varchar(200) not null,
    primary key (trigger_name,trigger_group,trigger_listener),
    foreign key (trigger_name,trigger_group) 
	references qrtz_triggers(trigger_name,trigger_group)
);


create table qrtz_calendars
  (
    calendar_name  varchar(200) not null, 
    calendar bytea not null,
    primary key (calendar_name)
);


create table qrtz_paused_trigger_grps
  (
    trigger_group  varchar(200) not null, 
    primary key (trigger_group)
);

create table qrtz_fired_triggers 
  (
    entry_id varchar(95) not null,
    trigger_name varchar(200) not null,
    trigger_group varchar(200) not null,
    is_volatile bool not null,
    instance_name varchar(200) not null,
    fired_time bigint not null,
    priority integer not null,
    state varchar(16) not null,
    job_name varchar(200) null,
    job_group varchar(200) null,
    is_stateful bool null,
    requests_recovery bool null,
    primary key (entry_id)
);

create table qrtz_scheduler_state 
  (
    instance_name varchar(200) not null,
    last_checkin_time bigint not null,
    checkin_interval bigint not null,
    primary key (instance_name)
);

create table qrtz_locks
  (
    lock_name  varchar(40) not null, 
    primary key (lock_name)
);


insert into qrtz_locks values('trigger_access');
insert into qrtz_locks values('job_access');
insert into qrtz_locks values('calendar_access');
insert into qrtz_locks values('state_access');
insert into qrtz_locks values('misfire_access');

commit;
