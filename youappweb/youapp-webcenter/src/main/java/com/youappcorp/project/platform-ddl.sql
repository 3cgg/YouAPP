--  ----------------USERS-------------------------
CREATE TABLE USERS (
  ID VARCHAR(32),
  USERNAME VARCHAR(32) DEFAULT NULL,
  PASSWORD VARCHAR(64) DEFAULT NULL,
  CREATEID VARCHAR(32),
  CREATETIME TIMESTAMP  ,
  UPDATEID VARCHAR(32) , 
  UPDATETIME TIMESTAMP,
  DELETED VARCHAR(1) DEFAULT NULL,
  VERSION INT, 
  PRIMARY KEY (ID)
);
--  ----------------USER-EXTEND-------------------------
CREATE TABLE USERS_EXTEND (
  ID VARCHAR(32),
  USERID VARCHAR(32) DEFAULT NULL,
  USERNAME VARCHAR(32) DEFAULT NULL,
  USERIMAGE VARCHAR(256) DEFAULT NULL,
  NATURENAME VARCHAR(64) DEFAULT NULL,
  CREATEID VARCHAR(32),
  CREATETIME TIMESTAMP  ,
  UPDATEID VARCHAR(32) , 
  UPDATETIME TIMESTAMP,
  DELETED VARCHAR(1) DEFAULT NULL,
  VERSION INT, 
  PRIMARY KEY (ID)
);
--  ---------------ROLES-------------------------------
CREATE TABLE ROLES (
  ID VARCHAR(32),
  ROLECODE VARCHAR(32) DEFAULT NULL,
  ROLENAME VARCHAR(64) DEFAULT NULL,
  DESCRIPTION VARCHAR(256) DEFAULT NULL,
  CREATEID VARCHAR(32),
  CREATETIME TIMESTAMP  ,
  UPDATEID VARCHAR(32) , 
  UPDATETIME TIMESTAMP,
  DELETED VARCHAR(1) DEFAULT NULL,
  VERSION INT, 
  PRIMARY KEY (ID)
) ;
--  -----------------GROUPS------------------------------
CREATE TABLE GROUPS (
  ID VARCHAR(32),
  GROUPCODE VARCHAR(32) DEFAULT NULL,
  GROUPNAME VARCHAR(64) DEFAULT NULL,
  DESCRIPTION VARCHAR(256) DEFAULT NULL,
  CREATEID VARCHAR(32),
  CREATETIME TIMESTAMP  ,
  UPDATEID VARCHAR(32) , 
  UPDATETIME TIMESTAMP,
  DELETED VARCHAR(1) DEFAULT NULL,
  VERSION INT, 
  PRIMARY KEY (ID)
) ;
--  ----------------ROLES_GROUPS-----------------------------------
CREATE TABLE ROLES_GROUPS (
  ID VARCHAR(32),
  ROLEID VARCHAR(32) DEFAULT NULL,
  GROUPID VARCHAR(32) DEFAULT NULL,
  DESCRIPTION VARCHAR(256) DEFAULT NULL,
  CREATEID VARCHAR(32),
  CREATETIME TIMESTAMP  ,
  UPDATEID VARCHAR(32) , 
  UPDATETIME TIMESTAMP,
  DELETED VARCHAR(1) DEFAULT NULL,
  VERSION INT, 
  PRIMARY KEY (ID)
) ;
--  ----------------USERS_ROLES-----------------------------------
CREATE TABLE USERS_ROLES (
  ID VARCHAR(32),
  USERID VARCHAR(32) DEFAULT NULL,
  ROLEID VARCHAR(32) DEFAULT NULL,
  DESCRIPTION VARCHAR(256) DEFAULT NULL,
  CREATEID VARCHAR(32),
  CREATETIME TIMESTAMP  ,
  UPDATEID VARCHAR(32) , 
  UPDATETIME TIMESTAMP,
  DELETED VARCHAR(1) DEFAULT NULL,
  VERSION INT, 
  PRIMARY KEY (ID)
) ;
--  ----------------USERS_GROUPS-----------------------------------
CREATE TABLE USERS_GROUPS (
  ID VARCHAR(32),
  USERID VARCHAR(32) DEFAULT NULL,
  GROUPID VARCHAR(32) DEFAULT NULL,
  DESCRIPTION VARCHAR(256) DEFAULT NULL,
  CREATEID VARCHAR(32),
  CREATETIME TIMESTAMP  ,
  UPDATEID VARCHAR(32) , 
  UPDATETIME TIMESTAMP,
  DELETED VARCHAR(1) DEFAULT NULL,
  VERSION INT, 
  PRIMARY KEY (ID)
) ;
--  --------------------USER_TRACKER--------------------------------
CREATE TABLE USER_TRACKER (
  ID VARCHAR(32),
  USERID VARCHAR(32),
  USERNAME VARCHAR(32) DEFAULT NULL,
  IP VARCHAR(32) DEFAULT NULL,
  LOGIN_TIME TIMESTAMP  ,
  LOGIN_CLIENT VARCHAR(256),
  CREATEID VARCHAR(32),
  CREATETIME TIMESTAMP  ,
  UPDATEID VARCHAR(32) , 
  UPDATETIME TIMESTAMP,
  DELETED VARCHAR(1) DEFAULT NULL,
  VERSION INT, 
  PRIMARY KEY (ID)
) ;

--  --------------PARAM_CODE----------------------------
CREATE TABLE PARAM_CODE (
  ID VARCHAR(32),
  TYPEID VARCHAR(32) DEFAULT NULL,
  CODE VARCHAR(32) DEFAULT NULL,
  NAME VARCHAR(128) DEFAULT NULL,
  DESCRIPTION VARCHAR(512) DEFAULT NULL,
  CREATEID VARCHAR(32),
  CREATETIME TIMESTAMP  ,
  UPDATEID VARCHAR(32) , 
  UPDATETIME TIMESTAMP,
  DELETED VARCHAR(1) DEFAULT NULL,
  VERSION INT, 
  PRIMARY KEY (ID)
);
--  --------------PARAM_TYPE----------------------------
CREATE TABLE PARAM_TYPE (
  ID VARCHAR(32),
  CODE VARCHAR(32) DEFAULT NULL,
  NAME VARCHAR(128) DEFAULT NULL,
  DESCRIPTION VARCHAR(512) DEFAULT NULL,
  CREATEID VARCHAR(32),
  CREATETIME TIMESTAMP  ,
  UPDATEID VARCHAR(32) , 
  UPDATETIME TIMESTAMP,
  DELETED VARCHAR(1) DEFAULT NULL,
  VERSION INT, 
  PRIMARY KEY (ID)
);
--  -----------------RESOURCES---------------------------
CREATE TABLE RESOURCES (
  ID VARCHAR(32),
  URL VARCHAR(128) DEFAULT NULL,
  DESCRIPTION VARCHAR(256) DEFAULT NULL,
  CREATEID VARCHAR(32),
  CREATETIME TIMESTAMP  ,
  UPDATEID VARCHAR(32) , 
  UPDATETIME TIMESTAMP,
  DELETED VARCHAR(1) DEFAULT NULL,
  VERSION INT, 
  PRIMARY KEY (ID)
) ;
--  -----------------------------------------------------
CREATE TABLE RESOURCES_EXTEND (
  ID VARCHAR(32),
  RESOURCEID VARCHAR(32) DEFAULT NULL,
  URL VARCHAR(128) DEFAULT NULL,
  ACTION VARCHAR(64) DEFAULT NULL,
  CACHED VARCHAR(1) DEFAULT 'N',
  DESCRIPTION VARCHAR(256) DEFAULT NULL,
  CREATEID VARCHAR(32),
  CREATETIME TIMESTAMP  ,
  UPDATEID VARCHAR(32) , 
  UPDATETIME TIMESTAMP,
  DELETED VARCHAR(1) DEFAULT NULL,
  VERSION INT, 
  PRIMARY KEY (ID)
);
--  ---------------------RESOURCES_ROLES-------------------------------
CREATE TABLE RESOURCES_ROLES (
  ID VARCHAR(32),
  RESOURCEID VARCHAR(32) DEFAULT NULL,
  ROLEID VARCHAR(32) DEFAULT NULL,
  DESCRIPTION VARCHAR(256) DEFAULT NULL,
  ENABLE VARCHAR(1) DEFAULT NULL,
  CREATEID VARCHAR(32),
  CREATETIME TIMESTAMP  ,
  UPDATEID VARCHAR(32) , 
  UPDATETIME TIMESTAMP,
  DELETED VARCHAR(1) DEFAULT NULL,
  VERSION INT, 
  PRIMARY KEY (ID)
) ;
--  ---------------------RESOURCES_GROUPS-------------------------------
CREATE TABLE RESOURCES_GROUPS (
  ID VARCHAR(32),
  RESOURCEID VARCHAR(32) DEFAULT NULL,
  GROUPID VARCHAR(32) DEFAULT NULL, 
  ENABLE VARCHAR(1) DEFAULT NULL,
  DESCRIPTION VARCHAR(256) DEFAULT NULL,
  CREATEID VARCHAR(32),
  CREATETIME TIMESTAMP  ,
  UPDATEID VARCHAR(32) , 
  UPDATETIME TIMESTAMP,
  DELETED VARCHAR(1) DEFAULT NULL,
  VERSION INT, 
  PRIMARY KEY (ID)
) ;