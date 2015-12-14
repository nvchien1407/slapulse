USE [slapulse]
GO
/****** Object:  Table [user_cookie]    Script Date: 08/13/2010 13:07:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [user_cookie](
   [id] [numeric](19, 0) NOT NULL,
   [cookie_id] [varchar](100) NOT NULL,
   [date_created] [datetime] NOT NULL,
   [username] [varchar](30) NOT NULL,
PRIMARY KEY CLUSTERED 
(
   [id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [role]    Script Date: 08/13/2010 13:07:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [role](
   [name] [varchar](20) NOT NULL,
   [description] [varchar](255) NULL,
   [version] [int] NULL,
PRIMARY KEY CLUSTERED 
(
   [name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [sequence_list]    Script Date: 08/23/2010 13:07:23 ******/
SET ANSI_NULLS ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [sequence_list](
   [name] [varchar] (255),
   [next_value] [int]
) 
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [Reference]    Script Date: 08/13/2010 13:07:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [Reference](
   [id] [int] NOT NULL,
   [groupName] [varchar](100) NULL,
   [hashcodeValue] [int] NULL,
   [itemName] [varchar](250) NULL,
   [note] [varchar](255) NULL,
   [subGroupName] [varchar](100) NULL,
   [timezone] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
   [id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [DefaultDay]    Script Date: 08/13/2010 13:07:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [DefaultDay](
   [id] [int] NOT NULL,
   [defaultDayFlag] [tinyint] NOT NULL,
   [nonWorkingDay] [tinyint] NULL,
   [regionId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
   [id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [ServiceLevelAgreement]    Script Date: 08/13/2010 13:07:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [ServiceLevelAgreement](
   [id] [int] NOT NULL,
   [tFormulaeDays] [int] NULL,
   [deadlineType] [varchar](255) NOT NULL,
   [durationHours] [int] NULL,
   [durationMinutes] [int] NULL,
   [durationType] [varchar](255) NOT NULL,
   [fixedTimeDaysToRoll] [int] NULL,
   [fixedTimeDeadline] [datetime] NULL,
   [fixedTimeThreshold] [datetime] NULL,
   [includeSpecialDays] [varchar](255) NULL,
   [name] [varchar](100) NOT NULL,
   [notificationThreshold] [numeric](19, 0) NULL,
   [notificationThresholdDays] [int] NULL,
   [notificationThresholdHours] [int] NULL,
   [notificationThresholdMinutes] [int] NULL,
   [notifyDeadlineApproaching] [tinyint] NULL,
   [pauseThresholdHours] [int] NULL,
   [pauseThresholdMinutes] [int] NULL,
   [enableCutOffTime] [tinyint] NOT NULL,
   [stopSlaWhenPaused] [tinyint] NOT NULL,
   [workTime] [varchar](255) NOT NULL,
   [calendarId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
   [id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [user_role]    Script Date: 08/13/2010 13:07:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [user_role](
   [user_username] [varchar](20) NOT NULL,
   [roles_name] [varchar](20) NOT NULL,
   [role_name] [varchar](20) NOT NULL,
   [users_username] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
   [role_name] ASC,
   [users_username] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [WorkHourRange]    Script Date: 08/13/2010 13:07:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [WorkHourRange](
   [id] [int] NOT NULL,
   [endHour] [varchar](255) NULL,
   [endMinute] [varchar](255) NULL,
   [fromTime] [datetime] NULL,
   [startHour] [varchar](255) NULL,
   [startMinute] [varchar](255) NULL,
   [toTime] [datetime] NULL,
   [dayId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
   [id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [DefaultWeekDay]    Script Date: 08/13/2010 13:07:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [DefaultWeekDay](
   [defaultWeekDayId] [int] NOT NULL,
   [day] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
   [defaultWeekDayId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [CalendarDate]    Script Date: 08/13/2010 13:07:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [CalendarDate](
   [calendarDateId] [int] NOT NULL,
   [dateInfo] [datetime] NOT NULL,
   [description] [varchar](255) NOT NULL,
   [name] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
   [calendarDateId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [BusinessProcess]    Script Date: 08/13/2010 13:07:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [BusinessProcess](
   [id] [int] NOT NULL,
   [description] [varchar](255) NOT NULL,
   [serviceLevelAgreementId] [int] NULL,
   [typeId] [int] NOT NULL,
   [nameId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
   [id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  ForeignKey [FKA290766F66869F78]    Script Date: 08/13/2010 13:07:23 ******/
ALTER TABLE [BusinessProcess]  WITH CHECK ADD  CONSTRAINT [FKA290766F66869F78] FOREIGN KEY([serviceLevelAgreementId])
REFERENCES [ServiceLevelAgreement] ([id])
GO
ALTER TABLE [BusinessProcess] CHECK CONSTRAINT [FKA290766F66869F78]
GO
/****** Object:  ForeignKey [FKA290766F908CE9D8]    Script Date: 08/13/2010 13:07:23 ******/
ALTER TABLE [BusinessProcess]  WITH CHECK ADD  CONSTRAINT [FKA290766F908CE9D8] FOREIGN KEY([nameId])
REFERENCES [Reference] ([id])
GO
ALTER TABLE [BusinessProcess] CHECK CONSTRAINT [FKA290766F908CE9D8]
GO
/****** Object:  ForeignKey [FKA290766F9C1D8EC7]    Script Date: 08/13/2010 13:07:23 ******/
ALTER TABLE [BusinessProcess]  WITH CHECK ADD  CONSTRAINT [FKA290766F9C1D8EC7] FOREIGN KEY([typeId])
REFERENCES [Reference] ([id])
GO
ALTER TABLE [BusinessProcess] CHECK CONSTRAINT [FKA290766F9C1D8EC7]
GO
/****** Object:  ForeignKey [FKF4BEE42C10985C6D]    Script Date: 08/13/2010 13:07:23 ******/
ALTER TABLE [CalendarDate]  WITH CHECK ADD  CONSTRAINT [FKF4BEE42C10985C6D] FOREIGN KEY([calendarDateId])
REFERENCES [DefaultDay] ([id])
GO
ALTER TABLE [CalendarDate] CHECK CONSTRAINT [FKF4BEE42C10985C6D]
GO
/****** Object:  ForeignKey [FK9F44DDBBA6282001]    Script Date: 08/13/2010 13:07:23 ******/
ALTER TABLE [DefaultDay]  WITH CHECK ADD  CONSTRAINT [FK9F44DDBBA6282001] FOREIGN KEY([regionId])
REFERENCES [Reference] ([id])
GO
ALTER TABLE [DefaultDay] CHECK CONSTRAINT [FK9F44DDBBA6282001]
GO
/****** Object:  ForeignKey [FK84DB29A754F17388]    Script Date: 08/13/2010 13:07:23 ******/
ALTER TABLE [DefaultWeekDay]  WITH CHECK ADD  CONSTRAINT [FK84DB29A754F17388] FOREIGN KEY([defaultWeekDayId])
REFERENCES [DefaultDay] ([id])
GO
ALTER TABLE [DefaultWeekDay] CHECK CONSTRAINT [FK84DB29A754F17388]
GO
/****** Object:  ForeignKey [FKA0720CDBE8DB09CB]    Script Date: 08/13/2010 13:07:23 ******/
ALTER TABLE [ServiceLevelAgreement]  WITH CHECK ADD  CONSTRAINT [FKA0720CDBE8DB09CB] FOREIGN KEY([calendarId])
REFERENCES [Reference] ([id])
GO
ALTER TABLE [ServiceLevelAgreement] CHECK CONSTRAINT [FKA0720CDBE8DB09CB]
GO
/****** Object:  ForeignKey [FK143BF46A4758D3C]    Script Date: 08/13/2010 13:07:23 ******/
ALTER TABLE [user_role]  WITH CHECK ADD  CONSTRAINT [FK143BF46A4758D3C] FOREIGN KEY([roles_name])
REFERENCES [role] ([name])
GO
ALTER TABLE [user_role] CHECK CONSTRAINT [FK143BF46A4758D3C]
GO
/****** Object:  ForeignKey [FK143BF46A8A99C723]    Script Date: 08/13/2010 13:07:23 ******/
ALTER TABLE [user_role]  WITH CHECK ADD  CONSTRAINT [FK143BF46A8A99C723] FOREIGN KEY([role_name])
REFERENCES [role] ([name])
GO
ALTER TABLE [user_role] CHECK CONSTRAINT [FK143BF46A8A99C723]
GO
/****** Object:  ForeignKey [FK7F6C4D88E35C981D]    Script Date: 08/13/2010 13:07:23 ******/
ALTER TABLE [WorkHourRange]  WITH CHECK ADD  CONSTRAINT [FK7F6C4D88E35C981D] FOREIGN KEY([dayId])
REFERENCES [DefaultDay] ([id])
GO
ALTER TABLE [WorkHourRange] CHECK CONSTRAINT [FK7F6C4D88E35C981D]
GO
