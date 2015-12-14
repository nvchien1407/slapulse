--------------------------------------------------------
--  File created - Thursday-May-13-2010   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Sequence HIBERNATE_SEQUENCE
--------------------------------------------------------

   CREATE SEQUENCE  "HIBERNATE_SEQUENCE"  MINVALUE 1 MAXVALUE 1.00000000000000E+27 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table APPUSER
--------------------------------------------------------

  CREATE TABLE "APPUSER" 
   (	"USERNAME" VARCHAR2(20 CHAR), 
	"PASSWORD" VARCHAR2(20 CHAR), 
	"CONFIRMPASSWORD" VARCHAR2(255 CHAR), 
	"FIRST_NAME" VARCHAR2(50 CHAR), 
	"LAST_NAME" VARCHAR2(50 CHAR), 
	"PHONE_NUMBER" VARCHAR2(50 CHAR), 
	"EMAIL" VARCHAR2(50 CHAR), 
	"WEBSITE" VARCHAR2(255 CHAR), 
	"PASSWORD_HINT" VARCHAR2(255 CHAR), 
	"VERSION" NUMBER(10,0), 
	"ENABLED" NUMBER(1,0)
   ) ;
--------------------------------------------------------
--  DDL for Table BUSINESSPROCESS
--------------------------------------------------------

  CREATE TABLE "BUSINESSPROCESS" 
   (	"ID" NUMBER(10,0), 
	"DESCRIPTION" VARCHAR2(255 CHAR) NOT NULL, 
	"NAMEID" NUMBER(10,0), 
	"SERVICELEVELAGREEMENTID" NUMBER(10,0), 
	"TYPEID" NUMBER(10,0)
   ) ;
--------------------------------------------------------
--  DDL for Table CALENDARDATE
--------------------------------------------------------

  CREATE TABLE "CALENDARDATE" 
   (	"CALENDARDATEID" NUMBER(10,0), 
	"DATEINFO" TIMESTAMP (6), 
	"NAME" VARCHAR2(255 CHAR), 
	"DESCRIPTION" VARCHAR2(255 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table DEFAULTDAY
--------------------------------------------------------

  CREATE TABLE "DEFAULTDAY" 
   (	"ID" NUMBER(10,0), 
	"NONWORKINGDAY" NUMBER(1,0), 
	"DEFAULTDAYFLAG" NUMBER(1,0), 
	"REGIONID" NUMBER(10,0)
   ) ;
--------------------------------------------------------
--  DDL for Table DEFAULTWEEKDAY
--------------------------------------------------------

  CREATE TABLE "DEFAULTWEEKDAY" 
   (	"DEFAULTWEEKDAYID" NUMBER(10,0), 
	"DAY" VARCHAR2(255 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table REFERENCE
--------------------------------------------------------

  CREATE TABLE "REFERENCE" 
   (	"ID" NUMBER(10,0), 
	"GROUPNAME" VARCHAR2(100 CHAR), 
	"SUBGROUPNAME" VARCHAR2(100 CHAR), 
	"ITEMNAME" VARCHAR2(250 CHAR), 
	"NOTE" VARCHAR2(255 CHAR), 
	"HASHCODEVALUE" NUMBER(10,0),
	"TIMEZONE" VARCHAR2(50 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table ROLE
--------------------------------------------------------

  CREATE TABLE "ROLE" 
   (	"NAME" VARCHAR2(20 CHAR), 
	"DESCRIPTION" VARCHAR2(255 CHAR), 
	"VERSION" NUMBER(10,0)
   ) ;
--------------------------------------------------------
--  DDL for Table SEQUENCE_LIST
--------------------------------------------------------

  CREATE TABLE "SEQUENCE_LIST" 
   (	"NAME" VARCHAR2(255 CHAR), 
	"NEXT_VALUE" NUMBER(10,0)
   ) ;
--------------------------------------------------------
--  DDL for Table SERVICELEVELAGREEMENT
--------------------------------------------------------

  CREATE TABLE "SERVICELEVELAGREEMENT" 
   (	"ID" NUMBER(10,0), 
	"NAME" VARCHAR2(100 CHAR), 
	"DURATIONTYPE" VARCHAR2(255 CHAR), 
	"TFORMULAEDAYS" NUMBER(10,0), 
	"DURATIONHOURS" NUMBER(10,0), 
	"DURATIONMINUTES" NUMBER(10,0), 
	"DEADLINETYPE" VARCHAR2(255 CHAR), 
	"WORKTIME" VARCHAR2(255 CHAR), 
	"PAUSETHRESHOLDHOURS" NUMBER(10,0), 
	"PAUSETHRESHOLDMINUTES" NUMBER(10,0),
	"ENABLECUTOFFTIME" NUMBER(1,0),
	"STOPSLAWHENPAUSED" NUMBER(1,0), 
	"INCLUDESPECIALDAYS" VARCHAR2(255 CHAR), 
	"NOTIFYDEADLINEAPPROACHING" NUMBER(1,0), 
	"NOTIFICATIONTHRESHOLD" NUMBER(19,0), 
	"NOTIFICATIONTHRESHOLDDAYS" NUMBER(10,0), 
	"NOTIFICATIONTHRESHOLDHOURS" NUMBER(10,0), 
	"NOTIFICATIONTHRESHOLDMINUTES" NUMBER(10,0), 
	"FIXEDTIMEDEADLINE" TIMESTAMP (6), 
	"FIXEDTIMETHRESHOLD" TIMESTAMP (6), 
	"FIXEDTIMEDAYSTOROLL" NUMBER(10,0), 
	"CALENDARID" NUMBER(10,0)
   ) ;
--------------------------------------------------------
--  DDL for Table USER_COOKIE
--------------------------------------------------------

  CREATE TABLE "USER_COOKIE" 
   (	"ID" NUMBER(19,0), 
	"USERNAME" VARCHAR2(30 CHAR), 
	"COOKIE_ID" VARCHAR2(100 CHAR), 
	"DATE_CREATED" TIMESTAMP (6)
   ) ;
--------------------------------------------------------
--  DDL for Table USER_ROLE
--------------------------------------------------------

  CREATE TABLE "USER_ROLE" 
   (	"APPUSER_USERNAME" VARCHAR2(20 CHAR), 
	"ROLES_NAME" VARCHAR2(20 CHAR), 
	"ROLE_NAME" VARCHAR2(20 CHAR), 
	"USERS_USERNAME" VARCHAR2(20 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table WORKHOURRANGE
--------------------------------------------------------

  CREATE TABLE "WORKHOURRANGE" 
   (	"ID" NUMBER(10,0), 
	"FROMTIME" TIMESTAMP (6), 
	"TOTIME" TIMESTAMP (6), 
	"STARTMINUTE" VARCHAR2(255 CHAR), 
	"STARTHOUR" VARCHAR2(255 CHAR), 
	"ENDMINUTE" VARCHAR2(255 CHAR), 
	"ENDHOUR" VARCHAR2(255 CHAR), 
	"DAYID" NUMBER(10,0)
   ) ;
--------------------------------------------------------
--  Constraints for Table APPUSER
--------------------------------------------------------

  ALTER TABLE "APPUSER" ADD PRIMARY KEY ("USERNAME") ENABLE;
--------------------------------------------------------
--  Constraints for Table BUSINESSPROCESS
--------------------------------------------------------

  ALTER TABLE "BUSINESSPROCESS" ADD PRIMARY KEY ("ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table CALENDARDATE
--------------------------------------------------------

  ALTER TABLE "CALENDARDATE" ADD PRIMARY KEY ("CALENDARDATEID") ENABLE;
--------------------------------------------------------
--  Constraints for Table DEFAULTDAY
--------------------------------------------------------

  ALTER TABLE "DEFAULTDAY" ADD PRIMARY KEY ("ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table DEFAULTWEEKDAY
--------------------------------------------------------

  ALTER TABLE "DEFAULTWEEKDAY" ADD PRIMARY KEY ("DEFAULTWEEKDAYID") ENABLE;
--------------------------------------------------------
--  Constraints for Table REFERENCE
--------------------------------------------------------

  ALTER TABLE "REFERENCE" ADD PRIMARY KEY ("ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table ROLE
--------------------------------------------------------

  ALTER TABLE "ROLE" ADD PRIMARY KEY ("NAME") ENABLE;

--------------------------------------------------------
--  Constraints for Table SERVICELEVELAGREEMENT
--------------------------------------------------------

  ALTER TABLE "SERVICELEVELAGREEMENT" ADD PRIMARY KEY ("ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table USER_COOKIE
--------------------------------------------------------

  ALTER TABLE "USER_COOKIE" ADD PRIMARY KEY ("ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table USER_ROLE
--------------------------------------------------------

  ALTER TABLE "USER_ROLE" ADD PRIMARY KEY ("ROLE_NAME", "USERS_USERNAME") ENABLE;
--------------------------------------------------------
--  Constraints for Table WORKHOURRANGE
--------------------------------------------------------

  ALTER TABLE "WORKHOURRANGE" ADD PRIMARY KEY ("ID") ENABLE;
--------------------------------------------------------
--  DDL for Index SYS_C0010713
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010713" ON "APPUSER" ("USERNAME") 
  ;
--------------------------------------------------------
--  DDL for Index SYS_C0010682
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010682" ON "BUSINESSPROCESS" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index SYS_C0010687
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010687" ON "CALENDARDATE" ("CALENDARDATEID") 
  ;
--------------------------------------------------------
--  DDL for Index SYS_C0010691
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010691" ON "DEFAULTDAY" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index SYS_C0010694
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010694" ON "DEFAULTWEEKDAY" ("DEFAULTWEEKDAYID") 
  ;
--------------------------------------------------------
--  DDL for Index SYS_C0010696
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010696" ON "REFERENCE" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index SYS_C0010715
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010715" ON "ROLE" ("NAME") 
  ;
--------------------------------------------------------
--  DDL for Index SYS_C0010704
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010704" ON "SERVICELEVELAGREEMENT" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index SYS_C0010720
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010720" ON "USER_COOKIE" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index SYS_C0010725
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010725" ON "USER_ROLE" ("ROLE_NAME", "USERS_USERNAME") 
  ;
--------------------------------------------------------
--  DDL for Index SYS_C0010707
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010707" ON "WORKHOURRANGE" ("ID") 
  ;

--------------------------------------------------------
--  Ref Constraints for Table BUSINESSPROCESS
--------------------------------------------------------

  ALTER TABLE "BUSINESSPROCESS" ADD CONSTRAINT "FKA290766F66869F78" FOREIGN KEY ("SERVICELEVELAGREEMENTID")
	  REFERENCES "SERVICELEVELAGREEMENT" ("ID") ENABLE;
 
  ALTER TABLE "BUSINESSPROCESS" ADD CONSTRAINT "FKA290766F908CE9D8" FOREIGN KEY ("NAMEID")
	  REFERENCES "REFERENCE" ("ID") ENABLE;
 
  ALTER TABLE "BUSINESSPROCESS" ADD CONSTRAINT "FKA290766F9C1D8EC7" FOREIGN KEY ("TYPEID")
	  REFERENCES "REFERENCE" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CALENDARDATE
--------------------------------------------------------

  ALTER TABLE "CALENDARDATE" ADD CONSTRAINT "FKF4BEE42C10985C6D" FOREIGN KEY ("CALENDARDATEID")
	  REFERENCES "DEFAULTDAY" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table DEFAULTDAY
--------------------------------------------------------

  ALTER TABLE "DEFAULTDAY" ADD CONSTRAINT "FK9F44DDBBA6282001" FOREIGN KEY ("REGIONID")
	  REFERENCES "REFERENCE" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table DEFAULTWEEKDAY
--------------------------------------------------------

  ALTER TABLE "DEFAULTWEEKDAY" ADD CONSTRAINT "FK84DB29A754F17388" FOREIGN KEY ("DEFAULTWEEKDAYID")
	  REFERENCES "DEFAULTDAY" ("ID") ENABLE;



--------------------------------------------------------
--  Ref Constraints for Table SERVICELEVELAGREEMENT
--------------------------------------------------------

  ALTER TABLE "SERVICELEVELAGREEMENT" ADD CONSTRAINT "FKA0720CDBE8DB09CB" FOREIGN KEY ("CALENDARID")
	  REFERENCES "REFERENCE" ("ID") ENABLE;

--------------------------------------------------------
--  Ref Constraints for Table USER_ROLE
--------------------------------------------------------

  ALTER TABLE "USER_ROLE" ADD CONSTRAINT "FK143BF46A30F4C0D" FOREIGN KEY ("APPUSER_USERNAME")
	  REFERENCES "APPUSER" ("USERNAME") ENABLE;
 
  ALTER TABLE "USER_ROLE" ADD CONSTRAINT "FK143BF46A4758D3C" FOREIGN KEY ("ROLES_NAME")
	  REFERENCES "ROLE" ("NAME") ENABLE;
 
  ALTER TABLE "USER_ROLE" ADD CONSTRAINT "FK143BF46A8A99C723" FOREIGN KEY ("ROLE_NAME")
	  REFERENCES "ROLE" ("NAME") ENABLE;
 
  ALTER TABLE "USER_ROLE" ADD CONSTRAINT "FK143BF46ADF4A1AF1" FOREIGN KEY ("USERS_USERNAME")
	  REFERENCES "APPUSER" ("USERNAME") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table WORKHOURRANGE
--------------------------------------------------------

  ALTER TABLE "WORKHOURRANGE" ADD CONSTRAINT "FK7F6C4D88E35C981D" FOREIGN KEY ("DAYID")
	  REFERENCES "DEFAULTDAY" ("ID") ENABLE;
