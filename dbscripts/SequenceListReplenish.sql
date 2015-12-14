DECLARE @result int

SELECT @result=MAX(id) FROM BusinessProcess
if(@result is NULL) 
begin
    select @result=0
end
INSERT INTO sequence_list (name, next_value) VALUES ('BusinessProcess', @result+1)

SELECT @result=MAX(calendarDateId) FROM CalendarDate
if(@result is NULL) 
begin
	select @result=0
end
INSERT INTO sequence_list (name, next_value) VALUES ('CalendarDate', @result+1)

SELECT @result=MAX(id) FROM DefaultDay
if(@result is NULL) 
begin
	select @result=0
end
INSERT INTO sequence_list (name, next_value) VALUES ('DefaultDay', @result+1)

SELECT @result=MAX(defaultWeekDayId) FROM DefaultWeekDay
if(@result is NULL) 
begin
	select @result=0
end
INSERT INTO sequence_list (name, next_value) VALUES ('DefaultWeekDay', @result+1)

SELECT @result=MAX(id) FROM Reference
if(@result is NULL) 
begin
	select @result=0
end
INSERT INTO sequence_list (name, next_value) VALUES ('Reference', @result+1)

SELECT @result=MAX(id) FROM ServiceLevelAgreement
if(@result is NULL) 
begin
	select @result=0
end
INSERT INTO sequence_list (name, next_value) VALUES ('ServiceLevelAgreement', @result+1)

SELECT @result=MAX(id) FROM user_cookie
if(@result is NULL) 
begin
	select @result=0
end
INSERT INTO sequence_list (name, next_value) VALUES ('user_cookie', @result+1)

SELECT @result=MAX(id) FROM WorkHourRange
if(@result is NULL) 
begin
	select @result=0
end
INSERT INTO sequence_list (name, next_value) VALUES ('WorkHourRange', @result+1)