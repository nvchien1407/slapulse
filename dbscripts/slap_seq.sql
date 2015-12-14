/*
Fix up sequence_list table so that you can continue maintaining SLAP using admin console
*/

delete from SEQUENCE_LIST;

insert into SEQUENCE_LIST select 'Reference', max(id)+1 from REFERENCE;
insert into SEQUENCE_LIST select 'DefaultDay', max(id)+1 from DefaultDay;
insert into SEQUENCE_LIST select 'WorkHourRange', max(id)+1 from WorkHourRange;
insert into SEQUENCE_LIST select 'BusinessProcess', max(id)+1 from BusinessProcess;
insert into SEQUENCE_LIST select 'ServiceLevelAgreement', max(id)+1 from ServiceLevelAgreement;
insert into SEQUENCE_LIST select 'DefaultWeekDay', max(DEFAULTWEEKDAYID)+1 from DefaultWeekDay;
insert into SEQUENCE_LIST select 'CalendarDate', max(CALENDARDATEID)+1 from CalendarDate;
insert into SEQUENCE_LIST select 'ChangeLog', max(ID)+1 from ChangeLog;

commit;
