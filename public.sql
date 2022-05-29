/*
 Navicat Premium Data Transfer

 Source Server         : openguass
 Source Server Type    : PostgreSQL
 Source Server Version : 90204
 Source Host           : 124.70.139.117:26000
 Source Catalog        : car
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90204
 File Encoding         : 65001

 Date: 29/05/2022 22:36:52
*/


-- ----------------------------
-- Sequence structure for client_client_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."client_client_id_seq";
CREATE SEQUENCE "public"."client_client_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for material_mat_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."material_mat_id_seq";
CREATE SEQUENCE "public"."material_mat_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for project_pro_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."project_pro_id_seq";
CREATE SEQUENCE "public"."project_pro_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for repair_repair_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."repair_repair_id_seq";
CREATE SEQUENCE "public"."repair_repair_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for user_user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."user_user_id_seq";
CREATE SEQUENCE "public"."user_user_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for vehicle_vehicle_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."vehicle_vehicle_id_seq";
CREATE SEQUENCE "public"."vehicle_vehicle_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS "public"."client";
CREATE TABLE "public"."client" (
  "client_id" int4 NOT NULL DEFAULT nextval('client_client_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "nature" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "discount" float8 DEFAULT NULL,
  "contact" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "phone" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "client_id2" varchar(32) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "password" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL
)
;

-- ----------------------------
-- Records of client
-- ----------------------------
INSERT INTO "public"."client" VALUES (3, '沈腾', '个人', .6, '沈腾', '56781234', '100003', '123456');
INSERT INTO "public"."client" VALUES (15, '刘茂林', '个人', .9, '刘茂林', '15026659346', 'lml', '123');
INSERT INTO "public"."client" VALUES (16, '肖军', '个人', .9, '肖军', '15026659346', 'xj', '123');
INSERT INTO "public"."client" VALUES (9, '藤原拓江', '个人', .6, '藤原豆腐店', '02185767', '100004', '12345678');
INSERT INTO "public"."client" VALUES (2, '特斯拉', '单位', .7, '部门经理', '110', '100002', 'tesla');
INSERT INTO "public"."client" VALUES (17, '李想', '个人', NULL, '李想', '1562882737', 'lixiang', '123');
INSERT INTO "public"."client" VALUES (1, '皮皮', '个人', .9, '闪光皮皮', '19382761717', '100001', '123');
INSERT INTO "public"."client" VALUES (0, '0', '0', 0, '0', '0', '0', '0');
INSERT INTO "public"."client" VALUES (18, '田逸凡', '个人', .9, '田逸凡', '15026659346', 'tyf', '123');

-- ----------------------------
-- Table structure for detail
-- ----------------------------
DROP TABLE IF EXISTS "public"."detail";
CREATE TABLE "public"."detail" (
  "pro_id" int4 DEFAULT NULL,
  "mat_id" int4 DEFAULT NULL,
  "repair_id" int4 DEFAULT NULL,
  "number" int4 DEFAULT NULL,
  "repairman_id" int4 DEFAULT NULL
)
;

-- ----------------------------
-- Records of detail
-- ----------------------------
INSERT INTO "public"."detail" VALUES (1, 4, 13, 1, 8);
INSERT INTO "public"."detail" VALUES (4, 6, 13, 2, 8);
INSERT INTO "public"."detail" VALUES (1, 3, 13, 2, 8);

-- ----------------------------
-- Table structure for maintain
-- ----------------------------
DROP TABLE IF EXISTS "public"."maintain";
CREATE TABLE "public"."maintain" (
  "repair_id" int4 NOT NULL DEFAULT NULL,
  "repairman_id" int4 NOT NULL DEFAULT NULL,
  "pro_id" int4 NOT NULL DEFAULT NULL,
  "isfinished" varchar(255) COLLATE "pg_catalog"."default" NOT NULL DEFAULT '待确认'::character varying,
  "user_id" int4 NOT NULL DEFAULT NULL
)
;
COMMENT ON COLUMN "public"."maintain"."isfinished" IS '该字段为状态为，有：待确认，进行中及已完成';

-- ----------------------------
-- Records of maintain
-- ----------------------------
INSERT INTO "public"."maintain" VALUES (13, 8, 1, '已完成', 1);
INSERT INTO "public"."maintain" VALUES (13, 8, 4, '已完成', 1);

-- ----------------------------
-- Table structure for material
-- ----------------------------
DROP TABLE IF EXISTS "public"."material";
CREATE TABLE "public"."material" (
  "mat_id" int4 NOT NULL DEFAULT nextval('material_mat_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "cost" float8 DEFAULT NULL
)
;

-- ----------------------------
-- Records of material
-- ----------------------------
INSERT INTO "public"."material" VALUES (1, '机油', 10);
INSERT INTO "public"."material" VALUES (4, '空调氟利昂', 45);
INSERT INTO "public"."material" VALUES (6, '火花塞', 100);
INSERT INTO "public"."material" VALUES (8, '高级融合宝石(速度+3)', 29);
INSERT INTO "public"."material" VALUES (2, '变速箱油', 20);
INSERT INTO "public"."material" VALUES (3, '防冻液', 30);
INSERT INTO "public"."material" VALUES (5, '顶级油漆', 70);
INSERT INTO "public"."material" VALUES (7, '专用赛车方向盘', 30000);
INSERT INTO "public"."material" VALUES (9, '空调电路', 100);

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS "public"."project";
CREATE TABLE "public"."project" (
  "pro_id" int4 NOT NULL DEFAULT nextval('project_pro_id_seq'::regclass),
  "pname" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "hour" float4 DEFAULT '1'::character varying,
  "pjob" varchar(255) COLLATE "pg_catalog"."default" DEFAULT '奇才'::character varying
)
;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO "public"."project" VALUES (1, '更换空调氟利昂', 1, '机修');
INSERT INTO "public"."project" VALUES (6, '更换空调电路', 2, '电工');
INSERT INTO "public"."project" VALUES (5, '车辆属性升级', 10, '宝石融合技师');
INSERT INTO "public"."project" VALUES (4, '赛用方向盘组装', 4, '机修');
INSERT INTO "public"."project" VALUES (2, '加机油', 1, '机修');
INSERT INTO "public"."project" VALUES (7, '重新打磨车身', 8, '钣金');
INSERT INTO "public"."project" VALUES (3, '重新喷漆', 1, '油漆');

-- ----------------------------
-- Table structure for repair
-- ----------------------------
DROP TABLE IF EXISTS "public"."repair";
CREATE TABLE "public"."repair" (
  "repair_id" int4 NOT NULL DEFAULT nextval('repair_repair_id_seq'::regclass),
  "type" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "classification" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "payment" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "mile" float8 DEFAULT NULL,
  "fuel" float8 DEFAULT NULL,
  "approach_time" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "failure" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "ddl" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "cost" float8 DEFAULT 0,
  "user_id" int4 DEFAULT NULL,
  "vehicle_id" int4 DEFAULT NULL,
  "hour" float8 DEFAULT 0,
  "statu" varchar(255) COLLATE "pg_catalog"."default" DEFAULT '未接单'::character varying
)
;
COMMENT ON COLUMN "public"."repair"."statu" IS '一共有五个状态：未接单、等待派单、等待维修、进行中、已完成';

-- ----------------------------
-- Records of repair
-- ----------------------------
INSERT INTO "public"."repair" VALUES (10, '加急', '大修', '自付', 300, .92, '2022-07-08 11:12:13', '我升级失败了', NULL, 0, 1, 8, 60, '等待派单');
INSERT INTO "public"."repair" VALUES (3, '普通', '小修', '三包', 2000, 1, '2022-03-16 20:00', '添加空调氟利昂', NULL, 0, 1, 3, 0, '未接单');
INSERT INTO "public"."repair" VALUES (2, '加急', '大修', '进保', 90000, .5, '2022-01-02 13:00:00', '我要比赛，帮我改装方向盘', NULL, 0, 1, 4, 0, '未接单');
INSERT INTO "public"."repair" VALUES (11, '加急', '中修', '自付', 23, 2, '2022-05-29 21:49:21', '汽车漏水', '2022-05-31 00:00:00', 55, 1, 17, 5, '已完成');
INSERT INTO "public"."repair" VALUES (12, NULL, NULL, NULL, 1, .1, '2022-05-29 22:13:55', '1', NULL, NULL, NULL, 2, 0, '未接单');
INSERT INTO "public"."repair" VALUES (13, '普通', '小修', '索赔', 20, 25, '2022-05-29 22:18:27', '汽车外表磨损，水箱漏水', '2022-05-31 03:10:00', 305, 1, 1, 5, '已完成');
INSERT INTO "public"."repair" VALUES (14, '加急', '小修', '自付', 1, .1, '2022-05-29 22:32:06', '1', '2022-05-29 22:33:39', 0, 1, 1, 0, '等待派单');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS "public"."user";
CREATE TABLE "public"."user" (
  "user_id" int4 NOT NULL DEFAULT nextval('user_user_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "password" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "phone" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "is_repairman" int1(8) DEFAULT NULL,
  "job" varchar(255) COLLATE "pg_catalog"."default" DEFAULT '奇才'::character varying,
  "statu" varchar(255) COLLATE "pg_catalog"."default" NOT NULL DEFAULT '空闲中'::character varying,
  "realname" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL
)
;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO "public"."user" VALUES (3, '19120407', '310013', '12345678', '1', '电工', '空闲中', '田二凡');
INSERT INTO "public"."user" VALUES (9, '19120004', '310013', '12142', '1', '机修', '工作中', '肖君');
INSERT INTO "public"."user" VALUES (5, '19120189', '310013', '12138', '1', '宝石融合技师', '空闲中', '刘茂林');
INSERT INTO "public"."user" VALUES (0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO "public"."user" VALUES (8, '19120003', '310013', ' 12141', '1', '机修', '空闲中', '肖军');
INSERT INTO "public"."user" VALUES (4, 'xx', '310013', '166211', '0', '业务员', '空闲中', '刘毛毛2号');
INSERT INTO "public"."user" VALUES (2, '19120307', '310013', '112234513', '1', '焊工', '空闲中', '田一凡');
INSERT INTO "public"."user" VALUES (6, '19120001', '310013', '12139', '1', '钣金', '空闲中', '解家祥');
INSERT INTO "public"."user" VALUES (7, '19120002', '310013', '12140', '1', '油漆', '空闲中', '解家羊');
INSERT INTO "public"."user" VALUES (1, '19120207', '310013', '13761921059', '0', '业务员', '空闲中', '刘毛毛1号');

-- ----------------------------
-- Table structure for vehicle
-- ----------------------------
DROP TABLE IF EXISTS "public"."vehicle";
CREATE TABLE "public"."vehicle" (
  "vehicle_id" int4 NOT NULL DEFAULT nextval('vehicle_vehicle_id_seq'::regclass),
  "license" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "color" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "type" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "class" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "client_id" int4 DEFAULT NULL,
  "vin" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL
)
;
COMMENT ON TABLE "public"."vehicle" IS '车架号';

-- ----------------------------
-- Records of vehicle
-- ----------------------------
INSERT INTO "public"."vehicle" VALUES (0, '1', '1', '1', '1', 1, '1');
INSERT INTO "public"."vehicle" VALUES (1, '沪E88888', '红色', '中型车', '特斯拉', 18, '88881234');

-- ----------------------------
-- Function structure for changerepairmanstatu
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."changerepairmanstatu"();
CREATE OR REPLACE FUNCTION "public"."changerepairmanstatu"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
	DECLARE countResult char(20);
	begin
		countResult = NULL;
		SELECT statu INTO countResult from repair WHERE repair.repair_id = NEW.repair_id;
		if tg_op = 'INSERT' AND countResult = '等待派单'  THEN
			UPDATE repair SET repair.statu = '等待维修' WHERE repair.repair_id = NEW.repair_id;
		end if;
		return new;
	end; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for changerepairmanstatu2
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."changerepairmanstatu2"();
CREATE OR REPLACE FUNCTION "public"."changerepairmanstatu2"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
	DECLARE finished char(20);
	begin
		finished = NULL;
		SELECT statu INTO finished from "user" WHERE "user".user_id = NEW.repairman_id;
		if tg_op = 'INSERT' AND finished = '空闲中'  THEN
			UPDATE "user"	SET "user".statu = '工作中' WHERE "user".user_id = NEW.repairman_id;
		end if;
		return new;
	end; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for finishmaintain
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."finishmaintain"();
CREATE OR REPLACE FUNCTION "public"."finishmaintain"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
	DECLARE finished char(20);
	DECLARE phour float;
	DECLARE mcost float;
	DECLARE cnt1 numeric;
	DECLARE cnt2 numeric;
	DECLARE changed int;
	begin
		finished = NULL;
		phour = 0;
		mcost = 0;
		cnt1 = 0;
		cnt2 = 0;
		
		if tg_op = 'UPDATE' AND NEW.isfinished = '已完成' THEN
			SELECT sum((material.cost)*(detail.number)) INTO mcost FROM maintain,detail,material WHERE maintain.pro_id = detail.pro_id AND maintain.repair_id = detail.repair_id AND maintain.repairman_id = detail.repairman_id AND detail.mat_id = material.mat_id AND maintain.pro_id = OLD.pro_id AND maintain.repair_id = OLD.repair_id AND maintain.repairman_id = OLD.repairman_id;
			
		  UPDATE repair SET repair."cost" = repair."cost" + mcost WHERE repair_id = OLD.repair_id;
		 
		  SELECT "hour" INTO phour FROM maintain,project WHERE maintain.pro_id = project.pro_id AND maintain.pro_id = OLD.pro_id AND maintain.repair_id = OLD.repair_id AND maintain.repairman_id = OLD.repairman_id;
		  UPDATE repair SET repair."hour" = repair."hour" + phour WHERE repair_id = OLD.repair_id;	
		end if;
		
		SELECT COUNT(*) INTO cnt1 FROM maintain WHERE maintain.repair_id = OLD.repair_id AND isfinished != '已完成';
		IF cnt1 = 0 THEN
			UPDATE repair SET repair.statu = '已完成' WHERE repair.repair_id = OLD.repair_id;
		END IF;	
		
		IF NEW.isfinished = '已完成' THEN
			SELECT COUNT(*) INTO cnt2 FROM maintain WHERE maintain.repairman_id = OLD.repairman_id AND isfinished != '已完成';
			IF cnt1 = 0 THEN
				UPDATE "user" SET "user".statu = '空闲中' WHERE "user".user_id = OLD.repairman_id;
			END IF;	
		END IF;	
		
		return new;
	end; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."client_client_id_seq"
OWNED BY "public"."client"."client_id";
SELECT setval('"public"."client_client_id_seq"', 13, true);
ALTER SEQUENCE "public"."material_mat_id_seq"
OWNED BY "public"."material"."mat_id";
SELECT setval('"public"."material_mat_id_seq"', 12, true);
ALTER SEQUENCE "public"."project_pro_id_seq"
OWNED BY "public"."project"."pro_id";
SELECT setval('"public"."project_pro_id_seq"', 3, true);
ALTER SEQUENCE "public"."repair_repair_id_seq"
OWNED BY "public"."repair"."repair_id";
SELECT setval('"public"."repair_repair_id_seq"', 3, true);
ALTER SEQUENCE "public"."user_user_id_seq"
OWNED BY "public"."user"."user_id";
SELECT setval('"public"."user_user_id_seq"', 2, false);
ALTER SEQUENCE "public"."vehicle_vehicle_id_seq"
OWNED BY "public"."vehicle"."vehicle_id";
SELECT setval('"public"."vehicle_vehicle_id_seq"', 2, false);

-- ----------------------------
-- Primary Key structure for table client
-- ----------------------------
ALTER TABLE "public"."client" ADD CONSTRAINT "client_pkey" PRIMARY KEY ("client_id");

-- ----------------------------
-- Triggers structure for table maintain
-- ----------------------------
CREATE TRIGGER "trg_repairman_finish" AFTER UPDATE ON "public"."maintain"
FOR EACH ROW
EXECUTE PROCEDURE "public"."finishmaintain"();
CREATE TRIGGER "trg_repairman_statu" AFTER INSERT ON "public"."maintain"
FOR EACH ROW
EXECUTE PROCEDURE "public"."changerepairmanstatu"();
CREATE TRIGGER "trg_repairman_statu2" AFTER INSERT ON "public"."maintain"
FOR EACH ROW
EXECUTE PROCEDURE "public"."changerepairmanstatu2"();

-- ----------------------------
-- Primary Key structure for table maintain
-- ----------------------------
ALTER TABLE "public"."maintain" ADD CONSTRAINT "maintain_pkey" PRIMARY KEY ("repair_id", "repairman_id", "pro_id");

-- ----------------------------
-- Primary Key structure for table material
-- ----------------------------
ALTER TABLE "public"."material" ADD CONSTRAINT "material_pkey" PRIMARY KEY ("mat_id");

-- ----------------------------
-- Primary Key structure for table project
-- ----------------------------
ALTER TABLE "public"."project" ADD CONSTRAINT "project_pkey" PRIMARY KEY ("pro_id");

-- ----------------------------
-- Primary Key structure for table repair
-- ----------------------------
ALTER TABLE "public"."repair" ADD CONSTRAINT "repair_pkey" PRIMARY KEY ("repair_id");

-- ----------------------------
-- Primary Key structure for table user
-- ----------------------------
ALTER TABLE "public"."user" ADD CONSTRAINT "usert_pkey" PRIMARY KEY ("user_id");

-- ----------------------------
-- Primary Key structure for table vehicle
-- ----------------------------
ALTER TABLE "public"."vehicle" ADD CONSTRAINT "vehicle_pkey" PRIMARY KEY ("vehicle_id");
