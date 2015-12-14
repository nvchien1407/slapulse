SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Reference]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Reference](
	[id] [int] NOT NULL,
	[groupName] [varchar](100) NULL,
	[subGroupName] [varchar](100) NULL,
	[itemName] [varchar](250) NULL,
	[note] [varchar](255) NULL,
	[hashcodeValue] [int] NULL,
        [timezone] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[appuser]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[appuser](
	[username] [varchar](20) NOT NULL,
	[password] [varchar](20) NOT NULL,
	[confirmPassword] [varchar](255) NULL,
	[first_name] [varchar](50) NOT NULL,
	[last_name] [varchar](50) NOT NULL,
	[phone_number] [varchar](50) NULL,
	[email] [varchar](50) NOT NULL,
	[website] [varchar](255) NULL,
	[password_hint] [varchar](255) NULL,
	[version] [int] NULL,
	[enabled] [tinyint] NULL,
PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[role]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[role](
	[name] [varchar](20) NOT NULL,
	[description] [varchar](255) NULL,
	[version] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[name] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[user_cookie]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[user_cookie](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[username] [varchar](30) NOT NULL,
	[cookie_id] [varchar](100) NOT NULL,
	[date_created] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sequence_list]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[sequence_list](
	[name] [varchar](255) NULL,
	[next_value] [int] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[DefaultWeekDay]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[DefaultWeekDay](
	[defaultWeekDayId] [int] NOT NULL,
	[day] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[defaultWeekDayId] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[WorkHourRange]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[WorkHourRange](
	[id] [int] NOT NULL,
	[fromTime] [datetime] NULL,
	[toTime] [datetime] NULL,
	[startMinute] [varchar](255) NULL,
	[startHour] [varchar](255) NULL,
	[endMinute] [varchar](255) NULL,
	[endHour] [varchar](255) NULL,
	[dayId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CalendarDate]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[CalendarDate](
	[calendarDateId] [int] NOT NULL,
	[dateInfo] [datetime] NOT NULL,
	[name] [varchar](255) NOT NULL,
	[description] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[calendarDateId] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[DefaultDay]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[DefaultDay](
	[id] [int] NOT NULL,
	[nonWorkingDay] [tinyint] NULL,
	[defaultDayFlag] [tinyint] NOT NULL,
	[regionId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[BusinessProcess]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[BusinessProcess](
	[id] [int] NOT NULL,
	[description] [varchar](255) NOT NULL,
	[serviceLevelAgreementId] [int] NULL,
	[nameId] [int] NOT NULL,
	[typeId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ServiceLevelAgreement]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[ServiceLevelAgreement](
	[id] [int] NOT NULL,
	[name] [varchar](100) NOT NULL,
	[durationType] [varchar](255) NOT NULL,
	[tFormulaeDays] [int] NULL,
	[durationHours] [int] NULL,
	[durationMinutes] [int] NULL,
	[deadlineType] [varchar](255) NOT NULL,
	[workTime] [varchar](255) NOT NULL,
	[pauseThresholdHours] [int] NULL,
	[pauseThresholdMinutes] [int] NULL,
	[enableCutOffTime] [tinyint] NOT NULL,
	[stopSlaWhenPaused] [tinyint] NOT NULL,
	[includeSpecialDays] [varchar](255) NULL,
	[notifyDeadlineApproaching] [tinyint] NULL,
	[notificationThreshold] [numeric](19, 0) NULL,
	[notificationThresholdDays] [int] NULL,
	[notificationThresholdHours] [int] NULL,
	[notificationThresholdMinutes] [int] NULL,
	[fixedTimeDeadline] [datetime] NULL,
	[fixedTimeThreshold] [datetime] NULL,
	[fixedTimeDaysToRoll] [int] NULL,
	[calendarId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[user_role]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[user_role](
	[appuser_username] [varchar](20) NOT NULL,
	[roles_name] [varchar](20) NOT NULL,
	[role_name] [varchar](20) NOT NULL,
	[users_username] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[role_name] ASC,
	[users_username] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK84DB29A754F17388]') AND parent_object_id = OBJECT_ID(N'[dbo].[DefaultWeekDay]'))
ALTER TABLE [dbo].[DefaultWeekDay]  WITH CHECK ADD  CONSTRAINT [FK84DB29A754F17388] FOREIGN KEY([defaultWeekDayId])
REFERENCES [dbo].[DefaultDay] ([id])
GO
ALTER TABLE [dbo].[DefaultWeekDay] CHECK CONSTRAINT [FK84DB29A754F17388]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK7F6C4D88E35C981D]') AND parent_object_id = OBJECT_ID(N'[dbo].[WorkHourRange]'))
ALTER TABLE [dbo].[WorkHourRange]  WITH CHECK ADD  CONSTRAINT [FK7F6C4D88E35C981D] FOREIGN KEY([dayId])
REFERENCES [dbo].[DefaultDay] ([id])
GO
ALTER TABLE [dbo].[WorkHourRange] CHECK CONSTRAINT [FK7F6C4D88E35C981D]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FKF4BEE42C10985C6D]') AND parent_object_id = OBJECT_ID(N'[dbo].[CalendarDate]'))
ALTER TABLE [dbo].[CalendarDate]  WITH CHECK ADD  CONSTRAINT [FKF4BEE42C10985C6D] FOREIGN KEY([calendarDateId])
REFERENCES [dbo].[DefaultDay] ([id])
GO
ALTER TABLE [dbo].[CalendarDate] CHECK CONSTRAINT [FKF4BEE42C10985C6D]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK9F44DDBBA6282001]') AND parent_object_id = OBJECT_ID(N'[dbo].[DefaultDay]'))
ALTER TABLE [dbo].[DefaultDay]  WITH CHECK ADD  CONSTRAINT [FK9F44DDBBA6282001] FOREIGN KEY([regionId])
REFERENCES [dbo].[Reference] ([id])
GO
ALTER TABLE [dbo].[DefaultDay] CHECK CONSTRAINT [FK9F44DDBBA6282001]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FKA290766F66869F78]') AND parent_object_id = OBJECT_ID(N'[dbo].[BusinessProcess]'))
ALTER TABLE [dbo].[BusinessProcess]  WITH CHECK ADD  CONSTRAINT [FKA290766F66869F78] FOREIGN KEY([serviceLevelAgreementId])
REFERENCES [dbo].[ServiceLevelAgreement] ([id])
GO
ALTER TABLE [dbo].[BusinessProcess] CHECK CONSTRAINT [FKA290766F66869F78]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FKA290766F908CE9D8]') AND parent_object_id = OBJECT_ID(N'[dbo].[BusinessProcess]'))
ALTER TABLE [dbo].[BusinessProcess]  WITH CHECK ADD  CONSTRAINT [FKA290766F908CE9D8] FOREIGN KEY([nameId])
REFERENCES [dbo].[Reference] ([id])
GO
ALTER TABLE [dbo].[BusinessProcess] CHECK CONSTRAINT [FKA290766F908CE9D8]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FKA290766F9C1D8EC7]') AND parent_object_id = OBJECT_ID(N'[dbo].[BusinessProcess]'))
ALTER TABLE [dbo].[BusinessProcess]  WITH CHECK ADD  CONSTRAINT [FKA290766F9C1D8EC7] FOREIGN KEY([typeId])
REFERENCES [dbo].[Reference] ([id])
GO
ALTER TABLE [dbo].[BusinessProcess] CHECK CONSTRAINT [FKA290766F9C1D8EC7]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FKA0720CDBE8DB09CB]') AND parent_object_id = OBJECT_ID(N'[dbo].[ServiceLevelAgreement]'))
ALTER TABLE [dbo].[ServiceLevelAgreement]  WITH CHECK ADD  CONSTRAINT [FKA0720CDBE8DB09CB] FOREIGN KEY([calendarId])
REFERENCES [dbo].[Reference] ([id])
GO
ALTER TABLE [dbo].[ServiceLevelAgreement] CHECK CONSTRAINT [FKA0720CDBE8DB09CB]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK143BF46A30F4C0D]') AND parent_object_id = OBJECT_ID(N'[dbo].[user_role]'))
ALTER TABLE [dbo].[user_role]  WITH CHECK ADD  CONSTRAINT [FK143BF46A30F4C0D] FOREIGN KEY([appuser_username])
REFERENCES [dbo].[appuser] ([username])
GO
ALTER TABLE [dbo].[user_role] CHECK CONSTRAINT [FK143BF46A30F4C0D]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK143BF46A4758D3C]') AND parent_object_id = OBJECT_ID(N'[dbo].[user_role]'))
ALTER TABLE [dbo].[user_role]  WITH CHECK ADD  CONSTRAINT [FK143BF46A4758D3C] FOREIGN KEY([roles_name])
REFERENCES [dbo].[role] ([name])
GO
ALTER TABLE [dbo].[user_role] CHECK CONSTRAINT [FK143BF46A4758D3C]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK143BF46A8A99C723]') AND parent_object_id = OBJECT_ID(N'[dbo].[user_role]'))
ALTER TABLE [dbo].[user_role]  WITH CHECK ADD  CONSTRAINT [FK143BF46A8A99C723] FOREIGN KEY([role_name])
REFERENCES [dbo].[role] ([name])
GO
ALTER TABLE [dbo].[user_role] CHECK CONSTRAINT [FK143BF46A8A99C723]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK143BF46ADF4A1AF1]') AND parent_object_id = OBJECT_ID(N'[dbo].[user_role]'))
ALTER TABLE [dbo].[user_role]  WITH CHECK ADD  CONSTRAINT [FK143BF46ADF4A1AF1] FOREIGN KEY([users_username])
REFERENCES [dbo].[appuser] ([username])
GO
ALTER TABLE [dbo].[user_role] CHECK CONSTRAINT [FK143BF46ADF4A1AF1]
