--  ---------------ROLES-------------------------------
insert into ROLES(ROLECODE,ROLENAME,DESCRIPTION,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('DEFAULT','默认角色','所有没有其他角色的用户自动并入此角色','YouAPP','3f87f4eeafd243c0806953c582f820c7','YouAPP',now(),now(),'N',1);
insert into ROLES(ROLECODE,ROLENAME,DESCRIPTION,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('ADMIN','管理员角色','此角色用户拥有最高权限','YouAPP','4af5304ffe244b18b5af975417360b17','YouAPP',now(),now(),'N',1);

--  -----------------GROUPS------------------------------
insert into GROUPS(GROUPCODE,GROUPNAME,DESCRIPTION,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('DEFAULT','默认组','不在其他组的用户自动并入此组','YouAPP','0e22b4c4fdc7471892a5ca07a1896099','YouAPP',now(),now(),'N',1);
insert into GROUPS(GROUPCODE,GROUPNAME,DESCRIPTION,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('ADMIN','管理员组','此组用户拥有最高权限','YouAPP','01cf51a38a604433876ee9fee824c5a8','YouAPP',now(),now(),'N',1);

--  ----------------ROLES_GROUPS-----------------------------------
insert into ROLES_GROUPS(ROLEID,GROUPID,DESCRIPTION,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('3f87f4eeafd243c0806953c582f820c7','0e22b4c4fdc7471892a5ca07a1896099','初始化','YouAPP','4fd08b9270314c72b61f3cc9d1b58150','YouAPP',now(),now(),'N',1);
insert into ROLES_GROUPS(ROLEID,GROUPID,DESCRIPTION,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('4af5304ffe244b18b5af975417360b17','01cf51a38a604433876ee9fee824c5a8','初始化','YouAPP','2290be6a29b14bb0952d896510bd9fcf','YouAPP',now(),now(),'N',1);

--  -----------------RESOURCES---------------------------
insert into RESOURCES(URL,DESCRIPTION,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('/tablemanager.tablemanageraction/toViewRecords','查看表记录','YouAPP','39bb07bf9b2546208f128cca32d4db8f','YouAPP',now(),now(),'N',1);
insert into RESOURCES(URL,DESCRIPTION,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('/login.loginaction/toViewAllUser','查看所有用户','YouAPP','a22875a2548248f89ac39627d861c149','YouAPP',now(),now(),'N',1);

--   ---------------------RESOURCES_ROLES-------------------------------
insert into RESOURCES_ROLES(RESOURCEID,ROLEID,ENABLE,DESCRIPTION,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('39bb07bf9b2546208f128cca32d4db8f','4af5304ffe244b18b5af975417360b17','Y','','YouAPP','287c3e816bbb44ecaa2707d8cde0dae6','YouAPP',now(),now(),'N',1);
insert into RESOURCES_ROLES(RESOURCEID,ROLEID,ENABLE,DESCRIPTION,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('a22875a2548248f89ac39627d861c149','4af5304ffe244b18b5af975417360b17','Y','','YouAPP','7d6b7e69085545778aed437ee3d4503c','YouAPP',now(),now(),'N',1);

--   ---------------------RESOURCES_GROUPS-------------------------------
insert into RESOURCES_GROUPS(RESOURCEID,GROUPID,DESCRIPTION,ENABLE,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('39bb07bf9b2546208f128cca32d4db8f','01cf51a38a604433876ee9fee824c5a8','','Y','YouAPP','6973cb084db7490da29345e3856accfa','YouAPP',now(),now(),'N',1);
insert into RESOURCES_GROUPS(RESOURCEID,GROUPID,DESCRIPTION,ENABLE,CREATEID,ID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('a22875a2548248f89ac39627d861c149','01cf51a38a604433876ee9fee824c5a8','','Y','YouAPP','7496ec547e09458fbfd6f5803cb5755e','YouAPP',now(),now(),'N',1);
--  ---------------------RESOURCES_EXTEND-------------------------------
insert into RESOURCES_EXTEND(RESOURCEID,URL,ACTION,CACHED,DESCRIPTION,ID,CREATEID,UPDATEID,CREATETIME,UPDATETIME,DELETED,VERSION)values('a22875a2548248f89ac39627d861c149','/login.loginaction/toViewAllUser','','Y','','174675a0c6e746cfacd4a583015ce91d','YouAPP','YouAPP',now(),now(),'N',1);

