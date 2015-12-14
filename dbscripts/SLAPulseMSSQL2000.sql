/****** Object:  Database slapulse  Script Date: 8/04/2010 11:10:52 AM ******/

use [slapulse]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FKA290766F908CE9D8]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[BusinessProcess] DROP CONSTRAINT FKA290766F908CE9D8
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FKA290766F9C1D8EC7]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[BusinessProcess] DROP CONSTRAINT FKA290766F9C1D8EC7
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK9F44DDBBA6282001]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[DefaultDay] DROP CONSTRAINT FK9F44DDBBA6282001
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FKA0720CDBE8DB09CB]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[ServiceLevelAgreement] DROP CONSTRAINT FKA0720CDBE8DB09CB
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK143BF46A66C02E9A]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[user_role] DROP CONSTRAINT FK143BF46A66C02E9A
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK143BF46AEA241C9]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[user_role] DROP CONSTRAINT FK143BF46AEA241C9
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK143BF46A8A99C723]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[user_role] DROP CONSTRAINT FK143BF46A8A99C723
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FKF4BEE42C10985C6D]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[CalendarDate] DROP CONSTRAINT FKF4BEE42C10985C6D
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK84DB29A754F17388]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[DefaultWeekDay] DROP CONSTRAINT FK84DB29A754F17388
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK7F6C4D88E35C981D]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[WorkHourRange] DROP CONSTRAINT FK7F6C4D88E35C981D
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FKA290766F66869F78]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[BusinessProcess] DROP CONSTRAINT FKA290766F66869F78
GO

/****** Object:  Table [dbo].[BusinessProcess]    Script Date: 8/04/2010 11:10:55 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[BusinessProcess]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[BusinessProcess]
GO

/****** Object:  Table [dbo].[CalendarDate]    Script Date: 8/04/2010 11:10:55 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[CalendarDate]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[CalendarDate]
GO

/****** Object:  Table [dbo].[DefaultWeekDay]    Script Date: 8/04/2010 11:10:55 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[DefaultWeekDay]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[DefaultWeekDay]
GO

/****** Object:  Table [dbo].[WorkHourRange]    Script Date: 8/04/2010 11:10:55 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[WorkHourRange]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[WorkHourRange]
GO

/****** Object:  Table [dbo].[DefaultDay]    Script Date: 8/04/2010 11:10:55 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[DefaultDay]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[DefaultDay]
GO

/****** Object:  Table [dbo].[ServiceLevelAgreement]    Script Date: 8/04/2010 11:10:55 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ServiceLevelAgreement]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ServiceLevelAgreement]
GO

/****** Object:  Table [dbo].[user_role]    Script Date: 8/04/2010 11:10:55 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[user_role]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[user_role]
GO

/****** Object:  Table [dbo].[ChangeLog]    Script Date: 8/04/2010 11:10:55 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ChangeLog]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ChangeLog]
GO

/****** Object:  Table [dbo].[Reference]    Script Date: 8/04/2010 11:10:55 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[Reference]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[Reference]
GO

/****** Object:  Table [dbo].[app_user]    Script Date: 8/04/2010 11:10:55 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[app_user]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[app_user]
GO

/****** Object:  Table [dbo].[role]    Script Date: 8/04/2010 11:10:55 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[role]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[role]
GO

/****** Object:  Table [dbo].[user_cookie]    Script Date: 8/04/2010 11:10:55 AM ******/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[user_cookie]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[user_cookie]
GO

/****** Object:  Table [dbo].[ChangeLog]    Script Date: 8/04/2010 11:10:56 AM ******/
CREATE TABLE [dbo].[ChangeLog] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[operateType] [varchar] (8) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[operateTime] [datetime] NOT NULL ,
	[changes] [varchar] (1024) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[tableName] [varchar] (64) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[UserId] [varchar] (64) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[entityId] [varchar] (10) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[Reference]    Script Date: 8/04/2010 11:10:58 AM ******/
CREATE TABLE [dbo].[Reference] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[groupName] [varchar] (100) COLLATE SQL_Latin1_General_CP1_CS_AS NULL ,
	[subGroupName] [varchar] (100) COLLATE SQL_Latin1_General_CP1_CS_AS NULL ,
	[itemName] [varchar] (250) COLLATE SQL_Latin1_General_CP1_CS_AS NULL ,
	[note] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CS_AS NULL ,
        [timezone] [varchar] (50) COLLATE SQL_Latin1_General_CP1_CS_AS NULL
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[app_user]    Script Date: 8/04/2010 11:10:58 AM ******/
CREATE TABLE [dbo].[app_user] (
	[username] [varchar] (20) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[version] [int] NOT NULL ,
	[password] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[first_name] [varchar] (50) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[last_name] [varchar] (50) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[address] [varchar] (150) COLLATE SQL_Latin1_General_CP1_CS_AS NULL ,
	[city] [varchar] (50) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[province] [varchar] (100) COLLATE SQL_Latin1_General_CP1_CS_AS NULL ,
	[country] [varchar] (100) COLLATE SQL_Latin1_General_CP1_CS_AS NULL ,
	[postal_code] [varchar] (15) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[email] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[phone_number] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CS_AS NULL ,
	[website] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CS_AS NULL ,
	[password_hint] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CS_AS NULL ,
	[enabled] [tinyint] NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[role]    Script Date: 8/04/2010 11:10:58 AM ******/
CREATE TABLE [dbo].[role] (
	[name] [varchar] (20) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[version] [int] NOT NULL ,
	[description] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CS_AS NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[user_cookie]    Script Date: 8/04/2010 11:10:58 AM ******/
CREATE TABLE [dbo].[user_cookie] (
	[id] [numeric](19, 0) NOT NULL ,
	[username] [varchar] (30) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[cookie_id] [varchar] (100) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[date_created] [datetime] NOT NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[DefaultDay]    Script Date: 8/04/2010 11:11:00 AM ******/
CREATE TABLE [dbo].[DefaultDay] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[regionId] [int] NULL ,
	[nonWorkingDay] [tinyint] NULL ,
	[defaultDayFlag] [tinyint] NOT NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[ServiceLevelAgreement]    Script Date: 8/04/2010 11:11:00 AM ******/
CREATE TABLE [dbo].[ServiceLevelAgreement] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[deadlineType] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[durationHours] [int] NULL ,
	[durationMinutes] [int] NULL ,
	[durationType] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[name] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[pauseThresholdHours] [int] NULL ,
	[pauseThresholdMinutes] [int] NULL ,
	[tFormulaeDays] [int] NULL ,
	[workTime] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[enableCutOffTime] [tinyint] NOT NULL ,
	[stopSlaWhenPaused] [tinyint] NOT NULL ,
	[calendarId] [int] NULL ,
	[includeSpecialDays] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CS_AS NULL ,
	[notifyDeadlineApproaching] [tinyint] NULL ,
	[notificationThreshold] [numeric](19, 0) NULL ,
	[fixedTimeDaysToRoll] [int] NULL ,
	[fixedTimeDeadline] [datetime] NULL ,
	[fixedTimeThreshold] [datetime] NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[user_role]    Script Date: 8/04/2010 11:11:00 AM ******/
CREATE TABLE [dbo].[user_role] (
	[username] [varchar] (20) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[role_name] [varchar] (20) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[BusinessProcess]    Script Date: 8/04/2010 11:11:00 AM ******/
CREATE TABLE [dbo].[BusinessProcess] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[nameId] [int] NULL ,
	[typeId] [int] NULL ,
	[serviceLevelAgreementId] [int] NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[CalendarDate]    Script Date: 8/04/2010 11:11:00 AM ******/
CREATE TABLE [dbo].[CalendarDate] (
	[calendarDateId] [int] NOT NULL ,
	[name] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL ,
	[dateInfo] [datetime] NOT NULL ,
	[description] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[DefaultWeekDay]    Script Date: 8/04/2010 11:11:01 AM ******/
CREATE TABLE [dbo].[DefaultWeekDay] (
	[defaultWeekDayId] [int] NOT NULL ,
	[day] [varchar] (255) COLLATE SQL_Latin1_General_CP1_CS_AS NOT NULL 
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[WorkHourRange]    Script Date: 8/04/2010 11:11:02 AM ******/
CREATE TABLE [dbo].[WorkHourRange] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[fromTime] [datetime] NULL ,
	[toTime] [datetime] NULL ,
	[dayId] [int] NULL 
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ChangeLog] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[Reference] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[app_user] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[username]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[role] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[name]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[user_cookie] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[DefaultDay] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[ServiceLevelAgreement] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[user_role] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[username],
		[role_name]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[BusinessProcess] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[CalendarDate] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[calendarDateId]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[DefaultWeekDay] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[defaultWeekDayId]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[WorkHourRange] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[app_user] ADD 
	 UNIQUE  NONCLUSTERED 
	(
		[email]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[DefaultDay] ADD 
	CONSTRAINT [FK9F44DDBBA6282001] FOREIGN KEY 
	(
		[regionId]
	) REFERENCES [dbo].[Reference] (
		[id]
	)
GO

ALTER TABLE [dbo].[ServiceLevelAgreement] ADD 
	CONSTRAINT [FKA0720CDBE8DB09CB] FOREIGN KEY 
	(
		[calendarId]
	) REFERENCES [dbo].[Reference] (
		[id]
	)
GO

ALTER TABLE [dbo].[user_role] ADD 
	CONSTRAINT [FK143BF46A66C02E9A] FOREIGN KEY 
	(
		[username]
	) REFERENCES [dbo].[app_user] (
		[username]
	),
	CONSTRAINT [FK143BF46A8A99C723] FOREIGN KEY 
	(
		[role_name]
	) REFERENCES [dbo].[role] (
		[name]
	),
	CONSTRAINT [FK143BF46AEA241C9] FOREIGN KEY 
	(
		[username]
	) REFERENCES [dbo].[app_user] (
		[username]
	)
GO

ALTER TABLE [dbo].[BusinessProcess] ADD 
	CONSTRAINT [FKA290766F66869F78] FOREIGN KEY 
	(
		[serviceLevelAgreementId]
	) REFERENCES [dbo].[ServiceLevelAgreement] (
		[id]
	),
	CONSTRAINT [FKA290766F908CE9D8] FOREIGN KEY 
	(
		[nameId]
	) REFERENCES [dbo].[Reference] (
		[id]
	),
	CONSTRAINT [FKA290766F9C1D8EC7] FOREIGN KEY 
	(
		[typeId]
	) REFERENCES [dbo].[Reference] (
		[id]
	)
GO

ALTER TABLE [dbo].[CalendarDate] ADD 
	CONSTRAINT [FKF4BEE42C10985C6D] FOREIGN KEY 
	(
		[calendarDateId]
	) REFERENCES [dbo].[DefaultDay] (
		[id]
	)
GO

ALTER TABLE [dbo].[DefaultWeekDay] ADD 
	CONSTRAINT [FK84DB29A754F17388] FOREIGN KEY 
	(
		[defaultWeekDayId]
	) REFERENCES [dbo].[DefaultDay] (
		[id]
	)
GO

ALTER TABLE [dbo].[WorkHourRange] ADD 
	CONSTRAINT [FK7F6C4D88E35C981D] FOREIGN KEY 
	(
		[dayId]
	) REFERENCES [dbo].[DefaultDay] (
		[id]
	)
GO

﻿
